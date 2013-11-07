package controller;

import Criptografia.BlowFish;
import SisProfessores.AreaInteresse;
import SisProfessores.Professor;
import controller.util.PagingInfo;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.FacesException;
import controller.util.JsfUtil;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import jpa.ProfessorJpaController;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author uefscompras
 */
public class ProfessorController {
    
    private Professor professor = null;
    private Professor professorCorrente = null;
    private Professor professorLogado = null;
    private List<Professor> professorItems = null;
    private List<Professor> professorItems2 = null;
    private boolean temIgual;
    private ProfessorJpaController jpaController = null;
    private ProfessorConverter converter = null;
    private PagingInfo pagingInfo = null;
    private String interest;
    private String loginBusca = null;
    private String loginDigitado = null;
    private String senhaBusca = null;
    private String mensagem = null;
    Object user;
    private boolean render = false;

    public ProfessorController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (ProfessorJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "professorJpa");
        pagingInfo = new PagingInfo();
        converter = new ProfessorConverter();
    }

    public boolean isRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getLoginBusca() {
        return loginBusca;
    }

    public void setLoginBusca(String loginBusca) {
        this.loginBusca = loginBusca;
    }

    public String getSenhaBusca() {
        return senhaBusca;
    }

    public void setSenhaBusca(String senhaBusca) {
        this.senhaBusca = senhaBusca;
    }

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getProfessorCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getProfessorItems2AvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findProfessorEntities2(loginBusca, senhaBusca), false);
    }

    public SelectItem[] getProfessorItems2AvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findProfessorEntities2(loginBusca, senhaBusca), true);
    }

    public SelectItem[] getProfessorItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findProfessorEntities(), false);
    }

    public SelectItem[] getProfessorItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findProfessorEntities(), true);
    }

    public String pgLogin() {
        reset(true);
        return "professor_voltaLogin";
    }

    public void logout() {
        //pego a minha sessão atual
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        //pego o objeto de sessão para depois torná-lo nulo
        Object os = (Object) session.getAttribute("currentUser");
        os = null;

        //pego uma instancia do FacesContext para buscar nela o meu ManagedBean de usuário para setar meu objeto global
        FacesContext facesContext = FacesContext.getCurrentInstance();
        // ProfessorController professorC = (ProfessorController) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "professorController");
        // professorC.setProfessor(new Professor());

        //seto o meu objeto de sessão nulo na sessão
        session.setAttribute("currentUser", os);

        //invalido a minha session
        session.invalidate();

        //pego a instancia do navigationHandler para chamar manualmente a navegação para o login do sistema
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext, null, "professor_voltaLogin");

    }

    public String buscaLogin() {
        reset(true);
        int valida = isUsuarioLogado();
        if (valida == 1) {
            mensagem = "O login e/ou senha digitados estão incorretos. Por favor, tente novamente, ou entre em contato com o suporte.";
            return "professor_voltaLogin";
        } else {
            mensagem = null;
            return "welcome";
        }
    }

    public void teste() {
        if (professorItems2 == null) {
            System.out.println("vazio1: " + professorItems2);
        } else {
            System.out.println("cheio1: " + professorItems2);
        }
    }

    public int isUsuarioLogado() {
        getPagingInfo();
        professorItems2 = jpaController.findProfessorEntities2(pagingInfo.getBatchSize(), pagingInfo.getFirstItem(), loginBusca, senhaBusca);
        if (professorItems2.isEmpty()) {
            System.out.println("vazio logado: " + professorItems2);
            return 1;
        } else {
            System.out.println("eh vazio ou não logado: " + professorItems2);
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", professorItems2);
            for (int i = 0; i < professorItems2.size(); i++) {
                if (professorItems2.get(i).getGrupo() != null && professorItems2.get(i).getGrupo().getNome().equalsIgnoreCase("Administrador")) {
                    System.out.println("encontrou o login " + loginBusca);
                    render = true;
                }
            }
            System.out.println(render);
            return 2;
        }
    }

    public boolean retornaTrue() {
        return true;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public Professor getProfessor() {
        if (professor == null) {
            professor = (Professor) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentProfessor", converter, null);
        }
        if (professor == null) {
            professor = new Professor();
        }
        return professor;
    }

    public String listSetup() {
        reset(true);
        return "professor_list";
    }

    public String createSetup() {
        reset(false);
        professor = new Professor();
        return "professor_create";
    }
    public String createSetupIfFailed() {
        //reset(false);
        ///professor = new Professor();
        return "professor_create";
    }

    public String create() {
        try {
            //verificando se já tem esse login
            getPagingInfo();
            temIgual = jpaController.HaveProfessorIgual(pagingInfo.getBatchSize(), pagingInfo.getFirstItem(), professor.getLogin());
            System.out.println("tem igual: " + temIgual);
            if (temIgual == true) {
                JsfUtil.addErrorMessage("O Login digitado já existe, por favor escolha outro!");
                System.out.println("jah tem esse login");
                return createSetup();
            }
            //verificar se o usuario digitou a confirmação de senha diferente da senha digitada
            System.out.println("senha " + professor.getSenha() + " confirma senha " + professor.getConfirmaSenha());
            if (!(BlowFish.decript(professor.getSenha(), professor.getLogin())).equals(professor.getConfirmaSenha())) {
                System.out.println("colocou errado");
                JsfUtil.addErrorMessage("Senha e Confirmação da Senha não correspondem!");
                return createSetupIfFailed();
            }

            jpaController.create(professor);
            JsfUtil.addSuccessMessage("O usuário foi criado com sucesso.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ocorreu um erro de persistência.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("professor_detail");
    }

    public String editSetup() {
        return scalarSetup("professor_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        Integer professorId = Integer.parseInt(JsfUtil.getRequestParameter("jsfcrud.currentProfessor"));
        professor = jpaController.findFullProfessor(professorId);
        if (professor == null) {
            String requestProfessorString = JsfUtil.getRequestParameter("jsfcrud.currentProfessor");
            JsfUtil.addErrorMessage("O usuário com id " + requestProfessorString + " não existe mais.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        System.out.println("----> no edit de prof.");
        String professorString = converter.getAsString(FacesContext.getCurrentInstance(), null, professor);
        String currentProfessorString = JsfUtil.getRequestParameter("jsfcrud.currentProfessor");
        if (professorString == null || professorString.length() == 0 || !professorString.equals(currentProfessorString)) {
            String outcome = editSetup();
            if ("professor_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Não foi possível editar o usuário. Tente novamente.");
            }
            return outcome;
        }
        try {
            //loginVelho = loginBusca;
            System.out.println("logins" + loginBusca + " e " + professor.getLogin());
            loginDigitado = professor.getLogin();
            //pra pegar o professor corrente, não o login corrente
            Integer professorId = Integer.parseInt(JsfUtil.getRequestParameter("jsfcrud.currentProfessor"));
            professorCorrente = jpaController.findFullProfessor(professorId);
            System.out.println("professor corrente " + professorCorrente.getLogin());
            //verificando se tem login igual
            getPagingInfo();
            temIgual = jpaController.HaveProfessorIgual(pagingInfo.getBatchSize(), pagingInfo.getFirstItem(), professor.getLogin());
            System.out.println("tem igual: " + temIgual);
            if (temIgual == true && !((professorCorrente.getLogin()).equals(loginDigitado))) {

                JsfUtil.addErrorMessage("Login já existe");
                System.out.println("jah tem esse login");
                return editSetup();
            }
            //verificar se o usuario digitou a confirmação de senha diferente da senha digitada
            System.out.println("senha " + professor.getSenha() + " confirma senha " + professor.getConfirmaSenha());
            if (!(BlowFish.decript(professor.getSenha(), professor.getLogin())).equals(professor.getConfirmaSenha())) {
                System.out.println("colocou errado");
                JsfUtil.addErrorMessage("Confirmação de senha incorreta");
                return editSetup();
            }
            //verificar se tah mechendo no login logado
            if ((professorCorrente.getLogin()).equals(loginBusca)) {
                loginBusca = loginDigitado;
                senhaBusca = BlowFish.decript(professor.getSenha(), professor.getLogin());
            }
            jpaController.edit(professor);
            JsfUtil.addSuccessMessage("O usuário foi atualizado com sucesso");
            System.out.println("prof: " + professor.getSenha());
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ocorreu um erro de persistência");
            return null;
        }
        return detailSetup();
    }

    public String destroy() {
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentProfessor");
        int id = Integer.parseInt(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("O usuário foi apagado com sucesso.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return relatedOrListOutcome();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ocorreu um erro de persistência");
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

    public List<Professor> getProfessorItems() {
        if (professorItems == null) {
            getPagingInfo();
            professorItems = jpaController.findProfessorEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return professorItems;
    }

    public List<Professor> getProfessorItems2() {
        getPagingInfo();
        professorItems2 = jpaController.findProfessorEntities2(pagingInfo.getBatchSize(), pagingInfo.getFirstItem(), loginBusca, senhaBusca);
        if (professorItems2.isEmpty()) {
            System.out.println("vazio: " + professorItems2);
        }
        return professorItems2;
    }

    public String getProfessorItems3() {
        getPagingInfo();
        professorItems2 = jpaController.findProfessorEntities2(pagingInfo.getBatchSize(), pagingInfo.getFirstItem(), loginBusca, senhaBusca);
        if (professorItems2.isEmpty()) {
            System.out.println("vazio: " + professorItems2);
        }
        return professorItems2.get(0).getNome();
    }

    public Professor getProfessorLogado(){
        getPagingInfo();
        professorItems2 = jpaController.findProfessorEntities2(pagingInfo.getBatchSize(), pagingInfo.getFirstItem(), loginBusca, senhaBusca);
        if (professorItems2.isEmpty()) {
            System.out.println("vazio: " + professorItems2);
        }
        this.professorLogado = professorItems2.get(0);
        return this.professorLogado;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "professor_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "professor_list";
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
        professor = null;
        professorItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Professor newProfessor = new Professor();
        String newProfessorString = converter.getAsString(FacesContext.getCurrentInstance(), null, newProfessor);
        String professorString = converter.getAsString(FacesContext.getCurrentInstance(), null, professor);
        if (!newProfessorString.equals(professorString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }

    public String actionAddAreaInteresse() {
        if (!interest.equals("")){
            AreaInteresse areaInteresse = new AreaInteresse();
            areaInteresse.setAreaInteresse(interest);
            this.professor.getAreasInteresse().add(areaInteresse);
            interest = "";
            return "SUCCESS";
        }
        return "UNSUCCESS";
    }

    public String actionRemoveAreaInteresse() {
        String key = JsfUtil.getRequestParameter("area");
        for (AreaInteresse area : this.professor.getAreasInteresse()) {
            if (area.getAreaInteresse().equalsIgnoreCase(key)) {
                professor.getAreasInteresse().remove(area);
                break;
            }
        }
        return "SUCCESS";
    }
}
