<h3 class="form-section font-blue-madison bold"><g:message
        code="comptes.labels"
        default="Tous les comptes de bureaux"/></h3>
<div class="marge-5-5">
    <div class="row">
        <div class="col-md-6">
            <div class="form-group  form-md-line-input has-info ">
                <div class="form-group form-md-line-input form-md-floating-label has-info">
                    <label>
                        <g:message code="dateDebut.label"
                                   default="Période du"/>
                        <span class="required">*</span>
                    </label>

                    <input type="text" name="dateDebut"
                           id="dateDebut" value="${dateDebut}" required="required"
                           class="form-control form-control-inline input-medium date-picker"
                           autocomplete="off"/>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group  form-md-line-input has-info ">
                <div class="form-group form-md-line-input form-md-floating-label has-info">
                    <label>
                        <g:message code="dateFin.label"
                                   default="Au"/>
                        <span class="required">*</span>
                    </label>

                    <input type="text" name="dateFin"
                           id="dateFin" value="${dateFin}" required="required"
                           class="form-control form-control-inline input-medium date-picker"
                           autocomplete="off"/>
                </div>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    jQuery(document).ready(function () {

        $(".date-picker").datepicker();

    });


</script>