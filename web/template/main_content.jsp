<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE composition PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:jsp="http://java.sun.com/JSP/Page"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:ui="http://java.sun.com/jsf/facelets">

    <ui:composition template="portal_template.xhtml">

        <ui:define name="content" >

            <div class="entry">
                 <h1 class="title">Ações</h1>
                <fieldset>

                    <f:view >
                        <h:form>
                            <br/>


                            <h:commandLink action="#{areaInteresse.listSetup}" value="Show All AreaInteresse Items"/>

                            <br/>
                            <h:commandLink action="#{professor.listSetup}" value="Show All Professor Items"/>

                            <br/>
                            <h:commandLink action="#{grupoProfessor.listSetup}" value="Show All GrupoProfessor Items"/>

                            <br/>
                            <h:commandLink action="#{recurso.listSetup}" value="Show All Recurso Items"/>

                            <br/>
                            <h:commandLink action="#{produto.listSetup}" value="Show All Produto Items"/>

                            <br/>
                            <h:commandLink action="#{problema.listSetup}" value="Show All Problema Items"/>

                            <br/>
                            <h:commandLink action="#{palavraChave.listSetup}" value="Show All PalavraChave Items"/>

                            <br/>
                            <h:commandLink action="#{objetivo.listSetup}" value="Show All Objetivo Items"/>

                            <br/>
                            <h:commandLink action="#{imagem.listSetup}" value="Show All Imagem Items"/>

                            <br/>
                            <h:commandLink action="#{disciplina.listSetup}" value="Show All Disciplina Items"/>

                            <br/>
                            <h:commandLink action="#{cronograma.listSetup}" value="Show All Cronograma Items"/>

                            <br/>
                            <h:commandLink action="#{atividade.listSetup}" value="Show All Atividade Items"/>

                        </h:form>
                    </f:view>
                </fieldset>
            </div>


        </ui:define>
    </ui:composition>
</html>