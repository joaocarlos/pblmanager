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

    <head>
        <script language="JavaScript">
        </script>
        <style type="text/css">
            .caixa_alta{
                text-transform: uppercase;
            }
        </style>
    </head>

    <ui:composition template="../template/portal_template.xhtml">
        <ui:define name="content" >
            <h3>Novo Usuário</h3><br/>
            <h:form>
                <h:inputHidden id="validateCreateField" validator="#{professor.validateCreate}" value="value"/>
                <label>Nome completo</label><br />
                <h:inputText style="width:65%" maxlength="80" class="text"
                             value="#{professor.professor.nome}"
                             required="true" requiredMessage="#{messages.nome}"/>
                <br />
                <label>Login</label><br />
                <h:inputText style="width:20%" maxlength="40" class="caixa_alta text"
                             value="#{professor.professor.login}"
                             required="true" requiredMessage="#{messages.login}"/>
                <br />
                <label>Senha</label><br />
                <h:inputSecret style="width:20%" maxlength="40" class="text"
                               value="#{professor.professor.confirmaSenha}"
                               required="true" requiredMessage="#{messages.confirmaSenha}"/><br />
                <label>Confirme a Senha</label><br />
                <h:inputSecret style="width:20%" maxlength="40" class="text"
                               value="#{professor.professor.senha}"
                               required="true" requiredMessage="#{messages.senha}"/>
                <br />
                <div class="summaryButton">
                    <div class="buttons">
                        <h:commandLink action="#{professor.listSetup}" class="negative" immediate="true">
                            <img src="#{request.contextPath}/template/images/cross.png" alt=""/>Cancelar
                        </h:commandLink>
                        <h:commandLink action="#{professor.create}" class="positive">
                            <img src="#{request.contextPath}/template/images/tick.png" alt=""/>Cadastrar
                        </h:commandLink>
                    </div>
                    <div class="clear"></div>
                </div>
            </h:form>
        </ui:define>
    </ui:composition>
</html>