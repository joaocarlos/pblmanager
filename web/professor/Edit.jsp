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
            <h3>Atualizando Usuário</h3><br/>
            <h:form>
                <label>Nome</label><br />
                <h:inputText maxlength="80" class="text"
                             value="#{professor.professor.nome}"
                             required="true" requiredMessage="#{messages.nome}"/>
                <br />
                <label>Senha</label><br />
                <h:inputSecret maxlength="40" style="width: 100px" class="text"
                               value="#{professor.professor.senha}"
                               required="true" requiredMessage="#{messages.senha}"/><br />
                <label>Confirme a Senha</label><br />
                <h:inputSecret class="text" maxlength="40" style="width: 100px"
                               value="#{professor.professor.confirmaSenha}"
                               required="true" requiredMessage="#{messages.confirmaSenha}"/><br />                
                <div class="summaryButton">
                    <div class="buttons">
                        <h:commandLink action="#{professor.listSetup}" class="negative" immediate="true">
                            <img src="#{request.contextPath}/template/images/cross.png" alt=""/>Cancelar
                        </h:commandLink>
                        <h:commandLink action="#{professor.edit}">
                            <img src="#{request.contextPath}/template/images/save.png" alt=""/>Salvar
                            <f:param name="jsfcrud.currentProfessor" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][professor.professor][professor.converter].jsfcrud_invoke}"/>

                        </h:commandLink>
                    </div>
                    <div class="clear"></div>
                </div>
            </h:form>
        </ui:define>
    </ui:composition>
</html>