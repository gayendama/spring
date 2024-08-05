%{--<fieldset class="m-b-20">--}%

    %{--<div class="col-md-12 col-sm-12 col-xs-12">--}%
        %{--<ul>--}%
            %{--<g:each in="${parentInstanceList}" status="j" var="parentInstance">--}%
                %{--<g:if test="${!accessInstanceList.intersect(parentInstance?.menuMapFils).empty}">--}%
                    %{--<li class="niveau2Rq col-md-4 col-sm-4 col-xs-4" style="list-style-type:none">--}%
                        %{--<div class="row-fluid">--}%

                            %{--<b>--}%
                                %{--<g:message code="${parentInstance?.label}" default="${parentInstance?.url}"/>--}%
                            %{--</b>--}%

                        %{--</div>--}%

                        %{--<div id="filsNode${parentInstance?.id}">--}%
                            %{--<ul>--}%
                                %{--<g:each in="${parentInstance?.menuMapFils}" status="k" var="filsInstance">--}%
                                    %{--<g:if test="${accessInstanceList.contains(filsInstance)}">--}%
                                        %{--<li class="${filsInstance?.parent.id}" style="list-style-type:none">--}%
                                            %{--<g:message code="${filsInstance?.label}"--}%
                                                       %{--default="${filsInstance?.label}"/>--}%
                                            %{--<div id="coursRegNode${filsInstance?.id}">--}%

                                                %{--<g:if test="${filsInstance?.menuMapFils}">--}%
                                                    %{--<ul>--}%
                                                        %{--<g:each in="${filsInstance?.menuMapFils}" status="m" var="fils">--}%
                                                            %{--<g:if test="${accessInstanceList.contains(fils)}">--}%
                                                                %{--<li class="f${fils?.parent.id}"--}%
                                                                    %{--style="list-style-type:none;">--}%
                                                                    %{--<g:message code="${fils?.label}"--}%
                                                                               %{--default="${fils?.label}"/>--}%
                                                                %{--</li>--}%
                                                            %{--</g:if>--}%
                                                        %{--</g:each>--}%
                                                    %{--</ul>--}%
                                                %{--</g:if>--}%

                                            %{--</div>--}%
                                        %{--</li>--}%
                                    %{--</g:if>--}%
                                %{--</g:each>--}%
                            %{--</ul>--}%
                        %{--</div>--}%
                    %{--</li>--}%
                %{--</g:if>--}%
            %{--</g:each>--}%
        %{--</ul>--}%

    %{--</div>--}%

%{--</fieldset>--}%

<div class="scrolling">
    <g:each in="${parentInstanceList}" status="j" var="parentInstance">
    <g:if test="${!accessInstanceList.intersect(parentInstance?.menuMapFils).empty}">

        <fieldset class="m-b-0">
            <legend>
                <p style="font-size: 13px;" class="bold">
                    <g:message code="${parentInstance?.label}.label"/>
                </p>
            </legend>

            <div id="filsNode${parentInstance.id}">

                <div class="col-md-12 col-sm-12 col-xs-12">
                    <g:each in="${parentInstance?.menuMapFils}" status="k"
                            var="filsInstance">
                        <div class="col-md-4 col-sm-4 col-xs-4">
                            <input type="hidden" name="url.id" value="${filsInstance.id}"/>

                            <div class="f${filsInstance?.parent.id}" style="list-style-type:none">
                                <div title="${filsInstance?.url}">
                                    <g:hiddenField name="choix_${filsInstance.id}" value="${filsInstance}"/>
                                    <g:if test="${(accessInstanceList.find { it.url == filsInstance.url } != "" && accessInstanceList.find { it.url == filsInstance.url } != null)}">
                                        <input type="checkbox" name="access.${filsInstance.id}" checked disabled
                                               style="width: 10px; display: inline"
                                               class="choix${parentInstance.id}"/>
                                    </g:if>
                                    <g:else>
                                        <input type="checkbox" name="access.${filsInstance.id}" disabled
                                               style="width: 10px; display: inline"
                                               class="choix${parentInstance.id}"/>
                                    </g:else>
                                    <g:message code="${filsInstance?.label}"
                                               default="${filsInstance?.label}"/>
                                    %{--<g:if--}%
                                            %{--test="${messageList.contains('menumap.' + filsInstance?.label) || (filsInstance?.defaultMessage == null || filsInstance?.defaultMessage == '')}">--}%
                                        %{--<g:message code="menumap.${filsInstance?.label}"--}%
                                                   %{--default="${filsInstance?.url}"/>--}%
                                    %{--</g:if>--}%
                                    %{--<g:else>--}%
                                        %{--${filsInstance?.defaultMessage}--}%
                                    %{--</g:else>--}%
                                </div>

                                <div id="coursRegNode${filsInstance?.id}">

                                    <ul>
                                        <g:each in="${filsInstance.menuMapFils}" status="m"
                                                var="fils">
                                            <input type="hidden" name="url.id" value="${fils.id}"/>

                                            <div class="f${fils?.parent.id}"
                                                 style="list-style-type:none;" title="${fils?.url}">
                                                <g:hiddenField name="coursRegNode_${fils.id}" value="${fils}"/>

                                                <g:if test="${(accessInstanceList.find { it.url == fils.url } != "" && accessInstanceList.find { it.url == fils.url } != null) }">
                                                    <input type="checkbox" name="access.${fils.id}" checked disabled
                                                           style="width: 10px; display: inline"
                                                           class="choix${parentInstance.id}"/>
                                                </g:if>
                                                <g:else>
                                                    <input type="checkbox" name="access.${fils.id}" disabled
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

    </g:if>

    </g:each>
</div>
