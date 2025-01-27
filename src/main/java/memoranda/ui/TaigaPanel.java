package memoranda.ui;

import memoranda.util.Local;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class TaigaPanel extends JPanel {

    JLabel panelTitleH = new JLabel();
    JLabel pfpH = new JLabel();
    JLabel pRealNameH = new JLabel();
    JLabel pUsernameH = new JLabel();
    JLabel pEmailH = new JLabel();
    JLabel pProjectsH = new JLabel();
    JLabel pClosedUSH = new JLabel();
    JLabel pContactsH = new JLabel();

    JPanel infoPanel = new JPanel();
    JPanel titlePanel = new JPanel();
    JPanel pfpPanel = new JPanel();
    JPanel profileInfoPanel = new JPanel();
    JPanel profilePanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    GridBagConstraints gbc;
    DailyItemsPanel parentPanel = null;

    public TaigaPanel(DailyItemsPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    void jbInit() throws Exception {

        // Parent panel
        this.setLayout(new BorderLayout());

        // This is the panel which will hold the profile information of the user
        //profilePanel.setBackground(Color.RED);
        profilePanel.setPreferredSize(new Dimension(100, 450));

        // Bottom panel is not used nor implemented
        //bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setPreferredSize(new Dimension(100, 100));

        this.add(profilePanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.CENTER);

        profilePanel.setLayout(new BorderLayout());
        // Subsection of the profile panel which will hold the main information
        // of the user including; pfp, username, number of projects, contacts.
        //infoPanel.setBackground(Color.WHITE);
        infoPanel.setPreferredSize(new Dimension(250, 100));

        profilePanel.add(infoPanel, BorderLayout.WEST);

        infoPanel.setLayout(new BorderLayout());

        // Panel that holds the name of the parent panel
        //titlePanel.setBackground(Color.GREEN);
        titlePanel.setPreferredSize(new Dimension(100, 30));
        panelTitleH.setText("Taiga Profile");
        panelTitleH.setFont(new java.awt.Font("Dialog", 0, 20));
        panelTitleH.setForeground(new Color(0, 0, 124));
        titlePanel.add(panelTitleH);
        // Panel that holds the pfp
        //pfpPanel.setBackground(Color.BLACK);
        ImageIcon taigaLogo = new ImageIcon(Objects.requireNonNull(
                App.class.getResource("/ui/icons/emptypfp.jpg")));
        Image originalImage = taigaLogo.getImage();
        Image resizedImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedTaigaLogo = new ImageIcon(resizedImage);
        pfpH.setSize(200, 200);
        pfpH.setIcon(resizedTaigaLogo);
        pfpPanel.setPreferredSize(new Dimension(200, 200));
        pfpPanel.add(pfpH);
        // Panel that will hold information such as; username, number of projects, contacts
        profileInfoPanel.setLayout(new GridBagLayout());
        //profileInfoPanel.setBackground(Color.BLUE);
        profileInfoPanel.setPreferredSize(new Dimension(100, 200));

        pRealNameH.setText("Name:");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.FIRST_LINE_START;
        gbc.anchor = GridBagConstraints.WEST;
        profileInfoPanel.add(pRealNameH, gbc);

        pUsernameH.setText("Username:");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        profileInfoPanel.add(pUsernameH, gbc);

        pEmailH.setText("Email:");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        profileInfoPanel.add(pEmailH, gbc);

        pProjectsH.setText("Number of Projects:");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        profileInfoPanel.add(pProjectsH, gbc);

        pClosedUSH.setText("Closed US:");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        profileInfoPanel.add(pClosedUSH, gbc);

        pContactsH.setText("Contacts:");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        profileInfoPanel.add(pContactsH, gbc);

        infoPanel.add(titlePanel, BorderLayout.NORTH);
        infoPanel.add(pfpPanel, BorderLayout.WEST);
        infoPanel.add(profileInfoPanel, BorderLayout.SOUTH);
    }
}
