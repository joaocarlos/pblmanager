package controller;

import SisProfessores.GrupoProfessor;
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
import jpa.GrupoProfessorJpaController;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author uefscompras
 */

public class GrupoProfessorController {

    public GrupoProfessorController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (GrupoProfessorJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "grupoProfessorJpa");
        pagingInfo = new PagingInfo();
        converter = new GrupoProfessorConverter();
    }
    private GrupoProfessor grupoProfessor = null;
    private List<GrupoProfessor> grupoProfessorItems = null;
    private GrupoProfessorJpaController jpaController = null;
    private GrupoProfessorConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getGrupoProfessorCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getGrupoProfessorItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findGrupoProfessorEntities(), false);
    }

    public SelectItem[] getGrupoProfessorItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findGrupoProfessorEntities(), true);
    }

    public GrupoProfessor getGrupoProfessor() {
        if (grupoProfessor == null) {
            grupoProfessor = (GrupoProfessor) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentGrupoProfessor", converter, null);
        }
        if (grupoProfessor == null) {
            grupoProfessor = new GrupoProfessor();
        }
        return grupoProfessor;
    }

    public String listSetup() {
        reset(true);
        return "grupoProfessor_list";
    }

    public String createSetup() {
        reset(false);
        grupoProfessor = new GrupoProfessor();
        return "grupoProfessor_create";
    }

    public String create() {
        try {
            jpaController.create(grupoProfessor);
            JsfUtil.addSuccessMessage("O grupo de usuário foi criado com sucesso.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ocorreu um erro de persistência.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("grupoProfessor_detail");
    }

    public String editSetup() {
        return scalarSetup("grupoProfessor_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        grupoProfessor = (GrupoProfessor) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentGrupoProfessor", converter, null);
        if (grupoProfessor == null) {
            String requestGrupoProfessorString = JsfUtil.getRequestParameter("jsfcrud.currentGrupoProfessor");
            JsfUtil.addErrorMessage("O grupo de usuário com id " + requestGrupoProfessorString + " não existe mais.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String grupoProfessorString = converter.getAsString(FacesContext.getCurrentInstance(), null, grupoProfessor);
        String currentGrupoProfessorString = JsfUtil.getRequestParameter("jsfcrud.currentGrupoProfessor");
        if (grupoProfessorString == null || grupoProfessorString.length() == 0 || !grupoProfessorString.equals(currentGrupoProfessorString)) {
            String outcome = editSetup();
            if ("grupoProfessor_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Não foi possível editar o grupo de usuário. Tente novamente.");
            }
            return outcome;
        }
        try {
            jpaController.edit(grupoProfessor);
            JsfUtil.addSuccessMessage("O grupo de usuário foi atualizado com sucesso.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ocorreu um erro de persistência.");
            return null;
        }
        return detailSetup();
    }

    public String destroy() {
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentGrupoProfessor");
        int id = Integer.parseInt(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("O grupo de usuário foi apagado com sucesso.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return relatedOrListOutcome();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ocorreu um erro de persistência.");
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

    public List<GrupoProfessor> getGrupoProfessorItems() {
        if (grupoProfessorItems == null) {
            getPagingInfo();
            grupoProfessorItems = jpaController.findGrupoProfessorEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return grupoProfessorItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "grupoProfessor_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "grupoProfessor_list";
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
        grupoProfessor = null;
        grupoProfessorItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        GrupoProfessor newGrupoProfessor = new GrupoProfessor();
        String newGrupoProfessorString = converter.getAsString(FacesContext.getCurrentInstance(), null, newGrupoProfessor);
        String grupoProfessorString = converter.getAsString(FacesContext.getCurrentInstance(), null, grupoProfessor);
        if (!newGrupoProfessorString.equals(grupoProfessorString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
