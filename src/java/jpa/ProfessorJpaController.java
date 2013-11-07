/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import Criptografia.BlowFish;
import SisProfessores.AreaInteresse;
import SisProfessores.Professor;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.transaction.UserTransaction;
import jpa.exceptions.NonexistentEntityException;
import jpa.exceptions.RollbackFailureException;

/**
 *
 * @author gabriel
 */
public class ProfessorJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "RepositorioProblemasPU")
    private EntityManagerFactory emf = null;
     private List<Professor> p = null;

    public ProfessorJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Professor professor) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(professor);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Professor professor) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            professor = em.merge(professor);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = professor.getId();
                if (findProfessor(id) == null) {
                    throw new NonexistentEntityException("The professor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Professor professor;
            try {
                professor = em.getReference(Professor.class, id);
                professor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The professor with id " + id + " no longer exists.", enfe);
            }
            em.remove(professor);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Professor> findProfessorEntities() {
        return findProfessorEntities(true, -1, -1);
    }

    public List<Professor> findProfessorEntities(int maxResults, int firstResult) {
        return findProfessorEntities(false, maxResults, firstResult);
    }

    public  List<Professor> findProfessorEntities2(String loginBusca, String senhaBusca) {

        return findProfessorEntities2(true, -1, -1, loginBusca, senhaBusca);
    }

     public List<Professor> findProfessorEntities2(int maxResults, int firstResult, String loginBusca, String senhaBusca) {
        return findProfessorEntities2(false, maxResults, firstResult, loginBusca, senhaBusca);
    }

    private List<Professor> findProfessorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Professor as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    private List<Professor> findProfessorEntities2(boolean all, int maxResults, int firstResult, String loginBusca, String senhaBusca) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Professor as o");
            ArrayList<Professor> r = new ArrayList<Professor>();
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }

             p = q.getResultList();

             loginBusca = loginBusca.toLowerCase();
             senhaBusca = senhaBusca.toLowerCase();
             String decript = null;
             for (int i=0;i<p.size();i++) {
                //for (int j=0;j<p.get(i).getSemestreCriacao().size();j++){
                if (p.get(i).getLogin().equalsIgnoreCase(loginBusca)){
                     //System.out.println("encontrou o login " + loginBusca);
                     //System.out.println("veio certo: "+ p.get(i).getSenha());
                     decript = BlowFish.decript(p.get(i).getSenha(), loginBusca);
                     //System.out.println("veio certo: "+ p.get(i).getSenha() + "descript: " + decript + "s correta" + senhaBusca);
                     if(decript.equalsIgnoreCase(senhaBusca)){
                        // System.out.println("senha correta " + senhaBusca);
                         r.add(p.get(i));    //add(p.get(i));
                         FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", p.get(i) );
                     }
                     else{
                         System.out.println("senha errada ");
                     }
                    //r.add(p.get(i));    //add(p.get(i));
                }
            }
             for (int i = 0; i < p.size(); i++) {
                Professor prof = p.get(i);
                for (AreaInteresse a : prof.getAreasInteresse()) { }
            }
             //eu num kero ki retorne nada....soh to testando...
            return r;
        } finally {
            em.close();
        }
    }

    public boolean HaveProfessorIgual(int maxResults, int firstResult, String loginBusca) {
        EntityManager em = getEntityManager();
        boolean all = false;
        try {
            Query q = em.createQuery("select object(o) from Professor as o");
            ArrayList<Professor> r = new ArrayList<Professor>();
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
             p = q.getResultList();

             for (int i=0;i<p.size();i++) {
                //for (int j=0;j<p.get(i).getSemestreCriacao().size();j++){
                 System.out.println("dentro do have " + loginBusca + "vez " + i);
                if (p.get(i).getLogin().equalsIgnoreCase(loginBusca)){
                    System.out.println("axou " + loginBusca);
                    return true;
                }

            }

            return false;
        } finally {
            em.close();
        }
    }

    public Professor findProfessor(int id) {
        EntityManager em = getEntityManager();
        try {
             Professor p = em.find(Professor.class, id);
             for (AreaInteresse a : p.getAreasInteresse()) { }
             return p;
        } finally {
            em.close();
        }
    }

    public Professor findFullProfessor (int id){
        EntityManager em = getEntityManager();
        try {
             Professor p = em.find(Professor.class, id);
             for (AreaInteresse a : p.getAreasInteresse()) { }
             return p;
        } finally {
            em.close();
        }
    }

    public int getProfessorCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Professor as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
