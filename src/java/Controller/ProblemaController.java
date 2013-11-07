package controller;

import SisDisciplinas.Atividade;
import SisDisciplinas.ImageData;
import SisDisciplinas.Imagem;
import SisDisciplinas.Objetivo;
import SisDisciplinas.PalavraChave;
import SisDisciplinas.Problema;
import SisDisciplinas.Recurso;
import SisProfessores.Professor;
import controller.util.PagingInfo;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.FacesException;
import controller.util.JsfUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import jpa.ProblemaJpaController;
import jpa.exceptions.NonexistentEntityException;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

/**
 *
 * @author uefscompras
 */
public class ProblemaController {

    private Problema problema = null;
    private Problema problema2 = null;
    private List<Problema> problemaItems = null;
    private List<Problema> problemaItems4 = null;
    private ProblemaJpaController jpaController = null;
    private ProblemaConverter converter = null;
    private PagingInfo pagingInfo = null;
    private String keyWord;
    private String goal;
    private String resource;
    private String product;
    private String activities;
    private String palavraChaveBusca;
    private String tituloBusca;
    private String semestreBusca;
    private String disciplinaBusca;
    private String autorBusca;
    private String assuntoBusca;
    private String textoBusca;
    private Professor autor;

    public ProblemaController() {
        this.autor = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (ProblemaJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "problemaJpa");
        pagingInfo = new PagingInfo();
        converter = new ProblemaConverter();
    }

    public int getQntImagens() {
        return this.problema.getImagensAssociadas().size();
    }

    public Professor getAutor() {
        return autor;
    }

    public void setAutor(Professor autor) {
        this.autor = autor;
    }

    public String getTextoBusca() {
        return textoBusca;
    }

    public void setTextoBusca(String textoBusca) {
        this.textoBusca = textoBusca;
    }

    public String getAssuntoBusca() {
        return assuntoBusca;
    }

    public void setAssuntoBusca(String assuntoBusca) {
        this.assuntoBusca = assuntoBusca;
    }

    public String getAutorBusca() {
        return autorBusca;
    }

    public void setAutorBusca(String autorBusca) {
        this.autorBusca = autorBusca;
    }

    public String getDisciplinaBusca() {
        return disciplinaBusca;
    }

    public void setDisciplinaBusca(String disciplinaBusca) {
        this.disciplinaBusca = disciplinaBusca;
    }

    public String getTituloBusca() {
        //  System.out.println("aki titulo: " + this.tituloBusca);
        return tituloBusca;
    }

    public void setTituloBusca(String tituloBusca) {
        this.tituloBusca = tituloBusca;
    }

    public String getSemestreBusca() {
        // System.out.println("aki semestre: " + this.semestreBusca);
        return semestreBusca;
    }

    public void setSemestreBusca(String semestreBusca) {
        this.semestreBusca = semestreBusca;
    }

    public String getPalavraChaveBusca() {
        // System.out.println("aki palavra chave: " + this.palavraChaveBusca);
        return palavraChaveBusca;

    }

