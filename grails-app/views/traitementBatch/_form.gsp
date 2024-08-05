<%@ page import="sn.sensoft.springbatch.parametrage.ProgrammeBatch" %>
<g:set var="entityName" value="${message(code: 'traitementBatch.label', default: 'traitement batch')}"/>
<h4 class="form-section font-blue-madison bold"><g:message code="default.info.label" args="[entityName]"/></h4>

<div class="marge-5-5">
    <div class="row">

        <div class="col-md-6 ${hasErrors(bean: traitementBatchInstance, field: 'numeroSequence', 'has-error')} ">
            <div class="form-group form-md-line-input form-md-floating-label has-info">
                <label
                        for="numeroSequence"><g:message
                        code="traitementBatch.numeroSequence.label"
                        default="Numéro Sequence"/>
                    <span class="required">*</span>
                </label>

                <input type="text" name="numeroSequence" required="required"
                       class="form-control"
                       value="${fieldValue(bean: traitementBatchInstance, field: 'numeroSequence')}"/>
            </div>
        </div>

        <div class="col-md-6 ${hasErrors(bean: traitementBatchInstance, field: 'codeProgramme', 'has-error')}">
            <div class="form-group form-md-line-input form-md-floating-label has-info">
                <label
                        for="codeProgramme"><g:message
                        code="traitementBatch.codeProgramme.label"
                        default="Code programme"/>
                    <span class="required">*</span>
                </label>

                <input type="text" name="codeProgramme" required="required"
                       class="form-control"
                       value="${fieldValue(bean: traitementBatchInstance, field: 'codeProgramme')}"/>
            </div>
        </div>

    </div>

    <div class="row">

        <div class="col-md-6 ${hasErrors(bean: traitementBatchInstance, field: 'programmeALancer', 'has-error')} ">
            <div class="form-group form-md-line-input form-md-floating-label has-info">
                <label
                        for="programmeALancer"><g:message
                        code="traitementBatch.programmeALancer.label"
                        default="Programme a lancer"/>
                </label>

                <input type="text" name="programmeALancer"
                       class="form-control"
                       value="${fieldValue(bean: traitementBatchInstance, field: 'programmeALancer')}"/>
            </div>
        </div>

        <div class="col-md-6 ${hasErrors(bean: traitementBatchInstance, field: 'majRealisee', 'has-error')}">
            <div class="form-group form-md-line-input form-md-floating-label has-info">
                <label
                        for="majRealisee"><g:message
                        code="traitementBatch.majRealisee.label"
                        default="MAJ réalisée"/>
                </label>

                <input type="text" name="majRealisee"
                       class="form-control"
                       value="${fieldValue(bean: traitementBatchInstance, field: 'majRealisee')}"/>
            </div>
        </div>

    </div>

    <div class="row">

        <div class="col-md-6 ${hasErrors(bean: traitementBatchInstance, field: 'numeroEtape', 'has-error')} ">
            <div class="form-group form-md-line-input form-md-floating-label has-info">
                <label
                        for="numeroEtape"><g:message
                        code="traitementBatch.numeroEtape.label"
                        default="Numéro etape"/>
                </label>

                <input type="text" name="numeroEtape"
                       class="form-control"
                       value="${fieldValue(bean: traitementBatchInstance, field: 'numeroEtape')}"/>
            </div>
        </div>

        <div class="col-md-6 ${hasErrors(bean: traitementBatchInstance, field: 'dateComptable', 'has-error')}">
            <div class="form-group form-md-line-input form-md-floating-label has-info">
                <label
                        for="dateComptable"><g:message
                        code="traitementBatch.dateComptable.label"
                        default="Date comptable"/>
                </label>

                <input type="text" name="dateComptable" id="dateComptable"
                       class="form-control form-control-inline input-medium date-picker" autocomplete="off"
                       value="${fieldValue(bean: traitementBatchInstance, field: 'dateComptableDisplay')}">
            </div>
        </div>

    </div>

    <div class="row">

        <div class="col-md-6 ${hasErrors(bean: traitementBatchInstance, field: 'dateDebutLancement', 'has-error')} ">
            <div class="form-group form-md-line-input form-md-floating-label has-info">
                <label
                        for="dateDebutLancement"><g:message
                        code="traitementBatch.dateDebutLancement.label"
                        default="Date début lancement"/>
                </label>

                <input type="text" name="dateDebutLancement" id="dateDebutLancement"
                       class="form-control form-control-inline input-medium date-picker" autocomplete="off"
                       value="${fieldValue(bean: traitementBatchInstance, field: 'dateDebutLancementDisplay')}">
            </div>
        </div>

        <div class="col-md-6 ${hasErrors(bean: traitementBatchInstance, field: 'dateFinLancement', 'has-error')}">
            <div class="form-group form-md-line-input form-md-floating-label has-info">
                <label
                        for="dateFinLancement"><g:message
                        code="traitementBatch.dateFinLancement.label"
                        default="Date fin lancement"/>
                </label>

                <input type="text" name="dateFinLancement" id="dateFinLancement"
                       class="form-control form-control-inline input-medium date-picker" autocomplete="off"
                       value="${fieldValue(bean: traitementBatchInstance, field: 'dateFinLancementDisplay')}">
            </div>
        </div>

    </div>

    <div class="row">

        <div class="col-md-6 ${hasErrors(bean: traitementBatchInstance, field: 'dateModif', 'has-error')} ">
            <div class="form-group form-md-line-input form-md-floating-label has-info">
                <label
                        for="dateModif"><g:message
                        code="traitementBatch.dateModif.label"
                        default="Date modification"/>
                </label>

                <input type="text" name="dateModif" id="dateModif"
                       class="form-control form-control-inline input-medium date-picker" autocomplete="off"
                       value="${fieldValue(bean: traitementBatchInstance, field: 'dateModifDisplay')}">
            </div>
        </div>

        <div class="col-md-6 ${hasErrors(bean: traitementBatchInstance, field: 'codeUtilisateur', 'has-error')}">
            <div class="form-group form-md-line-input form-md-floating-label has-info">
                <label
                        for="codeUtilisateur"><g:message
                        code="traitementBatch.codeUtilisateur.label"
                        default="Code utilisateur"/>
                    <span class="required">*</span>
                </label>

                <input type="text" name="codeUtilisateur" required="required"
                       class="form-control"
                       value="${fieldValue(bean: traitementBatchInstance, field: 'codeUtilisateur')}"/>
            </div>
        </div>

    </div>

    <div class="row">
        <div class="col-md-6 ${hasErrors(bean: traitementBatchInstance, field: 'programmeBatch', 'has-error')}">
            <div class="form-group form-md-line-input form-md-floating-label has-info">
                <label
                        for="programmeBatch"><g:message
                        code="traitementBatch.programmeBatch.label"
                        default="Programme batch"/>
                    <span class="required">*</span>
                </label>

                <g:select class="form-control select2 "
                          name="programmeBatch.id" id="programmeBatchId" optionKey="id"
                          from="${ProgrammeBatch.list()}" required="required"
                          value="${fieldValue(bean: traitementBatchInstance, field: 'programmeBatch.id')}"
                          noSelection="['': '--- Choisir ---']"/>
            </div>
        </div>

    </div>

    <div class="row">
        <div class="col-md-6">
            <div class="form-group form-md-line-input form-md-floating-label has-info">
                <label for="messageBatch"><g:message
                        code="traitementBatch.messageBatch.label"
                        default="Message "/>
                </label>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <div class="col-md-12">
                    <textarea id="mytextarea" name="messageBatch"
                              rows="5"
                              cols="15">
                        ${fieldValue(bean: traitementBatchInstance, field: 'messageBatch')}
                    </textarea>
                </div>
            </div>
        </div>
    </div>
</div>

