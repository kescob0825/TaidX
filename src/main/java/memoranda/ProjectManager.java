/**
 * ProjectManager.java
 * Created on 11.02.2003, 17:50:27 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package memoranda;

import java.util.Vector;

import memoranda.date.CalendarDate;
import memoranda.util.CurrentStorage;
import memoranda.util.Local;
import memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

/**
 *
 */
/*$Id: ProjectManager.java,v 1.9 2005/12/01 08:12:26 alexeya Exp $*/
public class ProjectManager {
//    public static final String NS_JNPROJECT = "http://www.openmechanics.org/2003/jnotes-projects-file";

    public static Document _doc = null;
    static Element _root = null;
    
    static {
    	init();
    }

    public static void init() {
        CurrentStorage.get().openProjectManager();
        if (_doc == null) {
            _root = new Element("projects-list");
//            _root.addNamespaceDeclaration("jnotes", NS_JNPROJECT);
//            _root.appendChild(new Comment("This is JNotes 2 data file. Do not modify."));
            _doc = new Document(_root);
            createProject("__default", Local.getString("Default project"), CalendarDate.today(), null);
        }
        else
            _root = _doc.getRootElement();
    }

    public static Project getProject(String id) {
        Elements prjs = _root.getChildElements("project");
        for (int i = 0; i < prjs.size(); i++) {
            String pid = ((Element) prjs.get(i)).getAttribute("id").getValue();
            if (pid.equals(id)) {
                return new ProjectImpl((Element) prjs.get(i));
            }
        }
        return null;
    }

    public static Vector getAllProjects() {
        return new Vector<>();
    }

    public static int getAllProjectsNumber() {
		return 0;
    }

    public static Vector getActiveProjects() {
        return new Vector<>();
    }
		
    public static int getActiveProjectsNumber() {
        return 0;
    }

    public static Project createProject(String id, String title, CalendarDate startDate, CalendarDate endDate) {
        return new ProjectImpl((Element) null);
    }

    public static Project createProject(String title, CalendarDate startDate, CalendarDate endDate) {
        return new ProjectImpl((Element) null);
    }
    
    public static void removeProject(String id) {

    }

}
