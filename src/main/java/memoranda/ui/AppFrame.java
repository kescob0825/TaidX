package memoranda.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.util.*;

import javax.swing.*;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import memoranda.*;
import memoranda.util.Configuration;
import memoranda.util.Context;
import memoranda.util.Local;

/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: AppFrame.java,v 1.33 2005/07/05 08:17:24 alexeya Exp $*/

public class AppFrame extends JFrame {

    JPanel contentPane;
    JMenuBar menuBar = new JMenuBar();
    JMenu jMenuFile = new JMenu();
    JMenuItem jMenuFileExit = new JMenuItem();

    JToolBar toolBar = new JToolBar();
    //JButton jButton3 = new JButton();
    ImageIcon image1;
    ImageIcon image2;
    ImageIcon image3;
    JLabel statusBar = new JLabel();
    BorderLayout borderLayout1 = new BorderLayout();
    JSplitPane splitPane = new JSplitPane();

    JMenu jMenuEdit = new JMenu();
    JMenu jMenuFormat = new JMenu();
    JMenu jMenuInsert = new JMenu();
    public WorkPanel workPanel = new WorkPanel();

    static Vector exitListeners = new Vector();


    public Action minimizeAction = new AbstractAction("Close the window") {
        public void actionPerformed(ActionEvent e) {
            doMinimize();
        }
    };

    public Action preferencesAction = new AbstractAction("Preferences") {
        public void actionPerformed(ActionEvent e) {
            showPreferences();
        }
    };

    
    JMenuItem jMenuFileNewPrj = new JMenuItem();


    JMenuItem jMenuFileMin = new JMenuItem(minimizeAction);

    JMenu jMenuGo = new JMenu();

    JMenu jMenuInsertList = new JMenu();

    JMenu jMenuFormatPStyle = new JMenu();

    JMenu jMenuFormatAlign = new JMenu();

    JMenu jMenuFormatTable = new JMenu();

    JMenuItem jMenuEditPref = new JMenuItem(preferencesAction);

    JMenu jMenuInsertSpecial = new JMenu();
    
    JMenu jMenuHelp = new JMenu();
    
    JMenuItem jMenuHelpGuide = new JMenuItem();
    JMenuItem jMenuHelpWeb = new JMenuItem();
    JMenuItem jMenuHelpBug = new JMenuItem();
    JMenuItem jMenuHelpAbout = new JMenuItem();

    JMenu jMenuLogin = new JMenu();
    JMenuItem  jMenuTaigiLogin = new JMenuItem();
    JMenuItem  jMenuTaigiLogout = new JMenuItem();

    JMenu jMenuTheme = new JMenu();
    JMenuItem jMenuThemeDark = new JMenuItem();
    JMenuItem  jMenuThemeLight = new JMenuItem();

