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

    <ui:composition template="./template/login_template.xhtml">
        <ui:define name="contentLogin">
            <strong class="login_welcome">Bem vindo ao PBL Manager!</strong>
            <h:form id="login_form" >
                <fieldset class="login" style="width: 250px; float: left;">
                    <div style="background: #f3f3f3;">
                        <br />
                        <span class="text">Entrar no <strong>PBL Manager</strong><br />
                            Engenharia de Computação</span><br />
                        <label class="login">Login: </label><h:inputText styleClass="text login" id="in_login" maxlength="40" value="#{professor.loginBusca}" /><br />
                        <label class="login">Senha: </label><h:inputSecret styleClass="text login" id="in_senha" maxlength="40" value="#{professor.senhaBusca}"/><br />
                        <p class="botao" align="center">
                            <center><h:commandButton action="#{professor.buscaLogin}" id="in_button" value="Entrar" /></center>
                        </p>
                    </div>
                </fieldset>
                <div id="mensagens_login">
                    <span>O PBL Manager possibilita a elaboração e o compartilhamento de problemas utilizados em
                        disciplinas da metodologia PBL.</span>
                    <ul>
                        <li>
                            <div class="crie"></div>
                            <div class="log_msg" >
                                <strong>Crie</strong><br />
                                <span>Seja criativo e desenvolva novos problemas!</span>
                            </div>
                            <div class="clear"></div>
                        </li>
                        <li>
                            <div class="share"></div>
                            <div class="log_msg" >
                                <strong>Compartilhe</strong><br />
                                <span>Acesso à todos os problemas produzidos por sua equipe.</span>
                            </div>
                            <div class="clear"></div>
                        </li>
                        <li>
                            <div class="aprimore"></div>
                            <div class="log_msg" >
                                <strong>Aprimore</strong><br />
                                <span>Crie novos problema com base em uma aplicação anterior.</span>
                            </div>
                            <div class="clear"></div>
                        </li>
                    </ul>
                </div>
                <div class="clear"></div>
            </h:form>
        </ui:define>        
    </ui:composition>
</html>

