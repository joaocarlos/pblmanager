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
        <ui:define name="content" >
           <h3>Listagem de Grupos</h3><br/>
                        <h:form >
                            <center><h:dataTable value="#{grupoProfessor.grupoProfessorItems}" var="item"
                                                 width="100%" rowClasses="jsfcrud_odd_row,jsfcrud_even_row"
                                                 style="border:appworkspace solid 1px;"
                                                 border="4" cellpadding="1" cellspacing="2px">
                                <h:column>
                                    <f:facet name="header">
                                        <label>Nome</label>
                                    </f:facet><center>
                                    <rich:spacer width="5"></rich:spacer>
                                    <h:outputText value="#{item.nome}"/>
                                    <rich:spacer width="5"></rich:spacer></center>
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <label>Opções</label>
                                    </f:facet><center>
                                    <rich:spacer width="5"></rich:spacer>
                                    <h:commandLink value="Exibir" action="#{grupoProfessor.detailSetup}">
                                        <f:param name="jsfcrud.currentGrupoProfessor" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][grupoProfessor.converter].jsfcrud_invoke}"/>
                                    </h:commandLink>
                                    <rich:spacer width="5"></rich:spacer>
                                    <h:commandLink value="Editar" action="#{grupoProfessor.editSetup}" rendered="#{!professor.render}" >
                                        <f:param name="jsfcrud.currentGrupoProfessor" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][grupoProfessor.converter].jsfcrud_invoke}"/>
                                    </h:commandLink>
                                    <rich:spacer width="5"></rich:spacer>
                                    <h:outputLink value="#" id="link" rendered="#{!professor.render}">
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
                                        <h:commandLink value="Sim" styleClass="button" action="#{grupoProfessor.destroy}">
                                            <f:param name="jsfcrud.currentGrupoProfessor" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][grupoProfessor.converter].jsfcrud_invoke}"/>
                                        </h:commandLink>                                        
                                        <rich:spacer width="15%"></rich:spacer>
                                        <h:commandLink onclick="#{rich:component('panel')}.hide()" value="Não" styleClass="button"/><br/>
                                    </rich:modalPanel>
                                    <rich:spacer width="5"></rich:spacer></center>
                                </h:column>
                            </h:dataTable><br/></center>
                        <div class="summaryButton" align="right">
                            <h:commandLink action="#{grupoProfessor.createSetup}" value="Novo grupo" styleClass="button" rendered="#{!professor.render}"/>
                            <h:outputText value="    "/>                            
                        </div>
                    </h:form>
        </ui:define>
    </ui:composition>
</html>
