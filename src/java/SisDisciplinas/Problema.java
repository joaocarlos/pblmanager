package SisDisciplinas;

import SisProfessores.Professor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author uefscompras
 */

@Entity
public class Problema implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String tituloProblema;
    @OneToMany(cascade=CascadeType.ALL)
    private List<Objetivo> objetivos;
    @Lob
    private String descricao;
    @Lob
    private String produtos;
    @OneToMany(cascade=CascadeType.ALL)
    private List<Recurso> recursos;
    @Lob
    private String orientacoesTutor;
    @Lob
    private String avaliacaoAlunos;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataCriacao;
    private String semestreCriacao;
    @OneToMany (cascade=CascadeType.ALL)
    private List<Imagem> imagensAssociadas;
    private String assunto;
    @ManyToOne
    private Professor professorAutor;
    @OneToMany(cascade=CascadeType.ALL)
    private List<PalavraChave> palavrasChave;
    @OneToOne(cascade=CascadeType.ALL)
    private Cronograma cronograma;
    @ManyToOne
    private Disciplina disciplina;
    private Problema ProblemaDeReferencia;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(nullable=false)
    private Bloom bloom;

    
    public Problema() {
        this.palavrasChave = new ArrayList<PalavraChave>();
        this.objetivos = new ArrayList<Objetivo>();       
        this.recursos = new ArrayList<Recurso>();
        this.imagensAssociadas = new ArrayList<Imagem>();
        this.cronograma = new Cronograma();
        this.ProblemaDeReferencia = null;
        this.bloom = new Bloom();
    }

    public Problema getProblemaDeReferencia() {
        return ProblemaDeReferencia;
    }

    public Bloom getBloom() {
        return bloom;
    }
    public void setBloom(Bloom bloom) {
        this.bloom = bloom;
    }

    public void setProblemaDeReferencia(Problema ProblemaDeReferencia) {
        this.ProblemaDeReferencia = ProblemaDeReferencia;
    }

    public Cronograma getCronograma() {
        return cronograma;
    }

    public void setCronograma(Cronograma cronograma) {
        this.cronograma = cronograma;
    }

    public List<PalavraChave> getPalavrasChave() {
        return palavrasChave;
    }

    public void setPalavrasChave(List<PalavraChave> palavrasChave) {
        this.palavrasChave = palavrasChave;
    }

    public Professor getProfessorAutor() {
        return professorAutor;
    }

    public void setProfessorAutor(Professor professorAutor) {
        this.professorAutor = professorAutor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getAvaliacaoAlunos() {
        return avaliacaoAlunos;
    }

    public void setAvaliacaoAlunos(String avaliacaoAlunos) {
        this.avaliacaoAlunos = avaliacaoAlunos;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Imagem> getImagensAssociadas() {
        return imagensAssociadas;
    }

    public void setImagensAssociadas(List<Imagem> imagensAssociadas) {
        this.imagensAssociadas = imagensAssociadas;
    }

    public List<Objetivo> getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(List<Objetivo> objetivos) {
        this.objetivos = objetivos;
    }

    public String getOrientacoesTutor() {
        return orientacoesTutor;
    }

    public void setOrientacoesTutor(String orientacoesTutor) {
        this.orientacoesTutor = orientacoesTutor;
    }

    public String getProdutos() {
        return produtos;
    }

    public void setProdutos(String produtos) {
        this.produtos = produtos;
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }

    public String getSemestreCriacao() {
        return semestreCriacao;
    }

    public void setSemestreCriacao(String semestreCriacao) {
        this.semestreCriacao = semestreCriacao;
    }

    public String getTituloProblema() {
        return tituloProblema;
    }

    public void setTituloProblema(String tituloProblema) {
        this.tituloProblema = tituloProblema;
    }    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Problema)) {
            return false;
        }
        Problema other = (Problema) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SisDisciplinas.Problema[id=" + id + "]";
    }

}
