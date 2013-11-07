/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import SisProfessores.AreaInteresse;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.AreaInteresseJpaController;

/**
 *
 * @author gabriel
 */
public class AreaInteresseConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        int id = Integer.parseInt(string);
        AreaInteresseJpaController controller = (AreaInteresseJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "areaInteresseJpa");
        return controller.findAreaInteresse(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof AreaInteresse) {
            AreaInteresse o = (AreaInteresse) object;
            return String.valueOf(o.getId());
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: SisProfessores.AreaInteresse");
        }
    }

}
