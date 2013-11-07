/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import SisProfessores.GrupoProfessor;
import java.util.List;
import javax.annotation.Resource;
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
public class GrupoProfessorJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "RepositorioProblemasPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GrupoProfessor grupoProfessor) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(grupoProfessor);
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

    public void edit(GrupoProfessor grupoProfessor) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            grupoProfessor = em.merge(grupoProfessor);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = grupoProfessor.getId();
                if (findGrupoProfessor(id) == null) {
                    throw new NonexistentEntityException("The grupoProfessor with id " + id + " no longer exists.");
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
            GrupoProfessor grupoProfessor;
            try {
                grupoProfessor = em.getReference(GrupoProfessor.class, id);
                grupoProfessor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupoProfessor with id " + id + " no longer exists.", enfe);
            }
            em.remove(grupoProfessor);
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

    public List<GrupoProfessor> findGrupoProfessorEntities() {
        return findGrupoProfessorEntities(true, -1, -1);
    }

    public List<GrupoProfessor> findGrupoProfessorEntities(int maxResults, int firstResult) {
        return findGrupoProfessorEntities(false, maxResults, firstResult);
    }

    private List<GrupoProfessor> findGrupoProfessorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from GrupoProfessor as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public GrupoProfessor findGrupoProfessor(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GrupoProfessor.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoProfessorCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from GrupoProfessor as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
