package controller;

import SisDisciplinas.Disciplina;
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
import jpa.DisciplinaJpaController;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author uefscompras
 */

public class DisciplinaController {

    public DisciplinaController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (DisciplinaJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "disciplinaJpa");
        pagingInfo = new PagingInfo();
        converter = new DisciplinaConverter();
    }
    private Disciplina disciplina = null;
    private List<Disciplina> disciplinaItems = null;
    private DisciplinaJpaController jpaController = null;
    private DisciplinaConverter converter = null;
    private PagingInfo pagingInfo = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getDisciplinaCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getDisciplinaItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findDisciplinaEntities(), false);
    }

    public SelectItem[] getDisciplinaItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findDisciplinaEntities(), true);
    }

    public Disciplina getDisciplina() {
        if (disciplina == null) {
            disciplina = (Disciplina) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentDisciplina", converter, null);
        }
        if (disciplina == null) {
            disciplina = new Disciplina();
        }
        return disciplina;
    }

    public String listSetup() {
        reset(true);
        return "disciplina_list";
    }

    public String createSetup() {
        reset(false);
        disciplina = new Disciplina();
        return "disciplina_create";
    }

    public String create() {
        try {
            jpaController.create(disciplina);
            JsfUtil.addSuccessMessage("A disciplina foi criada com sucesso.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ocorreu um erro de persistência");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("disciplina_detail");
    }

    public String editSetup() {
        return scalarSetup("disciplina_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        disciplina = (Disciplina) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentDisciplina", converter, null);
        if (disciplina == null) {
            String requestDisciplinaString = JsfUtil.getRequestParameter("jsfcrud.currentDisciplina");
            JsfUtil.addErrorMessage("A disciplina com id " + requestDisciplinaString + " não existe mais.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String disciplinaString = converter.getAsString(FacesContext.getCurrentInstance(), null, disciplina);
        String currentDisciplinaString = JsfUtil.getRequestParameter("jsfcrud.currentDisciplina");
        if (disciplinaString == null || disciplinaString.length() == 0 || !disciplinaString.equals(currentDisciplinaString)) {
            String outcome = editSetup();
            if ("disciplina_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Não foi possível editar a disciplina. Tente novamente.");
            }
            return outcome;
        }
        try {
            jpaController.edit(disciplina);
            JsfUtil.addSuccessMessage("A disciplina foi atualizada com sucesso.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentDisciplina");
        int id = Integer.parseInt(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("A disciplina foi apagada com sucesso.");
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

    public List<Disciplina> getDisciplinaItems() {
        if (disciplinaItems == null) {
            getPagingInfo();
            disciplinaItems = jpaController.findDisciplinaEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return disciplinaItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "disciplina_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "disciplina_list";
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
        disciplina = null;
        disciplinaItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Disciplina newDisciplina = new Disciplina();
        String newDisciplinaString = converter.getAsString(FacesContext.getCurrentInstance(), null, newDisciplina);
        String disciplinaString = converter.getAsString(FacesContext.getCurrentInstance(), null, disciplina);
        if (!newDisciplinaString.equals(disciplinaString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

}
