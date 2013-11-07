<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE composition PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:jsp="http://java.sun.com/JSP/Page"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:rich="http://richfaces.org/rich">

    <ui:composition template="../template/portal_template.xhtml">
        <ui:define name="head">
            <link href="${request.contextPath}/template/css/tablecloth.css" rel="stylesheet" type="text/css" media="screen"/>
            <script type="text/javascript" src="${request.contextPath}/template/js/tablecloth.js" /> <!-- Menu dropdown -->
        </ui:define>
        <ui:define name="content" >
            <h3>Listagem de Disciplinas</h3><br/>
            <h:form id="lista_disciplinas">
                <h:dataTable id="tab_disciplinas"
                             value="${disciplina.disciplinaItems}"
                             var="item">
                    <h:column>
                        <f:facet name="header">
                            <label>Nome da Disciplina</label>
                        </f:facet><center>
                            <h:outputText value="${item.nome}"/>
                        </center>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <label>Código</label>
                        </f:facet>
                        <center>
                            <h:outputText value="${item.codigo}"/>
                        </center>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <label>Departamento</label>
                        </f:facet><center>
                            <h:outputText value="${item.departamento}"/>
                        </center>
                    </h:column>
                    <h:column rendered="${professor.render}">
                        <f:facet name="header">
                            <label>Opções</label>
                        </f:facet>
                        <center>
                            <h:commandLink action="${disciplina.detailSetup}">
                                <img src="${request.contextPath}/template/images/file.png" alt="Exibir Detalhes" />
                                <f:param name="jsfcrud.currentDisciplina" value="${jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][disciplina.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:commandLink action="${disciplina.editSetup}" rendered="${professor.render}" >
                                <img src="${request.contextPath}/template/images/file_edit.png" alt="Editar" />
                                <f:param name="jsfcrud.currentDisciplina" value="${jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][disciplina.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputLink value="#" id="link" rendered="${professor.render}" >
                                <img src="${request.contextPath}/template/images/delete.png" alt="Apagar" />
                                <rich:componentControl for="panel" attachTo="link" operation="show" event="onclick"/>
                            </h:outputLink>
                            <rich:modalPanel id="panel" styleClass="painel" width="350" height="130">
                                <f:facet name="header">
                                    <h:panelGroup>
                                        <label>Atenção</label>
                                    </h:panelGroup>
                                </f:facet>
                                <f:facet name="controls">
                                    <h:commandLink onclick="${rich:component('panel')}.hide()" id="fechar" value="[X]"/>
                                </f:facet>
                                <h:outputText value="Você tem certeza de que deseja excluir esta disciplina?"/>
                                <br /><br />
                                <h:commandLink value="Sim" styleClass="button" id="apagerDisciplina" action="${disciplina.destroy}">
                                    <f:param name="jsfcrud.currentDisciplina" value="${jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][disciplina.converter].jsfcrud_invoke}"/>
                                </h:commandLink>
                                <rich:spacer width="1%"></rich:spacer>
                                <h:commandLink onclick="${rich:component('panel')}.hide()" id="naoApagar" value="Não" styleClass="button"/><br/>
                            </rich:modalPanel>
                        </center>
                    </h:column>
                </h:dataTable>                
                <div class="summaryButton">
                    <div class="buttons">
                        <h:commandLink action="${disciplina.createSetup}" styleClass="positive" rendered="${professor.render}">
                            <img src="#{request.contextPath}/template/images/new_file.png" alt=""/>Nova Disciplina
                        </h:commandLink>
                    </div>
                    <div class="clear"></div>
                </div>
            </h:form>
        </ui:define>
    </ui:composition>
</html>
