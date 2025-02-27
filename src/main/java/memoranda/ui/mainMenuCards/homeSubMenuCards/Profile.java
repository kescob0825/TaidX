package memoranda.ui.mainMenuCards.homeSubMenuCards;

import memoranda.Start;
import memoranda.api.models.ProjectData;
import memoranda.api.models.UserStatsData;
import memoranda.api.models.UserProfile;

import memoranda.api.TaigaClient;
import memoranda.ui.App;
import memoranda.ui.ExceptionDialog;
import memoranda.util.TaigaJsonData;
import memoranda.util.subscriber.Subscriber;

import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

import java.util.List;

public class Profile extends JPanel implements Subscriber {

    // Main panels
    private JPanel leftPanel;
    private static UserStatsData stats;
    private static UserProfile user;
    TaigaClient client = Start.getInjector().getInstance(TaigaClient.class);
    //Left Panel
    JLabel fullNameH;
    JPanel fullNameP;
    JLabel userNameH;
    JPanel userNameP;
    JLabel emailH;
    JPanel emailP;
    JPanel topCenterWP;
    JLabel projectsL;
    JPanel projectsP;
    JLabel closedUSL;
    JPanel closedUSP;
    JLabel contactsL;
    JPanel contactsP;
    JPanel topBottomWP;
    JLabel rolesTitleL;
    JPanel rolesTitleP;
    JPanel rolesListP;

    JPanel topPanel;
    JPanel centerPanel;
    JPanel bottomPanel;

    public final int NULL_VAL = 100;
    public final int LEFT_PANEL_WIDTH = 450;

    public Profile() {

        leftPanel = leftPanel();
        setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);
        client.register(this);

    }

    @Override
    public void update() throws IOException {
        SwingUtilities.invokeLater(() -> {
            try {
                leftPanel.setVisible(false);
                this.remove(leftPanel);
                leftPanel = leftPanel();
                this.add(leftPanel);
                leftPanel.revalidate();
                leftPanel.repaint();
                leftPanel.setVisible(true);
                this.revalidate();
                this.repaint();
            } catch (Exception e) {
                new ExceptionDialog(e);
            }
        });
    }

    public void setInfo() {
        fullNameH.setText((user.getFullName() == null || user.getFullName().isEmpty()
                ? "name" : "Name: " + user.getFullName()));
        userNameH.setText("Username: @" + (user.getUsername() == null || user.getUsername().isEmpty()
                ? "username" : user.getUsername()));
        emailH.setText(user.getEmail() == null || user.getEmail().isBlank()
                ? "email" : "Email: " + user.getEmail());
        rolesTitleL.setText(user.getRoles() == null || user.getRoles().isEmpty()
                ? "No Roles" : "<html><body style='width: 200px'>Roles: " + user.getRoles().toString() + "</body></html>");
    }

    private synchronized JPanel leftPanel() {
        leftPanel = new JPanel(new BorderLayout());
        fullNameH = new JLabel("Name: ");
        userNameH = new JLabel("UserName: @");
        emailH = new JLabel("Email: ");
        rolesTitleL = new JLabel("Roles: ");
        if (client.getUserProfile() != null) {
            user = client.getUserProfile();
            stats = client.getUserStatsData();
            setInfo();
        }
        else {
            JLabel noDataLabel = new JLabel("No data available. Login, close the program and reopen to view project. ");
            add(noDataLabel);
        }
            leftPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, NULL_VAL));

            topPanel = new JPanel(new BorderLayout());
            topPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, 220));

            fullNameH.setFont(new Font("Arial", Font.BOLD, 35));
            fullNameP = new JPanel(new BorderLayout());
            fullNameP.setBorder(new EmptyBorder(0, 20, 0, 0));
            fullNameP.add(fullNameH, BorderLayout.WEST);
            fullNameP.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, 60));

            topCenterWP = new JPanel(new GridLayout(2, 1));
            topCenterWP.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, 50));
            topCenterWP.setBorder(new EmptyBorder(0, 20, 0, 0));

            userNameH.setFont(new Font("Arial", Font.PLAIN, 20));
            userNameP = new JPanel(new BorderLayout());
            userNameP.add(userNameH, BorderLayout.WEST);
            topCenterWP.add(userNameP);

            emailH.setFont(new Font("Arial", Font.PLAIN, 20));
            emailP = new JPanel(new BorderLayout());
            emailP.add(emailH, BorderLayout.WEST);
            topCenterWP.add(emailP);

            rolesTitleL.setFont(new Font("Arial", Font.BOLD, 25));
            rolesTitleP = new JPanel(new BorderLayout());
            rolesTitleP.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, 50));
            rolesTitleP.add(rolesTitleL, BorderLayout.WEST);

            topBottomWP = new JPanel(new GridLayout(1, 3));
            topBottomWP.setBorder(new EmptyBorder(10, 10, 0, 0));
            topBottomWP.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, 90));

            projectsL = new JLabel();
            closedUSL = new JLabel();
            contactsL = new JLabel();

            projectsL.setFont(new Font("Arial", Font.PLAIN, 18));
            projectsP = new JPanel();
            projectsP.setBorder(new EmptyBorder(10, 0, 0, 0));
            projectsP.add(projectsL);
            topBottomWP.add(projectsP);

            contactsL.setFont(new Font("Arial", Font.PLAIN, 18));
            contactsP = new JPanel();
            contactsP.setBorder(new EmptyBorder(10, 0, 0, 0));
            contactsP.add(contactsL);
            topBottomWP.add(contactsP);

            topPanel.add(fullNameP, BorderLayout.NORTH);
            topPanel.add(topCenterWP, BorderLayout.CENTER);
            topPanel.add(topBottomWP, BorderLayout.SOUTH);

            centerPanel = new JPanel(new BorderLayout());
            centerPanel.setBorder(new EmptyBorder(20, 20, 0, 0));

            rolesListP = new JPanel(new GridLayout(6, 1));
            rolesListP.setBorder(new EmptyBorder(0, 30, 0, 0));

            if (stats != null) {
                projectsL.setText("Projects: " + stats.getNumProjects());
                contactsL.setText("Contacts: " + stats.getNumContacts());
                List<String> roles = stats.getRoles();
                for (String role : roles) {
                    JLabel roleH = new JLabel(role);
                    roleH.setFont(new Font("Arial", Font.PLAIN, 20));
                    JPanel roleP = new JPanel(new BorderLayout());
                    roleP.add(roleH, BorderLayout.WEST);
                    rolesListP.add(roleP);
                }
            }
            centerPanel.add(rolesTitleP, BorderLayout.NORTH);
            centerPanel.add(rolesListP, BorderLayout.CENTER);

            bottomPanel = new JPanel();
            bottomPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, 400));

            leftPanel.add(topPanel, BorderLayout.NORTH);
            leftPanel.add(centerPanel, BorderLayout.CENTER);
            leftPanel.add(bottomPanel, BorderLayout.SOUTH);
            return leftPanel;
    }

}