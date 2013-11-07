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
            <h3>Atualizando Disciplina</h3><br/>
            <h:form>
                <label>Nome</label><br />
                <h:inputText size="25" maxlength="50" class="text"
                             value="#{disciplina.disciplina.nome}"
                             required="true" requiredMessage="#{messages.nome}"/><br />

                <label>Codigo</label><br />
                <h:inputText size="25" maxlength="7" class="text"
                             value="#{disciplina.disciplina.codigo}"
                             required="true" requiredMessage="#{messages.codigo}"/>
                <br />
                <label>Departamento</label><br />
                <h:inputText size="25" maxlength="50" class="text"
                             value="#{disciplina.disciplina.departamento}"
                             required="true" requiredMessage="#{messages.departamento}"/>
                <br />
                <div class="summaryButton">
                    <div class="buttons">
                        <h:commandLink action="#{disciplina.listSetup}" class="negative" immediate="true">
                            <img src="#{request.contextPath}/template/images/cross.png" alt=""/>Cancelar
                        </h:commandLink>
                        <h:commandLink action="#{disciplina.edit}">
                            <img src="#{request.contextPath}/template/images/save.png" alt=""/>Salvar
                            <f:param name="jsfcrud.currentDisciplina" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][disciplina.disciplina][disciplina.converter].jsfcrud_invoke}"/>
                        </h:commandLink>
                    </div>
                    <div class="clear"></div>
                </div>
            </h:form>
        </ui:define>
    </ui:composition>
</html>
