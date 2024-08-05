// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require global/plugins/respond.min
//= require global/plugins/excanvas.min
//= require global/plugins/ie8.fix.min
//= require global/plugins/js.cookie.min
//= require global/plugins/moment.min
//= require global/plugins/jquery.min
//= require global/plugins/bootstrap/js/bootstrap.min
//= require global/plugins/jquery-ui/jquery-ui.min
//= require global/plugins/jquery.blockui.min
//= require global/plugins/jquery.sparkline.min
//= require global/plugins/jquery-slimscroll/jquery.slimscroll.min
//= require global/plugins/jquery-validation/js/jquery.validate.min
//= require global/plugins/jquery-validation/js/additional-methods.min
//= require global/plugins/bootstrap-wizard/jquery.bootstrap.wizard.min
//= require global/plugins/backstretch/jquery.backstretch.min
//= require global/plugins/bootstrap-switch/js/bootstrap-switch.min
//= require pages/scripts/components-bootstrap-switch.min
//= require global/plugins/bootstrap-modal/js/bootstrap-modalmanager
//= require global/plugins/bootstrap-modal/js/bootstrap-modal
//= require global/plugins/bootstrap-confirmation/bootstrap-confirmation.min
//= require global/plugins/bootstrap-select/js/bootstrap-select.min
//= require global/plugins/bootstrap-datepicker/js/bootstrap-datepicker
//= require global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min
//= require global/plugins/bootstrap-table/bootstrap-table
//= require global/plugins/bootstrap-table/extensions/tableExport/jquery.base64
//= require global/plugins/bootstrap-table/extensions/tableExport/libs/js-xlsx/xlsx.core.min
//= require global/plugins/bootstrap-table/extensions/tableExport/libs/jsPDF/jspdf.min
//= require global/plugins/bootstrap-table/extensions/tableExport/tableExport
//= require global/plugins/bootstrapTable/jsPDF-AutoTable/jspdf.plugin.autotable
//= require global/plugins/bootstrap-table/extensions/export/bootstrap-table-export
//= require global/plugins/bootstrap-fileinput/bootstrap-fileinput
//= require global/plugins/select2/js/select2.full.min
//= require global/plugins/datatables/datatables.min
//= require global/plugins/datatables/plugins/bootstrap/datatables.bootstrap
//= require global/plugins/ckeditor/ckeditor
//= require global/plugins/ladda/spin.min
//= require global/plugins/ladda/ladda.min
//= require global/plugins/bootstrap-sweetalert/sweetalert2.min.js

//= require global/scripts/app.min
//= require global/scripts/datatable
//= require pages/scripts/table-datatables-responsive
//= require pages/scripts/profile.min
//= require pages/scripts/ui-extended-modals.min
//= require pages/scripts/ui-confirmations.min
//= require pages/scripts/components-bootstrap-select.min
//= require pages/scripts/components-select2.min
//= require pages/scripts/components-date-time-pickers.min
//= require pages/scripts/form-wizard.min
//= require pages/scripts/ui-buttons.min
//= require global/plugins/jQuerySteps/jquery.steps.min

//= require layouts/layout3/scripts/layout.min
//= require layouts/layout3/scripts/demo.min
//= require layouts/global/scripts/quick-sidebar.min
//= require layouts/global/scripts/quick-nav.min


var mytextarea = document.getElementById('mytextarea');
CKEDITOR.config.height = "85px";
if (mytextarea) {
    CKEDITOR.replace(mytextarea);
}

$(document).tooltip({
    items: '.title-display-tooltip a, .photonic-slideshow.title-display-tooltip img',
    track: true,
    show: false,
    selector: '.title-display-tooltip a, .photonic-slideshow.title-display-tooltip img',
    hide: false
});