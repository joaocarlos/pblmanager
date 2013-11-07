/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import SisDisciplinas.PalavraChave;
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
public class PalavraChaveJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "RepositorioProblemasPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PalavraChave palavraChave) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(palavraChave);
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

    public void edit(PalavraChave palavraChave) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            palavraChave = em.merge(palavraChave);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = palavraChave.getId();
                if (findPalavraChave(id) == null) {
                    throw new NonexistentEntityException("The palavraChave with id " + id + " no longer exists.");
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
            PalavraChave palavraChave;
            try {
                palavraChave = em.getReference(PalavraChave.class, id);
                palavraChave.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The palavraChave with id " + id + " no longer exists.", enfe);
            }
            em.remove(palavraChave);
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

    public List<PalavraChave> findPalavraChaveEntities() {
        return findPalavraChaveEntities(true, -1, -1);
    }

    public List<PalavraChave> findPalavraChaveEntities(int maxResults, int firstResult) {
        return findPalavraChaveEntities(false, maxResults, firstResult);
    }

    private List<PalavraChave> findPalavraChaveEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from PalavraChave as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PalavraChave findPalavraChave(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PalavraChave.class, id);
        } finally {
            em.close();
        }
    }

    public int getPalavraChaveCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from PalavraChave as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
