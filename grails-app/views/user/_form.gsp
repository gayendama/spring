<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
<h3 class="form-section"><g:message code="default.info.label" args="[entityName]"/></h3>

<div class="row">
    <div class="col-md-10">
        <div class="form-group">
            <label class="control-label col-md-4" for="nom"><g:message
                    code="user.username.label" default="Identifiant"/></label>

            <div class="col-md-8">
                <input type="text" name="username" id="username" disabled="disabled"
                       class="form-control" placeholder="" value="${fieldValue(bean: userInstance, field: 'username')}">
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-10">
        <div class="form-group">
            <label class="control-label col-md-4" for="nom"><g:message
                    code="user.nom.label" default="Nom"/></label>

            <div class="col-md-8">
                <input type="text" name="nom" id="nom"
                       class="form-control" placeholder="" value="${fieldValue(bean: userInstance, field: 'nom')}">
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-10">
        <div class="form-group">
            <label class="control-label col-md-4" for="prenom"><g:message
                    code="user.prenom.label" default="Prénom"/></label>

            <div class="col-md-8">
                <input type="text" name="prenom" id="prenom"
                       class="form-control" placeholder="" value="${fieldValue(bean: userInstance, field: 'prenom')}">
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-10">
        <div class="form-group">
            <label class="control-label col-md-4" for="fonction"><g:message
                    code="user.fonction.label" default="Fonction"/></label>

            <div class="col-md-8">
                <input type="text" name="fonction" id="fonction"
                       class="form-control" placeholder="" value="${fieldValue(bean: userInstance, field: 'fonction')}">
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-10">
        <div id="telephoneDiv" class="form-group">
            <label class="control-label col-md-4" for="telephone"><g:message
                    code="user.telephone.label" default="Téléphone"/></label>

            <div class="col-md-8">
                <div class="input-icon right">
                    <i id="error-msg" class="fa fa-warning hide"></i>
                    <input type="text" name="telephone" id="telephone"
                           class="form-control inputTelephone"
                           value="${fieldValue(bean: userInstance, field: 'telephone') } '+221'">
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-10">
        <div class="form-group">
            <label class="control-label col-md-4" for="email"><g:message
                    code="user.email.label" default="Email"/></label>

            <div class="col-md-8">
                <input type="email" name="email" id="email"
                       class="form-control" placeholder="" value="${fieldValue(bean: userInstance, field: 'email')}">
            </div>
        </div>
    </div>
</div>