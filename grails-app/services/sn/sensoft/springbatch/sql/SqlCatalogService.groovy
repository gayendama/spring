package sn.sensoft.springbatch.sql

import grails.gorm.transactions.Transactional
import groovy.json.JsonOutput
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import sn.sensoft.springbatch.exception.SpringbatchException

import javax.sql.DataSource
import java.sql.SQLException

@Transactional
class SqlCatalogService {

    private static final Object lock = new Object()
    private static Map<String, String> sqlCache = [:]
    private static DatabaseType databaseType
    static SqlExecutor sqlExecutor
    static DataSource dataSourceIn
    private static Sql sqlIn

    static int BATCH_SIZE = 500

    // Set the database type and the sqlExecutor from the Datasource
    void setDatabaseType() {
        def sql = new Sql(dataSourceIn)
        def driverConnexion = sql.dataSource.getConnection().getMetaData().getDriverName().toString()

        switch (driverConnexion) {
            case ["MySQL Connector Java", "MySQL-AB JDBC Driver"]:
                databaseType = DatabaseType.MYSQL
                sqlExecutor = new MysqlExecutor(dataSourceIn)
                break
            case "Oracle JDBC driver":
                databaseType = DatabaseType.ORACLE
                sqlExecutor = new OracleExecutor(dataSourceIn)
                break
            case "PostgreSQL JDBC Driver":
                databaseType = DatabaseType.POSTGRESQL
                sqlExecutor = new PostgresqlExecutor(dataSourceIn)
                break
            case "PostgreSQL Native Driver":
                databaseType = DatabaseType.POSTGRESQL
                sqlExecutor = new PostgresqlExecutor(dataSourceIn)
                break
            case "H2 JDBC Driver":
                databaseType = DatabaseType.H2
                sqlExecutor = new H2Executor(dataSourceIn)
                break
            default:
                if (driverConnexion.contains("Microsoft MSSQL Server JDBC Driver")
                        || driverConnexion.contains("Microsoft SQL Server JDBC Driver")) {
                    databaseType = DatabaseType.MSSQL
                    sqlExecutor = new MssqlExecutor(dataSourceIn)
                }
                else {
                    log.error(JsonOutput.toJson([status: 'error', method: 'setDatabaseType', message: "Driver name unknown : ${driverConnexion}"]))
                    databaseType = DatabaseType.UNKNOWN
                    sqlExecutor = null
                }
        }

        log.debug("driverConnexion: ${driverConnexion} ---> databaseType : ${databaseType}")
    }

    void initServiceFromDataSource(DataSource dataSourceP) {
        if (dataSourceP != null && dataSourceP != dataSourceIn) {
            dataSourceIn = dataSourceP
            setDatabaseType()
        }
    }

    void initServiceFromJdbcParam(ConnexionParamDTO connexionParam) {
        if (connexionParam != null) {
            sqlIn = getSqlInstance(connexionParam)
            dataSourceIn = null   // Seul un des deux est à utiliser

            // Pour pouvoir executer les procédures stockées baséees sur le type de base
            setDatabaseType(connexionParam, sqlIn)
        }
    }

    void loadSqlCacheFromDirectory(File directory) {
        log.debug(JsonOutput.toJson([method: "loadSqlCacheFromDirectory", message: "Loading SQL cache from disk using base directory ${directory.name}"]))
        synchronized (lock) {
            if (sqlCache.size() == 0) {
                try {
                    directory.eachFileRecurse { sqlFile ->
                        if (sqlFile.isFile() && sqlFile.name.toUpperCase().startsWith(databaseType.code + "_") && sqlFile.name.toUpperCase().endsWith(".SQL")) {
                            def sqlKey = sqlFile.name.toUpperCase()[0..-5] //Enlever le .sql à la fin dans la clé
                            //sqlCache[sqlKey] = sqlFile.text
                            loadSqlCache(sqlKey, sqlFile.text)
                        }
                    }
                }
                catch (Exception ex) {
                    log.error(ex)
                }
            }
            else {
                log.warn(JsonOutput.toJson([method: "loadSqlCacheFromDirectory", message: "request to load sql cache and cache not empty: size [${sqlCache.size()}]"]))
            }
        }
        log.debug(JsonOutput.toJson([method: "loadSqlCacheFromDirectory", message: "End loading SQL cache from disk using base directory ${directory.name}"]))
    }

    void loadSqlCache() {
        try {
            setDatabaseType()
            loadSqlCacheFromDirectory(new File(this.class.getResource("/sql/").getFile()))
            loadSqlCacheFromDatabase()
        }
        catch (Exception ex) {
            log.error(JsonOutput.toJson([method: "loadSqlCache", status: "error", message: ex.message]), ex)
        }
    }

