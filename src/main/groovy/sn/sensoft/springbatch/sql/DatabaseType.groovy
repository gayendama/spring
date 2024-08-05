package sn.sensoft.springbatch.sql

/**
 * Created by thiam on 15/02/2017.
 */
enum DatabaseType {

    MYSQL("MYSQL", "MYSQL Database"),
    ORACLE("ORACLE", "ORACLE Database"),
    MSSQL("MSSQL", "Microsoft Sql Server Database"),
    POSTGRESQL("POSTGRESQL", "PostgreSQL Server Database"),
    H2("H2", "H2 Database"),
    UNKNOWN("UNKNOWN", "UNKNOWN Database")

    String code
    String name
    DatabaseType(String code, String name) {
        this.code = code
        this.name = name
    }

    static DatabaseType getDefault() {
        return MYSQL
    }


    @Override
    String toString() {
        return code
    }
}