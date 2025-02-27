package memoranda.ui.mainMenuCards;

import com.google.inject.Inject;
import memoranda.ui.mainMenuCards.homeSubMenuCards.ConfigureProject;
import memoranda.ui.mainMenuCards.homeSubMenuCards.Profile;
import memoranda.ui.mainMenuCards.homeSubMenuCards.Projects;

import javax.swing.*;
import javax.swing.JLabel;
import java.awt.*;

public class HomeToolBarCards extends JPanel{
    public CardLayout cardLayout;
    public JPanel cardPanel;
    public JPanel homeTitle;

    public static final String PROFILE_PANEL = "PROFILE";
    public static final String PROJECTS_PANEL = "PROJECTS";
    public static final String CONFIGURE_PANEL = "CONFIGURE";

    private Profile profile = new Profile();
    private Projects projects = new Projects();
    private ConfigureProject  configureProject = new ConfigureProject();

    private JPanel projectsPanel;
    private JPanel userProfilePanel;
    private JPanel configureProjectsPanel;
    public static JLabel homeTitleLabel = new JLabel("Welcome to Taidx!");


    @Inject
    public HomeToolBarCards(CardLayout newCardLayout, JPanel newCardPanel){
        cardLayout = newCardLayout;
        cardPanel = new JPanel(cardLayout);

        homeTitle = new JPanel(new BorderLayout());
        homeTitleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        homeTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        // Add padding around the title
        homeTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, UIManager.getColor("Button.darkShadow")));
        homeTitle.add(homeTitleLabel, BorderLayout.CENTER);


        userProfilePanel = createProfileCard();
        projectsPanel =createProjectsCard();
        configureProjectsPanel = createConfigureCard();

        cardPanel.add(userProfilePanel, PROFILE_PANEL);
        cardPanel.add(projectsPanel, PROJECTS_PANEL);
        cardPanel.add(configureProjectsPanel, CONFIGURE_PANEL);


        setLayout(new BorderLayout());
        add(homeTitle, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.WEST);
    }

    public void showCard(String cardName) {
        cardLayout.show(cardPanel, cardName);
    }

    private JPanel createProfileCard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(profile);
        return panel;
    }

    private JPanel createProjectsCard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(projects, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createConfigureCard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(configureProject, BorderLayout.CENTER);
        return panel;
    }

}
