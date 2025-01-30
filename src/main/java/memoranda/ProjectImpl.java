/**
 * ProjectImpl.java
 * Created on 11.02.2003, 23:06:22 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package memoranda;

import memoranda.date.CalendarDate;
import memoranda.date.CurrentDate;
import nu.xom.Attribute;
import nu.xom.Element;

/**
 * Default implementation of Project interface
 */
/*$Id: ProjectImpl.java,v 1.7 2004/11/22 10:02:37 alexeya Exp $*/
public class ProjectImpl implements Project {


    @Override
    public String getID() {
        return "";
    }

    @Override
    public CalendarDate getStartDate() {
        return null;
    }

    @Override
    public void setStartDate(CalendarDate date) {

    }

    @Override
    public CalendarDate getEndDate() {
        return null;
    }

    @Override
    public void setEndDate(CalendarDate date) {

    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setDescription(String description) {

    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public int getStatus() {
        return 0;
    }
}
