/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SisDisciplinas.filter;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import util.Filter;

/**
 *
 * @author lidiany
 */
public class ProblemDateFilter implements Filter{

 private Date start;

    private Date end;

    private String coVariable = "o";

    public ProblemDateFilter( Date start , Date end ) {
        this.start = start;
        this.end = end;
    }

    public void setDpVariable(String coVariable) {
        this.coVariable = coVariable;
    }

    public String getSQLWhereClause() {

        String sqlClause = "";

        if( start != null && end != null ) {
            sqlClause = coVariable + ".scheduledTime between :startDate and :endDate ";
        }

        return sqlClause;
    }

    public HashMap<String, Object> getParameters() {
        prepareDates();
        HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("startDate", start);
            params.put("endDate", end);

        return params;
    }

    private void prepareDates() {
        Calendar c = Calendar.getInstance();
        c.setTime(start);
        c.set(Calendar.HOUR_OF_DAY, 0 );
        c.set(Calendar.MINUTE, 0 );
        c.set(Calendar.SECOND, 0 );

        start = c.getTime();

        c = Calendar.getInstance();
        c.setTime(end);
        c.set(Calendar.HOUR_OF_DAY, 23 );
        c.set(Calendar.MINUTE, 59 );
        c.set(Calendar.SECOND, 59 );

        end = c.getTime();
    }

}