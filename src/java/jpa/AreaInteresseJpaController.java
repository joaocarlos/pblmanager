/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import SisProfessores.AreaInteresse;
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
public class AreaInteresseJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "RepositorioProblemasPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AreaInteresse areaInteresse) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(areaInteresse);
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

    public void edit(AreaInteresse areaInteresse) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            areaInteresse = em.merge(areaInteresse);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = areaInteresse.getId();
                if (findAreaInteresse(id) == null) {
                    throw new NonexistentEntityException("The areaInteresse with id " + id + " no longer exists.");
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
            AreaInteresse areaInteresse;
            try {
                areaInteresse = em.getReference(AreaInteresse.class, id);
                areaInteresse.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The areaInteresse with id " + id + " no longer exists.", enfe);
            }
            em.remove(areaInteresse);
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

    public List<AreaInteresse> findAreaInteresseEntities() {
        return findAreaInteresseEntities(true, -1, -1);
    }

    public List<AreaInteresse> findAreaInteresseEntities(int maxResults, int firstResult) {
        return findAreaInteresseEntities(false, maxResults, firstResult);
    }

    private List<AreaInteresse> findAreaInteresseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from AreaInteresse as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public AreaInteresse findAreaInteresse(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AreaInteresse.class, id);
        } finally {
            em.close();
        }
    }

    public int getAreaInteresseCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from AreaInteresse as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