    boolean loadSqlCache(String sqlKey, String sqlText) {
        synchronized (lock) {
            if (sqlKey?.trim() && sqlText?.trim()) {
                sqlCache[sqlKey] = sqlText
                log.debug "added SQL [${sqlKey}] to cache"
                return true

            }
            else {
                log.error(JsonOutput.toJson([method: "loadSqlCache", sqlKey: sqlKey, sqlText: sqlText, message: "loadSqlCache: key or text invalid!!"]))
                return false
            }
        }
    }

    void loadSqlCacheFromDatabase() {
        log.debug(JsonOutput.toJson([method: "loadSqlCacheFromDatabase", message: "Loading SQL cache from database table sql_request"]))
        try {
            SqlRequest.findAllWhere(deleted: false).each { sqlRequest ->
                def sqlKey = sqlRequest.databaseType + "_" + sqlRequest.code.toUpperCase()
                loadSqlCache(sqlKey, sqlRequest.requestText)
            }
        }
        catch (Exception ex) {
            log.error(JsonOutput.toJson([method: "loadSqlCacheFromDatabase", status: "error", message: ex.message]), ex)
        }
        log.debug(JsonOutput.toJson([method: "loadSqlCacheFromDatabase", message: "End loading SQL cache from database table  sql_request"]))
    }

    void loadSqlCacheFromDatabase(String sqlRequestCode) throws SpringbatchException {
        log.debug(JsonOutput.toJson([method: "loadSqlCacheFromDatabase", sqlRequestCode: sqlRequestCode, message: "Loading SQL cache from database table sql_request"]))
        synchronized (lock) {
            def sqlRequest = SqlRequest.findByCodeAndDeleted(sqlRequestCode, false)
            if (!sqlRequest) {
                throw new SpringbatchException("La requête ${sqlRequestCode} n'existe pas dans la base de données")
            }
            def sqlKey = sqlRequest.databaseType + "_" + sqlRequest.code.toUpperCase()
            loadSqlCache(sqlKey, sqlRequest.requestText)
        }
        log.debug(JsonOutput.toJson([method: "loadSqlCacheFromDatabase", message: "End loading SQL cache from database table  sql_request"]))
    }

    // Set the database type and the sqlExecutor from the connexion param
    void setDatabaseType(ConnexionParamDTO connexionParam, Sql sql) {
        if (!connexionParam || !sql) {
            databaseType = DatabaseType.UNKNOWN
            sqlExecutor = null
        }
        else {
            databaseType = connexionParam.databaseType
            switch (databaseType) {
                case DatabaseType.MSSQL:
                    sqlExecutor = new MssqlExecutor(sql)
                    break
                case DatabaseType.MYSQL:
                    sqlExecutor = new MysqlExecutor(sql)
                    break
                case DatabaseType.POSTGRESQL:
                    sqlExecutor = new PostgresqlExecutor(sql)
                    break
                case DatabaseType.H2:
                    sqlExecutor = new H2Executor(sql)
                    break
                case DatabaseType.ORACLE:
                    sqlExecutor = new OracleExecutor(sql)
                    break
            }
        }
    }


    String getSql(String sqlId) {
        def sqlKey = (databaseType.code + "_" + sqlId)?.toUpperCase()
        log.debug "SQL Id requested: ${sqlKey}"
        if (!sqlCache[sqlKey]) {
            log.error(JsonOutput.toJson([method: "getSql", status: "error", message: "SQL [${sqlKey}] not found in cache, loading cache from disk"]))
            loadSqlCache()
        }
        return sqlCache[sqlKey]
    }

    /**
     *
     * @param sqlId
     * @return le commentaire rattaché à la requete
     */
    String getSqlComment(String requeteAndComment) {
        def comment = ""

        // Extraire le commentaire lié  à la requête s'il y'en a (séparateur @@)
        def commentAndRequest = requeteAndComment.split("@@")
        log.debug("${commentAndRequest[0]}")
        log.debug("${commentAndRequest[1]}")

        if (2 == commentAndRequest.size() && commentAndRequest[1].trim()) {
            comment = commentAndRequest[0].trim()
        }

        return comment
    }


