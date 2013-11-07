package SisProfessores;

import Criptografia.BlowFish;
import SisDisciplinas.Disciplina;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author uefscompras
 */

@Entity
public class Professor implements Serializable {    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nome;
    private String login;
    private String senha;
    String cript;

    @Transient
    private String confirmaSenha;

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    @OneToMany(cascade=CascadeType.ALL)
    private List<AreaInteresse> areasInteresse;

    public Professor(){
        this.areasInteresse = new ArrayList<AreaInteresse>();
    }

    public List<AreaInteresse> getAreasInteresse() {
        return areasInteresse;
    }

    public void setAreasInteresse(List<AreaInteresse> areasInteresse) {
        this.areasInteresse = areasInteresse;
    }


    @ManyToOne
    private GrupoProfessor grupo;   

    public GrupoProfessor getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoProfessor grupo) {
        this.grupo = grupo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {

        this.login = login.toLowerCase();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {        
        //System.out.println("no get" + senha);
        return senha;
    }

    public void setSenha(String senha) {
        senha = senha.toLowerCase();
        //System.out.println("no set" + senha);
        cript = BlowFish.cript(senha, login);
        //String decript = BlowFish.decript(cript, "login");
        //System.out.println("no set2 " + cript);
        System.out.println("criptografou!!  " + senha + " "+ cript);
        this.senha = cript;
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
        if (!(object instanceof Professor)) {
            return false;
        }
        Professor other = (Professor) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ""+nome;
    }

}
