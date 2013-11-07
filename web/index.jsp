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

    <ui:composition template="./template/portal_template.xhtml">
        <ui:define name="content" >
            <h3>P�gina Principal</h3>
            <span>
                Bem-Vindo ao <strong>Problem Database Manager</strong>. Trata-se de uma ferramenta que possibilita a
                elabora��o e o compartilhamento de problemas a serem aplicados em
                disciplinas que se utilizem da metodologia PBL (<em>Problem Based
                    Learning</em>). Os problemas j� desenvolvidos ficam armazenados em
                uma base de dados e � poss�vel realizar buscas a partir de
                diversas informa��es de interesse como palavras-chaves, semestre, disciplina
                entre outras. O sistema tamb�m permite a impress�o de problemas para
                uso nas sess�es tutoriais.<br></br>
                Para come�ar a utilizar o sistema utilize os menus de navega��o.
            </span>
            <h4>Informa��es �teis</h4>
            <ul id="news">
                <li>O sistema permite que qualquer professor crie um novo problema.</li>
                <li>Qualquer professor registrado no sistema poder� editar um problema ou criar um novo apartir dele.</li>
                <li>O professor pode usar a busca para localizar problemas mais facilmente.</li>
            </ul>
        </ui:define>
    </ui:composition>
</html>

