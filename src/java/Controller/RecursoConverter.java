/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import SisDisciplinas.Recurso;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.RecursoJpaController;

/**
 *
 * @author gabriel
 */
public class RecursoConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        int id = Integer.parseInt(string);
        RecursoJpaController controller = (RecursoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "recursoJpa");
        return controller.findRecurso(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Recurso) {
            Recurso o = (Recurso) object;
            return String.valueOf(o.getId());
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: SisDisciplinas.Recurso");
        }
    }

}
