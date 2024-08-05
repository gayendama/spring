<asset:stylesheet src="lc_switch.css.css"/>
<asset:javascript src="lc_switch.js"/>
<script>
    $(document).ready(function () {
        $('input').lc_switch();
    });
</script>
<table class="table table-striped table-bordered table-hover dt-responsive" id="sample_step">
    <thead>
    <tr>
        <g:sortableColumn property="code" title="CODE"/>
        <g:sortableColumn property="name" title="NAME"/>
        <g:sortableColumn property="sequence" title="SEQUENCE" style="text-align: center;"/>
        <g:sortableColumn property="status" title="STATUS" style="text-align: center;"/>
    </tr>
    </thead>
    <tbody>
    <g:each in="${programmeBatchList}" status="i" var="step">
        <tr>
            <td>${fieldValue(bean: step, field: "codeProgramme")}</td>
            <td>${fieldValue(bean: step, field: "nomProgramme")}</td>
            <td align="center">
                <input type="hidden" id="${i}-input-step-id" value="${step.codeProgramme}">
                <i class="fa fa-spinner fa-spin fa-lg load-step" style="display: none;"></i>

                <div id="${i}-edit-seq-div" style="display: none" class="edit-step-div">
                    <input id="${i}-input-seq" type="number" min="1" class="seq-input form-control input-inline"
                           value="${fieldValue(bean: step, field: "sequenceNumber")}" onkeyup="onKeyPress(${i})"/>
                    <i id="${i}-seq-done" class="fa fa-check fa-lg btn-done " onclick="saveSequeces(${i});"></i>
                </div>

                <div id="${i}-show-seq-div" class="show-step-div">
                    ${step.sequenceNumber}
                    <i id="${i}-seq-pencil" class="fa fa-pencil fa-lg edit-step " onclick="editSequence(${i});"></i>
                </div>
            </td>
            <td align="center">
                <g:if test="${step.sequenceNumber != null}">
                    <g:if test="${step.batchStepStatus != null}">
                        <g:if test="${step.batchStepStatus.id == sn.sensoft.springbatch.utils.BatchStepStatus.ACTIVER.id}">
                            <input id="${i}-switch" type="checkbox" class="lcs_check lcs_tt1" checked="checked"
                                   autocomplete="off"/>
                        </g:if>
                        <g:else>
                            <input id="${i}-switch" type="checkbox" class="lcs_check" autocomplete="off"/>
                        </g:else>
                    </g:if>
                    <g:else>
                        <input id="${i}-switch" type="checkbox" class="lcs_check" disabled="disabled"
                               autocomplete="off"/>
                    </g:else>
                </g:if>
                <g:else>
                    <input id="${i}-switch" type="checkbox" class="lcs_check" disabled="disabled" autocomplete="off"/>
                </g:else>
            </td>
        </tr>
    </g:each>
    </tbody>
</table>
<script>
    $(document).ready(function () {
        $('#sample_step').DataTable({
            "ordering": false,
            paging: false,
        });
    });
</script>