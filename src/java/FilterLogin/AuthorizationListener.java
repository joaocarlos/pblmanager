/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FilterLogin;


import java.io.IOException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

/**
 *
 * @author larissa
 */
public class AuthorizationListener implements PhaseListener {
    
  



    public void afterPhase(PhaseEvent event) {
        //System.out.println("no after "+ event.getPhaseId());

        FacesContext facesContext = event.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();
        //System.out.println("Imprime pagina atual: " + currentPage);
        
        boolean isLoginPage = (currentPage.lastIndexOf("login.jsp") > -1);
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        Object currentUser = session.getAttribute("currentUser");

        //System.out.println("pagina atual "+currentPage + " l " + isLoginPage + " u " + currentUser);

        if (!isLoginPage && currentUser == null) {
            System.out.println("no if do after ");
            NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
            nh.handleNavigation(facesContext, null, "professor_voltaLogin");
        }
      /*  else {
            
            if (!isLoginPage && currentUser != null) {
            System.out.println("no if do after 2 ");

            if(!verificarAutorizacao(currentPage)){

//seo usuario não possuir acesso dereciona para uma página de erro
                     NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                     nh.handleNavigation(facesContext, null, "professor_voltaErro");

                        }
            
            
            
        }

        } */
    }

    public void beforePhase(PhaseEvent event) {
        //System.out.println("dentro do before ");
        
    }

    public PhaseId getPhaseId() {
       // System.out.println("dentro do getPhase");
        return PhaseId.RESTORE_VIEW;
    }


}

