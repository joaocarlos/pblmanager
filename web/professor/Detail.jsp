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
            <h3>Dados do Usuário</h3><br/>
            <h:form>
                <label>Nome:</label><br />
                <h:outputText value="#{professor.professor.nome}" title="Nome" /><br /><br />
                <label>Login:</label><br />
                <h:outputText value="#{professor.professor.login}" title="Login" /><br /><br />
                <label>Grupo:</label><br />
                <h:outputText value="#{professor.professor.grupo.nome}" title="Grupo" /><br /><br />
                <div class="summaryButton">
                    <div class="buttons">
                        <h:outputLink value="#" id="link" styleClass="negative" rendered="#{professor.professor.nome != professor.professorItems3 and professor.render}">
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
                            <h:outputText value="Você tem certeza de que deseja excluir esse usuário?"></h:outputText>
                            <br/><br/>
                            <h:commandLink action="#{professor.destroy}" value="Sim" styleClass="button" >
                                <f:param name="jsfcrud.currentProfessor" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][professor.professor][professor.converter].jsfcrud_invoke}" />
                            </h:commandLink>
                            <rich:spacer width="15%"></rich:spacer>
                            <h:commandLink onclick="#{rich:component('panel')}.hide()" value="Não" styleClass="button"/><br/>
                        </rich:modalPanel>
                        <h:commandLink action="#{professor.editSetup}" rendered="#{(professor.professor.nome == professor.professorItems3) || professor.render}">
                            <img src="#{request.contextPath}/template/images/edit.png" alt=""/>Editar
                            <f:param name="jsfcrud.currentProfessor" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][professor.professor][professor.converter].jsfcrud_invoke}" />
                        </h:commandLink>
                        <h:commandLink action="#{professor.listSetup}" value="Listar Usuários"/>
                    </div>
                    <div class="clear"></div>
                </div>
            </h:form>
        </ui:define>
    </ui:composition>
</html>
