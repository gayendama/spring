<fieldset class="margin-top-15">

    <div class="col-md-6">
        <div class="form-group form-md-line-input form-md-floating-label has-info">
            <label
                    for="typeLog"><g:message
                    code="logActivity.typeLog.label"
                    default="Type log"/>
            </label>
            <g:select class="form-control select2" name="typeLog"
                      from="${typeLogList}"
                      noSelection="['': '--- Choisir ---']"/>
        </div>
    </div>

    <div class="col-md-6">
        <div class="form-group form-md-line-input form-md-floating-label has-info">
            <label
            for="objectName"><g:message
                    code="logActivity.objetName.label"
                    default="Objet"/>
        </label>
            <g:select class="form-control select2" name="objectName"
                      from="${objetNameList}"
                      noSelection="['': '--- Choisir ---']"/>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group form-md-line-input form-md-floating-label has-info">
            <label
                    for="message"><g:message
                    code="logActivity.message.label"
                    default="Message"/>
            </label>
            <input type="text" name="message"
                   class="form-control"/>
        </div>
    </div>

    <div class="col-md-6">
        <div class="form-group form-md-line-input form-md-floating-label has-info">
            <label
                    for="dateCreated"><g:message
                    code="logActivity.dateCreated.label"
                    default="Date"/>
            </label>
            <input type="text" name="dateCreated" id="dateCreated" data-date-today-highlight="true"
                   class="form-control form-control-inline input-medium date-picker" autocomplete="off">
        </div>
    </div>

</fieldset>

