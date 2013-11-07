/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import SisDisciplinas.Cronograma;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.CronogramaJpaController;

/**
 *
 * @author gabriel
 */
public class CronogramaConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        int id = Integer.parseInt(string);
        CronogramaJpaController controller = (CronogramaJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "cronogramaJpa");
        return controller.findCronograma(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Cronograma) {
            Cronograma o = (Cronograma) object;
            return String.valueOf(o.getId());
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: SisDisciplinas.Cronograma");
        }
    }

}
