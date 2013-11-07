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
        <ui:define name="content">
            <h3>Novo Problema</h3>
            <h:form id="novoproblema">
                <h:inputHidden id="validateCreateField" 
                               validator="${problema.validateCreate}"
                               value="value"/>
                <div class="buttons">
                    <h:commandLink action="${problema.create}" class="positive">
                        <img src="#{request.contextPath}/template/images/tick.png" alt=""/>Salvar e Sair
                        <f:setPropertyActionListener target="${problema.autor}" value="${professor.professorLogado}"/>
                    </h:commandLink>
                    <h:commandLink action="${problema.create2}">
                        <img src="#{request.contextPath}/template/images/save.png" alt=""/>Salvar
                        <f:setPropertyActionListener target="${problema.autor}" value="${professor.professorLogado}"/>
                    </h:commandLink>
                    <h:commandLink action="${problema.listSetup}" immediate="true" class="negative">
                        <img src="#{request.contextPath}/template/images/cross.png" alt=""/>Cancelar
                    </h:commandLink>
                </div>
                <div class="clear" style="height:15px"></div>
                <rich:tabPanel switchType="client" width="712" id="tab">
                    <rich:tab label="Geral">
                        <br />
                        <div class="content_problema">
                            <label>Título</label><br />
                            <h:inputText maxlength="150"
                                         style="width:70%"
                                         class="title"
                                         id="titulo"
                                         value="${problema.problema.tituloProblema}"
                                         required="true"
                                         requiredMessage="${messages.titulo}"/>
                            <br /><br />
                            <label>Semestre</label><rich:spacer width="30%"/>
                            <label>Assunto</label><br />
                            <h:inputText  maxlength="6" 
                                          style="width:30%"
                                          class="text"
                                          id="semestre"
                                          value="${problema.problema.semestreCriacao}"
                                          required="true"
                                          requiredMessage="${messages.semestre}"/>
                            <rich:spacer width="9%"></rich:spacer>
                            <h:inputText maxlength="200"
                                         id="assunto"
                                         style="width:30%"
                                         value="${problema.problema.assunto}"
                                         class="text"
                                         required="true"
                                         requiredMessage="${messages.assunto}"/>
                            <br /><br />

                            <label>Disciplina:</label><br />
                            <h:column>
                                <h:selectOneMenu id="disciplina"
                                                 style="width:41%"
                                                 value="${problema.problema.disciplina}">
                                    <f:selectItems value="${disciplina.disciplinaItemsAvailableSelectOne}"/>
                                </h:selectOneMenu>
                            </h:column>
                            <br /><br />

                            <a4j:region>
                                <label>Palavras-chave</label><br />
                                <p>                                    
                                    <div class="buttons" style="float:none; width: 50%">
                                        <h:inputText id="dadosPalavraChave"
                                                     style="width:60%"
                                                     class="text"
                                                     value="${problema.keyWord}"
                                                     maxlength="40"/>
                                        <a4j:commandLink action="${problema.actionAddPalavraChave}"
                                                         styleClass="positive adjust"
                                                         reRender="keyWordTable, dadosPalavraChave">
                                            <img src="#{request.contextPath}/template/images/tick.png" alt=""/>Adicionar
                                        </a4j:commandLink>
                                    </div>
                                </p>
                                <h:dataTable id="keyWordTable" 
                                             value="${problema.problema.palavrasChave}"
                                             var="item"                                             
                                             border="0" cellpadding="0" cellspacing="2">
                                    <h:column>
                                        <f:facet name="header">
                                            <label style="display: block; width: 230px;"></label>
                                        </f:facet>
                                        <h:outputText value="${item.palavraChave}"/>
                                    </h:column>
                                    <h:column>
                                        <f:facet name="header">
                                            <label style="display: block; width: 90px;"></label>
                                        </f:facet>
                                        <div class="buttons">
                                            <a4j:commandLink action="${problema.actionRemovePalavraChave}"
                                                             styleClass="negative"
                                                             reRender="keyWordTable">
                                                <f:param value="${item.palavraChave}" name="pc"/>
                                                <img src="#{request.contextPath}/template/images/cross.png" alt=""/>Remover
                                            </a4j:commandLink>
                                        </div>
                                    </h:column>
                                </h:dataTable>
                            </a4j:region>
                            <br /><br />
                            <a4j:region>
                                <label>Cronograma</label><br />
                                <span>Para definir o cronograma do problema, adicione uma descrição e informe
                                    a data prevista.</span><br />
                                <div class="buttons">
                                    <label>Descrição da atividade:</label>
                                    <rich:spacer width="16%"></rich:spacer>
                                    <label>Data:</label><br />
                                    <h:inputText id="dadosAtividade"
                                                 class="text"
                                                 style="width:30%"
                                                 value="${problema.activities}"
                                                 maxlength="150"/>
                                    <rich:spacer width="8%"></rich:spacer>
                                    <a4j:outputPanel id="dadosData">
                                        <rich:calendar value="${problema.activitiesDate}"
                                                       datePattern="dd/MM/yyyy"
                                                       locale="pt/BR"
                                                       showApplyButton="${calendarBean.showApply}"
                                                       cellWidth="24px"
                                                       cellHeight="22px"/>
                                    </a4j:outputPanel>
                                    <a4j:commandLink styleClass="positive adjust"
                                                     action="${problema.actionAddAtividades}"
                                                     reRender="activityTable, dadosAtividade, dadosData">
                                        <img src="#{request.contextPath}/template/images/tick.png" alt=""/>Adicionar
                                    </a4j:commandLink>
                                </div>
                                <h:dataTable id="activityTable"
                                             value="${problema.problema.cronograma.atividades}"
                                             var="item"
                                             border="0"
                                             cellpadding="0"
                                             cellspacing="2">
                                    <h:column>
                                        <f:facet name="header">
                                            <label style="display: block; width: 350px; border-bottom: solid 1px #e2e2e2;"></label>
                                        </f:facet>
                                        <h:outputText value="${item.descricaoAtividade}"/>
                                    </h:column>
                                    <h:column>
                                        <f:facet name="header">
                                            <label style="display: block; width: 80px; border-bottom: solid 1px #e2e2e2;"></label>
                                        </f:facet>
                                        <h:outputText value="${item.dataAtividade}"> <f:convertDateTime pattern="dd/MM/yyyy" /></h:outputText>
                                    </h:column>
                                    <h:column>
                                        <f:facet name="header">
                                            <label style="display: block; width: 50px;"></label>
                                        </f:facet>
                                        <div class="buttons">
                                            <a4j:commandLink action="${problema.actionRemoveAtividades}"
                                                             styleClass="negative"
                                                             reRender="activityTable">
                                                <f:param value="${item.descricaoAtividade}" name="act"/>
                                                <img src="#{request.contextPath}/template/images/cross.png" alt=""/>Remover
                                            </a4j:commandLink>
                                        </div>
                                    </h:column>
                                </h:dataTable>
                            </a4j:region>
                            <br /><br />
                            <a4j:region>
                                <label>Upload de Imagens<br/></label>
                                <h:panelGrid columns="1" columnClasses="top">
                                    <rich:fileUpload fileUploadListener="${problema.listener}"
                                                     autoclear="true"
                                                     addControlLabel="Enviar"
                                                     maxFilesQuantity="10"
                                                     id="upload"
                                                     immediateUpload="true"
                                                     acceptedTypes="jpg, gif, png, bmp, svg">
                                        <a4j:support event="onuploadcomplete"
                                                     reRender="panelComImagens"
                                                     ajaxSingle="true"/>
                                    </rich:fileUpload>
                                </h:panelGrid>
                            </a4j:region>
                        </div>
                        <br /><br />
                        <a4j:region>
                            <center>
                                <h:panelGrid columns="2" columnClasses="top2,top2" id="panelComImagens">
                                    <rich:panel id="teste123"
                                                bodyClass="info3"
                                                style="width:438px;height:190px;"
                                                columnClass="top2">
                                        <f:facet name="header">
                                            <label>Visualização das Imagens</label>
                                        </f:facet>
                                        <div id="scrollable" style="height:150px;">
                                            <rich:dataGrid id="imagens"
                                                           columns="3"
                                                           value="${problema.problema.imagensAssociadas}"
                                                           var="item">
                                                <rich:panel style="width:120px;height:140px;"
                                                            bodyClass="rich-laguna-panel-no-header">
                                                    <f:facet name="header">
                                                        <label>${item.nome}</label>
                                                    </f:facet>
                                                    <a4j:mediaOutput element="img"
                                                                     mimeType="jpg,bmp,gif,png, svg"
                                                                     createContent="${problema.paintLivre2}"
                                                                     value="imagem"
                                                                     style="width:100px; height:100px;"
                                                                     cacheable="false">
                                                        <f:param value="${item.referencia}" name="path"/>
                                                    </a4j:mediaOutput>
                                                </rich:panel>
                                            </rich:dataGrid>
                                        </div>
                                    </rich:panel>
                                    <h:dataTable id="imageTable2" value="${problema.problema.imagensAssociadas}"
                                                 var="item2" border="0" cellpadding="0" cellspacing="0"
                                                 rerendered="${problema.qntImagens!=0}">
                                        <h:column rendered="${problema.qntImagens!=0}">
                                            <rich:spacer width="10"></rich:spacer>
                                            <f:facet name="header">
                                                <label style="display: block; width: 150px; padding: 10px; border-bottom: solid 1px #e2e2e2;">Referencia</label>
                                            </f:facet>
                                            <h:outputText value="${item2.nome}"/>
                                        </h:column>
                                        <h:column rendered="${problema.qntImagens!=0}">
                                            <f:facet name="header" rendered="${problema.qntImagens!=0}">
                                                <label style="display: block; width: 150px;  padding: 10px; border-bottom: solid 1px #e2e2e2;" >Opções</label>
                                            </f:facet>
                                            <a4j:commandLink action="${problema.removerImagem}" style="text-align: center; display:block" value="remover"
                                                             reRender="imageTable2, imagens">
                                                <f:param value="${item2.referencia}" name="img2"/>
                                            </a4j:commandLink>
                                        </h:column>
                                    </h:dataTable>
                                </h:panelGrid>
                            </center>
                        </a4j:region>
                        <br />
                    </rich:tab>
                    <rich:tab label="Descrição, Produtos e Orientações">
                        <div class="descricao" align="center"><br />
                            <label>Descrição</label><br />
                            <rich:editor value="${problema.problema.descricao}" width ="600" theme="advanced">
                                <f:param name="theme_advanced_buttons2" value="bold,italic,underline,search,replace,undo,redo,fontsizeselect"/>
                            </rich:editor>
                        </div><br /><br />
                        <div class="produto" align="center">
                            <label>Produtos</label>
                            <rich:editor value="${problema.problema.produtos}" width ="600" theme="advanced">
                                <f:param name="theme_advanced_buttons2" value="bold,italic,underline,search,replace,undo,redo,fontsizeselect"/>
                            </rich:editor>
                        </div><br /><br />
                        <div class="field" align="center">
                            <rich:spacer width="10"></rich:spacer>
                            <label>Critérios para avaliação</label><br />
                            <rich:editor value="${problema.problema.produtos}" width ="600" theme="advanced">
                                <f:param name="theme_advanced_buttons2" value="bold,italic,underline,search,replace,undo,redo,fontsizeselect"/>
                            </rich:editor>
                        </div>
                        <br /><br />
                        <div class="field" align="center">
                            <label>Orientações aos tutores</label><br />
                            <rich:editor value="${problema.problema.orientacoesTutor}" width ="600" theme="advanced">
                                <f:param name="theme_advanced_buttons2" value="bold,italic,underline,search,replace,undo,redo,fontsizeselect"/>
                            </rich:editor>
                        </div>
                        <br /><br />
                    </rich:tab>
                    <rich:tab label="Metas de aprendizagem e Recursos" width="100px">
                        <div class="content_problema">
                            <a4j:region>
                                <label>Objetivos</label><br />
                                <h:inputText id="dadosObjetivo"
                                             size="65"
                                             class="text"
                                             value="${problema.goal}"
                                             maxlength="100"/>
                                <rich:spacer width="10"></rich:spacer>
                                <a4j:commandLink styleClass="button"
                                                 action="${problema.actionAddObjetivo}"
                                                 value="adicionar"
                                                 reRender="goalTable, dadosObjetivo"/>
                                <h:dataTable id="goalTable"
                                             value="${problema.problema.objetivos}"
                                             var="item"
                                             border="0"
                                             cellpadding="0"
                                             cellspacing="0">
                                    <h:column>
                                        <f:facet name="header">
                                            <label style="display: block; width: 450px; border-bottom: solid 1px #e2e2e2;"></label>
                                        </f:facet>
                                        <h:outputText value="${item.objetivo}"/>
                                    </h:column>
                                    <h:column>
                                        <f:facet name="header">
                                            <label style="display: block; width: 50px; border-bottom: solid 1px #e2e2e2;"></label>
                                        </f:facet>
                                        <a4j:commandLink action="${problema.actionRemoveObjetivo}"
                                                         value="remover"
                                                         reRender="goalTable">
                                            <f:param value="${item.objetivo}" name="obj"/>
                                        </a4j:commandLink>
                                    </h:column>
                                </h:dataTable>
                            </a4j:region>
                            <br /><br />
                            <a4j:region>
                                <label>Recursos</label><br />
                                <h:inputText id="dadosRecurso"
                                             size="65"
                                             class="text"
                                             value="${problema.resource}"
                                             maxlength="250"/>
                                <a4j:commandLink styleClass="button"
                                                 action="${problema.actionAddRecurso}"
                                                 value="adicionar"
                                                 reRender="resourceTable, dadosRecurso"/>
                                <h:dataTable id="resourceTable"
                                             value="${problema.problema.recursos}"
                                             var="item" border="0" cellpadding="0" cellspacing="0">
                                    <h:column>
                                        <f:facet name="header">
                                            <label style="display: block; width: 450px; border-bottom: solid 1px #e2e2e2;"></label>
                                        </f:facet>
                                        <h:outputText value="${item.recurso}"/>
                                    </h:column>
                                    <h:column>
                                        <f:facet name="header">
                                            <label style="display: block; width: 50px; border-bottom: solid 1px #e2e2e2;"></label>
                                        </f:facet>
                                        <a4j:commandLink action="${problema.actionRemoveRecurso}"
                                                         value="remover"
                                                         reRender="resourceTable">
                                            <f:param value="${item.recurso}" name="rec"/>
                                        </a4j:commandLink>
                                    </h:column>
                                </h:dataTable>
                            </a4j:region>
                        </div>
                    </rich:tab>
                    <rich:tab label="Taxonomia de Bloom">
                        <div class="content_problema">
                            <h:outputText value="A Taxonomia de Bloom Revisada define seis níveis de cognição: Relembrar
                                          (produzir informação certa a partir da memória),
                                          Entender (dar um significado ao material ou experiências educacionais), Aplicar (usar um
                                          procedimento), Analisar (dividir um conceito em partes e descrever como elas se
                                          relacionam com o todo), Avaliar (fazer julgamentos com base em critérios e padrões),
                                          Criar (reunir dados para formar algo novo, ou reconhecer componentes de uma nova
                                          estrutura)."/>
                            <br /><br />
                            <center>
                                <label>Relembrar</label><br />
                                <rich:editor value="${problema.problema.bloom.relembrar}" width="600" theme="advanced">
                                    <f:param name="theme_advanced_buttons2" value="bold,italic,underline,search,replace,undo,redo,fontsizeselect"/>
                                </rich:editor>
                                <br />
                                <label>Entender</label><br />
                                <rich:editor value="${problema.problema.bloom.entender}" width="600" theme="advanced">
                                    <f:param name="theme_advanced_buttons2" value="bold,italic,underline,search,replace,undo,redo,fontsizeselect"/>
                                </rich:editor>
                                <br />
                                <label>Aplicar</label><br />
                                <rich:editor value="${problema.problema.bloom.aplicar}" width="600" theme="advanced">
                                    <f:param name="theme_advanced_buttons2" value="bold,italic,underline,search,replace,undo,redo,fontsizeselect"/>
                                </rich:editor>
                                <br />
                                <label>Analisar</label><br />
                                <rich:editor value="${problema.problema.bloom.analisar}" width="600" theme="advanced">
                                    <f:param name="theme_advanced_buttons2" value="bold,italic,underline,search,replace,undo,redo,fontsizeselect"/>
                                </rich:editor>
                                <br />
                                <label>Avaliar</label><br />
                                <rich:editor value="${problema.problema.bloom.avaliar}" width="600" theme="advanced">
                                    <f:param name="theme_advanced_buttons2" value="bold,italic,underline,search,replace,undo,redo,fontsizeselect"/>
                                </rich:editor>
                                <br />
                                <label>Criar</label><br />
                                <rich:editor value="${problema.problema.bloom.criar}" width="600" theme="advanced">
                                    <f:param name="theme_advanced_buttons2" value="bold,italic,underline,search,replace,undo,redo,fontsizeselect"/>
                                </rich:editor>
                                <br />
                            </center>
                        </div>
                    </rich:tab>
                </rich:tabPanel>
                <br/>
                <div class="summaryButton">
                    <div class="buttons">
                        <h:commandLink action="${problema.create}" class="positive">
                            <img src="#{request.contextPath}/template/images/tick.png" alt=""/>Salvar e Sair
                            <f:setPropertyActionListener target="${problema.autor}" value="${professor.professorLogado}"/>
                        </h:commandLink>
                        <h:commandLink action="${problema.create2}">
                            <img src="#{request.contextPath}/template/images/save.png" alt=""/>Salvar
                            <f:setPropertyActionListener target="${problema.autor}" value="${professor.professorLogado}"/>
                        </h:commandLink>
                        <h:commandLink action="${problema.listSetup}" immediate="true" class="negative">
                            <img src="#{request.contextPath}/template/images/cross.png" alt=""/>Cancelar
                        </h:commandLink>
                    </div>
                    <div class="clear" style="height:15px"></div>
                </div>
            </h:form>
        </ui:define>
    </ui:composition>
</html>