    public void setPalavraChaveBusca(String palavraChaveBusca) {
        this.palavraChaveBusca = palavraChaveBusca;
    }
    private Date activitiesDate;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(jpaController.getProblemaCount());
        }
        return pagingInfo;
    }

    public SelectItem[] getProblemaItems4AvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findProblemaEntities4(palavraChaveBusca, semestreBusca, tituloBusca, disciplinaBusca, autorBusca, assuntoBusca, textoBusca), false);
    }

    public SelectItem[] getProblemaItems4AvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findProblemaEntities4(palavraChaveBusca, semestreBusca, tituloBusca, disciplinaBusca, autorBusca, assuntoBusca, textoBusca), true);
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public Date getActivitiesDate() {
        return activitiesDate;
    }

    public void setActivitiesDate(Date activitiesDate) {
        this.activitiesDate = activitiesDate;
    }

    public Problema getProblema() {
        if (problema == null) {
            problema = (Problema) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentProblema", converter, null);
        }
        if (problema == null) {
            problema = new Problema();
        }
        return problema;
    }

    public String listSetup() {
        reset(true);
        return "problema_list";
    }

    public String gerarRelatorio() {
        return scalarSetup("gerar_relatorio");
    }

    public String buscaSetup2() {
        reset(true);
        return "problema_busca2";
    }

    public String buscaSetup4() {
        reset(true);
        return "problema_buscaCompleta";
    }

    public String createSetup() {
        reset(false);
        problema = new Problema();
        System.out.println("no createSetup: " + problema.getTituloProblema());
        if (this.problema.getPalavrasChave().size() == 0) {
            System.out.println("imprima se naum tem palavras-chave");
        } else {
            System.out.println("tem palavras chave");
        }
        return "problema_create";
    }

    public void createSetup2() {
        System.out.println("no createSetup2: " + problema.getTituloProblema());
        if (this.problema.getPalavrasChave().size() == 0) {
            System.out.println("imprima se naum tem palavras-chave");
        } else {
            System.out.println("tem palavras chave");
        }
    }
    public String create2()
    {
        Date data = new Date();
        this.problema.setDataCriacao(data);
        this.problema.setProfessorAutor(this.autor);

        clearUploadData();
        try {
            jpaController.create(problema);
            System.out.println("criou!!");
            JsfUtil.addSuccessMessage("O Problema foi salvo com sucesso.");

        } catch (Exception e) {
            System.out.println("deu erro!");
            JsfUtil.ensureAddErrorMessage(e, "Ocorreu um erro de persistência.");
            return null;
        }
        if (problema2 != null) {
            problema.setProblemaDeReferencia(problema2);
        }
        problema2 = null;
        return "problema_edit";
    }
    public String create() {
        Date data = new Date();
        this.problema.setDataCriacao(data);
        this.problema.setProfessorAutor(this.autor);

        if (this.problema.getPalavrasChave().size() == 0) {
            System.out.println("X imprima se naum tem palavras-chave");
        } else {
            System.out.println("X tem palavras chave");
        }

        clearUploadData();
        try {
            jpaController.create(problema);
            System.out.println("criou!!");
            JsfUtil.addSuccessMessage("O Problema foi criado com sucesso.");

        } catch (Exception e) {
            System.out.println("deu erro!");
            JsfUtil.ensureAddErrorMessage(e, "Ocorreu um erro de persistência.");
            return null;
        }
        if (problema2 != null) {
            problema.setProblemaDeReferencia(problema2);
        }
        problema2 = null;
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("problema_detail");
    }

    public String editSetup() {
        return scalarSetup("problema_edit");
    }

    public String criarCopiandoDeOutroSetup() {
        Integer problemaId = Integer.parseInt(JsfUtil.getRequestParameter("jsfcrud.currentProblema"));
        problema2 = jpaController.findFullProblema(problemaId);
        System.out.println("titulo antigo: " + problema2.getTituloProblema());

        problema = new Problema();

        problema.setTituloProblema(problema2.getTituloProblema());
        problema.setAssunto(problema2.getAssunto());
        problema.setAvaliacaoAlunos(problema2.getAvaliacaoAlunos());
        problema.setDescricao(problema2.getDescricao());
        problema.setOrientacoesTutor(problema2.getOrientacoesTutor());
        problema.setProdutos(problema2.getProdutos());
        problema.setSemestreCriacao(problema2.getSemestreCriacao());

        for (int i = 0; i < problema2.getPalavrasChave().size(); i++) {
            //System.out.println("entrou");
            PalavraChave palavraChave = new PalavraChave();
            String palavraa = problema2.getPalavrasChave().get(i).getPalavraChave();
            //System.out.println("palavra: "+ palavraa);
            palavraChave.setPalavraChave(palavraa);
            //System.out.println("teste1");
            this.problema.getPalavrasChave().add(palavraChave);
            //System.out.println("teste2");
            }

        for (int i = 0; i < problema2.getObjetivos().size(); i++) {
            Objetivo objetivo = new Objetivo();
            objetivo.setObjetivo(problema2.getObjetivos().get(i).getObjetivo());
            this.problema.getObjetivos().add(objetivo);

        }

        for (int i = 0; i < problema2.getRecursos().size(); i++) {
            Recurso recurso = new Recurso();
            recurso.setRecurso(problema2.getRecursos().get(i).getRecurso());
            this.problema.getRecursos().add(recurso);
        }

        for (int i = 0; i < problema2.getCronograma().getAtividades().size(); i++) {
            Atividade atividade = new Atividade();
            atividade.setDescricaoAtividade(problema2.getCronograma().getAtividades().get(i).getDescricaoAtividade());
            atividade.setDataAtividade(problema2.getCronograma().getAtividades().get(i).getDataAtividade());
            this.problema.getCronograma().getAtividades().add(atividade);
        }

        if (this.problema.getPalavrasChave().size() == 0) {
            System.out.println("Y imprima se naum tem palavras-chave");
        } else {
            System.out.println("Y tem palavras chave");
        }


        // *******Copiando imagens do problema de referência para o novo *******
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String pathCapturado = servletContext.getRealPath("/");
        String pathReal = transformaPath(pathCapturado);
        char slash = identificaTipoSeparadorPath(pathReal);
        File copiaImagem = null;
        for (int j = 0; j < problema2.getImagensAssociadas().size(); j++) {
            String path = problema2.getImagensAssociadas().get(j).getReferencia();
            File imagemOriginal = new File(path);
            do {
                  if(slash == '\\'){
                    copiaImagem = new File(pathReal + "\\imagens\\" + "imagemCopia" + this.counter2 + ".jpg");
                    this.counter2++;
                  }
                  else if(slash == '/'){
                      copiaImagem = new File(pathReal + "/imagens/" + "imagemCopia" + this.counter2 + ".jpg");
                      this.counter2++;
                  }
            } while (copiaImagem.exists());
            try {
                FileInputStream fisOrigem = new FileInputStream(imagemOriginal);
                FileOutputStream fisDestino = new FileOutputStream(copiaImagem);
                FileChannel fcOrigem = fisOrigem.getChannel();
                FileChannel fcDestino = fisDestino.getChannel();
                fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);
                fisOrigem.close();
                fisDestino.close();
            } catch (Exception e) {
            }
            copiaImagem = imagemOriginal;
            int temp = this.counter2 - 1;
            Imagem imagem = new Imagem();
            if(slash == '\\')
                imagem.setReferencia(pathReal + "\\imagens\\" + "imagemCopia" + temp + ".jpg");
            if(slash == '/')
                imagem.setReferencia(pathReal + "/imagens/" + "imagemCopia" + temp + ".jpg");            
            imagem.setNome(pegaNomeImagem(imagem.getReferencia()));
            this.problema.getImagensAssociadas().add(imagem);
        }
        // *********************************************************************

        return "problema_criarCopiando";
    }

    private String scalarSetup(String destination) {
        reset(false);

        Integer problemaId = Integer.parseInt(JsfUtil.getRequestParameter("jsfcrud.currentProblema"));
        problema = jpaController.findFullProblema(problemaId);

        if (problema == null) {
            String requestProblemaString = JsfUtil.getRequestParameter("jsfcrud.currentProblema");
            JsfUtil.addErrorMessage("O problema com id " + requestProblemaString + " não existe mais.");
            return relatedOrListOutcome();
        }
        System.out.println("                            passou no scalar");
        return destination;
    }

    public String edit() {
        System.out.println("                             entrou no edit");
        clearUploadData();
        String problemaString = converter.getAsString(FacesContext.getCurrentInstance(), null, problema);
        String currentProblemaString = JsfUtil.getRequestParameter("jsfcrud.currentProblema");
        if (problemaString == null || problemaString.length() == 0 || !problemaString.equals(currentProblemaString)) {
            String outcome = editSetup();
            if ("problema_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Não foi possível editar o problema. Tente novamente.");
            }
            System.out.println("if do edit");
            return outcome;
        }
        try {
            jpaController.edit(problema);
            JsfUtil.addSuccessMessage("O problema foi atualizado com sucesso.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ocorreu um erro de persistência.");
            return null;
        }
        return detailSetup();
    }
    public String edit2() {
        clearUploadData();
        String problemaString = converter.getAsString(FacesContext.getCurrentInstance(), null, problema);
        String currentProblemaString = JsfUtil.getRequestParameter("jsfcrud.currentProblema");
        if (problemaString == null || problemaString.length() == 0 || !problemaString.equals(currentProblemaString)) {
            String outcome = editSetup();
            if ("problema_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Não foi possível editar o problema. Tente novamente.");
            }
            return outcome;
        }
        try {
            jpaController.edit(problema);
            JsfUtil.addSuccessMessage("O problema foi atualizado com sucesso.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ocorreu um erro de persistência.");
            return null;
        }
        return "problema_edit";
    }

    public String editAndCopy() {

        Integer problemaId = Integer.parseInt(JsfUtil.getRequestParameter("jsfcrud.currentProblema"));
        problema2 = jpaController.findFullProblema(problemaId);

        problema = new Problema();
        problema = problema2;
        System.out.println("1");
        //aki eh pra criar um novo
        Date data = new Date();
        this.problema.setDataCriacao(data);
        //clearUploadData();
        System.out.println("2");
        try {
            System.out.println("2.5");
            jpaController.create(problema);
            JsfUtil.addSuccessMessage("O Problema foi criado com sucesso.");
            System.out.println("3");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ocorreu um erro de persistência.");
            System.out.println("4");
            return null;
        }
        return listSetup();

    }

    public String destroy() {
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentProblema");
        //removerImagemAssociadaProblema();
        int id = Integer.parseInt(idAsString);
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("O problema foi apagado com sucesso.");
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

    public List<Problema> getProblemaItems() {
        if (problemaItems == null) {
            getPagingInfo();
            problemaItems = jpaController.findProblemaEntities(pagingInfo.getBatchSize(), pagingInfo.getFirstItem());
        }
        return problemaItems;
    }

    public List<Problema> getProblemaItems4() {
        getPagingInfo();
        problemaItems4 = jpaController.findProblemaEntities4(pagingInfo.getBatchSize(), pagingInfo.getFirstItem(), palavraChaveBusca, semestreBusca, tituloBusca, disciplinaBusca, autorBusca, assuntoBusca, textoBusca);
        return problemaItems4;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "problema_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "problema_list";
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
        problema = null;
        problemaItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        System.out.println("                                  validate");
        Problema newProblema = new Problema();
        String newProblemaString = converter.getAsString(FacesContext.getCurrentInstance(), null, newProblema);
        String problemaString = converter.getAsString(FacesContext.getCurrentInstance(), null, problema);
        if (!newProblemaString.equals(problemaString)) {
            System.out.println("                                  if do validade");
            createSetup();
        }
    }

    public void validateCreate2(FacesContext facesContext, UIComponent component, Object value) {
        System.out.println("                                  validate");
        Problema newProblema = new Problema();
        String newProblemaString = converter.getAsString(FacesContext.getCurrentInstance(), null, newProblema);
        String problemaString = converter.getAsString(FacesContext.getCurrentInstance(), null, problema);
        if (!newProblemaString.equals(problemaString)) {
            System.out.println("                                  if do validade");
            createSetup2();
        }
    }

    public Converter getConverter() {
        return converter;
    }

    //**** Código abaixo referente aos botões de "Adicionar" dinâmicos ****//
    public String actionAddPalavraChave() {
        if (!keyWord.equals("")) {
            if (this.problema.getPalavrasChave().size() < 5) {
                PalavraChave palavraChave = new PalavraChave();
                System.out.println("adicionando: " + keyWord);
                palavraChave.setPalavraChave(keyWord);
                System.out.println("teste1");
                this.problema.getPalavrasChave().add(palavraChave);
                System.out.println("teste2");

            }
            keyWord = "";
            return "SUCCESS";
        }
        return "UNSUCCESS";
    }

    public String actionRemovePalavraChave() {
        String key = JsfUtil.getRequestParameter("pc");
        List<PalavraChave> lista = problema.getPalavrasChave();

        for (int i = 0; i < lista.size(); i++) {
            PalavraChave pc = lista.get(i);
            if (pc.getPalavraChave().equalsIgnoreCase(key)) {
                lista.remove(i);
                problema.setPalavrasChave(lista);
                break;
            }
        }
        return "SUCCESS";
    }

    public String actionAddObjetivo() {
        if (!goal.equals("")) {
            Objetivo objetivo = new Objetivo();
            objetivo.setObjetivo(goal);
            this.problema.getObjetivos().add(objetivo);
            goal = "";
            return "SUCCESS";
        }
        return "UNSUCCESS";
    }

    public String actionRemoveObjetivo() {
        String key = JsfUtil.getRequestParameter("obj");
        List<Objetivo> lista = problema.getObjetivos();

        for (int i = 0; i < lista.size(); i++) {
            Objetivo obj = lista.get(i);
            if (obj.getObjetivo().equalsIgnoreCase(key)) {
                lista.remove(i);
                problema.setObjetivos(lista);
                break;
            }
        }
        
        return "SUCCESS";
    }

    public String actionAddRecurso() {
        if (!resource.equals("")) {
            Recurso recurso = new Recurso();
            recurso.setRecurso(resource);
            this.problema.getRecursos().add(recurso);
            resource = "";
            return "SUCCESS";
        }
        return "UNSUCCESS";
    }

    public String actionRemoveRecurso() {
        String key = JsfUtil.getRequestParameter("rec");
        List<Recurso> lista = problema.getRecursos();

        for (int i = 0; i < lista.size(); i++) {
            Recurso rec = lista.get(i);
            if (rec.getRecurso().equalsIgnoreCase(key)) {
                lista.remove(i);
                problema.setRecursos(lista);
                break;
            }
        }
        
        return "SUCCESS";
    }

    public String actionAddAtividades() {
        if (!activities.equals("") && activitiesDate != null) {
            Atividade atividade = new Atividade();
            atividade.setDescricaoAtividade(activities);
            atividade.setDataAtividade(activitiesDate);
            this.problema.getCronograma().getAtividades().add(atividade);
            activities = "";
            activitiesDate = null;
            return "SUCCESS";
        }
        return "UNSUCCESS";
    }

    public String actionRemoveAtividades() {
        String key = JsfUtil.getRequestParameter("act");
        List<Atividade> lista = problema.getCronograma().getAtividades();

        for (int i = 0; i < lista.size(); i++) {
            Atividade act = lista.get(i);
            if (act.getDescricaoAtividade().equalsIgnoreCase(key)) {
                lista.remove(i);
                problema.getCronograma().setAtividades(lista);
                break;
            }
        }

        return "SUCCESS";
    }

    //***************** Exibir Imagens de Problema ******************* //
    public void paintLivre(OutputStream stream, Object object) throws IOException, FileNotFoundException {
        File tempFile;
        InputStream is = null;
        byte[] buffer = null;
        try {
            if (this.problema.getImagensAssociadas().size() != 0) {
                for (int x = 0; x < this.problema.getImagensAssociadas().size(); x++) {
                    tempFile = new File(this.problema.getImagensAssociadas().get(x).getReferencia());
                    is = new FileInputStream(tempFile);
                    buffer = new byte[is.available()];
                    is.read(buffer);
                    System.out.println("esta tentando mostrar uma imagem no painel");
                }
                is.close();
            }
        } catch (IOException ex) {
            System.out.println("Erro ao tentar obter array de bytes da imagem:" + ex);
        }
        stream.write(buffer);
    }

    public void paintLivre2(OutputStream stream, Object object) throws IOException, FileNotFoundException {
        String pathToShow = JsfUtil.getRequestParameter("path");
        File tempFile;
        InputStream is = null;
        byte[] buffer = null;
        try {
            tempFile = new File(pathToShow);
            is = new FileInputStream(tempFile);
            buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
        } catch (IOException ex) {
        }
        stream.write(buffer);
    }

    public String removerImagem() {
        String key = JsfUtil.getRequestParameter("img2");
        for (int i = 0; i < this.problema.getImagensAssociadas().size(); i++) {
            Imagem image = this.problema.getImagensAssociadas().get(i);
            if (image.getReferencia().equalsIgnoreCase(key)) {
                this.problema.getImagensAssociadas().remove(i);
                File temp = new File(image.getReferencia());
                if (temp.exists() && temp.isFile()) {
                    temp.delete();
                }
                break;
            }
        }
        return "SUCCESS";
    }

    private void removerImagemAssociadaProblema() {
        for (int i = 0; i < this.problema.getImagensAssociadas().size(); i++) {
            Imagem image = this.problema.getImagensAssociadas().get(i);
            this.problema.getImagensAssociadas().remove(i);
            File temp = new File(image.getReferencia());
            if (temp.exists() && temp.isFile()) {
                temp.delete();
            }
        }
    }
    //***************** Extraido da classe Upload ******************* //
    private ArrayList<ImageData> files = new ArrayList<ImageData>();
    private int uploadsAvailable = 10;
    private boolean autoUpload = true;
    private boolean useFlash = false;
    private int counter = 0;
    private int counter2 = 0;

    public int getSize() {
        if (getFiles().size() > 0) {
            return getFiles().size();
        } else {
            return 0;
        }
    }

    public void paint(OutputStream stream, Object object) throws IOException {
        stream.write(getFiles().get((Integer) object).getData());
    }

    public void listener(UploadEvent event) throws Exception {
        UploadItem item = event.getUploadItem();
        ImageData file = new ImageData();
        file.setLength(item.getData().length);
        file.setName(item.getFileName());
        file.setData(item.getData());
        files.add(file);
        uploadsAvailable--;

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String pathCapturado = servletContext.getRealPath("/");
        String pathReal = transformaPath(pathCapturado);
        char slash = identificaTipoSeparadorPath(pathReal);
        File file2 = null;
        try {
            //checar se é um arquivo temporário
            //e copiar o arquivo
            if (item.isTempFile()) {
                do {
                      if(slash == '\\'){
                        file2 = new File(pathReal + "\\imagens\\" + "imagem" + this.counter + ".jpg");
                        this.counter++;
                      }
                      else if(slash == '/'){
                        file2 = new File(pathReal + "/imagens/" + "imagem" + this.counter + ".jpg");
                        this.counter++;
                      }
                } while (file2.exists());
                file2 = item.getFile();
                //caso nao seja, salvar no disco
            } else {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                b.write(item.getData());
                do {
                      if(slash == '\\'){
                        file2 = new File(pathReal + "\\imagens\\" + "imagem" + counter + ".jpg");
                        this.counter++;
                      }
                      else if(slash == '/'){
                        file2 = new File(pathReal + "/imagens/" + "imagem" + counter + ".jpg");
                        this.counter++;
                      }
                } while (file2.exists());
                b.writeTo(new FileOutputStream(file2));
                b.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int temp = this.counter - 1;
        Imagem imagem = new Imagem();
        if(slash == '\\')
            imagem.setReferencia(pathReal + "\\imagens\\" + "imagem" + temp + ".jpg");
        else if(slash == '/')
            imagem.setReferencia(pathReal + "/imagens/" + "imagem" + temp + ".jpg");        
        imagem.setNome(pegaNomeImagem(imagem.getReferencia()));
        this.problema.getImagensAssociadas().add(imagem);
    }

    public String pegaNomeImagem(String path) {
        String nomeAntes = "";
        String nomeDepois = "";
        String key = path;
        int i = key.length() - 1;
        while (key.charAt(i) != '\\' && key.charAt(i) != '/') {
            nomeAntes = nomeAntes + key.charAt(i);
            i--;
        }
        for (i = nomeAntes.length() - 1; i >= 0; i--) {
            nomeDepois = nomeDepois + nomeAntes.charAt(i);
        }
        return nomeDepois;
    }

    public char identificaTipoSeparadorPath(String path){
        for(int i = 0; i < path.length(); i++){
            if(path.charAt(i)=='\\')
                return '\\';
            else if(path.charAt(i)=='/')
                return '/';
        }
        return ' ';
    }

    public String transformaPath(String pathCapturada) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < pathCapturada.length() - 10; i++) {
            buffer.append(pathCapturada.charAt(i));
        }
        return buffer.toString();
    }

    public String clearUploadData() {
        files.clear();
        setUploadsAvailable(10);
        return null;
    }

    public long getTimeStamp() {
        return System.currentTimeMillis();
    }

    public ArrayList<ImageData> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<ImageData> files) {
        this.files = files;
    }

    public int getUploadsAvailable() {
        return uploadsAvailable;
    }

    public void setUploadsAvailable(int uploadsAvailable) {
        this.uploadsAvailable = uploadsAvailable;
    }

    public boolean isAutoUpload() {
        return autoUpload;
    }

    public void setAutoUpload(boolean autoUpload) {
        this.autoUpload = autoUpload;
    }

    public boolean isUseFlash() {
        return useFlash;
    }

    public void setUseFlash(boolean useFlash) {
        this.useFlash = useFlash;
    }
}
