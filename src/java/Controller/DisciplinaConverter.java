/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import SisDisciplinas.Disciplina;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import jpa.DisciplinaJpaController;

/**
 *
 * @author gabriel
 */
public class DisciplinaConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        int id = Integer.parseInt(string);
        DisciplinaJpaController controller = (DisciplinaJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "disciplinaJpa");
        return controller.findDisciplina(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Disciplina) {
            Disciplina o = (Disciplina) object;
            return String.valueOf(o.getId());
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: SisDisciplinas.Disciplina");
        }
    }

}
