package memoranda.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import javax.swing.*;

import memoranda.*;
import memoranda.api.TaigaClient;
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
    JButton jButton3 = new JButton();
    JLabel statusBar = new JLabel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel containerPanel = new JPanel();


    JMenu jMenuEdit = new JMenu();
    JMenu jMenuFormat = new JMenu();
    JMenu jMenuInsert = new JMenu();

    public WorkPanel workPanel = new WorkPanel();
    public static JPanel rightPanel = new JPanel();

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

        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(borderLayout1);
        statusBar.setText(" Version:" + App.VERSION_INFO + " (Build "
                + App.BUILD_INFO + " )");
        jMenuFile.setText(Local.getString("File"));
        jMenuFileExit.setText(Local.getString("Exit"));
        jMenuFileExit.addActionListener(e -> doExit());
        jMenuHelp.setText(Local.getString("Help"));

        jMenuHelpGuide.setText(Local.getString("Online user's guide"));
        jMenuHelpGuide.setIcon(new ImageIcon(Objects.requireNonNull(AppFrame.class.getResource(
                "/ui/icons/help.png"))));
        jMenuHelpGuide.addActionListener(this::jMenuHelpGuide_actionPerformed);
        
        jMenuHelpWeb.setText(Local.getString("Memoranda web site"));
        jMenuHelpWeb.setIcon(new ImageIcon(Objects.requireNonNull(AppFrame.class.getResource(
                "/ui/icons/web.png"))));
        jMenuHelpWeb.addActionListener(this::jMenuHelpWeb_actionPerformed);
        
        jMenuHelpBug.setText(Local.getString("Report a bug"));
        jMenuHelpBug.addActionListener(this::jMenuHelpBug_actionPerformed);
        
        jMenuHelpAbout.setText(Local.getString("About Memoranda"));
        jMenuHelpAbout.addActionListener(this::jMenuHelpAbout_actionPerformed);
        jButton3.setToolTipText(Local.getString("Help"));

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

        containerPanel.setLayout(new BorderLayout());
        int staticWidth = 200;
        workPanel.setPreferredSize(new Dimension(staticWidth, 0));
        workPanel.setMaximumSize(new Dimension(staticWidth, Integer.MAX_VALUE));
        workPanel.setMinimumSize(new Dimension(staticWidth, 0));

        rightPanel.setMinimumSize(new Dimension(100, 0));
        rightPanel.setBackground(UIManager.getColor("control"));

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

        rightPanel.setBorder(BorderFactory.createEtchedBorder());
        containerPanel.add(workPanel, BorderLayout.WEST);
        containerPanel.add(rightPanel, BorderLayout.CENTER);

        contentPane.add(toolBar, BorderLayout.NORTH);
        contentPane.add(statusBar, BorderLayout.SOUTH);
        contentPane.add(containerPanel, BorderLayout.CENTER);

        jMenuInsert.add(jMenuInsertList);
        jMenuInsert.addSeparator();

        jMenuFormat.add(jMenuFormatPStyle);
        jMenuFormat.add(jMenuFormatAlign);
        jMenuFormat.addSeparator();
        jMenuFormat.add(jMenuFormatTable);
        jMenuFormat.addSeparator();

        jMenuGo.addSeparator();

        containerPanel.setBorder(null);
        workPanel.setBorder(BorderFactory.createEtchedBorder(UIManager.getColor("Button.darkShadow"), UIManager.getColor("Button.shadow")));
        setEnabledEditorMenus(false);

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
    }

    protected void jMenuThemeDark_actionPerformed(ActionEvent e) {
        Configuration.put("LOOK_AND_FEEL", "dark");
        Configuration.saveConfig();
        App.updateLookAndFeel();
    }

    protected void jMenuThemeLight_actionPerformed(ActionEvent e) {
        Configuration.put("LOOK_AND_FEEL", "default");
        Configuration.saveConfig();
        App.updateLookAndFeel();
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
        taigaDlg.setLocation(x, y);
        taigaDlg.setLocationRelativeTo(App.frame);

        SwingUtilities.invokeLater(() -> {
            try {
                taigaDlg.setAlwaysOnTop(true);
                taigaDlg.setVisible(true);
            } catch (Exception ex) {
                new ExceptionDialog(ex);
            }
        });
    }
    protected void jMenuTaigiLogout_actionPerformed(ActionEvent e) {
        TaigaClient taigaClient  = Start.getInjector().getInstance(TaigaClient.class);
        taigaClient.logoutTaiga();
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
