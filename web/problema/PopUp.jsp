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
    <div class="summaryButton" align="center">
        <h3>Dados do Problema</h3>
    </div>

    <head> <title>Problem Database Manager </title>  </head>

    <h:form>
        <div id="texto">
        <center> <br/><br/><br/><h3>Título:</h3>
            <h:outputText value="#{problema.problema.tituloProblema}" title="TituloProblema" /><br/><br/><br/>
            <br/><br/><h3>Semestre:</h3>
            <h:outputText value="#{problema.problema.semestreCriacao}" title="SemestreCriacao" /><br/><br/><br/>
            <br/><br/><h3>Data de Criação:</h3>
            <h:outputText value="#{problema.problema.dataCriacao}" title="DataCriacao" >
                <f:convertDateTime pattern="dd/MM/yyyy" />
            </h:outputText><br/><br/><br/>
            <br/><br/><h3>Assunto:</h3>
            <h:outputText value="#{problema.problema.assunto}" title="Assunto" /><br/><br/><br/>
            <br/><br/><h3>Descrição:</h3>
            <h:outputText value="#{problema.problema.descricao}" title="Descricao" /><br/><br/><br/>
            <br/><br/><h3>Palavras-chave:</h3>
            <rich:spacer height="5px"/>
            <h:panelGroup>
                <h:outputText rendered="#{empty problema.problema.palavrasChave}" value="(No Items)"/>
                <rich:spacer width="30px">
                    <h:dataTable value="#{problema.problema.palavrasChave}" var="item"
                                 border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px"
                                 rendered="#{not empty problema.problema.palavrasChave}">
                        <h:column>
                            <h:outputText value="#{item.palavraChave}"/>
                        </h:column>
                    </h:dataTable></rich:spacer>
            </h:panelGroup><br/><br/><br/>
            <br/><br/><h3>Autor:</h3>
            <h:outputText value="#{problema.problema.professorAutor.nome}" title="ProfessorAutor" /><br/><br/><br/>
            <br/><br/><h3>Disciplina:</h3>
            <h:outputText value="#{problema.problema.disciplina.nome}" title="Disciplina" /><br/><br/><br/>
            <br/><br/><h3>Critérios para Avaliação:</h3>
            <h:outputText value="#{problema.problema.avaliacaoAlunos}" title="AvaliacaoAlunos" /><br/><br/><br/>
            <!-- Falta colocar o atributo imagem aqui-->
            <br/><br/><h3>Objetivos:</h3>
            <rich:spacer height="5px"/>
            <h:panelGroup>
                <h:outputText rendered="#{empty problema.problema.objetivos}" value="(No Items)"/>
                <h:dataTable value="#{problema.problema.objetivos}" var="item"
                             border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px"
                             rendered="#{not empty problema.problema.objetivos}">
                    <h:column>
                        <h:outputText value="#{item.objetivo}"/>
                    </h:column>
                </h:dataTable>
            </h:panelGroup><br/><br/>
            <br/><br/><h3>Orientações aos tutores:</h3>
            <h:outputText value="#{problema.problema.orientacoesTutor}" title="OrientacoesTutor" /><br/><br/><br/>
            <br/><br/><h3>Produtos:</h3>
            <rich:spacer height="5px"/>
            <h:panelGroup>
                <h:outputText rendered="#{empty problema.problema.produtos}" value="(No Items)"/>
                <h:dataTable value="#{problema.problema.produtos}" var="item"
                             border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px"
                             rendered="#{not empty problema.problema.produtos}">
                    <h:column>
                        <h:outputText value="#{item.produto}"/>
                    </h:column>
                </h:dataTable>
            </h:panelGroup><br/><br/>
            <br/><br/><h3>Recursos:</h3>
            <rich:spacer height="5px"/>
            <h:panelGroup>
                <h:outputText rendered="#{empty problema.problema.recursos}" value="(No Items)"/>
                <h:dataTable value="#{problema.problema.recursos}" var="item"
                             border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px"
                             rendered="#{not empty problema.problema.recursos}">
                    <h:column>
                        <h:outputText value="#{item.recurso}"/>
                    </h:column>
                </h:dataTable>
            </h:panelGroup><br/><br/>


            <h:panelGroup>
                <h:outputText rendered="#{empty problema.problema.cronograma.atividades}" value="(No Items)"/>
                <h:dataTable value="#{problema.problema.cronograma.atividades}" var="item"
                             border="1" cellpadding="2" cellspacing="3" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px"
                             rendered="#{not empty problema.problema.cronograma.atividades}">
                    <h:column>
                        <f:facet name="header">
                            <label>Descrição</label>
                        </f:facet>
                        <h:outputText value="#{item.descricaoAtividade}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <label>Data</label>
                        </f:facet>

                        <h:outputText value="#{item.dataAtividade}"/>

                    </h:column>


                </h:dataTable>
                <rich:spacer height="10px"/>
            </h:panelGroup>

            <div class="field">
                <rich:spacer width="10"></rich:spacer>
                <rich:spacer height="10"></rich:spacer>
                <rich:spacer width="40"></rich:spacer>
                <rich:spacer height="15px"/>

                <h:panelGrid columns="1" columnClasses="top">
                    <rich:panel bodyClass="info">
                        <f:facet name="header">
                            <label>Imagens anexadas</label>
                        </f:facet>
                        <h:outputText value="Não foi realizado upload de nenhum arquivo"
                                      rendered="#{problema.size==0}" />
                        <rich:dataGrid columns="1" value="#{problema.files}"
                                       var="file" rowKeyVar="row">

                            <h:panelGrid columns="1">
                                <a4j:mediaOutput element="img" mimeType="#{imageData.mime}"
                                                 createContent="#{problema.paint}" value="#{row}"
                                                 style="width:300px; height:300px;" cacheable="false">
                                    <f:param value="#{problema.timeStamp}" name="time"/>
                                </a4j:mediaOutput>
                            </h:panelGrid>

                        </rich:dataGrid>
                    </rich:panel>
                    <rich:spacer height="3"/>
                    <br />
                </h:panelGrid>
            </div>

            <div class="summaryButton" align="center"></div>

            <div class="summaryButton" align="center">
              <input name="button" type="button" onclick="window.print()" value="Imprimir" action="imprimir" />
            </div>
            </center>
            </div>
    </h:form>
</html>
