<div class="col-md-12">
    <div class="form-group form-md-line-input form-md-floating-label has-info">
        <label
                for="compteGeneralId"><g:message
                code="compteGeneral.label"
                default="Compte general"/>
            <span class="required">*</span>
        </label>

        <g:select class="form-control select2 input-sm select2Form" name="compteGeneralId" id="compteGeneralId"
                  from="${compteGeneralList}" optionKey="id" onchange="setIdCompte()"
                  required="required" noSelection="['':'--- Choix compte general!']" />
    </div>
</div>