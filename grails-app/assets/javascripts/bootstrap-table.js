/**
 * Created by macbook on 18/08/2018.
 */
//= require global/plugins/bootstrapTable/js/bootstrap-table
//= require global/plugins/bootstrapTable/js/bootstrap-table-locale-all
//= require global/plugins/bootstrapTable/js-xlsx/xlsx.core.min
//= require global/plugins/bootstrapTable/jsPDF-AutoTable/jspdf.plugin.autotable
//= require global/plugins/bootstrapTable/js/tableExport
//= require global/plugins/bootstrapTable/js/bootstrap-table-export

jQuery(document).ready(function () {
    try {
        jQuery.extend(jQuery.fn.bootstrapTable.defaults, {locale: 'fr-FR'});
    }
    catch (err) {
        console.error(err.message);
    }
});