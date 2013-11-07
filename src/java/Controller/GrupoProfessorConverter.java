/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import SisProfessores.GrupoProfessor;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.GrupoProfessorJpaController;

/**
 *
 * @author gabriel
 */
public class GrupoProfessorConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        int id = Integer.parseInt(string);
        GrupoProfessorJpaController controller = (GrupoProfessorJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "grupoProfessorJpa");
        return controller.findGrupoProfessor(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof GrupoProfessor) {
            GrupoProfessor o = (GrupoProfessor) object;
            return String.valueOf(o.getId());
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: SisProfessores.GrupoProfessor");
        }
    }

}
