<h3 class="form-section font-blue-madison bold"><g:message
        code="efzef"
        default="Pour un compte"/></h3>
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

    <div class="row">
        <div class="col-md-6">
            <div class="form-group form-md-line-input form-md-floating-label has-info">
                <label>
                    <g:message code="bureau.index"
                               default="Liste bureaux"/>
                    <span class="required">*</span>

                </label>

                <g:select class="form-control select2"
                          name="codeBureau" id="codeBureau"
                          optionKey="code" optionValue="name"
                          from="${bureauList}"
                          required="required"
                          noSelection="['': '--- Choisir ---']"/>

            </div>

        </div>
        <div class="col-md-6">
            <div class="form-group form-md-line-input form-md-floating-label has-info">
                <label
                        for="compte"><g:message
                        code="ecriture.compte.label"
                        default="Compte"/>
                    <span class="required">*</span>
                </label>


                <g:hiddenField name="compteId"
                               id="compteId"
                               value=""/>
                <select id="compte"
                        required="required"
                        class="form-control"></select>

            </div>
        </div>
    </div>
</div>



<script type="text/javascript">
    jQuery(document).ready(function () {

        $(".date-picker").datepicker();
        $("#bureauIdd").select2();

        $("#compte").select2({
            width: "off",
            ajax: {
                url: "${request.contextPath}/traitementBatch/rechercheCompte",
                dataType: 'json',
                delay: 250,
                data: function (params) {
                    return {
                        numeroCompte: params.term, // search term
                        codeBureau: $("#codeBureau").val(),
                        page: params.page
                    };
                },
                processResults: function (data, page) {
                    // parse the results into the format expected by Select2.
                    // since we are using custom formatting functions we do not need to
                    // alter the remote JSON data
                    return {
                        results: data
                    };
                },
                cache: false
            },
            escapeMarkup: function (markup) {
                return markup;
            }, // let our custom formatter work
            minimumInputLength: 8,
            templateResult: formatRepoCompte,
            templateSelection: formatRepoSelectionCompte
        }).on("select2:select", function (e) {
            // $("#numeroCompte").val(e[0].params.data.numeroCompte);
            $("#compteId").val(e.params.data.id);
            // $("#intituleCompte").val(e.params.data.intituleCompte);

        });

    });


</script>
