/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import SisDisciplinas.Bloom;
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
import jpa.BloomJpaController;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author Jhielson
 */
public class BloomController {

    public BloomController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (BloomJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "bloomJpa");
        pagingInfo = new PagingInfo();
        converter = new BloomConverter();
    }
    private Bloom bloom = null;
    private List<Bloom> bloomItems = null;
    private BloomJpaController jpaController = null;
    private BloomConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getBloomCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getBloomItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findBloomEntities(), false);
    }

    public SelectItem[] getBloomItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findBloomEntities(), true);
    }

    public Bloom getBloom() {
        if (bloom == null) {
            bloom = (Bloom) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentBloom", converter, null);
        }
        if (bloom == null) {
            bloom = new Bloom();
        }
        return bloom;
    }

    public String listSetup() {
        reset(true);
        return "bloom_list";
    }

    public String createSetup() {
        reset(false);
        bloom = new Bloom();
        return "bloom_create";
    }

    public String create() {
        try {
            jpaController.create(bloom);
            JsfUtil.addSuccessMessage("Bloom was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("bloom_detail");
    }

    public String editSetup() {
        return scalarSetup("bloom_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        bloom = (Bloom) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentBloom", converter, null);
        if (bloom == null) {
            String requestBloomString = JsfUtil.getRequestParameter("jsfcrud.currentBloom");
            JsfUtil.addErrorMessage("The bloom with id " + requestBloomString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String bloomString = converter.getAsString(FacesContext.getCurrentInstance(), null, bloom);
        String currentBloomString = JsfUtil.getRequestParameter("jsfcrud.currentBloom");
        if (bloomString == null || bloomString.length() == 0 || !bloomString.equals(currentBloomString)) {
            String outcome = editSetup();
            if ("bloom_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit bloom. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(bloom);
            JsfUtil.addSuccessMessage("Bloom was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentBloom");
        Integer id = new Integer(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Bloom was successfully deleted.");
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

    public List<Bloom> getBloomItems() {
        if (bloomItems == null) {
            getPagingInfo();
            bloomItems = jpaController.findBloomEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return bloomItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "bloom_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "bloom_list";
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
        bloom = null;
        bloomItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Bloom newBloom = new Bloom();
        String newBloomString = converter.getAsString(FacesContext.getCurrentInstance(), null, newBloom);
        String bloomString = converter.getAsString(FacesContext.getCurrentInstance(), null, bloom);
        if (!newBloomString.equals(bloomString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
