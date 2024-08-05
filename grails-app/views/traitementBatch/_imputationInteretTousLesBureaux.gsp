<h3 class="form-section font-blue-madison bold"><g:message
        code="interet.bureaux.label"
        default="Pour tous les bureaux compte"/></h3>
<div class="marge-5-5">
    <div class="row">
        <div class="col-md-6">
            <div class="form-group  form-md-line-input has-info ">
                <div class="form-group form-md-line-input form-md-floating-label has-info">
                    <label
                            for="dateArrete"><g:message
                            code="dateArrete.label"
                            default="Date arrêté"/>
                        <span class="required">*</span>
                    </label>

                    <g:select class="form-control select2"
                              name="dateArreteId" id="dateArreteId"
                              optionKey="id"
                              optionValue="dateArreteDisplay"
                              from="${dateArreteList}"
                              required="required"
                              noSelection="['': '--- Choisir ---']"/>
                </div>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    jQuery(document).ready(function () {

        $("#dateArreteId").select2();

    });


</script>
