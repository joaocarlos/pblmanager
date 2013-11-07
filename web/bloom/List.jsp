<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Listing Bloom Items</title>
            <link rel="stylesheet" type="text/css" href="/RepositorioProblemas/faces/jsfcrud.css" />
        </head>
        <body>
        <h:panelGroup id="messagePanel" layout="block">
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
        </h:panelGroup>
        <h1>Listing Bloom Items</h1>
        <h:form styleClass="jsfcrud_list_form">
            <h:outputText escape="false" value="(No Bloom Items Found)<br />" rendered="#{bloom.pagingInfo.itemCount == 0}" />
            <h:panelGroup rendered="#{bloom.pagingInfo.itemCount > 0}">
                <h:outputText value="Item #{bloom.pagingInfo.firstItem + 1}..#{bloom.pagingInfo.lastItem} of #{bloom.pagingInfo.itemCount}"/>&nbsp;
                <h:commandLink action="#{bloom.prev}" value="Previous #{bloom.pagingInfo.batchSize}" rendered="#{bloom.pagingInfo.firstItem >= bloom.pagingInfo.batchSize}"/>&nbsp;
                <h:commandLink action="#{bloom.next}" value="Next #{bloom.pagingInfo.batchSize}" rendered="#{bloom.pagingInfo.lastItem + bloom.pagingInfo.batchSize <= bloom.pagingInfo.itemCount}"/>&nbsp;
                <h:commandLink action="#{bloom.next}" value="Remaining #{bloom.pagingInfo.itemCount - bloom.pagingInfo.lastItem}"
                               rendered="#{bloom.pagingInfo.lastItem < bloom.pagingInfo.itemCount && bloom.pagingInfo.lastItem + bloom.pagingInfo.batchSize > bloom.pagingInfo.itemCount}"/>
                <h:dataTable value="#{bloom.bloomItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Analisar"/>
                        </f:facet>
                        <h:outputText value="#{item.analisar}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Aplicar"/>
                        </f:facet>
                        <h:outputText value="#{item.aplicar}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Avaliar"/>
                        </f:facet>
                        <h:outputText value="#{item.avaliar}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Criar"/>
                        </f:facet>
                        <h:outputText value="#{item.criar}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Entender"/>
                        </f:facet>
                        <h:outputText value="#{item.entender}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Id"/>
                        </f:facet>
                        <h:outputText value="#{item.id}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="IdProblem"/>
                        </f:facet>
                        <h:outputText value="#{item.idProblem}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Relembrar"/>
                        </f:facet>
                        <h:outputText value="#{item.relembrar}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText escape="false" value="&nbsp;"/>
                        </f:facet>
                        <h:commandLink value="Show" action="#{bloom.detailSetup}">
                            <f:param name="jsfcrud.currentBloom" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][bloom.converter].jsfcrud_invoke}"/>
                        </h:commandLink>
                        <h:outputText value=" "/>
                        <h:commandLink value="Edit" action="#{bloom.editSetup}">
                            <f:param name="jsfcrud.currentBloom" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][bloom.converter].jsfcrud_invoke}"/>
                        </h:commandLink>
                        <h:outputText value=" "/>
                        <h:commandLink value="Destroy" action="#{bloom.destroy}">
                            <f:param name="jsfcrud.currentBloom" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][bloom.converter].jsfcrud_invoke}"/>
                        </h:commandLink>
                    </h:column>
                </h:dataTable>
            </h:panelGroup>
            <br />
            <h:commandLink action="#{bloom.createSetup}" value="New Bloom"/>
            <br />
            <h:commandLink value="Index" action="welcome" immediate="true" />

        </h:form>
        </body>
    </html>
</f:view>
