/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import SisDisciplinas.Imagem;
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
import jpa.ImagemJpaController;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author dot
 */
public class ImagemController {

    public ImagemController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (ImagemJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "imagemJpa");
        pagingInfo = new PagingInfo();
        converter = new ImagemConverter();
    }
    private Imagem imagem = null;
    private List<Imagem> imagemItems = null;
    private ImagemJpaController jpaController = null;
    private ImagemConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getImagemCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getImagemItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findImagemEntities(), false);
    }

    public SelectItem[] getImagemItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findImagemEntities(), true);
    }

    public Imagem getImagem() {
        if (imagem == null) {
            imagem = (Imagem) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentImagem", converter, null);
        }
        if (imagem == null) {
            imagem = new Imagem();
        }
        return imagem;
    }

    public String listSetup() {
        reset(true);
        return "imagem_list";
    }

    public String createSetup() {
        reset(false);
        imagem = new Imagem();
        return "imagem_create";
    }

    public String create() {
        try {
            jpaController.create(imagem);
            JsfUtil.addSuccessMessage("Imagem was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("imagem_detail");
    }

    public String editSetup() {
        return scalarSetup("imagem_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        imagem = (Imagem) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentImagem", converter, null);
        if (imagem == null) {
            String requestImagemString = JsfUtil.getRequestParameter("jsfcrud.currentImagem");
            JsfUtil.addErrorMessage("The imagem with id " + requestImagemString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String imagemString = converter.getAsString(FacesContext.getCurrentInstance(), null, imagem);
        String currentImagemString = JsfUtil.getRequestParameter("jsfcrud.currentImagem");
        if (imagemString == null || imagemString.length() == 0 || !imagemString.equals(currentImagemString)) {
            String outcome = editSetup();
            if ("imagem_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit imagem. Try again.");
            }
            return outcome;
        }
        try {
            jpaController.edit(imagem);
            JsfUtil.addSuccessMessage("Imagem was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentImagem");
        int id = Integer.parseInt(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Imagem was successfully deleted.");
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

    public List<Imagem> getImagemItems() {
        if (imagemItems == null) {
            getPagingInfo();
            imagemItems = jpaController.findImagemEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return imagemItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "imagem_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "imagem_list";
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
        imagem = null;
        imagemItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Imagem newImagem = new Imagem();
        String newImagemString = converter.getAsString(FacesContext.getCurrentInstance(), null, newImagem);
        String imagemString = converter.getAsString(FacesContext.getCurrentInstance(), null, imagem);
        if (!newImagemString.equals(imagemString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
