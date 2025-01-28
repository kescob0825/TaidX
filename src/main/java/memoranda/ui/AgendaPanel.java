package memoranda.ui;


import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import memoranda.date.CalendarDate;
import memoranda.util.Util;

/*$Id: AgendaPanel.java,v 1.11 2005/02/15 16:58:02 rawsushi Exp $*/
public class AgendaPanel extends JPanel {

	JEditorPane viewer = new JEditorPane("text/html", "");
	JScrollPane scrollPane = new JScrollPane();

	DailyItemsPanel parentPanel = null;

	String gotoTask = null;

	boolean isActive = true;

	public AgendaPanel(DailyItemsPanel _parentPanel) {
		try {
			parentPanel = _parentPanel;
		} catch (Exception ex) {
			new ExceptionDialog(ex);
			ex.printStackTrace();
		}
	}

	public void refresh(CalendarDate date) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if(gotoTask != null) {
					viewer.scrollToReference(gotoTask);
					scrollPane.setViewportView(viewer);
					Util.debug("Set view port to " + gotoTask);
				}
			}
		});

		Util.debug("Summary updated.");
	}

	public void setActive(boolean isa) {
		isActive = isa;
	}


}
