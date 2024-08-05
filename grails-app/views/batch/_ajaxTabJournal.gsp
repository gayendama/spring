<%@ page import="sn.sensoft.springbatch.parametrage.ProgrammeBatch" %>
<div class="table-container"
     style="margin-top: -10px; padding-bottom: 15px;">
    <table class="table table-striped table-bordered table-hover dt-responsive">
        <thead>
        <tr>
            <td>Programmes</td>
            <td>Message</td>
        </tr>
        </thead>
        <tbody>
        <g:each in="${batchList}" var="batch" status="i">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td>${ ProgrammeBatch.findByCodeProgramme(batch.codeProgramme).libelleProgramme}</td>
                <td>${batch.message}</td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
