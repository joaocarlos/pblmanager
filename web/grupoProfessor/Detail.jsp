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
            <h3>Dados do Grupo</h3><br/>
            <h:form>
                <label>Nome:</label><br/>
                <h:outputText value="#{grupoProfessor.grupoProfessor.nome}" title="Nome" /><br/><br/><br/>
                <div class="summaryButton" align="right">
                    <h:outputLink value="#" id="link" rendered="#{!professor.render}" styleClass="button">
                        Apagar
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
                        <h:outputText value="Você tem certeza de que deseja excluir esse grupo?"></h:outputText>
                        <br/><br/>
                        <h:commandLink action="#{grupoProfessor.destroy}" value="Sim" styleClass="button">
                            <f:param name="jsfcrud.currentGrupoProfessor" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][grupoProfessor.grupoProfessor][grupoProfessor.converter].jsfcrud_invoke}" />
                        </h:commandLink>                        
                        <rich:spacer width="15%"></rich:spacer>
                        <h:commandLink onclick="#{rich:component('panel')}.hide()" value="Não" styleClass="button"/><br/>
                    </rich:modalPanel>
                    <h:outputText value="    "/>
                    <h:commandLink action="#{grupoProfessor.editSetup}" value="Editar" styleClass="button" rendered="#{!professor.render}" >
                        <f:param name="jsfcrud.currentGrupoProfessor" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][grupoProfessor.grupoProfessor][grupoProfessor.converter].jsfcrud_invoke}" />
                    </h:commandLink>
                            <h:outputText value="    "/>
                            <h:commandLink action="#{grupoProfessor.listSetup}" value="Listar grupos" styleClass="button"/>
                        </div>
                    </h:form>
          </ui:define>
      </ui:composition>
</html>
