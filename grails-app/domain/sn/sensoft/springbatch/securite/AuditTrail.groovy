/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
*/
package sn.sensoft.springbatch.securite

import javax.persistence.Version

/**
 * AuditTrails are reported to the AuditLog table.
 * This requires you to set up a table or allow
 * Grails to create a table for you. (e.g. DDL or db-migration plugin)
 */
class AuditTrail implements Serializable {
    private static final long serialVersionUID = 1L

    String id = UUID.randomUUID().toString()
    @Version
    Long version

    static auditable = false

    Date dateCreated
    Date lastUpdated

    String actor
    String uri
    String className
    String persistedObjectId
    Long persistedObjectVersion = 0

    String eventName
    String propertyName
    String oldValue
    String newValue

    static constraints = {
        actor(nullable: true)
        uri(nullable: true)
        className(nullable: true)
        persistedObjectId(nullable: true)
        persistedObjectVersion(nullable: true)
        eventName(nullable: true)
        propertyName(nullable: true)

        oldValue(nullable: true)
        newValue(nullable: true)

    }

    static mapping = {

        // Set similiar when you used "auditLog.tablename" in < 1.1.0 plugin version.
        table 'audit_log'

        // Remove when you used "auditLog.cacheDisabled = true" in < 1.1.0 plugin version.
        cache usage: 'read-only', include: 'non-lazy'

        // Set similiar when you used "auditLog.useDatasource" in < 1.1.0 plugin version.
        // datasource "yourdatasource"

        // Set similiar when you used "auditLog.idMapping" in < 1.1.0 plugin version. Example:
        // id generator:"uuid2", type:"string", "length:36"

        // no HQL queries package name import (was default in 1.x version)
        //autoImport false

        version false

        // for large column support (as in < 1.0.6 plugin versions), use this
        // oldValue type: 'text'
        // newValue type: 'text'


        id generator: 'assigned'

    }


    String toString() {
        String actorStr = actor ? "user ${actor}" : "user ?"
        "audit log ${dateCreated} ${actorStr} " +
                "${eventName} ${className} " +
                "id:${persistedObjectId} version:${persistedObjectVersion}"
    }
}
