/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SisDisciplinas;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Jhielson
 */
@Entity
@Table(name="Bloom")
public class Bloom implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Lob
    private String relembrar;
    @Lob
    private String entender;
    @Lob
    private String aplicar;
    @Lob
    private String analisar;
    @Lob
    private String avaliar;
    @Lob
    private String criar;

 //   @OneToOne(optional=false)
 //   @JoinColumn(nullable=false)
 //   private Problema Problem;

    public String getAnalisar() {
        return analisar;
    }

    public void setAnalisar(String analisar) {
        this.analisar = analisar;
    }

    public String getAplicar() {
        return aplicar;
    }

    public void setAplicar(String aplicar) {
        this.aplicar = aplicar;
    }


    public String getAvaliar() {
        return avaliar;
    }

    public void setAvaliar(String avaliar) {
        this.avaliar = avaliar;
    }

    public String getCriar() {
        return criar;
    }

    public void setCriar(String criar) {
        this.criar = criar;
    }

    public String getEntender() {
        return entender;
    }

    public void setEntender(String entender) {
        this.entender = entender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getRelembrar() {
        return relembrar;
    }

    public void setRelembrar(String relembrar) {
        this.relembrar = relembrar;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bloom other = (Bloom) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

 

    
}
