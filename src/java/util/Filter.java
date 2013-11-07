/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;


import java.util.HashMap;

/**
 *
 * @author lidiany
 */
public interface Filter {

    /*
     * @return a condição <b>WHERE</b> para consulta onde o filtro será usado
     */
    public String getSQLWhereClause();

    /*
     * Um MAP contendo o nome do parâmetro e o valor
     */
    public HashMap<String,Object> getParameters();

}
