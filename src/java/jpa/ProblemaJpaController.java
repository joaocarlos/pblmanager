/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import SisDisciplinas.Atividade;
import SisDisciplinas.Cronograma;
import SisDisciplinas.Imagem;
import SisDisciplinas.Objetivo;
import SisDisciplinas.PalavraChave;
import SisDisciplinas.Problema;
import SisDisciplinas.Recurso;
import java.util.ArrayList;
import java.util.HashMap;
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
import util.Filter;

/**
 *
 * @author gabriel
 */
public class ProblemaJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "RepositorioProblemasPU")
    private EntityManagerFactory emf = null;
    private List<Problema> p = null;


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Problema problema) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(problema);
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



    public void edit(Problema problema) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            problema = em.merge(problema);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = problema.getId();
                if (findProblema(id) == null) {
                    throw new NonexistentEntityException("The problema with id " + id + " no longer exists.");
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
            Problema problema;
            try {
                problema = em.getReference(Problema.class, id);
                problema.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The problema with id " + id + " no longer exists.", enfe);
            }
            em.remove(problema);
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

    public List<Problema> findProblemaEntities() {
        return findProblemaEntities(true, -1, -1);
    }

    /* public List<Problema> findProblemaEntities2(String palavraChave) {
        return findProblemaEntities2(true, -1, -1, palavraChave);
    }

    public List<Problema> findProblemaEntities3(String semestreCriacao) {
        return findProblemaEntities3(true, -1, -1, semestreCriacao);
    }*/

    public List<Problema> findProblemaEntities4(String palavraChave, String semestreCriacao, String tituloProblema, String disciplinaBusca, String autorBusca, String assuntoBusca, String textoBusca) {

        return findProblemaEntities4(true, -1, -1, palavraChave, semestreCriacao, tituloProblema, disciplinaBusca, autorBusca, assuntoBusca, textoBusca);
    }

    public List<Problema> findProblemaEntities(int maxResults, int firstResult) {
        return findProblemaEntities(false, maxResults, firstResult);
    }
/*
     public List<Problema> findProblemaEntities2(int maxResults, int firstResult, String palavraChaveBusca) {
        return findProblemaEntities2(false, maxResults, firstResult, palavraChaveBusca);
    }
    public List<Problema> findProblemaEntities3(int maxResults, int firstResult, String semestreBusca) {
        return findProblemaEntities3(false, maxResults, firstResult, semestreBusca);
    }
*/
    public List<Problema> findProblemaEntities4(int maxResults, int firstResult, String palavraChaveBusca, String semestreBusca, String tituloBusca, String disciplinaBusca, String autorBusca, String assuntoBusca, String textoBusca) {
        return findProblemaEntities4(false, maxResults, firstResult, palavraChaveBusca, semestreBusca, tituloBusca, disciplinaBusca, autorBusca, assuntoBusca, textoBusca);
    }

    private List<Problema> findProblemaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Problema as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /* private List<Problema> findProblemaEntities2 (boolean all, int maxResults, int firstResult, String palavraChave) {
        EntityManager em = getEntityManager();
         ArrayList<Problema> r = new ArrayList<Problema>();
        try {
            Query q = em.createQuery("select object(o) from Problema as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }

           // return q.getResultList();

            p = q.getResultList();
           // r.add(p.get(1));


            for (int i=0;i<p.size();i++) {
                for (int j=0;j<p.get(i).getPalavrasChave().size();j++){
                if (p.get(i).getPalavrasChave().get(j).getPalavraChave().equalsIgnoreCase(palavraChave))
                r.add(p.get(i));    //add(p.get(i));
                }
            }

            return r;

        } finally {
            em.close();
        }
    }

      private List<Problema> findProblemaEntities3 (boolean all, int maxResults, int firstResult, String semestreCriacao) {
        EntityManager em = getEntityManager();
         ArrayList<Problema> r = new ArrayList<Problema>();
        try {
            Query q = em.createQuery("select object(o) from Problema as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }

           // return q.getResultList();

            p = q.getResultList();
           // r.add(p.get(1));


            for (int i=0;i<p.size();i++) {
                //for (int j=0;j<p.get(i).getSemestreCriacao().size();j++){
                if (p.get(i).getSemestreCriacao().equalsIgnoreCase(semestreCriacao))
                r.add(p.get(i));    //add(p.get(i));
               // }
            }

            return r;

        } finally {
            em.close();
        }
    }
*/
  /*  private List<Problema> findProblemaEntities4 (boolean all, int maxResults, int firstResult, String palavraChave, String semestreCriacao, String tituloProblema, String disciplinaBusca, String autorBusca, String assuntoBusca) {
        EntityManager em = getEntityManager();
         ArrayList<Problema> r = new ArrayList<Problema>();
         ArrayList<Problema> rr = new ArrayList<Problema>();
         int tamPalavra = 0;
         int tamSemestre = 0;
         int tamTitulo = 0;
        try {
            Query q = em.createQuery("select object(o) from Problema as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }

           // return q.getResultList();

            p = q.getResultList();
           // r.add(p.get(1));

           //todas as buscas
           System.out.println("Veio pra ca: " + palavraChave + " + " + semestreCriacao + " + " + tituloProblema);
           if(semestreCriacao == ""){
               System.out.println("Sem eh null");
           }

               if(palavraChave != "" && semestreCriacao != "" && tituloProblema != ""){
                   for (int i=0;i<p.size();i++) {

                    for (int j=0;j<p.get(i).getPalavrasChave().size();j++){

                        if (p.get(i).getPalavrasChave().get(j).getPalavraChave().equalsIgnoreCase(palavraChave) &&
                            p.get(i).getSemestreCriacao().equalsIgnoreCase(semestreCriacao) &&
                            p.get(i).getTituloProblema().equalsIgnoreCase(tituloProblema)){
                            r.add(p.get(i));
                        }//add(p.get(i));
                    }
                    tamPalavra = r.size();
                   }
                   System.out.println("-> Opção todos");
               }

               else if(palavraChave == "" && semestreCriacao != "" && tituloProblema != ""){
                    for (int i=0;i<p.size();i++) {
                        if (p.get(i).getSemestreCriacao().equalsIgnoreCase(semestreCriacao) &&
                            p.get(i).getTituloProblema().equalsIgnoreCase(tituloProblema)){
                            r.add(p.get(i));
                        }//add(p.get(i));

                    tamPalavra = r.size();
                    }
                    System.out.println("-> Opção sem e ti");
               }

               else if(palavraChave != "" && semestreCriacao != "" && tituloProblema == ""){
                    for (int i=0;i<p.size();i++) {
                        for (int j=0;j<p.get(i).getPalavrasChave().size();j++){

                        if (p.get(i).getPalavrasChave().get(j).getPalavraChave().equalsIgnoreCase(palavraChave) &&
                            p.get(i).getSemestreCriacao().equalsIgnoreCase(semestreCriacao)){
                            r.add(p.get(i));
                        }//add(p.get(i));
                    }

                    tamPalavra = r.size();
                    }
                    System.out.println("-> Opção pa e sem");
               }

               else if(palavraChave != "" && semestreCriacao == "" && tituloProblema != ""){
                    for (int i=0;i<p.size();i++) {
                        for (int j=0;j<p.get(i).getPalavrasChave().size();j++){

                        if (p.get(i).getPalavrasChave().get(j).getPalavraChave().equalsIgnoreCase(palavraChave) &&
                            p.get(i).getTituloProblema().equalsIgnoreCase(tituloProblema)){
                            r.add(p.get(i));
                        }//add(p.get(i));
                    }

                    tamPalavra = r.size();
                    }
                    System.out.println("-> Opção pa e ti");
               }

               else if(palavraChave != "" && semestreCriacao == "" && tituloProblema == ""){
                  for (int i=0;i<p.size();i++) {
                    for (int j=0;j<p.get(i).getPalavrasChave().size();j++){

                        if (p.get(i).getPalavrasChave().get(j).getPalavraChave().equalsIgnoreCase(palavraChave)){
                            r.add(p.get(i));
                        }//add(p.get(i));
                    }
                    tamPalavra = r.size();
                  }
                  System.out.println("-> Opção pa");
               }
               else if(palavraChave == "" && semestreCriacao != "" && tituloProblema == ""){
                    for (int i=0;i<p.size();i++) {
                        if (p.get(i).getSemestreCriacao().equalsIgnoreCase(semestreCriacao)){
                            r.add(p.get(i));
                        }//add(p.get(i));

                    tamPalavra = r.size();
                    }
                    System.out.println("-> Opção sem");
               }

               else if(palavraChave == "" && semestreCriacao == "" && tituloProblema != ""){
                    for (int i=0;i<p.size();i++) {
                        if (p.get(i).getTituloProblema().equalsIgnoreCase(tituloProblema)){
                            r.add(p.get(i));
                        }//add(p.get(i));

                    tamPalavra = r.size();
                    }
                    System.out.println("-> Opção ti");
               }




            //busca de titulo
            for (int i=0;i<p.size();i++) {
                //for (int j=0;j<p.get(i).getSemestreCriacao().size();j++){
                if (p.get(i).getTituloProblema().equalsIgnoreCase(tituloProblema))
                r.add(p.get(i));    //add(p.get(i));
               // }
            }

            return r;

        } finally {
            em.close();
        }
    } */

    private List<Problema> findProblemaEntities4 (boolean all, int maxResults, int firstResult, String palavraChave, String semestreCriacao, String tituloProblema, String disciplinaBusca, String autorBusca, String assuntoBusca, String textoBusca) {
       EntityManager em = getEntityManager();
         ArrayList<Problema> r = new ArrayList<Problema>();
         int flag = 0;
         int flag2 = 0;
         int flag3 = 0;
         String [] array = null;

         String ini2 = new String("ini ");
         String fim2 = new String(" fim2");
         String ini = new String("ini2 ");
         String fim = new String(" fim");
        // textoBusca = ini.concat(textoBusca.concat(fim));
         String [] array2 = textoBusca.split("[\\| |?|!|.|;|,|:|)|(]");
         List<PalavraChave> palavrasChave = new ArrayList<PalavraChave>();

        try {
            Query q = em.createQuery("select object(o) from Problema as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }

           // return q.getResultList();

            p = q.getResultList();



           //todas as buscas
             for (int i=0;i<p.size();i++) {


                 if (p.get(i).getSemestreCriacao().equalsIgnoreCase(semestreCriacao) ||
                       semestreCriacao.equalsIgnoreCase("") ){

                     if (p.get(i).getTituloProblema().equalsIgnoreCase(tituloProblema) ||
                          tituloProblema.equalsIgnoreCase("") ){
                          System.out.println("o valor de disciplinaBusca é " + disciplinaBusca);

                         if ((p.get(i).getDisciplina() != null &&  p.get(i).getDisciplina().getNome().equalsIgnoreCase(disciplinaBusca)) ||
                          disciplinaBusca.equalsIgnoreCase("") ){

                             if ((p.get(i).getProfessorAutor() != null &&  p.get(i).getProfessorAutor().getNome().equalsIgnoreCase(autorBusca)) ||
                             autorBusca.equalsIgnoreCase("") ){

                                 if (p.get(i).getAssunto().equalsIgnoreCase(assuntoBusca) ||
                                 assuntoBusca.equalsIgnoreCase("") ){

                                  // String aux2 = p.get(i).getDescricao();
                                  // p.get(i).setDescricao(ini2.concat(aux2.concat(fim2)));
                                    array = p.get(i).getDescricao().split("[\\| |?|!|.|;|,|:|)|(]");
                                    if(array.length>3){
                                    System.out.println("o valor de array 3 é:"+array[3]);
                                    System.out.println("o tamanho do array é:"+array.length); }

                                    for (int k=0; k<array.length; k++){
                                        if(flag2==0){
                                            if(k==0){ array[k] = array[k].replaceAll("<p>",""); }
                                            if(k==array.length-1){ array[k] = array[k].replaceAll("</p>",""); }
                                        
                                           for (int w=0; w<array2.length; w++){
                                              if(flag3==0){
                                                

                                        if(array[k].equalsIgnoreCase(array2[w]) || textoBusca.equalsIgnoreCase("")){
                                         


                                     
                                     if(p.get(i).getPalavrasChave().size()<1){
                                         PalavraChave pc = new PalavraChave();
                                         //p.get(i).setPalavrasChave(palavrasChave);
                                         p.get(i).getPalavrasChave().add(pc);
                                         p.get(i).getPalavrasChave().get(0).setPalavraChave("");
                                     }

                                     for (int j=0;j<p.get(i).getPalavrasChave().size();j++){
                                        if (flag == 0) {
                                        if (( p.get(i).getPalavrasChave().get(j).getPalavraChave().equalsIgnoreCase(palavraChave)) ||
                                         palavraChave.equalsIgnoreCase("") ){ 

                                         if (semestreCriacao.equalsIgnoreCase("") &&
                                             tituloProblema.equalsIgnoreCase("") &&
                                             disciplinaBusca.equalsIgnoreCase("") &&
                                             autorBusca.equalsIgnoreCase("") &&
                                             assuntoBusca.equalsIgnoreCase("") &&
                                             palavraChave.equalsIgnoreCase("") &&
                                             textoBusca.equalsIgnoreCase("")) {}

                                         else {
                                                r.add(p.get(i));
                                                flag = 1;
                                                flag3 = 1;
                                                flag2 = 1;
                                             }

                                           }
                                        }
                                     }
                                     flag = 0;
                                     
                                   }
                                  }
                                 }
                                 flag3 = 0;
                                }
                              }
                               flag2 = 0;
                             }
                         }
                     }
                   }
                  }

             }

      return r;

        } finally {
            em.close();
        }
    }

    public Problema findProblema(int id) {
        EntityManager em = getEntityManager();
        try {
            Problema p2 = em.find(Problema.class, id);
             for (Imagem i : p2.getImagensAssociadas()) { }
             for(PalavraChave pc : p2.getPalavrasChave() ){}
             for(Objetivo o: p2.getObjetivos()){}
             //for(Produto prod : p2.getProdutos()){}
             for(Recurso r : p2.getRecursos()){}
             Cronograma c = p2.getCronograma();
             for(Atividade a : c.getAtividades()){}
             return p2;
        } finally {
            em.close();
        }
    }

    public Problema findFullProblema(int id) {
        EntityManager em = getEntityManager();
        try {
             Problema p2 = em.find(Problema.class, id);
             for (Imagem i : p2.getImagensAssociadas()) { }
             for(PalavraChave pc : p2.getPalavrasChave() ){}
             for(Objetivo o: p2.getObjetivos()){}
            // for(Produto prod : p2.getProdutos()){}
             for(Recurso r : p2.getRecursos()){}
             Cronograma c = p2.getCronograma();
             for(Atividade a : c.getAtividades()){}
             return p2;
        } finally {
            em.close();
        }
    }

    public int getProblemaCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Problema as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    /**
     * Busca uma lista de problemas a partir dos filtros recebidos
     * @param dpFilters os filtros para a busca
     * @param full <b> true </b> caso seja necessário carregar todos os dados da entidade <b> Problema</b>
     * <b> false </b>, caso não seja necessário
     * @return os resultados encontrados
     *
     */
    public List<Problema> findFilteredProblems(ArrayList<Filter> dpFilters, boolean full){
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery(buildQuery(dpFilters));
            applyFilterParameters(q, dpFilters);

            if(full){
                //TODO carregar todos os dados aq
            }

            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Aplica os filtros recebidos aos parâmetros da consulta
     * @param q a query
     * @param dpFilters os filtros para a consulta
     */
    private void applyFilterParameters( Query q  , ArrayList<Filter> dpFilters ) {
        for (Filter dPFilter : dpFilters) {

            HashMap<String , Object> params = dPFilter.getParameters();
            for (String param : params.keySet()) {
                q.setParameter(param, params.get(param) );
            }
        }
    }

    /**
     * Constrói a consulta inserindo os filtros recebidos
     * @param dpFilters os filtros para a consulta
     * @return a consulta no formato String
     */
    private String buildQuery(ArrayList<Filter> dpFilters) {

        StringBuffer hQl = new StringBuffer();
              hQl.append("select object(o) from Problema as o");
        if (!dpFilters.isEmpty()) {
            hQl.append(" where ");
            int i = 1;
            for (Filter dPFilter : dpFilters) {
                hQl.append( dPFilter.getSQLWhereClause());
                if (i < dpFilters.size()) {
                     hQl.append( " and " );
                }
                i++;
            }
        }
         hQl.append( " order by o.dataCriacao desc" );

        return hQl.toString();
    }

}