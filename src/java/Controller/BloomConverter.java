/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import SisDisciplinas.Bloom;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.BloomJpaController;

/**
 *
 * @author Jhielson
 */
public class BloomConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Integer id = new Integer(string);
        BloomJpaController controller = (BloomJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "bloomJpa");
        return controller.findBloom(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Bloom) {
            Bloom o = (Bloom) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: SisDisciplinas.Bloom");
        }
    }

}
