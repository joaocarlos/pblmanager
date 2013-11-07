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
            <link href="${request.contextPath}/template/css/relatorio.css" rel="stylesheet" type="text/css" media="screen, print"/>
            <style type="text/css" media="print">
                .oculta { visibility: hidden; }
            </style>
        </h:form>
    </head>

    <h:form>
        <div id="root">
            <div id="head">
                <img src="${request.contextPath}/problema/img/brasao_uefs.jpeg" align="center" border="0" alt="UEFS" />
                <strong class="head_title">Universidade Estadual de Feira de Santana</strong>
                <em class="head_title">Problem Based Learning</em>
                <span class="head_title">
                    <h:outputText value="${problema.problema.disciplina.codigo}" title="Disciplina"  escape="false" />-
                    <h:outputText value="${problema.problema.disciplina.nome}" title="Disciplina"  escape="false" /></span>
            </div>
            <div id="problem_title">
                <strong><h:outputText value="${problema.problema.tituloProblema}" title="TituloProblema" /></strong>
            </div>
            <div id="detail">
                <fieldset id="info">
                    <legend>Informações</legend>
                    <strong class="table_title">Autor: </strong>
                    <span class="table_description"><h:outputText value="${problema.problema.professorAutor.nome}" title="Nome do autor" /></span>
                    <strong class="table_title">Semestre: </strong>
                    <span class="table_description" style="width: 50px"><h:outputText value="${problema.problema.semestreCriacao}" title="Semestre" /></span>
                    <div class="spacer"></div>
                    <strong class="table_title">Data de Criação: </strong>
                    <span class="table_description"><h:outputText value="${problema.problema.dataCriacao}" title="Data de criação"><f:convertDateTime pattern="dd/MM/yyyy" /></h:outputText></span>
                </fieldset>
                <fieldset id="problem_detail">
                    <legend>Detalhes do Problema</legend>
                    <strong class="table_title">Tema: </strong>
                    <span class="table_description"><h:outputText value="${problema.problema.assunto}" title="Tema" /></span>
                    <strong class="table_title">Descrição: </strong>
                    <span class="table_description"><h:outputText value="${problema.problema.descricao}" title="Descrição"  escape="false"/></span>
                    <div class="clear"></div>
                    <strong class="table_title">Palavras-chave: </strong>
                    <h:panelGroup>
                        <span class="table_description"><h:outputText rendered="${empty problema.problema.palavrasChave}" value="Não há nenhuma palavra-chave armazenada."/></span>
                        <h:dataTable value="${problema.problema.palavrasChave}" var="item"
                                     rules="all" rendered="${not empty problema.problema.palavrasChave}">
                            <h:column>
                                <em><h:outputText value="${item.palavraChave}"/></em>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                </fieldset>
                <fieldset id="cronograma">
                    <legend>Cronograma</legend>
                    <h:panelGroup>
                        <span class="table_description"><h:outputText rendered="${empty problema.problema.cronograma.atividades}" value="Não há nenhum item registrado no cronograma."/></span>
                        <h:dataTable value="${problema.problema.cronograma.atividades}" var="item" width="100%"
                                     rowClasses="odd_row, even_row"
                                     rendered="${not empty problema.problema.cronograma.atividades}">
                            <h:column>
                                <h:outputText value="${item.descricaoAtividade}"/>
                            </h:column>
                            <h:column>
                                <center>
                                    <rich:spacer width="5"></rich:spacer>
                                    <h:outputText style="text-align: center;" value="${item.dataAtividade}"><f:convertDateTime pattern="dd/MM/yyyy" /></h:outputText>
                                    <rich:spacer width="5"></rich:spacer>
                                </center>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                </fieldset>
                <fieldset id="objetivos">
                    <legend>Objetivos</legend>
                    <h:panelGroup>
                        <h:outputText rendered="${empty problema.problema.objetivos}" value="Não há nenhum objetivo armazenado."/>
                        <h:dataTable value="${problema.problema.objetivos}" var="item"
                                     rowClasses="odd_row, even_row" rules="all"
                                     rendered="${not empty problema.problema.objetivos}">
                            <h:column>
                                <h:outputText value="- ${item.objetivo}"/>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                </fieldset>
                <fieldset id="crit_avaliacao">
                    <legend>Critérios de Avaliação</legend>
                    <span class="table_description"><h:outputText rendered="${empty problema.problema.avaliacaoAlunos}" value="Não há nenhum item de avaliação registrado."/></span>
                    <span class="table_description"><h:outputText value="${problema.problema.avaliacaoAlunos}" title="AvaliacaoAlunos" escape="false"/></span>
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
                <fieldset id="produtos">
                    <legend>Produtos</legend>
                    <span class="table_description"><h:outputText rendered="${empty problema.problema.produtos}" value="Não há nenhum produto definido."/></span>
                    <h:outputText value="${problema.problema.produtos}" title="Produtos" escape="false"/>
                </fieldset>
                <fieldset id="orient">
                    <legend>Orientações aos Tutores</legend>
                    <span class="table_description"><h:outputText rendered="${empty problema.problema.orientacoesTutor}" value="Não há nenhuma orientação específica aos tutores."/></span>
                    <h:outputText value="${problema.problema.orientacoesTutor}" title="Orientação aos Tutores"  escape="false"/>
                </fieldset>
                <fieldset id="objetivos">
                    <legend>Objetivos</legend>
                    <h:panelGroup>
                        <span class="table_description"><h:outputText rendered="${empty problema.problema.objetivos}" value="Nenhum objetivo foi definido para este problema."/></span>
                        <h:dataTable value="${problema.problema.objetivos}" var="item"
                                     rowClasses="odd_row, even_row" rules="all"
                                     rendered="${not empty problema.problema.objetivos}">
                            <h:column>
                                <h:outputText value="- ${item.objetivo}"/>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                </fieldset>                
                <fieldset id="taxonomia">
                    <legend>Taxonomia de Bloom</legend>
                    <strong class="strtitle">Relembrar </strong>
                    <span class="table_description"><h:outputText value="${problema.problema.bloom.relembrar}" title="Bloom Relembrar" escape="false"/></span>
                    <strong class="strtitle">Entender </strong>
                    <span class="table_description"><h:outputText value="${problema.problema.bloom.entender}" title="Bloom Entender" escape="false"/></span>
                    <strong class="strtitle">Aplicar </strong>
                    <span class="table_description"><h:outputText value="${problema.problema.bloom.aplicar}" title="Bloom Aplicar" escape="false"/></span>
                    <strong class="strtitle">Analisar </strong>
                    <span class="table_description"><h:outputText value="${problema.problema.bloom.analisar}" title="Bloom Analisar" escape="false"/></span>
                    <strong class="strtitle">Avaliar </strong>
                    <span class="table_description"><h:outputText value="${problema.problema.bloom.avaliar}" title="Bloom Avaliar" escape="false"/></span>
                    <strong class="strtitle">Criar </strong>
                    <span class="table_description"><h:outputText value="${problema.problema.bloom.criar}" title="Bloom Criar" escape="false"/></span>
                </fieldset>
                <div id="images">
                    <h:panelGrid columns="1" columnClasses="top" id="painelImagens">
                        <rich:panel bodyClass="info" style="border:none;" id="imagensAssociadas">
                            <h:outputText value="Não foi realizado upload de nenhum arquivo"
                                          rendered="${problema.problema.imagensAssociadas == null}" />
                            <rich:dataGrid columns="1" value="${problema.problema.imagensAssociadas}"
                                           id ="imagens" var="item" rowKeyVar="row">
                                <h:panelGrid columns="1">
                                    <a4j:mediaOutput element="img" mimeType="jpg,bmp,gif,png"
                                                     createContent="${problema.paintLivre2}" value="imagem"
                                                     cacheable="false">
                                        <f:param value="${item.referencia}" name="path"/>
                                    </a4j:mediaOutput>
                                </h:panelGrid>
                            </rich:dataGrid>
                        </rich:panel>
                        <rich:spacer height="3"/>
                    </h:panelGrid>
                </div>
                <div class="imprimir">
                    <input name="buttonImprimir" class="oculta" type="button" onclick="javascript:window.print()"  value="Imprimir Relatório" action="imprimir" />
                </div>
            </div>
        </div>
    </h:form>
</html>
