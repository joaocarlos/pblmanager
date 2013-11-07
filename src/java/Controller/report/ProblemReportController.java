/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.report;

import SisDisciplinas.Problema;
import controller.util.JsfUtil;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jpa.ProblemaJpaController;
import util.URLResolver;

/**
 *
 * @author lidiany
 */

public class ProblemReportController extends ReportController{

    private Problema problema;

    private List<Problema> problemaItens;

    private ProblemaJpaController jpaController;

    /** Creates a new instance of ProblemReportController */
    public ProblemReportController() {
        jpaController = (ProblemaJpaController) JsfUtil.getController( "problemaJpa" );
          problemaItens = new ArrayList<Problema>();
    }

    public ProblemaJpaController getJpaController() {
        return jpaController;
    }

    public void setJpaController(ProblemaJpaController jpaController) {
        this.jpaController = jpaController;
    }

    public Problema getProblema() {
        return problema;
    }

    public void setProblema(Problema problema) {
        this.problema = problema;
    }

    public List<Problema> getProblemaItens() {
        return problemaItens;
    }

    public void setProblemaItens(List<Problema> problemaItens) {
        this.problemaItens = problemaItens;
    }

    public String generateReportSetup() {
        reset();
        return "problema_list";
    }

    public String generateReport(){
        Integer id = Integer.parseInt(JsfUtil.getRequestParameter("problemaId"));
        this. problema = jpaController.findFullProblema(id);

        this.problemaItens.add(problema);
        super.printToPdf();
        reset();
        return "problema_list";
    }
    public String generateReport2(){
        Integer id = Integer.parseInt(JsfUtil.getRequestParameter("problemaId"));
        this. problema = jpaController.findFullProblema(id);

        this.problemaItens.add(problema);
        super.printToPdf2();
        reset();
        return "problema_list";
    }

    @Override
    public URL getReportURL() {
        return URLResolver.buildRelativeReportURL("/problema/reports/problemTutorialReport.jasper");
    }
     @Override
    public URL getReportURL2() {
        return URLResolver.buildRelativeReportURL("/problema/reports/reportAluno.jasper");
    }

    @Override
    public HashMap<String, Object> getReportParameters() {
        return new HashMap<String, Object>();
    }

    @Override
    public List getReportData() {

        return this.problemaItens;

        /*
         * caso o relatório tivesse filtros
        return this.jpaController.findFilteredProblems(buildFilters() , true );
         * */
    }



    private void reset() {
            problema = null;
            problemaItens = new ArrayList<Problema>();
    }

    /* caso esse relatório tivesse filtros
    private ArrayList<Filter> buildFilters() {

        ArrayList<Filter> filters = new ArrayList<Filter>();
        return filters;
    }
     * */

}
