/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.report;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author lidiany
 */
public abstract class ReportController {

    public abstract URL getReportURL();
     public abstract URL getReportURL2();

    public abstract HashMap<String,Object> getReportParameters();

    public abstract List getReportData();

    /**
     * Retorna o nome do arquivo de relatório
     * @return - o nome do relatório
     */
    public String getFileName() {
        return "report.pdf";
    }

    /**
     * Constrói o relatório, usando os parâmetros e o dataSource
     */
    private JasperPrint buildJasperPrint() throws JRException {
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(getReportURL());
        return JasperFillManager.fillReport(jasperReport, getReportParameters(), new JRBeanCollectionDataSource(getReportData()));
    }
     private JasperPrint buildJasperPrint2() throws JRException {
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(getReportURL2());
        return JasperFillManager.fillReport(jasperReport, getReportParameters(), new JRBeanCollectionDataSource(getReportData()));
    }

    /**
     * Gera e envia para a saída, o arquivo de relatório
     */
   public void printToPdf() {
        try {
            JasperPrint jasperPrint = buildJasperPrint();

            FacesContext facesContext = FacesContext.getCurrentInstance();

            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline; filename=\"" + getFileName() + "\"");

            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
   public void printToPdf2() {
        try {
            JasperPrint jasperPrint = buildJasperPrint2();

            FacesContext facesContext = FacesContext.getCurrentInstance();

            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline; filename=\"" + getFileName() + "\"");

            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
