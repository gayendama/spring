<g:set var="entityName" value="${message(code: 'propertyName.label', default: 'Profil')}"/>

<h3 class="form-section font-blue-madison bold"><g:message code="default.info.label" args="[entityName]"/></h3>

<div class="row">
    <div class="col-md-12">
        <div class="form-group">
            <label class="control-label col-md-2"
                   for="authority"><g:message
                    code="role.authority.label"
                    default="Libellé"/>
                <span class="required">*</span>
            </label>

            <div class="col-md-10">
                <input type="text"
                       name="authority"
                       required="required"
                       pattern="ROLE_[A-Za-z0-9_]+"
                       class="form-control"
                       placeholder="le rôle doit commercer par : ROLE_"
                       title="Le rôle doit commercer par : ROLE_"
                       value="${fieldValue(bean: roleInstance, field: 'authority')}"/>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="form-group">
            <label class="control-label col-md-2"
                   for="description"><g:message
                    code="role.description.label"
                    default="Description"/></label>

            <div class="col-md-10">
                <textarea
                        name="description"
                        class="form-control">${fieldValue(bean: roleInstance, field: 'description')}</textarea>
            </div>
        </div>
    </div>
</div>