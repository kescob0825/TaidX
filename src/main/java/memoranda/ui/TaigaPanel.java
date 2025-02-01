package memoranda.ui;

import memoranda.Start;
import memoranda.api.models.UserProfile;
import memoranda.api.TaigaClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TaigaPanel extends JPanel {

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

    public TaigaPanel(LayoutManager layout) {

        // Parent panel
        this.setLayout(layout);
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }
    void jbInit() throws Exception {

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
        // Taiga title panel on top of profilePanel
        panelTitleH.setText("Taiga Profile");
        panelTitleH.setFont(new Font("Dialog", 0, 20));
        panelTitleH.setForeground(new Color(0, 0, 124));
        //titlePanel.setBackground(Color.BLUE);
        titlePanel.setPreferredSize(new Dimension(300, 30));
        titlePanel.add(panelTitleH);

        //pfpPanel.setBackground(Color.WHITE);
        pfpPanel.setPreferredSize(new Dimension(300, NULL_VAL));
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setPreferredSize(new Dimension(300, 150));
        //infoPanel.setBackground(Color.GREEN);

        pRealNameH.setText("Name");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.FIRST_LINE_START;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(pRealNameH, gbc);

        pUsernameH.setText("Username");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(pUsernameH, gbc);

        pEmailH.setText("Email");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(pEmailH, gbc);

        pProjectsH.setText("Number of Projects");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(pProjectsH, gbc);

        pClosedUSH.setText("Closed US");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(pClosedUSH, gbc);

        pContactsH.setText("Contacts");
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

        UserProfile user = client.getUserProfile();

        pUsernameH.setText("@" + user.getUsername());
        pRealNameH.setText(user.getFullName());
        pEmailH.setText(user.getEmail());
    }
}
