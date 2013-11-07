package util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.net.MalformedURLException;
import java.net.URL;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author lidiany
 */
public class URLResolver {


    private static String urlBase;

    private static String getUrlBase() {
        if( urlBase == null ) {
            urlBase = getBaseURLAddress();
        }

        return urlBase;
    }

    /**
     * Retorna o caminho relativo para o relatório
     * @param relativeReportURL a String com o caminho relativo
     * @return URL - o caminho relativo para o relatório
     */
    public static URL buildRelativeReportURL( String relativeReportURL ) {
        try {
            return new URL( getUrlBase() + relativeReportURL );
        } catch (MalformedURLException ex) {
           ex.printStackTrace();
        }

        return null;
    }

    public static String getBaseURLAddress() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        StringBuffer path = new StringBuffer();
        path.append("http://");
        path.append(request.getLocalName());
        path.append(":");
        path.append(request.getLocalPort());
        path.append(request.getContextPath());

        return path.toString();
    }




}
