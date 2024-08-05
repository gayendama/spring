grails.gorm.default.mapping = {
    autowire true
}

/*
// Added by the Audit-Logging plugin:
grails {
    plugin {
        auditLog {
            auditDomainClassName = 'sn.sensoft.aicha.securite.AuditTrail'
            logFullClassName = false
            verbose = true
            failOnError = false   // Pas besoin de bloquer la transaction originale ABB
            excluded = ['version', 'lastUpdated', 'lastUpdatedBy']
            mask = ['password']
            logIds = true
            stampEnabled = true
            usePersistentDirtyPropertyValues=false
        }
    }
}

 */

