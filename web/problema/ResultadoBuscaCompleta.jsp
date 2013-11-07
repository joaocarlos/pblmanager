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
        <ui:define name="head">
            <link href="#{request.contextPath}/template/css/tablecloth.css" rel="stylesheet" type="text/css" media="screen"/>
            <script type="text/javascript" src="#{request.contextPath}/template/js/tablecloth.js" /> <!-- Menu dropdown -->
        </ui:define>
        <ui:define name="content">
            <h3>Resultado da Pesquisa</h3>
            <h:form id="listaDeProblemas">
                <br/>
                <h:dataTable value="#{problema.problemaItems4}" var="item" id="tabelaProblemas"
                             styleClass="listaProblemas" rowClasses="" cellspacing="0" cellpadding="0">
                    <h:column>
                        <f:facet name="header">
                            <label>Título do Problema</label>
                        </f:facet>
                        <span class="dcontexto">
                            <h:outputText value="#{item.tituloProblema}"/>
                            <span class="tooltip_disciplina">
                                <strong>Autor: </strong><h:outputText value="#{item.professorAutor.nome}"/><br />
                                <strong>Semestre: </strong><h:outputText value="#{item.semestreCriacao}"/><br />
                                <strong>Data de criação: </strong><h:outputText value="#{item.dataCriacao}"><f:convertDateTime pattern="dd/MM/yyyy" /></h:outputText><br />
                                <strong>Assunto: </strong><h:outputText value="#{item.assunto}"/>
                            </span>
                        </span>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <label>Disciplina</label>
                        </f:facet>
                        <center>
                        <!--<h:outputText value="#{item.disciplina.codigo}"/>-->
                            <span class="dcontexto">
                                <h:outputText value="#{item.disciplina.codigo}"/>
                                <span class="tooltip_disciplina">
                                    <strong>Detalhes de </strong><strong><h:outputText value="#{item.disciplina.codigo}"/></strong><br /><br />
                                    <strong>Nome: </strong><h:outputText value="#{item.disciplina.nome}"/><br />
                                    <strong>Departamento: </strong><h:outputText value="#{item.disciplina.departamento}"/>
                                </span>
                            </span>
                        </center>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <label>Relatório</label>
                        </f:facet>
                        <center class="no_dec">
                            <h:commandLink value="Gerar Relatório" action ="#{problema.gerarRelatorio}" >
                                <f:param name="jsfcrud.currentProblema" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][problema.converter].jsfcrud_invoke}"/>
                            </h:commandLink><br />
                            <h:commandLink style="margin: 0 2px;" value="Simples" action ="#{problema.gerarRelatorio}" onclick="window.open('#{request.contextPath}/portal/problema/Relatorio.jsp', 'Relatorio', 'status=yes,menubar=yes,scrollbars=yes,height=600,width=800,left=0,top=0')" >
                                <!--<img src="#{request.contextPath}/template/images/printer.png" alt="Imprimir Relatório Simplificado" />-->
                                <f:param name="jsfcrud.currentProblema" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][problema.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:commandLink style="margin: 0 2px;" value="Geral" action ="#{problema.gerarRelatorio}" onclick="window.open('#{request.contextPath}/portal/problema/RelatorioCompleto.jsp', 'Relatorio Completo', 'status=yes,menubar=yes,scrollbars=yes,height=600,width=800,left=0,top=0')" >
                                <!--<img src="#{request.contextPath}/template/images/printer.png" alt="Imprimir Relatório Completo" />-->
                                <f:param name="jsfcrud.currentProblema" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][problema.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </center>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <label>Opções</label>
                        </f:facet>
                        <center>
                            <span class="dcontexto">
                                <h:commandLink action="#{problema.detailSetup}">
                                    <img src="#{request.contextPath}/template/images/file.png" alt="Exibir Detalhes" />
                                    <f:param name="jsfcrud.currentProblema" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][problema.converter].jsfcrud_invoke}"/>
                                </h:commandLink>
                                <span class="tooltipMenor">Exibir detalhes.</span>
                            </span>
                            <span class="dcontexto">
                                <h:commandLink action="#{problema.editSetup}"  rendered="#{(item.professorAutor.nome == professor.professorItems3) || professor.render}">
                                    <img src="#{request.contextPath}/template/images/file_edit.png" alt="Editar" />
                                    <f:param name="jsfcrud.currentProblema" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][problema.converter].jsfcrud_invoke}"/>
                                </h:commandLink>
                                <span class="tooltipMenor">Editar o problema.</span>
                            </span>
                            <span class="dcontexto">
                                <h:outputLink value="#" id="link" rendered="#{(item.professorAutor.nome == professor.professorItems3) || professor.render}">
                                    <img src="#{request.contextPath}/template/images/delete.png" alt="Apagar" />
                                    <rich:componentControl for="panel" attachTo="link" operation="show" event="onclick"/>
                                </h:outputLink>
                                <span class="tooltipMenor">Apagar problema.</span>
                            </span>
                            <rich:modalPanel id="panel" styleClass="painel" width="350" height="130">
                                <f:facet name="header">
                                    <h:panelGroup>
                                        <label>Atenção</label>
                                    </h:panelGroup>
                                </f:facet>
                                <f:facet name="controls">
                                    <h:commandLink onclick="#{rich:component('panel')}.hide()" id="fecharPoupup" value="[X]"/>
                                </f:facet>
                                <h:outputText value="Você tem certeza de que deseja excluir esse problema?"></h:outputText>
                                <br /><br />
                                <h:commandLink value="Sim" styleClass="button" id="apagar" action="#{problema.destroy}">
                                    <f:param name="jsfcrud.currentProblema" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][problema.converter].jsfcrud_invoke}"/>
                                </h:commandLink>
                                <rich:spacer width="1%"></rich:spacer>
                                <h:commandLink onclick="#{rich:component('panel')}.hide()" id="naoApaga" value="Não" styleClass="button"/><br />
                            </rich:modalPanel>
                        </center>
                    </h:column>
                </h:dataTable>
                <span class="info" style="display:block;">* Antes de imprimir o relatório (<strong>Geral</strong> ou
                    <strong>Completo</strong>) do problema clique em <strong>Gerar Relatório</strong>.
                </span>
                <div class="summaryButton">
                    <div class="buttons">
                        <h:commandLink action="${problema.buscaSetup2}">
                            <img src="#{request.contextPath}/template/images/search.png" alt=""/>Nova pesquisa
                        </h:commandLink>
                    </div>
                    <div class="clear"></div>
                </div>
            </h:form>
        </ui:define>
    </ui:composition>
</html>


