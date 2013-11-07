/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import SisDisciplinas.Atividade;
import controller.util.PagingInfo;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.FacesException;
import controller.util.JsfUtil;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import jpa.AtividadeJpaController;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author gabriel
 */
public class AtividadeController {

    public AtividadeController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (AtividadeJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "atividadeJpa");
        pagingInfo = new PagingInfo();
        converter = new AtividadeConverter();
    }
    private Atividade atividade = null;
    private List<Atividade> atividadeItems = null;
    private AtividadeJpaController jpaController = null;
    private AtividadeConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getAtividadeCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getAtividadeItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findAtividadeEntities(), false);
    }

    public SelectItem[] getAtividadeItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findAtividadeEntities(), true);
    }

    public Atividade getAtividade() {
        if (atividade == null) {
            atividade = (Atividade) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentAtividade", converter, null);
        }
        if (atividade == null) {
            atividade = new Atividade();
        }
        return atividade;
    }

    public String listSetup() {
        reset(true);
        return "atividade_list";
    }

    public String createSetup() {
        reset(false);
        atividade = new Atividade();
        return "atividade_create";
    }

    public String create() {
        try {
            jpaController.create(atividade);
            JsfUtil.addSuccessMessage("Atividade was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("atividade_detail");
    }

    public String editSetup() {
        return scalarSetup("atividade_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        atividade = (Atividade) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentAtividade", converter, null);
        if (atividade == null) {
            String requestAtividadeString = JsfUtil.getRequestParameter("jsfcrud.currentAtividade");
            JsfUtil.addErrorMessage("The atividade with id " + requestAtividadeString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String atividadeString = converter.getAsString(FacesContext.getCurrentInstance(), null, atividade);
        String currentAtividadeString = JsfUtil.getRequestParameter("jsfcrud.currentAtividade");
        if (atividadeString == null || atividadeString.length() == 0 || !atividadeString.equals(currentAtividadeString)) {
            String outcome = editSetup();
            if ("atividade_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit atividade. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(atividade);
            JsfUtil.addSuccessMessage("Atividade was successfully updated.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return detailSetup();
    }

    public String destroy() {
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentAtividade");
        int id = Integer.parseInt(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Atividade was successfully deleted.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return relatedOrListOutcome();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return relatedOrListOutcome();
    }

    private String relatedOrListOutcome() {
        String relatedControllerOutcome = relatedControllerOutcome();
        if (relatedControllerOutcome != null) {
            return relatedControllerOutcome;
        }
        return listSetup();
    }

    public List<Atividade> getAtividadeItems() {
        if (atividadeItems == null) {
            getPagingInfo();
            atividadeItems = jpaController.findAtividadeEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return atividadeItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "atividade_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "atividade_list";
    }

    private String relatedControllerOutcome() {
        String relatedControllerString = JsfUtil.getRequestParameter("jsfcrud.relatedController");
        String relatedControllerTypeString = JsfUtil.getRequestParameter("jsfcrud.relatedControllerType");
        if (relatedControllerString != null && relatedControllerTypeString != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            Object relatedController = context.getApplication().getELResolver().getValue(context.getELContext(), null, relatedControllerString);
            try {
                Class<?> relatedControllerType = Class.forName(relatedControllerTypeString);
                Method detailSetupMethod = relatedControllerType.getMethod("detailSetup");
                return (String) detailSetupMethod.invoke(relatedController);
            } catch (ClassNotFoundException e) {
                throw new FacesException(e);
            } catch (NoSuchMethodException e) {
                throw new FacesException(e);
            } catch (IllegalAccessException e) {
                throw new FacesException(e);
            } catch (InvocationTargetException e) {
                throw new FacesException(e);
            }
        }
        return null;
    }

    private void reset(boolean resetFirstItem) {
        atividade = null;
        atividadeItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Atividade newAtividade = new Atividade();
        String newAtividadeString = converter.getAsString(FacesContext.getCurrentInstance(), null, newAtividade);
        String atividadeString = converter.getAsString(FacesContext.getCurrentInstance(), null, atividade);
        if (!newAtividadeString.equals(atividadeString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
