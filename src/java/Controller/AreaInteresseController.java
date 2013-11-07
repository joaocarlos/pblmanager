/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import SisProfessores.AreaInteresse;
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
import jpa.AreaInteresseJpaController;
import jpa.exceptions.NonexistentEntityException;
import org.hibernate.Hibernate;

/**
 *
 * @author gabriel
 */
public class AreaInteresseController {

    public AreaInteresseController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (AreaInteresseJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "areaInteresseJpa");
        pagingInfo = new PagingInfo();
        converter = new AreaInteresseConverter();
    }
    private AreaInteresse areaInteresse = null;
    private List<AreaInteresse> areaInteresseItems = null;
    private AreaInteresseJpaController jpaController = null;
    private AreaInteresseConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getAreaInteresseCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getAreaInteresseItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findAreaInteresseEntities(), false);
    }

    public SelectItem[] getAreaInteresseItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findAreaInteresseEntities(), true);
    }

    public AreaInteresse getAreaInteresse() {
        if (areaInteresse != null) {
            areaInteresse = (AreaInteresse) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentAreaInteresse", converter, null);
        }
        if (areaInteresse == null) {
            areaInteresse = new AreaInteresse();
        }
        return areaInteresse;
    }

    public String listSetup() {
        reset(true);
        return "areaInteresse_list";
    }

    public String createSetup() {
        reset(false);
        areaInteresse = new AreaInteresse();
        return "areaInteresse_create";
    }

    public String create() {
        try {
            jpaController.create(areaInteresse);
            JsfUtil.addSuccessMessage("AreaInteresse was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("areaInteresse_detail");
    }

    public String editSetup() {
        return scalarSetup("areaInteresse_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        areaInteresse = (AreaInteresse) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentAreaInteresse", converter, null);
        if (areaInteresse == null) {
            String requestAreaInteresseString = JsfUtil.getRequestParameter("jsfcrud.currentAreaInteresse");
            JsfUtil.addErrorMessage("The areaInteresse with id " + requestAreaInteresseString + " no longer exists.");
            return relatedOrListOutcome();
        }
        Hibernate.initialize(areaInteresse.getAreaInteresse());
        return destination;
    }

    public String edit() {
        String areaInteresseString = converter.getAsString(FacesContext.getCurrentInstance(), null, areaInteresse);
        String currentAreaInteresseString = JsfUtil.getRequestParameter("jsfcrud.currentAreaInteresse");
        if (areaInteresseString == null || areaInteresseString.length() == 0 || !areaInteresseString.equals(currentAreaInteresseString)) {
            String outcome = editSetup();
            if ("areaInteresse_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit areaInteresse. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(areaInteresse);
            JsfUtil.addSuccessMessage("AreaInteresse was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentAreaInteresse");
        int id = Integer.parseInt(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("AreaInteresse was successfully deleted.");
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

    public List<AreaInteresse> getAreaInteresseItems() {
        if (areaInteresseItems == null) {
            getPagingInfo();
            areaInteresseItems = jpaController.findAreaInteresseEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return areaInteresseItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "areaInteresse_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "areaInteresse_list";
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
        areaInteresse = null;
        areaInteresseItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        AreaInteresse newAreaInteresse = new AreaInteresse();
        String newAreaInteresseString = converter.getAsString(FacesContext.getCurrentInstance(), null, newAreaInteresse);
        String areaInteresseString = converter.getAsString(FacesContext.getCurrentInstance(), null, areaInteresse);
        if (!newAreaInteresseString.equals(areaInteresseString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