    def executeScript(String codeRequeteSql, Map sqlParams) {

        Sql sql
        String requeteToExecute
        def commentAndRequest
        int resultat
        String logResultat = ""
        String jsonResult
        def data

        log.debug(JsonOutput.toJson([method: "executeScript", codeRequeteSql: codeRequeteSql, sqlParams: sqlParams]))

        try {
            if (!codeRequeteSql?.trim()) {
                throw new IllegalArgumentException("Le code de la requête à exécuter est invalide")
            }
            String script = getSql(codeRequeteSql)
            String[] requetesScript = script?.split(";")

            // Utiliser la connexion SQL en paramètre en priorité si elle a été initialisée
            if (sqlIn) {
                sql = sqlIn
            }
            else if (dataSourceIn) {
                sql = new Sql(dataSourceIn)
            }
            else {
                throw new IllegalStateException("Service sql not correctly initialized or unknown")
            }

            requetesScript.each { requete ->
                if (requete?.trim()) {
                    // Pour isoler la requete du commentaire, besoin de //log
                    requeteToExecute = requete?.trim()
                    commentAndRequest = requete.split("@@")
                    if (2 == commentAndRequest.size()) {
                        log.debug(JsonOutput.toJson([status: 'info', method: 'executeScript', message: "Request comment >> ${commentAndRequest[0]?.trim()}"]))
                        requeteToExecute = commentAndRequest[1]?.trim()
                    }

                    log.debug(JsonOutput.toJson([status: 'info', method: 'executeScript', message: "Request >>> $requeteToExecute"]))

                    if (!requeteToExecute) {
                        throw new IllegalStateException("La requête SQL n'est pas bien formatée")
                    }
                    // bug https://issues.apache.org/jira/browse/GROOVY-8082
                    if (sqlParams == null || sqlParams.isEmpty()) {
                        resultat = sql.executeUpdate(requeteToExecute)
                    }
                    else {
//                        Boolean bool = requeteToExecute.contains(":")
                        if(requeteToExecute.contains(":")){
                            log.debug(JsonOutput.toJson([method:"executeScript", message:"Requete avec params"]))
                            resultat = sql.executeUpdate(requeteToExecute, sqlParams)
                        }
                        else{
                            log.debug(JsonOutput.toJson([method:"executeScript", message:"Requete sans params"]))
                            resultat = sql.executeUpdate(requeteToExecute)

                        }
                    }


                    log.debug("Rows updated : ${resultat}")
                    logResultat += resultat + "\n"
                }
            }

            data = [
                    success: true,
                    data   : logResultat
            ]

            log.debug(JsonOutput.toJson([method: "executeScript", status: "success", codeRequeteSql: codeRequeteSql]))

            jsonResult = JsonOutput.toJson(data)
            log.debug jsonResult

            return jsonResult

        }
        catch (IllegalArgumentException | IllegalStateException | SQLException ex) {
            log.error(JsonOutput.toJson([method: "executeScript", codeRequeteSql: codeRequeteSql, status: "error", message: ex.message]), ex)
            throw new SpringbatchException("Error executing ${codeRequeteSql}, error=${ex.message} ")
        }
    }

    String callStoredProcedure(String procName, List<Object> params, Boolean rows) throws IllegalStateException {
        log.debug(JsonOutput.toJson([method: "callStoredProcedure", procedureName: procName, databaseType: databaseType, rows: rows]))
        def data
        def jsonResult

        try {
            if (!sqlExecutor || !sqlExecutor.getSql()) {
                throw new IllegalStateException("Service datasource not correctly initialized or unknown. Make call to 'initServiceFromDataSource' or 'initServiceFromJdbc'  method before executing.")
            }
            Collection resultat = sqlExecutor.callStoredProcedure(procName, params ?: [], rows)

            data = [
                    success: true,
                    data   : resultat
            ]

            jsonResult = JsonOutput.toJson(data)
            log.debug jsonResult

            return jsonResult
        }
        catch (IllegalStateException | SQLException ex) {
            log.error(JsonOutput.toJson([method: "callStoredProcedure", procName:procName, status: "error", message: ex.message]), ex)
            throw new SpringbatchException("Error executing ${procName}, error=${ex.message} ")
       }
    }

