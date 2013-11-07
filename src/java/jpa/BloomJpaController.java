/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import SisDisciplinas.Bloom;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.transaction.UserTransaction;
import jpa.exceptions.NonexistentEntityException;
import jpa.exceptions.PreexistingEntityException;
import jpa.exceptions.RollbackFailureException;

/**
 *
 * @author Jhielson
 */
public class BloomJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "RepositorioProblemasPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bloom bloom) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(bloom);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findBloom(bloom.getId()) != null) {
                throw new PreexistingEntityException("Bloom " + bloom + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bloom bloom) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            bloom = em.merge(bloom);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bloom.getId();
                if (findBloom(id) == null) {
                    throw new NonexistentEntityException("The bloom with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Bloom bloom;
            try {
                bloom = em.getReference(Bloom.class, id);
                bloom.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bloom with id " + id + " no longer exists.", enfe);
            }
            em.remove(bloom);
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

    public List<Bloom> findBloomEntities() {
        return findBloomEntities(true, -1, -1);
    }

    public List<Bloom> findBloomEntities(int maxResults, int firstResult) {
        return findBloomEntities(false, maxResults, firstResult);
    }

    private List<Bloom> findBloomEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Bloom as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Bloom findBloom(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bloom.class, id);
        } finally {
            em.close();
        }
    }

    public int getBloomCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Bloom as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