    //Construct the frame
    public AppFrame() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        }
        catch (Exception e) {
            new ExceptionDialog(e);
        }
    }
    //Component initialization
    private void jbInit() throws Exception {
        this.setIconImage(new ImageIcon(Objects.requireNonNull(AppFrame.class.getResource(
                "/ui/icons/jnotes16.png")))
                .getImage());
        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(borderLayout1);
        this.setSize(new Dimension(800, 500));
        //this.setTitle("Memoranda - " + CurrentProject.get().getTitle());

        //Added a space to App.VERSION_INFO to make it look some nicer
        statusBar.setText(" Version:" + App.VERSION_INFO + " (Build "
                + App.BUILD_INFO + " )");

        jMenuFile.setText(Local.getString("File"));
        jMenuFileExit.setText(Local.getString("Exit"));
        jMenuFileExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doExit();
            }
        });
        jMenuHelp.setText(Local.getString("Help"));

        jMenuHelpGuide.setText(Local.getString("Online user's guide"));
        jMenuHelpGuide.setIcon(new ImageIcon(Objects.requireNonNull(AppFrame.class.getResource(
                "/ui/icons/help.png"))));
        jMenuHelpGuide.addActionListener(this::jMenuHelpGuide_actionPerformed);
        
        jMenuHelpWeb.setText(Local.getString("Memoranda web site"));
        jMenuHelpWeb.setIcon(new ImageIcon(Objects.requireNonNull(AppFrame.class.getResource(
                "/ui/icons/web.png"))));
        jMenuHelpWeb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuHelpWeb_actionPerformed(e);
            }
        });
        
        jMenuHelpBug.setText(Local.getString("Report a bug"));
        jMenuHelpBug.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuHelpBug_actionPerformed(e);
            }
        });        
        
        jMenuHelpAbout.setText(Local.getString("About Memoranda"));
        jMenuHelpAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuHelpAbout_actionPerformed(e);
            }
        });
        //jButton3.setIcon(image3);
        //jButton3.setToolTipText(Local.getString("Help"));

        jMenuLogin.setText(Local.getString("Taiga Login"));
        jMenuTaigiLogin.setText(Local.getString("Sign in"));
        jMenuLogin.setIcon(new ImageIcon(Objects.requireNonNull(AppFrame.class.getResource(
                "/ui/icons/taigaicon.png"))));
        jMenuTaigiLogout.setText(Local.getString("Sign out"));
        jMenuTaigiLogin.addActionListener(this::jMenuTaigiLogin_actionPerformed);
        jMenuTaigiLogout.addActionListener(this::jMenuTaigiLogout_actionPerformed);

        jMenuTheme.setText(Local.getString("Theme"));
        jMenuThemeDark.setText(Local.getString("Dark"));
        jMenuThemeLight.setText(Local.getString("Light"));
        jMenuThemeDark.addActionListener(this::jMenuThemeDark_actionPerformed);
        jMenuThemeLight.addActionListener(this::jMenuThemeLight_actionPerformed);

        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

        splitPane.setContinuousLayout(true);
        splitPane.setDividerSize(3);
        splitPane.setDividerLocation(28);

        jMenuFileMin.setText(Local.getString("Close the window"));
        jMenuFileMin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10,
                InputEvent.ALT_MASK));

        jMenuEdit.setText(Local.getString("Edit"));

        jMenuEditPref.setText(Local.getString("Preferences") + "...");

        jMenuInsert.setText(Local.getString("Insert"));

        jMenuInsertList.setText(Local.getString("List"));

        jMenuInsertSpecial.setText(Local.getString("Special"));

        jMenuFormat.setText(Local.getString("Format"));
        jMenuFormatPStyle.setText(Local.getString("Paragraph style"));

        jMenuFormatAlign.setText(Local.getString("Alignment"));
        jMenuFormatTable.setText(Local.getString("Table"));


        jMenuInsertSpecial.setText(Local.getString("Special"));

        //toolBar.add(jButton3);
        jMenuFile.add(jMenuFileNewPrj);

        jMenuFile.addSeparator();
        jMenuFile.add(jMenuEditPref);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuFileMin);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuFileExit);
        
        jMenuHelp.add(jMenuHelpGuide);
        jMenuHelp.add(jMenuHelpWeb);
        jMenuHelp.add(jMenuHelpBug);
        jMenuHelp.addSeparator();
        jMenuHelp.add(jMenuHelpAbout);

        jMenuLogin.add(jMenuTaigiLogin);
        jMenuLogin.add(jMenuTaigiLogout);

        jMenuTheme.add(jMenuThemeLight);
        jMenuTheme.add(jMenuThemeDark);

        menuBar.add(jMenuFile);
        menuBar.add(jMenuEdit);
        menuBar.add(jMenuInsert);
        menuBar.add(jMenuFormat);
        menuBar.add(jMenuGo);
        menuBar.add(jMenuHelp);
        menuBar.add(jMenuLogin);
        menuBar.add(jMenuTheme);

        this.setJMenuBar(menuBar);
        //contentPane.add(toolBar, BorderLayout.NORTH);
        contentPane.add(statusBar, BorderLayout.SOUTH);
        contentPane.add(splitPane, BorderLayout.CENTER);
        splitPane.add(workPanel, JSplitPane.BOTTOM);

        jMenuInsert.add(jMenuInsertList);
        //jMenuInsert.add(jMenuInsertSpecial);
        jMenuInsert.addSeparator();

        jMenuFormat.add(jMenuFormatPStyle);
        jMenuFormat.add(jMenuFormatAlign);
        jMenuFormat.addSeparator();
        jMenuFormat.add(jMenuFormatTable);
        jMenuFormat.addSeparator();

        jMenuGo.addSeparator();

        splitPane.setBorder(null);
        workPanel.setBorder(null);
        setEnabledEditorMenus(false);

        java.awt.event.ActionListener setMenusDisabled = new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setEnabledEditorMenus(false);
            }
        };

        this.workPanel.tasksB.addActionListener(setMenusDisabled);
        this.workPanel.eventsB.addActionListener(setMenusDisabled);
        this.workPanel.filesB.addActionListener(setMenusDisabled);
        this.workPanel.agendaB.addActionListener(setMenusDisabled);
        this.workPanel.homeB.addActionListener(setMenusDisabled);

        Object fwo = Context.get("FRAME_WIDTH");
        Object fho = Context.get("FRAME_HEIGHT");
        if ((fwo != null) && (fho != null)) {
            int w = Integer.parseInt((String) fwo);
            int h = Integer.parseInt((String) fho);
            this.setSize(w, h);
        }
        else
            this.setSize(new Dimension(800, 500));
            //this.setExtendedState(Frame.MAXIMIZED_BOTH);

        Object xo = Context.get("FRAME_XPOS");
        Object yo = Context.get("FRAME_YPOS");
        if ((xo != null) && (yo != null)) {
            int x = Integer.parseInt((String) xo);
            int y = Integer.parseInt((String) yo);
            this.setLocation(x, y);
        }

        String pan = (String) Context.get("CURRENT_PANEL");
        if (pan != null) {
            workPanel.selectPanel(pan);
            setEnabledEditorMenus(pan.equalsIgnoreCase("NOTES"));
        }


    }

    protected void jMenuThemeDark_actionPerformed(ActionEvent e) {
        Configuration.put("LOOK_AND_FEEL", "dark");
        Configuration.saveConfig();

        try {
            FlatDarkLaf.setup();
            UIManager.setLookAndFeel(new FlatDarkLaf());

            // Update all components to use the new Look and Feel
            SwingUtilities.updateComponentTreeUI(App.frame);

            // If you have any other frames or dialogs open, update them too
            for (Window window : Window.getWindows()) {
                SwingUtilities.updateComponentTreeUI(window);
            }
        }catch(UnsupportedLookAndFeelException ex){
            ex.printStackTrace();
        }
    }

    protected void jMenuThemeLight_actionPerformed(ActionEvent e) {
        Configuration.put("LOOK_AND_FEEL", "light");
        Configuration.saveConfig();
        try {
            FlatLightLaf.setup();
            UIManager.setLookAndFeel(new FlatLightLaf());

            // Update all components to use the new Look and Feel
            SwingUtilities.updateComponentTreeUI(App.frame);

            // If you have any other frames or dialogs open, update them too
            for (Window window : Window.getWindows()) {
                SwingUtilities.updateComponentTreeUI(window);
            }
        }catch(UnsupportedLookAndFeelException ex){
            ex.printStackTrace();
        }
    }

    protected void jMenuHelpBug_actionPerformed(ActionEvent e) {
        try{
            if(Desktop.isDesktopSupported()){
                Desktop desktop = Desktop.getDesktop();
                URI uri = new URI("https://github.com/amehlhase316/Ruebezahl_spring25A/issues/new?template=Blank+issue");
                desktop.browse(uri);
            }
            else{
                JOptionPane.showMessageDialog(this, "Desktop browsing is not supported on this platform.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Error opening online bug reporter: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void jMenuHelpWeb_actionPerformed(ActionEvent e) {
        try {
            // Check if Desktop is supported
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                // Replace this URL with your actual user guide URL
                URI uri = new URI("https://memoranda.sourceforge.net/");
                desktop.browse(uri);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Desktop browsing is not supported on this platform",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error opening online guide: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void jMenuHelpGuide_actionPerformed(ActionEvent e) {
        try {
            // Check if Desktop is supported
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                // Replace this URL with your actual user guide URL
                URI uri = new URI("https://memoranda.sourceforge.net/guide.html");
                desktop.browse(uri);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Desktop browsing is not supported on this platform",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error opening online guide: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void jMenuTaigiLogin_actionPerformed(ActionEvent e){
        TaigaLoginDialog taigaDlg = Start.getInjector().getInstance(TaigaLoginDialog.class);

        taigaDlg.pack();

        // Calculate the center position
        int x = App.getFrame().getX() + (App.getFrame().getWidth() - taigaDlg.getWidth()) / 2;
        int y = App.getFrame().getY() + (App.getFrame().getHeight() - taigaDlg.getHeight()) / 2;

        // Set the dialog position
        taigaDlg.setLocation(x, y);
        taigaDlg.setLocationRelativeTo(App.frame);
        taigaDlg.setAlwaysOnTop(true);
        taigaDlg.setVisible(true);
    }
    protected void jMenuTaigiLogout_actionPerformed(ActionEvent e){

    }
    
    //File | Exit action performed
    public void doExit() {
        if (Configuration.get("ASK_ON_EXIT").equals("yes")) {
                        Dimension frmSize = this.getSize();
                        Point loc = this.getLocation();
                        
                        ExitConfirmationDialog dlg = new ExitConfirmationDialog(this,Local.getString("Exit"));
                        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
                        dlg.setVisible(true);
                        if(dlg.CANCELLED) return;
        }

        Context.put("FRAME_WIDTH", Integer.valueOf(this.getWidth()));
        Context.put("FRAME_HEIGHT", Integer.valueOf(this.getHeight()));
        Context.put("FRAME_XPOS", Integer.valueOf(this.getLocation().x));
        Context.put("FRAME_YPOS", Integer.valueOf(this.getLocation().y));
        exitNotify();
        App.closeWindow();
    }

    public void doMinimize() {
        App.minimizeWindow();
    }

    //Help | About action performed
    public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
         AppFrame_AboutBox dlg = new AppFrame_AboutBox(this);        
         Dimension dlgSize = dlg.getSize();
         Dimension frmSize = getSize();
         Point loc = getLocation();
         dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
         dlg.setModal(true);
         dlg.setVisible(true);
    }

    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            if (Configuration.get("ON_CLOSE").equals("exit"))
                doExit();
            else
                doMinimize();
        }
        else if ((e.getID() == WindowEvent.WINDOW_ICONIFIED)) {
            doMinimize();
        }
        else
            super.processWindowEvent(e);
    }

    public static void addExitListener(ActionListener al) {
        exitListeners.add(al);
    }

    public static Collection getExitListeners() {
        return exitListeners;
    }

    private static void exitNotify() {
        for (int i = 0; i < exitListeners.size(); i++)
            ((ActionListener) exitListeners.get(i)).actionPerformed(null);
    }

    public void setEnabledEditorMenus(boolean enabled) {
        this.jMenuEdit.setEnabled(enabled);
        this.jMenuFormat.setEnabled(enabled);
        this.jMenuInsert.setEnabled(enabled);
    }

    public void showPreferences() {
        PreferencesDialog dlg = new PreferencesDialog(this);
        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }
    


}
