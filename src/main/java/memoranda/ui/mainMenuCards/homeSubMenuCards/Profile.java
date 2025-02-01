package memoranda.ui.mainMenuCards.homeSubMenuCards;

import memoranda.Start;
import memoranda.ui.mainMenuCards.HomeToolBarCards;

import memoranda.api.TaigaClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;


public class Profile extends JPanel {

    int NULL_VAL = 100;
    // Main left panel
    JPanel profileInfoPanel = new JPanel();
    // Top panel within the left panel
    JPanel profilePanel = new JPanel();
    // Top title panel within profile panel
    JPanel titlePanel = new JPanel();
    JLabel panelTitleH = new JLabel();
    // Center pfp panel within profile panel TODO: This will be implemented in Sprint 2
    JPanel pfpPanel = new JPanel();
    JLabel pfpH = new JLabel();
    // Bottom pfp panel within profile panel
    JPanel infoPanel = new JPanel();
    JLabel pRealNameH = new JLabel();
    JLabel pUsernameH = new JLabel();
    JLabel pEmailH = new JLabel();
    JLabel pProjectsH = new JLabel();
    JLabel pClosedUSH = new JLabel();
    JLabel pContactsH = new JLabel();

    GridBagConstraints gbc;

    TaigaClient client = Start.getInjector().getInstance(TaigaClient.class);
    JButton logIn = new JButton("Refresh");

    public Profile() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    void jbInit() throws Exception {

        // Parent panel
        this.setLayout(new BorderLayout());
        // Left side panel
        profileInfoPanel.setLayout(new BorderLayout());
        //profileInfoPanel.setBackground(Color.BLACK);
        profileInfoPanel.setPreferredSize(new Dimension(300, NULL_VAL));
        profileInfoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(profileInfoPanel, BorderLayout.WEST);
        // Top panel within left side panel
        //profilePanel.setBackground(Color.RED);
        profilePanel.setPreferredSize(new Dimension(298, 300));
        profileInfoPanel.add(profilePanel, BorderLayout.NORTH);
        //titlePanel.setBackground(Color.BLUE);
        titlePanel.setPreferredSize(new Dimension(300, 30));
        titlePanel.add(panelTitleH);

        //pfpPanel.setBackground(Color.WHITE);
        pfpPanel.setPreferredSize(new Dimension(300, NULL_VAL));
        pfpPanel.add(this.logIn);
        //this.logIn.addActionListener(this::refreshPanel);
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setPreferredSize(new Dimension(300, 150));
        //infoPanel.setBackground(Color.GREEN);

        pRealNameH.setText("Name:");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.FIRST_LINE_START;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(pRealNameH, gbc);

        pUsernameH.setText("Username:");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(pUsernameH, gbc);

        pEmailH.setText("Email:");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(pEmailH, gbc);

        pProjectsH.setText("Number of Projects:");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(pProjectsH, gbc);

        pClosedUSH.setText("Closed US:");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(pClosedUSH, gbc);

        pContactsH.setText("Contacts:");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(pContactsH, gbc);

        profilePanel.add(titlePanel, BorderLayout.NORTH);
        profilePanel.add(pfpPanel, BorderLayout.CENTER);
        profilePanel.add(infoPanel, BorderLayout.SOUTH);
    }
    public void refreshPanel() {
        try {
            TaigaClient client = Start.getInjector().getInstance(TaigaClient.class);
            pUsernameH.setText("Username: " + client.getUserProfile().getUsername());
            pRealNameH.setText("RealName: " + client.getUserProfile().getFullName());
            pEmailH.setText("Email: " + client.getUserProfile().getEmail());
        }
        catch (IOException io) {
            io.fillInStackTrace();
        }
    }
}
