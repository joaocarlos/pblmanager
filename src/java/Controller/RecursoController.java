/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import SisDisciplinas.Recurso;
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
import jpa.RecursoJpaController;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author gabriel
 */
public class RecursoController {

    public RecursoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (RecursoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "recursoJpa");
        pagingInfo = new PagingInfo();
        converter = new RecursoConverter();
    }
    private Recurso recurso = null;
    private List<Recurso> recursoItems = null;
    private RecursoJpaController jpaController = null;
    private RecursoConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getRecursoCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getRecursoItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findRecursoEntities(), false);
    }

    public SelectItem[] getRecursoItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findRecursoEntities(), true);
    }

    public Recurso getRecurso() {
        if (recurso == null) {
            recurso = (Recurso) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentRecurso", converter, null);
        }
        if (recurso == null) {
            recurso = new Recurso();
        }
        return recurso;
    }

    public String listSetup() {
        reset(true);
        return "recurso_list";
    }

    public String createSetup() {
        reset(false);
        recurso = new Recurso();
        return "recurso_create";
    }

    public String create() {
        try {
            jpaController.create(recurso);
            JsfUtil.addSuccessMessage("Recurso was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("recurso_detail");
    }

    public String editSetup() {
        return scalarSetup("recurso_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        recurso = (Recurso) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentRecurso", converter, null);
        if (recurso == null) {
            String requestRecursoString = JsfUtil.getRequestParameter("jsfcrud.currentRecurso");
            JsfUtil.addErrorMessage("The recurso with id " + requestRecursoString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String recursoString = converter.getAsString(FacesContext.getCurrentInstance(), null, recurso);
        String currentRecursoString = JsfUtil.getRequestParameter("jsfcrud.currentRecurso");
        if (recursoString == null || recursoString.length() == 0 || !recursoString.equals(currentRecursoString)) {
            String outcome = editSetup();
            if ("recurso_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit recurso. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(recurso);
            JsfUtil.addSuccessMessage("Recurso was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentRecurso");
        int id = Integer.parseInt(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Recurso was successfully deleted.");
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

    public List<Recurso> getRecursoItems() {
        if (recursoItems == null) {
            getPagingInfo();
            recursoItems = jpaController.findRecursoEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return recursoItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "recurso_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "recurso_list";
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
        recurso = null;
        recursoItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Recurso newRecurso = new Recurso();
        String newRecursoString = converter.getAsString(FacesContext.getCurrentInstance(), null, newRecurso);
        String recursoString = converter.getAsString(FacesContext.getCurrentInstance(), null, recurso);
        if (!newRecursoString.equals(recursoString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
