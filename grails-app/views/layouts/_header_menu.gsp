<%@ page import="sn.sensoft.springbatch.utils.Constantes; sn.sensoft.springbatch.utils.HabilitationService;" %>
<g:set var="habilitationService" bean="habilitationService"/>
<%
    def roles
%>
<div class="page-header-menu ">
    <div class="container">
        <!-- BEGIN MEGA MENU -->

        <div class="hor-menu hor-menu-light">

            <ul class="nav navbar-nav">

                <%
                    def listUrl8 = ["/user/index", "/role/index"]
                    roles = habilitationService.getRoleMenu(listUrl8)
                %>
                <sec:ifAnyGranted roles="${roles}">
                    <li aria-haspopup="true" class="menu-dropdown classic-menu-dropdown">
                        <sec:ifAnyGranted roles="${roles}">

                            <a href="javascript:"><g:message code="securite.label" args="['Securite']"
                                                             default="Sécurité"/>
                                <span class="arrow"></span>
                            </a>
                        </sec:ifAnyGranted>

                        <ul class="dropdown-menu pull-left">
                            <%
                                roles = habilitationService.getRoleSubMenu("/user/index")
                            %>
                            <sec:ifAnyGranted roles="${roles}">

                                <li aria-haspopup="true" class=" ">
                                    <g:link controller="user"
                                            action="index" class="nav-link">
                                        <i class="fa fa-user"></i> <g:message code="user.index"
                                                                              args="['Utilisateur']"
                                                                              default="Utilisateurs"/></g:link>
                                </li>
                            </sec:ifAnyGranted>
                            <%
                                roles = habilitationService.getRoleSubMenu("/role/index")
                            %>
                            <sec:ifAnyGranted roles="${roles}">

                                <li aria-haspopup="true" class=" ">
                                    <g:link controller="role"
                                            action="index" class="nav-link">
                                        <i class="fa fa-key"></i> <g:message code="role.index" args="['Profil']"
                                                                             default="Profils"/></g:link>
                                </li>
                            </sec:ifAnyGranted>

                        </ul>
                    </li>
                </sec:ifAnyGranted>

                    <%
                        def listUrlTraitementBatch = ["/batch/list","/batch/journal","/batch/index", '/quartz/list']
                        roles = habilitationService.getRoleMenu(listUrlTraitementBatch)

                    %>
                    <sec:ifAnyGranted roles="${roles}">
                        <li aria-haspopup="true" class="menu-dropdown mega-menu-dropdown mega-menu-full">
                            <a href="javascript:"><g:message code="parametrage.batch.label"
                                                             default="Parametrage batch"/>
                                <span class="arrow"></span>
                            </a>
                            <ul class="dropdown-menu" style="min-width: 810px">
                                <li>
                                    <div class="mega-menu-content">
                                        <div class="row">

                                            <%
                                                def listUrlBatch = ["/batch/index","/batch/list","/batch/journal",  "/quartz/list"]
                                                roles = habilitationService.getRoleMenu(listUrlBatch)

                                            %>
                                            <sec:ifAnyGranted roles="${roles}">
                                                <div class="col-md-4">

                                                    <ul class="mega-menu-submenu">
                                                        <li>
                                                            <h3 class="bold"><g:message code="batch.label"
                                                                                        default="Batch"/></h3>
                                                        </li>
                                                        <%
                                                            roles = habilitationService.getRoleSubMenu("/batch/index")
                                                        %>
                                                        <sec:ifAnyGranted roles="${roles}">
                                                            <li>
                                                                <g:link controller="batch"
                                                                        action="index" class="nav-link">
                                                                    <i class="fa fa-tasks"></i> <g:message
                                                                        code="batch.index"
                                                                        args="['Batch']"
                                                                        default="Job List"/></g:link>
                                                            </li>
                                                        </sec:ifAnyGranted>
                                                        <%
                                                            roles = habilitationService.getRoleSubMenu("/batch/list")
                                                        %>
                                                        <sec:ifAnyGranted roles="${roles}">
                                                            <li>
                                                                <g:link controller="batch"
                                                                        action="list" class="nav-link">
                                                                    <i class="fa fa-tasks"></i> <g:message
                                                                        code="batch.list"
                                                                        args="['Batch']"
                                                                        default="Job List"/></g:link>
                                                            </li>
                                                        </sec:ifAnyGranted>

                                                        <%
                                                            roles = habilitationService.getRoleSubMenu("/batch/journal")
                                                        %>
                                                        <sec:ifAnyGranted roles="${roles}">
                                                            <li>
                                                                <g:link controller="batch"
                                                                        action="journal" class="nav-link">
                                                                    <i class="fa fa-tasks"></i> <g:message
                                                                        code="batch.journal"
                                                                        default="Journal batch"/></g:link>
                                                            </li>
                                                        </sec:ifAnyGranted>
                                                    </ul>
                                                </div>
                                            </sec:ifAnyGranted>

                                            <%
                                                def listUrlTraitementDivers = ["/logActivity/recherche"]
                                                roles = habilitationService.getRoleMenu(listUrlTraitementDivers)

                                            %>
                                            <sec:ifAnyGranted roles="${roles}">
                                                <div class="col-md-4">

                                                    <sec:ifAnyGranted roles="${roles}">
                                                        <ul class="mega-menu-submenu">
                                                            <li>
                                                                <h3 class="bold"><g:message
                                                                        code="traitementBatch.divers"
                                                                        default="Divers"/></h3>
                                                            </li>
                                                            <%
                                                                roles = habilitationService.getRoleSubMenu("/logActivity/recherche")
                                                            %>
                                                            <sec:ifAnyGranted roles="${roles}">
                                                                <li>
                                                                    <g:link controller="logActivity"
                                                                            action="recherche" class="nav-link">
                                                                        <i class="fa fa-tasks"></i> <g:message
                                                                            code="logActivity.recherche"
                                                                            default="Recherche log activity"/></g:link>
                                                                </li>
                                                            </sec:ifAnyGranted>
                                                        </ul>

                                                    </sec:ifAnyGranted>
                                                </div>

                                            </sec:ifAnyGranted>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </li>
                    </sec:ifAnyGranted>


            </ul>
        </div>
        <!-- END MEGA MENU -->
    </div>
</div>
