/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import SisDisciplinas.Objetivo;
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
import jpa.ObjetivoJpaController;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author gabriel
 */
public class ObjetivoController {

    public ObjetivoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (ObjetivoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "objetivoJpa");
        pagingInfo = new PagingInfo();
        converter = new ObjetivoConverter();
    }
    private Objetivo objetivo = null;
    private List<Objetivo> objetivoItems = null;
    private ObjetivoJpaController jpaController = null;
    private ObjetivoConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getObjetivoCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getObjetivoItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findObjetivoEntities(), false);
    }

    public SelectItem[] getObjetivoItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findObjetivoEntities(), true);
    }

    public Objetivo getObjetivo() {
        if (objetivo == null) {
            objetivo = (Objetivo) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentObjetivo", converter, null);
        }
        if (objetivo == null) {
            objetivo = new Objetivo();
        }
        return objetivo;
    }

    public String listSetup() {
        reset(true);
        return "objetivo_list";
    }

    public String createSetup() {
        reset(false);
        objetivo = new Objetivo();
        return "objetivo_create";
    }

    public String create() {
        try {
            jpaController.create(objetivo);
            JsfUtil.addSuccessMessage("Objetivo was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("objetivo_detail");
    }

    public String editSetup() {
        return scalarSetup("objetivo_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        objetivo = (Objetivo) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentObjetivo", converter, null);
        if (objetivo == null) {
            String requestObjetivoString = JsfUtil.getRequestParameter("jsfcrud.currentObjetivo");
            JsfUtil.addErrorMessage("The objetivo with id " + requestObjetivoString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String objetivoString = converter.getAsString(FacesContext.getCurrentInstance(), null, objetivo);
        String currentObjetivoString = JsfUtil.getRequestParameter("jsfcrud.currentObjetivo");
        if (objetivoString == null || objetivoString.length() == 0 || !objetivoString.equals(currentObjetivoString)) {
            String outcome = editSetup();
            if ("objetivo_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit objetivo. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(objetivo);
            JsfUtil.addSuccessMessage("Objetivo was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentObjetivo");
        int id = Integer.parseInt(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Objetivo was successfully deleted.");
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

    public List<Objetivo> getObjetivoItems() {
        if (objetivoItems == null) {
            getPagingInfo();
            objetivoItems = jpaController.findObjetivoEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return objetivoItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "objetivo_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "objetivo_list";
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
        objetivo = null;
        objetivoItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Objetivo newObjetivo = new Objetivo();
        String newObjetivoString = converter.getAsString(FacesContext.getCurrentInstance(), null, newObjetivo);
        String objetivoString = converter.getAsString(FacesContext.getCurrentInstance(), null, objetivo);
        if (!newObjetivoString.equals(objetivoString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
