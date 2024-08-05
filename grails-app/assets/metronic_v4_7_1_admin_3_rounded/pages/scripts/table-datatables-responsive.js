var TableDatatablesResponsive = function () {

    var initTable1 = function () {
        var table = $('#sample_1');

        var oTable = table.dataTable({
            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "aria": {
                    "sortAscending": ": activer pour trier la colonne de manière ascendante",
                    "sortDescending": ": activer pour trier la colonne de manière descendante"
                },
                "emptyTable": "Aucune donnée disponible dans la table",
                "info": "Affichage de _START_ à _END_ sur _TOTAL_ enregistrements",
                "infoEmpty": "Aucun enregistrement trouvé",
                "infoFiltered": "(filtré sur _MAX_ enregistrements au total)",
                "lengthMenu": "_MENU_ enregistrements",
                "search": "Recherche:",
                "zeroRecords": "Aucun enregistrement correspondant trouvé"
            },

            // Or you can use remote translation file
            //"language": {
            //   url: '//cdn.datatables.net/plug-ins/3cfcc339e89/i18n/Portuguese.json'
            //},

            buttons: [
                {
                    extend: 'print',
                    className: 'btn dark btn-outline hide',
                    text: 'Imprimer',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                },
                {
                    extend: 'copy',
                    className: 'btn red btn-outline hide',
                    text: 'Copier',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                },
                {
                    extend: 'pdf',
                    className: 'btn green btn-outline hide',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                },
                {
                    extend: 'excel',
                    className: 'btn yellow btn-outline hide',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                },
                {
                    extend: 'csv',
                    className: 'btn purple btn-outline hide',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                }
                // {extend: 'colvis', className: 'btn green', text: 'Colonnes'}
            ],

            aoColumnDefs: [
                {sWidth: "5px", aTargets: ["width5"]},
                {"bSortable": false, aTargets: ["noSort"]},
            ],

            // setup responsive extension: http://datatables.net/extensions/responsive/
            responsive: true,

            "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

            order: [],

            // pagination control
            "lengthMenu": [
                [5, 10, 15, 20, -1],
                [5, 10, 15, 20, "Tous"] // change per page values here
            ],
            // set the initial value
            "pageLength": 10,
            "pagingType": 'bootstrap_extended', // pagination type

            "dom": "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><'table-scrollable't><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>", // horizobtal scrollable datatable

            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js).
            // So when dropdowns used the scrollable div should be removed.
            //"dom": "<'row' <'col-md-12'T>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",
        });
        // handle datatable custom tools
        $('#sample_1_tools > li > a.tool-action').on('click', function () {
            var action = $(this).attr('data-action');
            oTable.DataTable().button(action).trigger();
        });
    };

    var initTable2 = function () {
        var table = $('#sample_2');

        var oTable = table.dataTable({
            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "aria": {
                    "sortAscending": ": activer pour trier la colonne de manière ascendante",
                    "sortDescending": ": activer pour trier la colonne de manière descendante"
                },
                "emptyTable": "Aucune donnée disponible dans la table",
                "info": "Affichage de _START_ à _END_ sur _TOTAL_ enregistrements",
                "infoEmpty": "Aucun enregistrement trouvé",
                "infoFiltered": "(filtré sur _MAX_ enregistrements au total)",
                "lengthMenu": "_MENU_ enregistrements",
                "search": "Recherche:",
                "zeroRecords": "Aucun enregistrement correspondant trouvé"
            },

            // Or you can use remote translation file
            //"language": {
            //   url: '//cdn.datatables.net/plug-ins/3cfcc339e89/i18n/Portuguese.json'
            //},

            buttons: [
                {
                    extend: 'print',
                    className: 'btn dark btn-outline hide',
                    text: 'Imprimer',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                },
                {
                    extend: 'copy',
                    className: 'btn red btn-outline hide',
                    text: 'Copier',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                },
                {
                    extend: 'pdf',
                    className: 'btn green btn-outline hide',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                },
                {
                    extend: 'excel',
                    className: 'btn yellow btn-outline hide',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                },
                {
                    extend: 'csv',
                    className: 'btn purple btn-outline hide',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                }
            ],

            aoColumnDefs: [
                {sWidth: "5px", aTargets: ["width5"]},
                {"bSortable": false, aTargets: ["noSort"]}
            ],

            // setup responsive extension: http://datatables.net/extensions/responsive/
            responsive: true,

            "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

            order: [],

            // pagination control
            "lengthMenu": [
                [5, 10, 15, 20, -1],
                [5, 10, 15, 20, "Tous"] // change per page values here
            ],
            // set the initial value
            "pageLength": 10,
            "pagingType": 'bootstrap_extended', // pagination type

            "dom": "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><'table-scrollable't><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>" // horizobtal scrollable datatable

            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js). 
            // So when dropdowns used the scrollable div should be removed. 
            //"dom": "<'row' <'col-md-12'T>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",
        });
        // handle datatable custom tools
        $('#sample_2_tools > li > a.tool-action').on('click', function () {
            var action = $(this).attr('data-action');
            oTable.DataTable().button(action).trigger();
        });
    };

    var initTable3 = function () {
        var table = $('#sample_3');

        var oTable = table.dataTable({
            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "aria": {
                    "sortAscending": ": activer pour trier la colonne de manière ascendante",
                    "sortDescending": ": activer pour trier la colonne de manière descendante"
                },
                "emptyTable": "Aucune donnée disponible dans la table",
                "info": "Affichage de _START_ à _END_ sur _TOTAL_ enregistrements",
                "infoEmpty": "Aucun enregistrement trouvé",
                "infoFiltered": "(filtré sur _MAX_ enregistrements au total)",
                "lengthMenu": "_MENU_ enregistrements",
                "search": "Recherche:",
                "zeroRecords": "Aucun enregistrement correspondant trouvé"
            },

            // Or you can use remote translation file
            //"language": {
            //   url: '//cdn.datatables.net/plug-ins/3cfcc339e89/i18n/Portuguese.json'
            //},

            buttons: [
                {
                    extend: 'print',
                    className: 'btn dark btn-outline hide',
                    text: 'Imprimer',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                },
                {
                    extend: 'copy',
                    className: 'btn red btn-outline hide',
                    text: 'Copier',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                },
                {
                    extend: 'pdf',
                    className: 'btn green btn-outline hide',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                },
                {
                    extend: 'excel',
                    className: 'btn yellow btn-outline hide',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                },
                {
                    extend: 'csv',
                    className: 'btn purple btn-outline hide',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                }
            ],

            aoColumnDefs: [
                {sWidth: "5px", aTargets: ["width5"]},
                {"bSortable": false, aTargets: ["noSort"]}
            ],

            // setup responsive extension: http://datatables.net/extensions/responsive/
            responsive: true,

            "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

            order: [],

            // pagination control
            "lengthMenu": [
                [5, 10, 15, 20, -1],
                [5, 10, 15, 20, "Tous"] // change per page values here
            ],
            // set the initial value
            "pageLength": 10,
            "pagingType": 'bootstrap_extended', // pagination type

            "dom": "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><'table-scrollable't><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>" // horizobtal scrollable datatable

            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js).
            // So when dropdowns used the scrollable div should be removed.
            //"dom": "<'row' <'col-md-12'T>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",
        });
        // handle datatable custom tools
        $('#sample_3_tools > li > a.tool-action').on('click', function () {
            var action = $(this).attr('data-action');
            oTable.DataTable().button(action).trigger();
        });
    };

    var initTable4 = function () {
        var table = $('#sample_4');

        var oTable = table.dataTable({
            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "aria": {
                    "sortAscending": ": activer pour trier la colonne de manière ascendante",
                    "sortDescending": ": activer pour trier la colonne de manière descendante"
                },
                "emptyTable": "Aucune donnée disponible dans la table",
                "info": "Affichage de _START_ à _END_ sur _TOTAL_ enregistrements",
                "infoEmpty": "Aucun enregistrement trouvé",
                "infoFiltered": "(filtré sur _MAX_ enregistrements au total)",
                "lengthMenu": "_MENU_ enregistrements",
                "search": "Recherche:",
                "zeroRecords": "Aucun enregistrement correspondant trouvé"
            },

            // Or you can use remote translation file
            //"language": {
            //   url: '//cdn.datatables.net/plug-ins/3cfcc339e89/i18n/Portuguese.json'
            //},

            buttons: [
                {
                    extend: 'print',
                    className: 'btn dark btn-outline hide',
                    text: 'Imprimer',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                },
                {
                    extend: 'copy',
                    className: 'btn red btn-outline hide',
                    text: 'Copier',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                },
                {
                    extend: 'pdf',
                    className: 'btn green btn-outline hide',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                },
                {
                    extend: 'excel',
                    className: 'btn yellow btn-outline hide',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                },
                {
                    extend: 'csv',
                    className: 'btn purple btn-outline hide',
                    exportOptions: {columns: "thead th:not(.noExport)"}
                }
            ],

            aoColumnDefs: [
                {sWidth: "5px", aTargets: ["width5"]},
                {"bSortable": false, aTargets: ["noSort"]}
            ],

            // setup responsive extension: http://datatables.net/extensions/responsive/
            responsive: true,

            "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

            order: [],

            // pagination control
            "lengthMenu": [
                [5, 10, 15, 20, -1],
                [5, 10, 15, 20, "Tous"] // change per page values here
            ],
            // set the initial value
            "pageLength": 10,
            "pagingType": 'bootstrap_extended', // pagination type

            "dom": "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><'table-scrollable't><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>" // horizobtal scrollable datatable

            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js).
            // So when dropdowns used the scrollable div should be removed.
            //"dom": "<'row' <'col-md-12'T>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",
        });
        // handle datatable custom tools
        $('#sample_4_tools > li > a.tool-action').on('click', function () {
            var action = $(this).attr('data-action');
            oTable.DataTable().button(action).trigger();
        });
    };
    var initTable5 = function () {
        var table = $('#sample_5');

        var oTable = table.dataTable({
            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "aria": {
                    "sortAscending": ": activer pour trier la colonne de manière ascendante",
                    "sortDescending": ": activer pour trier la colonne de manière descendante"
                },
                "emptyTable": "Aucune donnée disponible dans la table",
                "info": "Afficher de _START_ à _END_ sur _TOTAL_ enregistrements",
                "infoEmpty": "Aucun enregistrement trouvé",
                "infoFiltered": "(filtré sur _MAX_ enregistrements au total)",
                "lengthMenu": "_MENU_ enregistrements",
                "search": "Recherche:",
                "zeroRecords": "Aucun enregistrement correspondant trouvé"
            },

            // Or you can use remote translation file
            //"language": {
            //   url: '//cdn.datatables.net/plug-ins/3cfcc339e89/i18n/Portuguese.json'
            //},

            // setup buttons extentension: http://datatables.net/extensions/buttons/
            buttons: [
                {extend: 'print', className: 'btn dark', text: 'Imprimer'},
                {extend: 'copy', className: 'btn blue-dark', text: 'Copier'},
                {extend: 'pdf', className: 'btn red'},
                {extend: 'csv', className: 'btn green-jungle'},
                {extend: 'excel', className: 'btn green-dark'}
                // {extend: 'colvis', className: 'btn green', text: 'Colonnes'}
            ],

            // setup responsive extension: http://datatables.net/extensions/responsive/
            responsive: true,

            "order": [
                []
            ],

            "lengthMenu": [
                [5, 10, 15, 20, -1],
                [5, 10, 15, 20, "Tous"] // change per page values here
            ],
            // set the initial value
            "pageLength": 10,

            "dom": "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><'table-scrollable't><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>" // horizobtal scrollable datatable

            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js).
            // So when dropdowns used the scrollable div should be removed.
            //"dom": "<'row' <'col-md-12'T>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",
        });
        // handle datatable custom tools
        $('#sample_5_tools > li > a.tool-action').on('click', function () {
            var action = $(this).attr('data-action');
            oTable.DataTable().button(action).trigger();
        });
    };


    return {

        //main function to initiate the module
        init: function () {

            if (!jQuery().dataTable) {
                return;
            }

            initTable1();
            initTable2();
            initTable3();
            initTable4();
            initTable5();
        }

    };

}();

jQuery(document).ready(function () {
    TableDatatablesResponsive.init();
});