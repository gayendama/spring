package sn.sensoft.springbatch.sql

import groovy.sql.Sql

import java.sql.SQLException

/**
 * Created by abadiane on 23/05/2017.
 */
class ConnexionParamDTO {

    ConnexionParamDTO(String driverClassName,String url,String user, String password, DatabaseType databaseType){
        this.driverClassName=driverClassName
        this.url=url
        this.user=user
        this.password=password
        this.databaseType=databaseType
    }

    String driverClassName
    String url
    String user
    String password
    DatabaseType databaseType
    private Sql _sql
    private SqlExecutor _executor

    Sql getSql() throws SQLException, ClassNotFoundException {
        if (!_sql) {
            reset()
            _sql = Sql.newInstance(url, user, password, driverClassName)
            if (_sql) {
                switch (databaseType) {
                    case DatabaseType.MYSQL:
                        _executor = new MysqlExecutor(_sql)
                        break
                    case DatabaseType.ORACLE:
                        _executor = new OracleExecutor(_sql)
                        break
                    case DatabaseType.MSSQL:
                        _executor = new MssqlExecutor(_sql)
                        break
                    case DatabaseType.POSTGRESQL:
                        _executor = new PostgresqlExecutor(_sql)
                        break
                    default:
                        _executor = null
                }
            }
        }
        return _sql
    }

    SqlExecutor getExecutor() {
        if (!_executor) {
            getSql()
        }
        return _executor
    }

    void reset() {
        if (_sql) {
            _sql.close()
        }
        _sql = null
        _executor = null
    }
}
