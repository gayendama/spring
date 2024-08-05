
<g:hasErrors bean="${objetInstance}">
    <div class="alert alert-danger alert-dismissable">
        <button type="button" class="close"
                data-dismiss="alert"
                aria-hidden="true"></button>
        <g:renderErrors bean="${objetInstance}"
                        as="list"/>
    </div>
</g:hasErrors>
<g:if test="${flash.message}">
    <div class="alert alert-success alert-dismissable">
        <button type="button" class="close"
                data-dismiss="alert"
                aria-hidden="true"></button>
        <strong>Succès !</strong> ${flash.message}.
    </div>
</g:if>
<g:if test="${flash.error}">
    <div class="alert alert-danger alert-dismissable">
        <button type="button" class="close"
                data-dismiss="alert"
                aria-hidden="true"></button>
        <strong>Erreur !</strong> ${flash.error}
        <g:if test="${nonValidatedDynamicForms != null && !nonValidatedDynamicForms?.empty}">
            <a data-toggle="modal" data-target="#details">détails</a>
        </g:if>.
    </div>
</g:if>
<g:if test="${flash.warning}">
    <div class="alert alert-warning alert-dismissable">
        <button type="button" class="close"
                data-dismiss="alert"
                aria-hidden="true"></button>
        <strong>Attention !</strong> ${flash.warning}.
    </div>
</g:if>
<g:if test="${flash.info}">
    <div class="alert alert-info alert-dismissable">
        <button type="button" class="close"
                data-dismiss="alert"
                aria-hidden="true"></button>
        <strong>Information !</strong> ${flash.info}.
    </div>
</g:if>
<g:if test="${flash.oldError}">
    <div class="alert alert-danger alert-dismissable">
        <button type="button" class="close"
                data-dismiss="alert"
                aria-hidden="true"></button>
        <strong>Erreur !</strong> ${flash.oldError}
    </div>
</g:if>
<g:if test="${flash.invalidToken}">
    <div class="alert alert-danger alert-dismissable">
        <button type="button" class="close"
                data-dismiss="alert"
                aria-hidden="true"></button>
        <strong>Erreur !</strong> <g:message code="default.invalidToken.label"  default="Merci ne pas clicker 2 fois sur le bouton"/>
    </div>
</g:if>