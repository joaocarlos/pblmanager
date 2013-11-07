/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import SisDisciplinas.Recurso;
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
public class RecursoJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "RepositorioProblemasPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Recurso recurso) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(recurso);
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

    public void edit(Recurso recurso) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            recurso = em.merge(recurso);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = recurso.getId();
                if (findRecurso(id) == null) {
                    throw new NonexistentEntityException("The recurso with id " + id + " no longer exists.");
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
            Recurso recurso;
            try {
                recurso = em.getReference(Recurso.class, id);
                recurso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recurso with id " + id + " no longer exists.", enfe);
            }
            em.remove(recurso);
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

    public List<Recurso> findRecursoEntities() {
        return findRecursoEntities(true, -1, -1);
    }

    public List<Recurso> findRecursoEntities(int maxResults, int firstResult) {
        return findRecursoEntities(false, maxResults, firstResult);
    }

    private List<Recurso> findRecursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Recurso as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Recurso findRecurso(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Recurso.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecursoCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Recurso as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
