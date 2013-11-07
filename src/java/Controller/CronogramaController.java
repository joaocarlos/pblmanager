/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import SisDisciplinas.Cronograma;
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
import jpa.CronogramaJpaController;
import jpa.exceptions.NonexistentEntityException;
import org.hibernate.Hibernate;

/**
 *
 * @author gabriel
 */
public class CronogramaController {

    public CronogramaController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (CronogramaJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "cronogramaJpa");
        pagingInfo = new PagingInfo();
        converter = new CronogramaConverter();
    }
    private Cronograma cronograma = null;
    private List<Cronograma> cronogramaItems = null;
    private CronogramaJpaController jpaController = null;
    private CronogramaConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getCronogramaCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getCronogramaItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findCronogramaEntities(), false);
    }

    public SelectItem[] getCronogramaItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findCronogramaEntities(), true);
    }

    public Cronograma getCronograma() {
        if (cronograma == null) {
            cronograma = (Cronograma) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentCronograma", converter, null);
        }
        if (cronograma == null) {
            cronograma = new Cronograma();
        }
        return cronograma;
    }

    public String listSetup() {
        reset(true);
        return "cronograma_list";
    }

    public String createSetup() {
        reset(false);
        cronograma = new Cronograma();
        return "cronograma_create";
    }

    public String create() {
        try {
            jpaController.create(cronograma);
            JsfUtil.addSuccessMessage("Cronograma was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("cronograma_detail");
    }

    public String editSetup() {
        return scalarSetup("cronograma_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        Hibernate.initialize(cronogramaItems);
        cronograma = (Cronograma) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentCronograma", converter, null);
        if (cronograma == null) {
            String requestCronogramaString = JsfUtil.getRequestParameter("jsfcrud.currentCronograma");
            JsfUtil.addErrorMessage("The cronograma with id " + requestCronogramaString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String cronogramaString = converter.getAsString(FacesContext.getCurrentInstance(), null, cronograma);
        String currentCronogramaString = JsfUtil.getRequestParameter("jsfcrud.currentCronograma");
        if (cronogramaString == null || cronogramaString.length() == 0 || !cronogramaString.equals(currentCronogramaString)) {
            String outcome = editSetup();
            if ("cronograma_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit cronograma. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(cronograma);
            JsfUtil.addSuccessMessage("Cronograma was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentCronograma");
        int id = Integer.parseInt(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Cronograma was successfully deleted.");
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

    public List<Cronograma> getCronogramaItems() {
        if (cronogramaItems == null) {
            getPagingInfo();
            cronogramaItems = jpaController.findCronogramaEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return cronogramaItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "cronograma_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "cronograma_list";
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
        cronograma = null;
        cronogramaItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Cronograma newCronograma = new Cronograma();
        String newCronogramaString = converter.getAsString(FacesContext.getCurrentInstance(), null, newCronograma);
        String cronogramaString = converter.getAsString(FacesContext.getCurrentInstance(), null, cronograma);
        if (!newCronogramaString.equals(cronogramaString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
