<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE composition PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:jsp="http://java.sun.com/JSP/Page"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:ui="http://java.sun.com/jsf/facelets">

    <ui:composition template="../template/portal_template.xhtml">
        <ui:define name="content" >
            <h3>Pesquisa de Problemas</h3><br/>
            Nosso sistema de busca utiliza um conjunto variado de filtros. 
            Preencha os campos abaixo, a partir dos quais você deseja filtrar a sua busca e clique no botão "Pesquisar".<br />
            <strong>ATENÇÃO:</strong> As palavras devem coincidir com o conteúdo armazenado no sistema.
            <h:form >
                <br/>
                <div class="field">
                    <label>Palavra-chave</label><br/>
                    <h:inputText size="75" maxlength="150" class="text"
                                 value="#{problema.palavraChaveBusca}"/>
                </div>
                <br/>

                <!--aki jah eh pra fazer outra busca-->
                <div class="field">

                    <label>Semestre de criação</label><br/>
                    <h:inputText style="width: 75px" maxlength="150" class="text"
                                 value="#{problema.semestreBusca}"/>
                </div>
                <br/>
                <!-- terceira busca-->
                <div class="field">
                    <label>Título do Problema</label><br/>
                    <h:inputText size="75" maxlength="150" class="text"
                                 value="#{problema.tituloBusca}"/>
                </div>
                <br/>
                <div class="field">
                    <label>Disciplina do Problema</label><br/>
                    <h:inputText size="75" maxlength="150" class="text"
                                 value="#{problema.disciplinaBusca}"/>
                </div>
                <br />
                <div class="field">
                    <label>Autor do Problema</label><br/>
                    <h:inputText size="75" maxlength="150" class="text"
                                 value="#{problema.autorBusca}"/>
                </div>
                <br/>
                <div class="field">
                    <label>Assunto</label><br/>
                    <h:inputText size="75" maxlength="150" class="text"
                                 value="#{problema.assuntoBusca}"/>
                </div>
                <br/>
                <div class="field">
                    <label>Palavra da descrição</label><br/>
                    <h:inputText size="75" maxlength="150" class="text"
                                 value="#{problema.textoBusca}"/>

                </div><br/>
                <div class="summaryButton">
                    <div class="buttons">
                        <h:commandLink action="#{problema.buscaSetup4}">
                            <img src="#{request.contextPath}/template/images/search.png" alt=""/>Pesquisar
                        </h:commandLink>
                    </div>
                    <div class="clear"></div>
                </div>

                       <!--  <h:dataTable value="#{problema.problemaItems}" var="iteme" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
                             <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Semestre da criaÃ§Ã£o"/>
                                </f:facet>
                                <h:outputText value="#{iteme.semestreCriacao}"/>
                            </h:column>
                         </h:dataTable>  -->
            </h:form>
        </ui:define>
    </ui:composition>
</html>


