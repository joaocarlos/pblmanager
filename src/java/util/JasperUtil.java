/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author lidiany
 */

public class JasperUtil {


    /**
     * Compila o arquivo de relatório
     * @param jrxmlFilePath - nome do arquivo de relatório jrxml com caminho completo
     *
     */
    public static void compileReport( String jrxmlFilePath ) throws JRException {
        System.out.println("Compiling:" + jrxmlFilePath );
        JasperCompileManager.compileReportToFile(jrxmlFilePath);
    }

      /**
     * Permite a visualização de um relatório utilizando a JasperViewer
     * @param jrxmlFilePath - caminho do arquivo de relatório jrxml
     * @param map - parâmetros passados para o relatório
     * @param data - o datasource utilizado no relatório
     */
    public static void viewReport( String jasperFilePath ,  Map map , JRDataSource data ) throws JRException {
		JasperPrint jp = JasperFillManager.fillReport( jasperFilePath , map, data );
		JasperViewer.viewReport( jp , false);

    }



    public static void main(String[] args) throws JRException {
        String filePath = "./web/problema/reports/problemTutorialReport.jrxml";
        JasperUtil.compileReport(filePath);
    }


}