    /**
     *
     * Pour éxécuter une requête unitaire (SELECT, UPDATE, DELETE, INSERTE, CREATE, DROP etc.)
     * Renvoie le résultat sous forme d'objet JSON contenant le résutat ainsi que la liste des objets retournés
     *
     */
    def executeRequete(String typeRequete, String requeteSql, Map sqlParams) {
        log.debug("requestType=${typeRequete}, requeteSql = ${requeteSql} , Paramètres sql : ${sqlParams}")

        RequestType requestType = RequestType.getByCode(typeRequete)
        if (!requestType) {
            throw new IllegalArgumentException("Type requête [${requestType}] invalide")
        }

        Sql sql
        String requeteToExecute = ""
        def resultat
        Map data
        String jsonResult
        boolean requeteSqlWithParam = false

        try {

            if (!requeteSql?.trim()) {
                throw new IllegalArgumentException("La requête à exécuter est vide")
            }
            // Utiliser la connexion SQL en paramètre en priorité si elle a été initialisée
            if (sqlIn) {
                sql = sqlIn
            }
            else if (dataSourceIn) {
                sql = new Sql(dataSourceIn)
            }
            else {
                throw new IllegalStateException("Service sql not correctly initialized or unknown")
            }

            // La requete à exécuter doit exister dans le cache de requêtes à l'exception des procédures stockées
            if (!(requestType in [RequestType.EXECUTESPROW, RequestType.EXECUTESP])) {
                requeteToExecute = getSql(requeteSql)
                if (!requeteToExecute?.trim()) {
                    throw new SpringbatchException("La requête ${requeteSql} n'existe pas dans le cache de requêtes SQL")
                }
                //requeteSqlWithParam = requeteToExecute.contains(':')
                // bug https://issues.apache.org/jira/browse/GROOVY-8082
                if (sqlParams.isEmpty()) {
                    requeteSqlWithParam = false
                }
                else {
                    requeteSqlWithParam = true
                }

            }

            switch (requestType) {
                case RequestType.SELECT:
                    resultat = requeteSqlWithParam ? sql.rows(requeteToExecute, sqlParams) : sql.rows(requeteToExecute)
                    break
                case [RequestType.INSERT, RequestType.UPDATE, RequestType.DELETE, RequestType.CREATE, RequestType.DROP, RequestType.ALTER]:
                    resultat = requeteSqlWithParam ? sql.executeUpdate(requeteToExecute, sqlParams) : sql.executeUpdate(requeteToExecute, [])
                    break;
                case RequestType.EXECUTESP:
                    List<Object> listParams = new ArrayList<Object>(sqlParams.values())
                    resultat = callStoredProcedure(requeteSql, listParams, new Boolean(false))
                    break
                case RequestType.EXECUTESPROW:
                    List<Object> listParams = new ArrayList<Object>(sqlParams.values())
                    resultat = callStoredProcedure(requeteSql, listParams, new Boolean(true))
                    break
                default:
                    throw new SpringbatchException("La requête sql [${requeteSql}] est invalide! ")
            }

            // Retourner les données
            if (requestType in [RequestType.EXECUTESPROW, RequestType.SELECT]) {
                resultat = (List<GroovyRowResult>) resultat
                data = [
                        success: true,
                        count  : resultat.size(),
                        data   : resultat
                ]
            }
            else if (RequestType.EXECUTESP == requestType) {
                data = [
                        success: true,
                        data   : resultat
                ]
            }
            else {
                data = [
                        success: true,
                        count  : resultat
                ]
            }

            jsonResult = JsonOutput.toJson(data)
            log.debug("Resultat ===== ${jsonResult}")

            return jsonResult
        }
        catch (IllegalStateException | IllegalArgumentException | SQLException | SpringbatchException ex) {
            log.error(JsonOutput.toJson([method: "executeRequete", requeteSql: requeteSql, status: "error", message: ex.message]), ex)
            throw new SpringbatchException("Error executing request, error=${ex.message} ")
        }
    }

    /**
     *
     * Pour éxécuter une requête d'insertion en mode batch
     * Renvoie le résultat sous forme d'objet JSON contenant le résutat
     *
     */

