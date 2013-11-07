/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import SisDisciplinas.PalavraChave;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.PalavraChaveJpaController;

/**
 *
 * @author gabriel
 */
public class PalavraChaveConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        int id = Integer.parseInt(string);
        PalavraChaveJpaController controller = (PalavraChaveJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "palavraChaveJpa");
        return controller.findPalavraChave(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof PalavraChave) {
            PalavraChave o = (PalavraChave) object;
            return String.valueOf(o.getId());
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: SisDisciplinas.PalavraChave");
        }
    }

}
