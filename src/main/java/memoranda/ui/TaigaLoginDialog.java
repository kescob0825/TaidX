package memoranda.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import memoranda.Project;
import memoranda.ProjectManager;
import memoranda.date.CalendarDate;
import memoranda.util.CurrentStorage;
import memoranda.util.Local;

public class TaigaLoginDialog extends JDialog {
    public boolean CANCELLED = true;
    GridBagConstraints gbc;
    JPanel topPanel = new JPanel(new FlowLayout());
    JLabel taigaheader = new JLabel();
    JPanel centerPanel = new JPanel(new GridBagLayout());
    public JTextField userNameField = new JTextField();
    JLabel userNameLabel = new JLabel();
    public JTextField passwordField = new JTextField();
    JLabel passwordLabel = new JLabel();
    JPanel bottomPanel = new JPanel();
    JButton loginButton = new JButton();
    JButton quitButton = new JButton();
    public TaigaLoginDialog(Frame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();
            pack();
        }
        catch(Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    void jbInit() throws Exception {
        this.setResizable(false);
        topPanel.setBorder(new EmptyBorder(new Insets(0, 5, 0, 5)));
        topPanel.setBackground(Color.WHITE);
        //taigaheader.setFont(new java.awt.Font("Dialog", 0, 20));
        //taigaheader.setForeground(new Color(0, 0, 124));
        //taigaheader.setText(Local.getString("Taiga Login"));
        ImageIcon taigaLogo = new ImageIcon(App.class.getResource("/ui/taiga_banner.jpg"));
        int bannerH = 110, bannerW = 380;
        Image originalImage = taigaLogo.getImage();
        Image resizedImage = originalImage.getScaledInstance(bannerW, bannerH, Image.SCALE_SMOOTH);
        ImageIcon resizedTaigaLogo = new ImageIcon(resizedImage);
        topPanel.setSize(bannerW, bannerH);
        taigaheader.setSize(bannerW, bannerH);
        taigaheader.setIcon(resizedTaigaLogo);
        topPanel.add(taigaheader);
        // Username label
        centerPanel.setBorder(new EtchedBorder());
        userNameLabel.setText(Local.getString("Username"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        centerPanel.add(userNameLabel, gbc);
        // Username text field
        userNameField.setPreferredSize(new Dimension(270, 20));
        gbc = new GridBagConstraints();
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(userNameField, gbc);
        // Password label
        centerPanel.setBorder(new EtchedBorder());
        passwordLabel.setText(Local.getString("Password"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        centerPanel.add(passwordLabel, gbc);
        // Password text field
        gbc = new GridBagConstraints();
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(passwordField, gbc);

        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        loginButton.setMaximumSize(new Dimension(100, 25));
        loginButton.setMinimumSize(new Dimension(100, 25));
        loginButton.setPreferredSize(new Dimension(100, 25));
        loginButton.setText(Local.getString("Login"));
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //okButton_actionPerformed(e);
            }
        });
        this.getRootPane().setDefaultButton(loginButton);
        quitButton.setMaximumSize(new Dimension(100, 25));
        quitButton.setMinimumSize(new Dimension(100, 25));
        quitButton.setPreferredSize(new Dimension(100, 25));
        quitButton.setText(Local.getString("Cancel"));
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
            //    cancelButton_actionPerformed(e);
            }
        });
        bottomPanel.add(loginButton);
        bottomPanel.add(quitButton);

        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(topPanel, "North");

        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        getContentPane().add(centerPanel, "Center");

        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(bottomPanel, "South");

        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
