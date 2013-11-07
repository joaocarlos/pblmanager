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
            <h3>Dados do Problema</h3>
            <h:form id="details">
                <div class="buttons">
                    <h:commandLink action="${problema.editSetup}" rendered="${(problema.problema.professorAutor.nome == professor.professorItems3) || !professor.render}">
                        <img src="#{request.contextPath}/template/images/edit.png" alt=""/>Editar
                        <f:param name="jsfcrud.currentProblema" value="${jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][problema.problema][problema.converter].jsfcrud_invoke}" />
                    </h:commandLink>
                    <h:outputText value="    "/>
                    <h:commandLink action="${problema.criarCopiandoDeOutroSetup}" class="positive">
                        <img src="#{request.contextPath}/template/images/new_file.png" alt=""/>Novo a partir deste
                        <f:param name="jsfcrud.currentProblema" value="${jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][problema.problema][problema.converter].jsfcrud_invoke}" />
                    </h:commandLink>
                    <h:outputText value="    "/>
                    <h:commandLink action="${problema.listSetup}" value="Listar Problemas"/>
                </div>
                <div class="clear" style="height:15px;"></div>

                <br/><rich:spacer width="10"></rich:spacer>
                <label>Título:</label><br/>
                <rich:spacer width="13"></rich:spacer>
                <h:outputText value="${problema.problema.tituloProblema}" title="TituloProblema" /><br/><br/><br/>
                <rich:spacer width="10"></rich:spacer>
                <label>Semestre:</label><br/>
                <rich:spacer width="13"></rich:spacer>
                <h:outputText value="${problema.problema.semestreCriacao}" title="SemestreCriacao" /><br/><br/><br/>
                <rich:spacer width="10"></rich:spacer>
                <label>Data de Criação:</label><br/>
                <rich:spacer width="13"></rich:spacer>
                <h:outputText value="${problema.problema.dataCriacao}" title="DataCriacao" >
                    <f:convertDateTime pattern="dd/MM/yyyy" />
                </h:outputText><br/><br/><br/>
                <rich:spacer width="10"></rich:spacer>
                <label>Assunto:</label><br/>
                <rich:spacer width="13"></rich:spacer>
                <h:outputText value="${problema.problema.assunto}" title="Assunto" /><br/><br/><br/>
                <rich:spacer width="10"></rich:spacer>
                <label>Descrição:</label><br/>
                <rich:spacer width="13"></rich:spacer>
                <h:outputText value="${problema.problema.descricao}" title="Descricao" style="padding-right:20px" escape="false"/><br/>
                <rich:spacer width="10"></rich:spacer>
                <label>Palavras-chave:</label><br/>
                <rich:spacer height="5px"/>
                <h:panelGroup>
                    <rich:spacer width="13"></rich:spacer>
                    <h:outputText rendered="${empty problema.problema.palavrasChave}" value="(Não há nenhuma palavra-chave armazenada)"/>
                    <rich:spacer width="30px">
                        <h:dataTable value="${problema.problema.palavrasChave}" var="item"
                                     rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all"
                                     style="border:appworkspace solid 1px;margin:10px;border-collapse:separate;"
                                     border="4" cellpadding="1" cellspacing="2px"
                                     rendered="${not empty problema.problema.palavrasChave}">
                            <h:column>
                                <rich:spacer width="10"></rich:spacer>
                                <h:outputText value="${item.palavraChave}"/>
                                <rich:spacer width="10"></rich:spacer>
                            </h:column>
                        </h:dataTable></rich:spacer>
                </h:panelGroup><br/><br/><br/>
                <rich:spacer width="10"></rich:spacer>
                <label>Autor:</label><br/>
                <rich:spacer width="13"></rich:spacer>
                <h:outputText value="${problema.problema.professorAutor.nome}" title="ProfessorAutor" /><br/><br/><br/>
                <rich:spacer width="10"></rich:spacer>
                <label>Disciplina:</label><br/>
                <rich:spacer width="13"></rich:spacer>
                <h:outputText value="${problema.problema.disciplina.nome}" title="Disciplina" /><br/><br/><br/>
                <rich:spacer width="10"></rich:spacer>
                <label>Critérios para Avaliação:</label><br/>
                <rich:spacer width="13"></rich:spacer>
                <h:outputText value="${problema.problema.avaliacaoAlunos}" title="AvaliacaoAlunos" /><br/><br/><br/>
                <!-- Falta colocar o atributo imagem aqui-->
                <rich:spacer width="10"></rich:spacer>
                <label>Objetivos:</label><br/>
                <rich:spacer height="5px"/>
                <h:panelGroup>
                    <rich:spacer width="13"></rich:spacer>
                    <h:outputText rendered="${empty problema.problema.objetivos}" value="(Não há nenhum objetivo armazenado)"/>
                    <h:dataTable value="${problema.problema.objetivos}" var="item"
                                 rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all"
                                 style="border:appworkspace solid 1px;margin:10px;border-collapse:separate;"
                                 border="4" cellpadding="1" cellspacing="2px"
                                 rendered="${not empty problema.problema.objetivos}">
                        <h:column>
                            <rich:spacer width="10"></rich:spacer>
                            <h:outputText value="${item.objetivo}"/>
                            <rich:spacer width="10"></rich:spacer>
                        </h:column>
                    </h:dataTable>
                </h:panelGroup><br/><br/>
                <rich:spacer width="10"></rich:spacer>
                <label>Orientações aos tutores:</label><br/>
                <rich:spacer width="13"></rich:spacer>
                <h:outputText value="${problema.problema.orientacoesTutor}" title="OrientacoesTutor" />
                <h:outputText value="(Não há nenhuma orientação aos tutores)" rendered="${empty problema.problema.orientacoesTutor}"/>
                <br/><br/><br/>
                <rich:spacer width="10"></rich:spacer>
                <label>Produtos:</label><br/>
                <rich:spacer width="13"></rich:spacer>
                <h:outputText value="${problema.problema.produtos}" title="Produtos" style="padding-right:20px" escape="false"/><br/>
                <rich:spacer width="10"></rich:spacer>
                <label>Recursos:</label><br/>
                <rich:spacer height="5px"/>
                <h:panelGroup>
                    <rich:spacer width="13"></rich:spacer>
                    <h:outputText rendered="${empty problema.problema.recursos}" value="(Não há nenhum recurso armazenado)"/>
                    <h:dataTable value="${problema.problema.recursos}" var="item"
                                 rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all"
                                 style="border:appworkspace solid 1px;margin:10px;border-collapse:separate;"
                                 border="4" cellpadding="1" cellspacing="2px"
                                 rendered="${not empty problema.problema.recursos}">
                        <h:column>
                            <rich:spacer width="10"></rich:spacer>
                            <h:outputText value="${item.recurso}"/>
                            <rich:spacer width="10"></rich:spacer>
                        </h:column>
                    </h:dataTable>
                </h:panelGroup><br/><br/>
                <rich:spacer width="10"></rich:spacer>
                <label>Cronograma:</label><br/>
                <h:panelGroup>
                    <rich:spacer width="13"></rich:spacer>
                    <h:outputText rendered="${empty problema.problema.cronograma.atividades}" value="(Não há nenhuma atividade armazenada)"/>
                    <h:dataTable value="${problema.problema.cronograma.atividades}" var="item"
                                 rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all"
                                 style="border:appworkspace solid 1px;margin:10px;border-collapse:separate;"
                                 border="4" cellpadding="1" cellspacing="2px"
                                 rendered="${not empty problema.problema.cronograma.atividades}">
                        <h:column>
                            <f:facet name="header">
                                <center><label>Descrição</label></center>
                            </f:facet>
                            <rich:spacer width="10"></rich:spacer>
                            <h:outputText value="${item.descricaoAtividade}"/>
                            <rich:spacer width="10"></rich:spacer>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <center><label>Data</label></center>
                            </f:facet>
                            <rich:spacer width="10"></rich:spacer>
                            <h:outputText value="${item.dataAtividade}"/>
                            <rich:spacer width="10"></rich:spacer>
                        </h:column>
                    </h:dataTable>
                </h:panelGroup><br/><br/>
                <rich:spacer width="10"></rich:spacer>
                <label>Imagens:</label><br/><br/>
                <center><rich:panel id="teste123" bodyClass="info3" style="width:600px;height:190px;margin-left:3%;"
                                    columnClass="top2" >
                        <f:facet name="header">
                            <label>Imagens Associadas ao Problema</label>
                        </f:facet>
                        <div id="scrollable" style="height:150px;">
                            <rich:dataGrid id="imagens" columns="4"
                                           value="${problema.problema.imagensAssociadas}"
                                           var="item">
                                <rich:panel style="width:120px;height:140px;" bodyClass="rich-laguna-panel-no-header">
                                    <f:facet name="header">
                                        <label>${item.nome}</label>
                                    </f:facet>
                                    <a4j:mediaOutput element="img" mimeType="jpg,bmp,gif,png"
                                                     createContent="${problema.paintLivre2}" value="imagem"
                                                     style="width:100px; height:100px;" cacheable="false">
                                        <f:param value="${item.referencia}" name="path"/>
                                    </a4j:mediaOutput>
                                </rich:panel>
                            </rich:dataGrid>
                        </div>
                    </rich:panel></center>
                <br/><br/>
                <div class="summaryButton">              
                    <div class="buttons">
                        <h:commandLink action="${problema.editSetup}" rendered="${(problema.problema.professorAutor.nome == professor.professorItems3) || !professor.render}">
                            <img src="#{request.contextPath}/template/images/edit.png" alt=""/>Editar
                            <f:param name="jsfcrud.currentProblema" value="${jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][problema.problema][problema.converter].jsfcrud_invoke}" />
                        </h:commandLink>
                        <h:outputText value="    "/>
                        <h:commandLink action="${problema.criarCopiandoDeOutroSetup}" class="positive">
                            <img src="#{request.contextPath}/template/images/new_file.png" alt=""/>Novo a partir deste
                            <f:param name="jsfcrud.currentProblema" value="${jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][problema.problema][problema.converter].jsfcrud_invoke}" />
                        </h:commandLink>
                        <h:outputText value="    "/>
                        <h:commandLink action="${problema.listSetup}" value="Listar Problemas"/>
                    </div>
                    <div class="clear" style="height:15px;"></div>
                </div>
            </h:form>
        </ui:define>
    </ui:composition>
</html>
