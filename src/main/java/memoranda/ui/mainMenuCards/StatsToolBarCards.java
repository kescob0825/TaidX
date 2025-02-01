package memoranda.ui.mainMenuCards;

import javax.swing.*;
import java.awt.*;

public class StatsToolBarCards extends JPanel{
    public CardLayout cardLayout;
    public JPanel cardPanel;
    public JPanel homeTitle;

    public static final String STATS_OVERVIEW = "STATS_OVERVIEW";
    public static final String IND_STATS = "IND_STATS";
    public static final String TEAM_STATS = "TEAM_STATS";

    public StatsToolBarCards(CardLayout newCardLayout, JPanel newCardPanel){
        cardLayout = newCardLayout;
        cardPanel = new JPanel(cardLayout);

        homeTitle = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Stats");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        // Add padding around the title
        homeTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, UIManager.getColor("Button.darkShadow")));
        homeTitle.add(titleLabel, BorderLayout.CENTER);

        cardPanel.add(createSPCard(), STATS_OVERVIEW);
        cardPanel.add(createPBCard(), IND_STATS);
        cardPanel.add(createSBCard(), TEAM_STATS);

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

    private JPanel createSPCard() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel subtitlePanel = new JPanel();
        JLabel subtitleLabel = new JLabel("Stats Overview:");
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        subtitlePanel.add(subtitleLabel);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        centerWrapper.add(content);

        panel.add(subtitlePanel, BorderLayout.NORTH);
        panel.add(centerWrapper, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPBCard() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel subtitlePanel = new JPanel();
        JLabel subtitleLabel = new JLabel("Individual Stats:");
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        subtitlePanel.add(subtitleLabel);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        centerWrapper.add(content);

        panel.add(subtitlePanel, BorderLayout.NORTH);
        panel.add(centerWrapper, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSBCard() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel subtitlePanel = new JPanel();
        JLabel subtitleLabel = new JLabel("Team Stats: ");
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        subtitlePanel.add(subtitleLabel);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        centerWrapper.add(content);

        panel.add(subtitlePanel, BorderLayout.NORTH);
        panel.add(centerWrapper, BorderLayout.CENTER);
        return panel;
    }

    public void refreshPanels() {
        //  TODO: JPanel.refreshPanel();
    }
}