    def executeScriptBulkInsert(String codeRequeteSql, Map sqlParams) {

        Sql sql
        String requeteToExecute
        def commentAndRequest
        int[] resultat = null
        String logResultat = ""
        String jsonResult
        def data

        log.debug(JsonOutput.toJson([method: "executeScriptBulkInsert", codeRequeteSql: codeRequeteSql, sqlParams: sqlParams]))

        try {
            if (!codeRequeteSql?.trim()) {
                throw new IllegalArgumentException("Le code de la requête à exécuter est invalide")
            }
            String script = getSql(codeRequeteSql)
            String[] requetesScript = script?.split(";")

            // Utiliser la connexion SQL en paramètre en priorité si elle a été initialisée
            if (sqlIn) {
                sql = sqlIn
            }
            else if (dataSourceIn) {
                sql = new Sql(dataSourceIn)
            }
            else {
                throw new IllegalStateException("Service sql not correctly initialized or unknown")
            }

            requetesScript.each { requete ->
                if (requete?.trim()) {
                    // Pour isoler la requete du commentaire, besoin de //log
                    requeteToExecute = requete?.trim()
                    commentAndRequest = requete.split("@@")
                    if (2 == commentAndRequest.size()) {
                        log.debug(JsonOutput.toJson([status: 'debug', method: 'executeScriptBulkInsert', message: "Request comment >> ${commentAndRequest[0]?.trim()}"]))
                        requeteToExecute = commentAndRequest[1]?.trim()
                    }

                    log.info(JsonOutput.toJson([status: 'debug', method: 'executeScriptBulkInsert', message: "Request >>> $requeteToExecute"]))

                    if (!requeteToExecute) {
                        throw new IllegalStateException("La requête SQL n'est pas bien formatée")
                    }

                    resultat = sql.withBatch(BATCH_SIZE) { ps ->
                        ps.addBatch(requeteToExecute)
                    }

                    log.info("Rows updated : ${resultat}")
                    logResultat = logResultat + resultat + "\n"
                }
            }

            data = [
                    success: true,
                    data   : logResultat
            ]

            log.debug(JsonOutput.toJson([method: "executeScriptBulkInsert", status: "success", codeRequeteSql: codeRequeteSql]))

            jsonResult = JsonOutput.toJson(data)
            log.debug jsonResult

            return jsonResult

        }
        catch (IllegalArgumentException | IllegalStateException | SQLException ex) {
            log.error(JsonOutput.toJson([method: "executeScriptBulkInsert", codeRequeteSql: codeRequeteSql, status: "error", message: ex.message]), ex)
            throw new SpringbatchException("Error executing ${codeRequeteSql}, error=${ex.message} ")
        }
    }


    String bulkInsert(String requeteSql, List<Map> sqlParams) {

        Sql sql
        String requeteToExecute
        int[] resultat = null
        def data
        def jsonResult

        log.debug("requeteSql = ${requeteSql} , Paramètres sql : ${sqlParams}")

        try {

            // Utiliser la connexion SQL en paramètre en priorité si elle a été initialisée
            if (sqlIn) {
                sql = sqlIn
            }
            else if (dataSourceIn) {
                sql = new Sql(dataSourceIn)
            }
            else {
                throw new IllegalStateException("Service sql not correctly initialized or unknown")
            }

            // La requete à exécuter doit exister dans le cache de requêtes à l'exception des procédures stockées
            if (!requeteSql?.trim()) {
                throw new IllegalArgumentException("Le code de la requête à exécuter est invalide")
            }
            requeteToExecute = getSql(requeteSql)
            if (!requeteToExecute?.trim()) {
                throw new SpringbatchException("La requête ${requeteSql} n'existe pas dans le cache de requêtes SQL")
            }
            log.debug("requeteToExecute = ${requeteToExecute}")
            resultat = sql.withBatch(BATCH_SIZE, requeteToExecute) { ps ->
                sqlParams.each { row ->
                    ps.addBatch(row)
                }
            }

            data = [
                    success: true,
                    data   : resultat
            ]

            jsonResult = JsonOutput.toJson(data)
            log.debug jsonResult
        }
        catch (IllegalStateException | IllegalArgumentException | SQLException | SpringbatchException ex) {
            log.error(JsonOutput.toJson([method: "bulkInsert", status: "error", message: ex.message]), ex)
            throw new SpringbatchException("Error executing bulkInsert, error=${ex.message} ")
        }

        return jsonResult
    }


    def callStoredProcedure(String procName, Map params, Boolean rows) throws IllegalStateException {
        List<Object> paramsObj = new ArrayList<Object>(params.values())
        return callStoredProcedure(procName, paramsObj, rows)
    }

    static Sql getSqlIn() {
        return sqlIn
    }

    static DatabaseType getDatabaseType() {
        return databaseType
    }

    /**
     *
     * Get an sql connexion from connexion parameters
     * this also set the service databaseType and sqlExecutor accordingly
     *
     * @param connexionParam
     * @return sql
     * @throws IllegalArgumentException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    Sql getSqlInstance(ConnexionParamDTO connexionParam) throws IllegalArgumentException, SQLException, ClassNotFoundException {

        if (!connexionParam) {
            throw new IllegalArgumentException("driverClassName is null")
        }
        if (!connexionParam.driverClassName?.trim()) {
            throw new IllegalArgumentException("driverClassName is null")
        }
        if (!connexionParam.url?.trim()) {
            throw new IllegalArgumentException("url is null")
        }
        if (!connexionParam.user?.trim()) {
            throw new IllegalArgumentException("user is null")
        }
        if (!connexionParam.password) {
            throw new IllegalArgumentException("password is null")
        }

        Sql sql = Sql.newInstance(connexionParam.url, connexionParam.password, connexionParam.password, connexionParam.driverClassName)

        return sql

    }


}


