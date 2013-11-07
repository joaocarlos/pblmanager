/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import SisProfessores.Professor;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.ProfessorJpaController;

/**
 *
 * @author gabriel
 */
public class ProfessorConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        int id = Integer.parseInt(string);
        ProfessorJpaController controller = (ProfessorJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "professorJpa");
        return controller.findProfessor(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Professor) {
            Professor o = (Professor) object;
            return String.valueOf(o.getId());
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: SisProfessores.Professor");
        }
    }

}
