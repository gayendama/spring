package sn.sensoft.springbatch.sql

import groovy.sql.GroovyResultSet
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import oracle.jdbc.OracleTypes

import javax.sql.DataSource
import java.sql.Connection

/**
 * @author thiam
 * @since 15/02/2017
 */
class OracleExecutor extends SqlExecutor{


    OracleExecutor(Sql sql) {
        super(sql)
    }

    OracleExecutor(DataSource dataSource) {
        super(dataSource)
    }

    OracleExecutor(Connection connection) {
        super(connection)
    }

    @Override
    Collection executeQuery(String query) {
        return null
    }

    @Override
    List<GroovyRowResult> callStoredProcedure(String name, List<Object> params, Boolean rows) {
        List<GroovyRowResult> res = []
        def parameters = []
        parameters.addAll(params)
        if (rows) {
            parameters.add(Sql.resultSet(OracleTypes.CURSOR))
            sql.call("BEGIN ${name}(${generateQuestionMarks(params?.size() + 1)}); END;",parameters) { cursorResults ->
                GroovyResultSet
                cursorResults.eachRow { callableResultSet->
                    res.add((GroovyRowResult)callableResultSet)
                }

            }
        }
        else {
            callStoredProcedure(name, params) {

            }
        }
        return res
    }

    @Override
    void callStoredProcedure(String name, List<Object> params, Closure closure) {
        def parameters = []
        parameters.addAll(params)
        parameters.add(Sql.resultSet(OracleTypes.CURSOR))
        sql.call("BEGIN ${name}(${generateQuestionMarks(params?.size() + 1)}); END;", parameters, closure)
    }
}
