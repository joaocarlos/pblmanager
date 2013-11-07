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
            <h3>Cópia de Problema</h3>
            <h:form>
                <h:inputHidden id="validateCreateField" validator="#{problema.validateCreate2}" value="value"/>
                <br/>
                <rich:tabPanel switchType="client" width="700">
                    <rich:tab label="Dados">
                        <br/>
                        <div class="field">
                            <rich:spacer width="8"></rich:spacer>
                            <label>Título</label><br/><rich:spacer height="10px"></rich:spacer><br/>
                            <rich:spacer width="10"></rich:spacer>
                            <h:inputText maxlength="150" style="width:70%"
                                         value="#{problema.problema.tituloProblema}"/>
                        </div><br/><br/>
                        <div class="field">
                            <rich:spacer width="8"></rich:spacer>
                            <label>Semestre</label><rich:spacer width="32%"></rich:spacer>
                            <label>Assunto</label><br/><rich:spacer height="20px"></rich:spacer>
                            <rich:spacer width="10"></rich:spacer>
                            <h:inputText maxlength="40" style="width:30%"
                                         value="#{problema.problema.semestreCriacao}" />
                            <rich:spacer width="9%"></rich:spacer>
                            <h:inputText maxlength="40" style="width:30%"
                                         value="#{problema.problema.assunto}"/>
                        </div><br/><br/>

                        <div class="field"><a4j:region>
                                <rich:spacer width="8"></rich:spacer>
                                <label>Palavras-chave</label><br/><rich:spacer height="10px"></rich:spacer><br/>
                                <p>
                                    <rich:spacer width="10"></rich:spacer>
                                    <h:inputText id="dadosPalavraChave" style="width:30%" value="#{problema.keyWord}" maxlength="40"/> &nbsp;
                                    <rich:spacer width="10"></rich:spacer>
                                    <a4j:commandLink styleClass="button" action="#{problema.actionAddPalavraChave}" value="adicionar"
                                                     reRender="keyWordTable, dadosPalavraChave"/>
                                </p>
                                <rich:spacer height="10"></rich:spacer>
                                <h:dataTable id="keyWordTable" value="#{problema.problema.palavrasChave}"
                                             var="item" style="border:appworkspace solid 1px;margin:10px;"
                                             border="4" cellpadding="1" cellspacing="2px">
                                    <rich:spacer width="10"></rich:spacer>
                                    <h:column>
                                        <rich:spacer width="10"></rich:spacer>
                                        <h:outputText value="#{item.palavraChave}"/>
                                        <rich:spacer width="10"></rich:spacer>
                                    </h:column>
                                    <h:column>
                                        <rich:spacer width="10"></rich:spacer>
                                        <a4j:commandLink action="#{problema.actionRemovePalavraChave}" value="remover"
                                                         reRender="keyWordTable">
                                            <f:param value="#{item.palavraChave}" name="pc"/>
                                        </a4j:commandLink>
                                        <rich:spacer width="10"></rich:spacer>
                                    </h:column>
                                </h:dataTable></a4j:region>
                        </div><br/><br/>
                        <div class="field"><a4j:region>
                                <rich:spacer width="8"></rich:spacer>
                                <label>Cronograma</label><br/>
                                <rich:spacer width="8" height="20px"></rich:spacer>
                                <label>Descrição da atividade:</label>
                                <rich:spacer width="22%"></rich:spacer>
                                <label>Data:</label><br/>
                                <rich:spacer width="10" height="20"></rich:spacer>
                                <h:inputText id="dadosAtividade" style="width:30%" value="#{problema.activities}" maxlength="150"/>
                                <rich:spacer width="8%"></rich:spacer>
                                <a4j:outputPanel id="dadosData">
                                    <rich:spacer width="10"></rich:spacer>
                                    <rich:calendar value="#{problema.activitiesDate}"
                                                   datePattern="dd/MM/yyyy"
                                                   showApplyButton="#{calendarBean.showApply}" cellWidth="24px" cellHeight="22px"
                                                   style="width:200px"/>
                                </a4j:outputPanel>
                                <rich:spacer width="10" height="20"></rich:spacer>
                                <a4j:commandLink styleClass="button" action="#{problema.actionAddAtividades}" value="adicionar"
                                                 reRender="activityTable, dadosAtividade, dadosData"/>
                                <br/><br/>
                                <h:dataTable id="activityTable" value="#{problema.problema.cronograma.atividades}"
                                             var="item" style="border:appworkspace solid 1px;margin:10px;"
                                             border="4" cellpadding="1" cellspacing="2px">
                                    <h:column>
                                        <rich:spacer width="10"></rich:spacer>
                                        <h:outputText value="#{item.descricaoAtividade}"/>
                                        <rich:spacer width="10"></rich:spacer>
                                    </h:column>
                                    <h:column>
                                        <rich:spacer width="10"></rich:spacer>
                                        <h:outputText value="#{item.dataAtividade}"> <f:convertDateTime pattern="dd/MM/yyyy" /></h:outputText>
                                        <rich:spacer width="10"></rich:spacer>
                                    </h:column>
                                    <h:column>
                                        <rich:spacer width="10"></rich:spacer>
                                        <a4j:commandLink action="#{problema.actionRemoveAtividades}" value="remover"
                                                         reRender="activityTable">
                                            <f:param value="#{item.descricaoAtividade}" name="act"/>
                                        </a4j:commandLink>
                                        <rich:spacer width="10"></rich:spacer>
                                    </h:column>
                                </h:dataTable></a4j:region>
                        </div><br/><br/>
                        <div class="field"><a4j:region>
                                <rich:spacer width="8"></rich:spacer>
                                <label>Imagens</label><br/><br/>
                                <rich:spacer width="20"></rich:spacer><br/>
                                <center>
                                    <style type="text/css">
                                        .top2 {
                                            vertical-align: top;
                                        }
                                        .info4{
                                            width:200px;
                                            height: 100px;
                                            overflow: scroll;
                                        }
                                    </style>
                                    <h:panelGrid columns="2" columnClasses="top2,top2" id="panelComImagens">
                                        <rich:panel id="teste123" bodyClass="info3" style="width:438px;height:190px;"
                                                    columnClass="top2" >
                                            <f:facet name="header">
                                                <label>Visualização das Imagens</label>
                                            </f:facet>
                                            <div id="scrollable" style="height:150px;">
                                                <rich:dataGrid id="imagens" columns="3"
                                                               value="#{problema.problema.imagensAssociadas}"
                                                               var="item">
                                                    <rich:panel style="width:120px;height:140px;" bodyClass="rich-laguna-panel-no-header">
                                                        <f:facet name="header">
                                                            <label>#{item.nome}</label>
                                                        </f:facet>
                                                        <a4j:mediaOutput element="img" mimeType="jpg,bmp,gif,png"
                                                                         createContent="#{problema.paintLivre2}" value="imagem"
                                                                         style="width:100px; height:100px;" cacheable="false">
                                                            <f:param value="#{item.referencia}" name="path"/>
                                                        </a4j:mediaOutput>
                                                    </rich:panel>
                                                </rich:dataGrid>
                                            </div>
                                        </rich:panel>
                                        <h:dataTable id="imageTable2" value="#{problema.problema.imagensAssociadas}"
                                                     var="item2" style="border:appworkspace solid 1px;margin:10px;"
                                                     border="4" cellpadding="1" cellspacing="2px"
                                                     rerendered="#{problema.qntImagens!=0}">
                                            <h:column rendered="#{problema.qntImagens!=0}">
                                                <rich:spacer width="10"></rich:spacer>
                                                <f:facet name="header">
                                                    <rich:spacer width="10"></rich:spacer>
                                                    <label>Referencia</label>
                                                </f:facet>
                                                <rich:spacer width="10"></rich:spacer>
                                                <h:outputText value="#{item2.nome}"/>
                                                <rich:spacer width="10"></rich:spacer>
                                            </h:column>
                                            <h:column rendered="#{problema.qntImagens!=0}">
                                                <rich:spacer width="10"></rich:spacer>
                                                <f:facet name="header" rendered="#{problema.qntImagens!=0}">
                                                    <rich:spacer width="10"></rich:spacer>
                                                    <label>Opções</label>
                                                </f:facet>
                                                <rich:spacer width="10"></rich:spacer>
                                                <a4j:commandLink action="#{problema.removerImagem}" value="remover"
                                                                 reRender="imageTable2, imagens">
                                                    <f:param value="#{item2.referencia}" name="img2"/>
                                                </a4j:commandLink>
                                                <rich:spacer width="10"></rich:spacer>
                                            </h:column>
                                        </h:dataTable>
                                    </h:panelGrid></center></a4j:region>
                        </div>
                        <div class="field">
                            <br/><a4j:region>
                                <rich:spacer width="10"></rich:spacer>
                                <label>Upload de Imagens<br/></label><rich:spacer height="10"></rich:spacer>
                                <rich:spacer width="40"></rich:spacer>
                                <style type="text/css">
                                    .top {
                                        vertical-align: top;
                                        padding-left:20px;
                                    }
                                </style>
                                <h:panelGrid columns="1" columnClasses="top">
                                    <rich:fileUpload fileUploadListener="#{problema.listener}"
                                                     addControlLabel="Enviar" maxFilesQuantity="10" autoclear="true"
                                                     id="upload" immediateUpload="true" acceptedTypes="jpg, gif, png, bmp">
                                        <a4j:support event="onuploadcomplete" reRender="panelComImagens" ajaxSingle="true"/>
                                    </rich:fileUpload>
                                </h:panelGrid></a4j:region>
                        </div><br/><br/>
                    </rich:tab>
                    <rich:tab label="Descrição,Produtos e Orientação sobre o Problema">


                        <div class="descricao" align="center"><br/>
                            <rich:spacer width="10"></rich:spacer>
                            <label>Descrição</label><br/><rich:spacer height="10px"></rich:spacer><br/>
                            <rich:spacer width="10"></rich:spacer>
                            <rich:editor value="#{problema.problema.descricao}" width ="600" theme="advanced">
                                <f:param name="theme_advanced_buttons2" value="bold,italic,underline,search,replace,undo,redo,fontsizeselect"/>
                            </rich:editor>
                        </div><br/><br/>

                        <div class="produto" align="center">
                            <rich:spacer width="10"></rich:spacer>
                            <label>Produtos</label><rich:spacer height="10px"></rich:spacer>
                            <rich:spacer width="10"></rich:spacer>
                            <rich:editor value="#{problema.problema.produtos}" width ="600" theme="advanced">
                                <f:param name="theme_advanced_buttons2" value="bold,italic,underline,search,replace,undo,redo,fontsizeselect"/>
                            </rich:editor>
                        </div><br/><br/>

                        <div class="field">
                            <rich:spacer width="10"></rich:spacer>
                            <label>Critérios para avaliação</label><br/><rich:spacer height="10px"></rich:spacer><br/>
                            <rich:spacer width="10"></rich:spacer>
                            <h:inputTextarea id="avaliacaoAlunos"
                                             rows="6"
                                             value="#{problema.problema.avaliacaoAlunos}" style="width: 95%"/>
                        </div><br/><br/>
                        <div class="field">
                            <rich:spacer width="10"></rich:spacer>
                            <label>Orientações aos tutores</label><br/><rich:spacer height="10px"></rich:spacer><br/>
                            <rich:spacer width="10"></rich:spacer>
                            <h:inputTextarea id="orientacoesTutor"
                                             rows="6"
                                             value="#{problema.problema.orientacoesTutor}" style="width: 95%"/>
                        </div><br/><br/>
                    </rich:tab>

                    <rich:tab label="Metas de aprendizagem, Produtos e Recursos" width="100px">
                        <br/>
                        <div class="field"><a4j:region>
                                <rich:spacer width="10"></rich:spacer>
                                <label>Objetivos</label><br/><rich:spacer height="10px"></rich:spacer><br/>
                                <p>
                                    <rich:spacer width="10"></rich:spacer>
                                    <h:inputText id="dadosObjetivo" style="width:60%" value="#{problema.goal}" maxlength="200"/> &nbsp;
                                    <rich:spacer width="10"></rich:spacer>
                                    <a4j:commandLink styleClass="button" action="#{problema.actionAddObjetivo}" value="adicionar"
                                                     reRender="goalTable, dadosObjetivo"/>
                                </p>
                                <rich:spacer height="10"></rich:spacer>
                                <h:dataTable id="goalTable" value="#{problema.problema.objetivos}"
                                             var="item" style="border:appworkspace solid 1px;margin:10px;"
                                             border="4" cellpadding="1" cellspacing="2px">
                                    <h:column>
                                        <rich:spacer width="10"></rich:spacer>
                                        <h:outputText value="#{item.objetivo}"/>
                                        <rich:spacer width="10"></rich:spacer>
                                    </h:column>
                                    <h:column>
                                        <rich:spacer width="10"></rich:spacer>
                                        <a4j:commandLink action="#{problema.actionRemoveObjetivo}" value="remover"
                                                         reRender="goalTable">
                                            <f:param value="#{item.objetivo}" name="obj"/>
                                        </a4j:commandLink>
                                        <rich:spacer width="10"></rich:spacer>
                                    </h:column>
                                </h:dataTable></a4j:region>
                        </div><br/><br/>

                        <div class="field"><a4j:region>
                                <rich:spacer width="10"></rich:spacer>
                                <label>Recursos</label><br/><rich:spacer height="10px"></rich:spacer><br/>
                                <p>
                                    <rich:spacer width="10"></rich:spacer>
                                    <h:inputText id="dadosRecursos" style="width:60%" value="#{problema.resource}" maxlength="250"/> &nbsp;
                                    <rich:spacer width="10"></rich:spacer>
                                    <a4j:commandLink styleClass="button" action="#{problema.actionAddRecurso}" value="adicionar"
                                                     reRender="resourceTable, dadosRecursos"/>
                                </p>
                                <rich:spacer height="10"></rich:spacer>
                                <h:dataTable id="resourceTable" value="#{problema.problema.recursos}"
                                             var="item" style="border:appworkspace solid 1px;margin:10px;"
                                             border="4" cellpadding="1" cellspacing="2px">
                                    <h:column>
                                        <rich:spacer width="10"></rich:spacer>
                                        <h:outputText value="#{item.recurso}"/>
                                        <rich:spacer width="10"></rich:spacer>
                                    </h:column>
                                    <h:column>
                                        <rich:spacer width="10"></rich:spacer>
                                        <a4j:commandLink action="#{problema.actionRemoveRecurso}" value="remover"
                                                         reRender="resourceTable">
                                            <f:param value="#{item.recurso}" name="rec"/>
                                        </a4j:commandLink>
                                        <rich:spacer width="10"></rich:spacer>
                                    </h:column>
                                </h:dataTable></a4j:region>
                        </div><br/><br/>
                    </rich:tab>

                </rich:tabPanel>
                <br/>
                <div class="summaryButton" align="right">
                    <h:commandLink action="#{problema.create}" value="Salvar" styleClass="button">
                        <f:param name="jsfcrud.currentProblema" value="#{jsfcrud_class['controller.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][problema.problema][problema.converter].jsfcrud_invoke}"/>
                        <f:setPropertyActionListener target="#{problema.autor}" value="#{professor.professorLogado}"/>
                    </h:commandLink>
                    <h:outputText value="    "/>
                    <h:commandLink action="#{problema.listSetup}" styleClass="button" value="Cancelar" immediate="true"/>
                </div>
            </h:form>
        </ui:define>
    </ui:composition>
</html>