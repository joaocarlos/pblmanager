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
        <h:form>
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
            <title>Problem Database Manager - UEFS</title>
            <link href="${request.contextPath}/template/css/reset.css" rel="stylesheet" type="text/css" media="screen, print"/>
            <link href="${request.contextPath}/template/css/relatorio.css" rel="stylesheet" type="text/css" media="screen,print"/>
            <style type="text/css" media="print">
                .oculta { visibility: hidden; }
            </style>
        </h:form>
    </head>

    <h:form id="relatorio">
        <div id="root">
            <div id="head">
                <img src="${request.contextPath}/problema/img/brasao_uefs.jpeg" align="center" border="0" alt="UEFS" />
                <strong class="head_title">Universidade Estadual de Feira de Santana</strong>
                <em class="head_title">Problem Based Learning</em>
                <span class="head_title"><h:outputText value="${problema.problema.disciplina.nome}" title="Disciplina"  escape="false" /></span>
            </div>
            <div id="problem_title">
                <strong><h:outputText value="${problema.problema.tituloProblema}" title="TituloProblema" /></strong>
            </div>
            <div id="detail">
                <fieldset id="problem_detail">
                    <legend>Detalhes do Problema</legend>
                    <strong class="table_title">Tema: </strong>
                    <span class="table_description"><h:outputText value="${problema.problema.assunto}" title="Tema" /></span>
                    <strong class="table_title">Descrição: </strong>
                    <span class="table_description"><h:outputText value="${problema.problema.descricao}" title="Descricao"  escape="false"/></span>
                    <div class="clear"></div>
                </fieldset>
                <fieldset id="cronograma">
                    <legend>Cronograma</legend>
                    <h:panelGroup>
                        <span class="table_description"><h:outputText rendered="${empty problema.problema.cronograma.atividades}" value="Não há nenhum item."/></span>
                        <h:dataTable value="${problema.problema.cronograma.atividades}" var="item" width="100%"
                                     rowClasses="odd_row, even_row" styleClass="cron"
                                     rendered="${not empty problema.problema.cronograma.atividades}">
                            <h:column>           
                                <h:outputText value="${item.descricaoAtividade}"/>
                            </h:column>
                            <h:column>
                                <center>
                                    <h:outputText value="${item.dataAtividade}"><f:convertDateTime pattern="dd/MM/yyyy" /></h:outputText>
                                </center>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                </fieldset>
                <fieldset id="produtos">
                    <legend>Produtos</legend>
                    <h:outputText value="${problema.problema.produtos}" title="Produtos" escape="false"/>
                </fieldset>
                <fieldset id="recursos">
                    <legend>Recursos de Aprendizagem</legend>
                    <h:panelGroup>
                        <span class="table_description"><h:outputText rendered="${empty problema.problema.recursos}" value="Não há nenhum recurso."/></span>
                        <h:dataTable value="${problema.problema.recursos}" var="item"
                                     rowClasses="odd_row, even_row" rules="all"
                                     rendered="${not empty problema.problema.recursos}">
                            <h:column>
                                <h:outputText value="- ${item.recurso}"/>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                </fieldset>
            </div>
            <div id="images">
                <h:panelGrid id="figuras" columns="1">
                    <rich:panel>
                        <h:outputText value="Não foi realizado upload de nenhum arquivo"
                                      rendered="${problema.problema.imagensAssociadas == null}" />
                        <rich:dataGrid columns="1" value="${problema.problema.imagensAssociadas}"
                                       id="imagens" var="item" rowKeyVar="row">
                            <h:panelGrid columns="1">
                                <a4j:mediaOutput element="img" mimeType="jpg,bmp,gif,png"
                                                 createContent="${problema.paintLivre2}" value="imagem"
                                                 cacheable="false">
                                    <f:param value="${item.referencia}" name="path"/>
                                </a4j:mediaOutput>
                            </h:panelGrid>
                        </rich:dataGrid>
                    </rich:panel>
                </h:panelGrid>
            </div>
            <div class="imprimir">
                <input name="buttonImprimir" class="oculta" type="button" onclick="javascript:window.print()"  value="Imprimir Relatório" action="imprimir" />
            </div>
        </div>
    </h:form>
</html>
