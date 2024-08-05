<g:form method="post" action="saveEditAccess">
    <div class="scrolling" >
        <input type="hidden" name="roleId" value="${roleInstance.id}">
        <input type="hidden" name="moduleId" value="${moduleInstance.id}">
        <g:each in="${parentInstanceList}" status="j" var="parentInstance">
            <fieldset class="m-b-0">
                <legend>
                    <%

                        def cocherChild = 0
                        def cocheAllChild

                        def cocheAll = parentInstance?.menuMapFils?.size()
                        def cocheAllParent = parentInstance?.menuMapFils?.findAll().size()

                        parentInstance?.menuMapFils?.each { child ->
                            if (accessInstanceList.find { it.url == child.url } != "" && accessInstanceList.find { it.url == child.url } != null){
                                cocherChild = cocherChild + 1
                            }
//                            if(cocherChild)

                            cocheAllChild = child?.menuMapFils?.findAll().size()
                        }
                    %>

                    <g:hiddenField name="cocherChild" value="${cocherChild}"/>
                    <g:hiddenField name="cocheAllChild" value="${cocheAllChild}"/>
                    <g:hiddenField name="cocheAll" value="${cocheAll}"/>
                    <g:hiddenField name="cocheAllParent" value="${cocheAllParent}"/>
                    %{--<g:if test="${(cocheAll > 0 && cocheAllParent > 0 && cocheAll == cocheAllParent) && (cocherChild == cocheAllChild)}">--}%
                    <g:if test="${cocherChild > 0 && cocheAll == cocherChild}">
                        <p style="font-size: 13px;" class="bold">
                            <input type="checkbox" class="selectAll" style="width: 10px; display: inline" checked
                                   data-id="${parentInstance?.id}"/>
                            <g:message code="${parentInstance?.label}.label" default="${parentInstance?.url}"/>
                        </p>
                    </g:if>
                    <g:else>
                        <p style="font-size: 13px;" class="bold">
                            <input type="checkbox" class="selectAll" style="width: 10px; display: inline"
                                   data-id="${parentInstance?.id}"/>
                            <g:message code="${parentInstance?.label}.label" default="${parentInstance?.url}"/>
                        </p>
                    </g:else>
                </legend>

                <div id="filsNode${parentInstance.id}">

                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <g:each in="${parentInstance?.menuMapFils}" status="k"
                                var="filsInstance">
                            <div class="col-md-3 col-sm-3 col-xs-3">
                                <input type="hidden" name="url.id" value="${filsInstance.id}"/>

                                <div class="f${filsInstance?.parent.id}" style="list-style-type:none">
                                    <div title="${filsInstance?.url}">
                                        <g:hiddenField name="choix_${filsInstance.id}" value="${filsInstance}"/>
                                        <g:if test="${(accessInstanceList.find { it.url == filsInstance.url } != "" && accessInstanceList.find { it.url == filsInstance.url } != null)}">
                                            <input type="checkbox" name="access.${filsInstance.id}" checked
                                                   style="width: 10px; display: inline"
                                                   class="choix${parentInstance.id}"/>
                                        </g:if>
                                        <g:else>
                                            <input type="checkbox" name="access.${filsInstance.id}"
                                                   style="width: 10px; display: inline"
                                                   class="choix${parentInstance.id}"/>
                                        </g:else>
                                        <g:message code="${filsInstance?.label}"
                                                   default="${filsInstance?.label}"/>
                                    </div>

                                    <div id="coursRegNode${filsInstance?.id}">

                                        <ul>
                                            <g:each in="${filsInstance.menuMapFils}" status="m"
                                                    var="fils">
                                                <input type="hidden" name="url.id" value="${fils.id}"/>

                                                <div class="f${fils?.parent.id}"
                                                     style="list-style-type:none;" title="${fils?.url}">
                                                    <g:hiddenField name="coursRegNode_${fils.id}" value="${fils}"/>

                                                    <g:if test="${(accessInstanceList.find { it.url == fils.url } != "" && accessInstanceList.find { it.url == fils.url } != null)}">
                                                        <input type="checkbox" name="access.${fils.id}" checked
                                                               style="width: 10px; display: inline"
                                                               class="choix${parentInstance.id}"/>
                                                    </g:if>
                                                    <g:else>
                                                        <input type="checkbox" name="access.${fils.id}"
                                                               style="width: 10px; display: inline"
                                                               class="choix${parentInstance.id}"/>
                                                    </g:else>
                                                    <g:message code="${fils?.label}"
                                                               default="${fils?.label}"/>

                                                </div>
                                            </g:each>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </g:each>
                    </div>

                </div>

            </fieldset>
            <br>
        </g:each>

        <hr/>
        <div class="clearfix"></div>

        <div class="form-actions m-t-10">
            <g:submitButton name="save" class="btn btn-success"
                            value="${message(code: 'default.save.label', default: 'Sauvegarder')}"/>
        </div>
    </div>

</g:form>
<script>
    $("fieldset legend p input").on("click", function () {
        var menuMapParentId = $(this).data("id");
        if (jQuery(this).is(':checked') === true) {
            $('#filsNode' + menuMapParentId).find(':checkbox').each(function(){
                $(".choix" + menuMapParentId).prop('checked', true);
            });
        }
        else if (jQuery(this).is(':checked') === false) {
            $('#filsNode' + menuMapParentId).find(':checkbox').each(function(){
                $(".choix" + menuMapParentId).prop('checked', false);
            });
        }

    });

</script>

