
<div class="tabbable" id="tabbable">
    <ul class="nav nav-tabs nav-justified"
        id="myTab">
        <li class="active">
            <a href="#logActivity" data-toggle="tab">
                <i class="fa fa-th-list margin-right-5"></i>
                <g:message code="activity.log.label"
                           default="Activités log"/>
            </a>
        </li>

        <li class="tab-red">
            <a href="#audit" data-toggle="tab">
                <i class="fa fa-clipboard margin-right-5"></i>
                <g:message code="activity.audit.log"
                           default="Audit"/>
            </a>
        </li>
    </ul>

    <div class="tab-content">
        <div class="tab-pane active" id="logActivity">
            <table class="table table-striped table-bordered table-hover dt-responsive"
                   id="sample_4">
                <thead>
                <tr>
                    <th class="all noSort width5"></th>
                    <th class="all"><g:message code="logActivity.codeBureau.label"
                                               default="Code bureau"/></th>
                    <th><g:message code="logActivity.dateCreated.label"
                                   default="Date"/></th>
                    <th><g:message code="logActivity.message.label"
                                   default="Message"/></th>
                    <th><g:message code="logActivity.user.label"
                                   default="Acteur"/></th>
                    <th><g:message code="logActivity.ipAddressUser.label"
                                   default="Adresse IP"/></th>
                    <th><g:message code="logActivity.typeLog.label"
                                   default="Type log"/></th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${logActivityList}"
                        var="historyEntity">
                    <tr>
                        <th></th>
                        <td>${fieldValue(bean: historyEntity, field: 'codeBureau')}</td>
                        <td>${historyEntity.dateCreated?.format("dd/MM/yyyy - HH:mm:ss.SSS")}</td>
                        <td>${fieldValue(bean: historyEntity, field: 'message')}</td>
                        <td>${fieldValue(bean: historyEntity, field: 'userCreate')}</td>
                        <td>${fieldValue(bean: historyEntity, field: 'ipAddressUser')}</td>
                        <td>${fieldValue(bean: historyEntity, field: 'typeLog')}</td>

                    </tr>
                </g:each>

                </tbody>
            </table>
        </div>

        <div class="tab-pane fade" id="audit">
            <table class="table table-striped table-bordered table-hover dt-responsive"
                   id="sample_3">
                <thead>
                <tr>
                    <th class="all noSort width5"></th>
                    <th class="all"><g:message code="auditTrail.user.label"
                                               default="Acteur"/></th>
                    <th><g:message code="auditTrail.dateCreated.label"
                                   default="Date"/></th>
                    <th><g:message code="auditTrail.propertyName.label"
                                   default="Champ"/></th>
                    <th><g:message code="auditTrail.oldValue.label"
                                   default="Ancienne valeur"/></th>
                    <th><g:message code="auditTrail.newValue.label"
                                   default="Nouvelle valeur"/></th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${auditTraitList}"
                        var="historyEntity">
                    <tr>
                        <th></th>
                        <td>${fieldValue(bean: historyEntity, field: 'actor')}</td>
                        <td>${historyEntity.dateCreated?.format("dd/MM/yyyy - HH:mm:ss.SSS")}</td>
                        <td>${fieldValue(bean: historyEntity, field: 'propertyName')}</td>
                        <td>${fieldValue(bean: historyEntity, field: 'oldValue')}</td>
                        <td>${fieldValue(bean: historyEntity, field: 'newValue')}</td>

                    </tr>
                </g:each>
                </tbody>
            </table>

        </div>

    </div>

</div>







