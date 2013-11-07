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
            <h3>Novo Grupo</h3><br/>
                    <h:form>
                        <h:inputHidden id="validateCreateField" validator="#{grupoProfessor.validateCreate}" value="value"/>
                        <div class="field">
                            <label>Nome</label><br/><rich:spacer height="10px"></rich:spacer><br/>
                            <h:inputText size="55" maxlength="80"
                                         value="#{grupoProfessor.grupoProfessor.nome}"
                                         required="true" requiredMessage="#{messages.nome}"/>
                        </div><br/>
                        <div class="summaryButton" align="right">                            
                            <h:commandLink action="#{grupoProfessor.create}" value="Cadastrar" styleClass="button"/>
                            <h:outputText value="    "/>
                            <h:commandLink action="#{grupoProfessor.listSetup}" styleClass="button" value="Cancelar" immediate="true"/>
                        </div>
                    </h:form>
          </ui:define>
      </ui:composition>
</html>
