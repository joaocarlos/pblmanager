<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE composition PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:jsp="http://java.sun.com/JSP/Page"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:a4j="http://richfaces.org/a4j"
      xmlns:rich="http://richfaces.org/rich">

    <ui:composition template="../template/portal_template.xhtml">
        <ui:define name="content" >
            <h3>Dados da Disciplina</h3>
            <h:form>
                <br/><label>Nome:</label><br/>
                <h:outputText value="#{disciplina.disciplina.nome}" title="Nome" /><br/><br/><br/>
                <label>Código:</label><br/>
                <h:outputText value="#{disciplina.disciplina.codigo}" title="Codigo" /><br/><br/><br/>
                <label>Departamento:</label><br/>
                <h:outputText value="#{disciplina.disciplina.departamento}" title="Departamento" /><br />
                <div class="summaryButton">
                    <div class="buttons">
                        <h:outputLink value="#" id="link" styleClass="negative" rendered="#{professor.render}" >
                            <img src="#{request.contextPath}/template/images/cross.png" alt=""/>Apagar
                            <rich:componentControl for="panel" attachTo="link" operation="show" event="onclick"/>
                        </h:outputLink>
                        <rich:modalPanel id="panel" width="350" height="100">
                            <f:facet name="header">
                                <h:panelGroup>
                                    <rich:spacer width="3%"></rich:spacer>
                                    <label>Atenção</label>
                                </h:panelGroup>
                            </f:facet>
                            <f:facet name="controls">
                                <h:commandLink onclick="#{rich:component('panel')}.hide()" value="[X]"/>
                            </f:facet>
                            <h:outputText value="Você tem certeza de que deseja excluir essa disciplina?"></h:outputText>
                            <br/><br/>
                            <h:commandLink action="#{disciplina.destroy}" value="Sim" styleClass="button">
                                <f:param name="jsfcrud.currentDisciplina" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][disciplina.disciplina][disciplina.converter].jsfcrud_invoke}" />
                            </h:commandLink>
                            <rich:spacer width="15%"></rich:spacer>
                            <h:commandLink onclick="#{rich:component('panel')}.hide()" value="Não" styleClass="button"/><br/>
                        </rich:modalPanel>
                        <h:commandLink action="#{disciplina.editSetup}" rendered="#{professor.render}" >
                            <img src="#{request.contextPath}/template/images/edit.png" alt=""/>Editar
                            <f:param name="jsfcrud.currentDisciplina" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][disciplina.disciplina][disciplina.converter].jsfcrud_invoke}" />
                        </h:commandLink>
                        <h:commandLink action="#{disciplina.listSetup}" value="Listar disciplinas"/>
                    </div>
                    <div class="clear"></div>
                </div>
            </h:form>
        </ui:define>
    </ui:composition>
</html>
