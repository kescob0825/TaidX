package memoranda.ui.mainMenuCards;

import memoranda.ui.mainMenuCards.homeSubMenuCards.ConfigureProject;
import memoranda.ui.mainMenuCards.homeSubMenuCards.Profile;

import javax.swing.*;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;

public class HomeToolBarCards extends JPanel{
    public CardLayout cardLayout;
    public JPanel cardPanel;
    public JPanel homeTitle;

    public static final String OVERVIEW_PANEL = "HOME_OVERVIEW";
    public static final String PROFILE_PANEL = "PROFILE";
    public static final String PROJECTS_PANEL = "PROJECTS";
    public static final String CONFIGURE_PANEL = "CONFIGURE";

    private JPanel centerWrapperOverview;
    Profile profile = new Profile();
    ConfigureProject  configureProject = new ConfigureProject();
    private JPanel centerWrapperProjects;
    private JPanel centerWrapperProfile;
    private JPanel centerWrapperConfigure;
    public static JLabel homeTitleLabel = new JLabel("Home");
    public HomeToolBarCards(CardLayout newCardLayout, JPanel newCardPanel){
        cardLayout = newCardLayout;
        cardPanel = new JPanel(cardLayout);

        homeTitle = new JPanel(new BorderLayout());
        homeTitleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        homeTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        // Add padding around the title
        homeTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, UIManager.getColor("Button.darkShadow")));
        homeTitle.add(homeTitleLabel, BorderLayout.CENTER);

        cardPanel.add(createOverviewCard(), OVERVIEW_PANEL);
        cardPanel.add(createProfileCard(), PROFILE_PANEL);
        cardPanel.add(createProjectsCard(), PROJECTS_PANEL);
        cardPanel.add(createConfigureCard(), CONFIGURE_PANEL);

        setLayout(new BorderLayout());
        add(homeTitle, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.WEST);
    }

    public void showCard(String cardName) {
        cardLayout.show(cardPanel, cardName);
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    private JPanel createOverviewCard() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel content = new JPanel();
        centerWrapperOverview = new JPanel(new GridBagLayout());
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        centerWrapperOverview = new JPanel(new GridBagLayout());
        centerWrapperOverview.add(content);

        panel.add(centerWrapperOverview, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createProfileCard() {
        JPanel panel = new JPanel(new BorderLayout());
        centerWrapperProfile = new JPanel(new GridBagLayout());
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        centerWrapperProfile.add(content);

        panel.add(profile, BorderLayout.WEST);
        return panel;
    }

    private JPanel createProjectsCard() {
        JPanel panel = new JPanel(new BorderLayout());
        centerWrapperProjects = new JPanel(new GridBagLayout());
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        centerWrapperProjects.add(content);

        panel.add(centerWrapperProjects, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createConfigureCard() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel subtitlePanel = new JPanel();
        centerWrapperConfigure = new JPanel(new GridBagLayout());
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        centerWrapperConfigure.add(content);

        panel.add(configureProject, BorderLayout.CENTER);

        return panel;
    }

    public void refreshPanels() {
        // TODO: centerWrapperOverview.refreshPanel();;
        profile.refreshPanel();
        //centerWrapperProjects.refreshPanel();
        //centerWrapperConfigure.refreshPanel();
    }
}
