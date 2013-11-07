/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import SisDisciplinas.PalavraChave;
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
import jpa.PalavraChaveJpaController;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author gabriel
 */
public class PalavraChaveController {

    public PalavraChaveController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (PalavraChaveJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "palavraChaveJpa");
        pagingInfo = new PagingInfo();
        converter = new PalavraChaveConverter();
    }
    private PalavraChave palavraChave = null;
    private List<PalavraChave> palavraChaveItems = null;
    private PalavraChaveJpaController jpaController = null;
    private PalavraChaveConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getPalavraChaveCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getPalavraChaveItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findPalavraChaveEntities(), false);
    }

    public SelectItem[] getPalavraChaveItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findPalavraChaveEntities(), true);
    }

    public PalavraChave getPalavraChave() {
        if (palavraChave == null) {
            palavraChave = (PalavraChave) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentPalavraChave", converter, null);
        }
        if (palavraChave == null) {
            palavraChave = new PalavraChave();
        }
        return palavraChave;
    }

    public String listSetup() {
        reset(true);
        return "palavraChave_list";
    }

    public String createSetup() {
        reset(false);
        palavraChave = new PalavraChave();
        return "palavraChave_create";
    }

    public String create() {
        try {
            jpaController.create(palavraChave);
            JsfUtil.addSuccessMessage("PalavraChave was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("palavraChave_detail");
    }

    public String editSetup() {
        return scalarSetup("palavraChave_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        palavraChave = (PalavraChave) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentPalavraChave", converter, null);
        if (palavraChave == null) {
            String requestPalavraChaveString = JsfUtil.getRequestParameter("jsfcrud.currentPalavraChave");
            JsfUtil.addErrorMessage("The palavraChave with id " + requestPalavraChaveString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String palavraChaveString = converter.getAsString(FacesContext.getCurrentInstance(), null, palavraChave);
        String currentPalavraChaveString = JsfUtil.getRequestParameter("jsfcrud.currentPalavraChave");
        if (palavraChaveString == null || palavraChaveString.length() == 0 || !palavraChaveString.equals(currentPalavraChaveString)) {
            String outcome = editSetup();
            if ("palavraChave_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit palavraChave. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(palavraChave);
            JsfUtil.addSuccessMessage("PalavraChave was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentPalavraChave");
        int id = Integer.parseInt(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("PalavraChave was successfully deleted.");
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

    public List<PalavraChave> getPalavraChaveItems() {
        if (palavraChaveItems == null) {
            getPagingInfo();
            palavraChaveItems = jpaController.findPalavraChaveEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return palavraChaveItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "palavraChave_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "palavraChave_list";
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
        palavraChave = null;
        palavraChaveItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        PalavraChave newPalavraChave = new PalavraChave();
        String newPalavraChaveString = converter.getAsString(FacesContext.getCurrentInstance(), null, newPalavraChave);
        String palavraChaveString = converter.getAsString(FacesContext.getCurrentInstance(), null, palavraChave);
        if (!newPalavraChaveString.equals(palavraChaveString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
