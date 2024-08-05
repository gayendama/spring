package sn.sensoft.springbatch.sql

import groovy.sql.GroovyRowResult
import groovy.sql.Sql

import javax.sql.DataSource
import java.sql.Connection

/**
 * @author thiam
 * @since 29/03/2017
 */
class MssqlExecutor extends SqlExecutor {
    MssqlExecutor(Sql sql) {
        super(sql)
    }

    MssqlExecutor(DataSource dataSource) {
        super(dataSource)
    }

    MssqlExecutor(Connection connection) {
        super(connection)
    }

    @Override
    Collection executeQuery(String query) {
        return null
    }

    @Override
    List<GroovyRowResult> callStoredProcedure(String name, List<Object> params, Boolean rows) {
        List<GroovyRowResult> res = []
        if (rows) {
            res = sql.rows("EXECUTE ${name} ${generateQuestionMarks(params?.size())}", params)
        }
        else {
            callStoredProcedure(name, params) {

            }

        }
        return res
    }

    @Override
    void callStoredProcedure(String name, List<Object> params, Closure closure) {
        sql.call("EXECUTE ${name} ${generateQuestionMarks(params?.size())}", params, closure)
    }
}
