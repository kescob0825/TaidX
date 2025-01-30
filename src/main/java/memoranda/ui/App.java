package memoranda.ui;

import java.awt.*;
import java.util.Calendar;

import javax.swing.*;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import memoranda.util.Configuration;
import memoranda.util.Context;

/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: App.java,v 1.28 2007/03/20 06:21:46 alexeya Exp $*/
public class App {
	// boolean packFrame = false;

	static AppFrame frame = null;
	
	public static final String GUIDE_URL = "http://memoranda.sourceforge.net/guide.html";
	public static final String BUGS_TRACKER_URL = "http://sourceforge.net/tracker/?group_id=90997&atid=595566";
	public static final String WEBSITE_URL = "http://memoranda.sourceforge.net";

	private JFrame splash = null;

	/*========================================================================*/ 
	/* Note: Please DO NOT edit the version/build info manually!
       The actual values are substituted by the Ant build script using 
       'version' property and datestamp.*/

	public static final String VERSION_INFO = "@VERSION@";
	public static final String BUILD_INFO = "@BUILD@";
	
	/*========================================================================*/

	public static AppFrame getFrame() {
		if(frame == null) {return null;}
		return frame;
	}

	public void show() {
		if (frame.isVisible()) {
			frame.toFront();
			frame.requestFocus();
		} else
			init();
	}

	public App(boolean fullmode) {
		super();
		if (fullmode)
			fullmode = !Configuration.get("START_MINIMIZED").equals("yes");
		/* DEBUG */
		if (!fullmode)
			System.out.println("Minimized mode");
		if (!Configuration.get("SHOW_SPLASH").equals("no"))
			showSplash();
		System.out.println(VERSION_INFO);

		if (Configuration.get("FIRST_DAY_OF_WEEK").equals("")) {
			String fdow;
			if (Calendar.getInstance().getFirstDayOfWeek() == 2)
				fdow = "mon";
			else
				fdow = "sun";
			Configuration.put("FIRST_DAY_OF_WEEK", fdow);
			Configuration.saveConfig();
			/* DEBUG */
			System.out.println("[DEBUG] first day of week is set to " + fdow);
		}
		
		frame = new AppFrame();
		if (fullmode) {
			init();
		}
		if (!Configuration.get("SHOW_SPLASH").equals("no"))
			splash.dispose();
		System.out.println(Configuration.get("LOOK_AND_FEEL"));
		updateLookAndFeel();
	}

	void init() {
		frame.pack();
		int width = Context.get("FRAME_WIDTH") != null ?
                Integer.valueOf((String)Context.get("FRAME_WIDTH")) : 800; // default width
		int height = Context.get("FRAME_HEIGHT") != null ?
				Integer.valueOf((String)Context.get("FRAME_HEIGHT")) : 600; // default height
		int xPos = Context.get("FRAME_XPOS") != null ?
				Integer.valueOf((String)Context.get("FRAME_XPOS")) : 0;
		int yPos = Context.get("FRAME_YPOS") != null ?
				Integer.valueOf((String)Context.get("FRAME_YPOS")) : 0;
		frame.setSize(width, height);
		frame.setLocation(xPos, yPos);

		frame.setVisible(true);
		frame.toFront();
		frame.requestFocus();

	}

	public static void updateLookAndFeel2(JPanel workPanel) {
		SwingUtilities.invokeLater(() -> {
			try {
				JFrame.setDefaultLookAndFeelDecorated(true);
				JDialog.setDefaultLookAndFeelDecorated(true);
				SwingUtilities.updateComponentTreeUI(workPanel);
				workPanel.getRootPane().revalidate();
				workPanel.repaint();
			} catch (Exception e) {
				new ExceptionDialog(e);
			}
		});
	}

	public static void updateLookAndFeel() {
		SwingUtilities.invokeLater(() -> {
			try {
				JFrame.setDefaultLookAndFeelDecorated(true);
				JDialog.setDefaultLookAndFeelDecorated(true);

				if (Configuration.get("LOOK_AND_FEEL").equals("default")) {
					FlatLightLaf.setup();
					UIManager.setLookAndFeel(new FlatLightLaf());
					SwingUtilities.updateComponentTreeUI(App.frame);
					for (Window window : Window.getWindows()) {
						SwingUtilities.updateComponentTreeUI(window);
					}
				}
				else if (Configuration.get("LOOK_AND_FEEL").equals("dark")) {
					FlatDarkLaf.setup();
					UIManager.setLookAndFeel(new FlatDarkLaf());
					SwingUtilities.updateComponentTreeUI(App.frame);
					for (Window window : Window.getWindows()) {
						SwingUtilities.updateComponentTreeUI(window);
					}
				}
				SwingUtilities.updateComponentTreeUI(frame);
				frame.getRootPane().revalidate();
				frame.repaint();
			} catch (Exception e) {
				new ExceptionDialog(e);
			}
		});
	}
	//Iconified will minimize Frame to toolbar
	public static void minimizeWindow(){
		if (frame == null)
			return;
		frame.setState(Frame.ICONIFIED);
	}
	//doExit calls
	public static void closeWindow() {
		if (frame == null)
			return;
		frame.dispose();
		System.exit(0);
	}

	/**
	 * Method showSplash.
	 */
	private void showSplash() {
		splash = new JFrame();
		ImageIcon originalIcon = new ImageIcon(App.class.getResource("/ui/splash.png"));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int targetWidth = 500;
		int targetHeight = 500;
		Image scaledImage = originalIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		JLabel l = new JLabel();
		l.setSize(targetWidth, targetHeight);
		l.setIcon(scaledIcon);
		splash.getContentPane().add(l);
		splash.setSize(targetWidth, targetHeight);
		splash.setLocation((screenSize.width - targetWidth) / 2, (screenSize.height - targetHeight) / 2);
		splash.setUndecorated(true);
		splash.setVisible(true);

	}
}