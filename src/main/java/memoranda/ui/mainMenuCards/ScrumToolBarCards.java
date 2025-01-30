package memoranda.ui.mainMenuCards;

import javax.swing.*;
import java.awt.*;

public class ScrumToolBarCards extends JPanel{
    public CardLayout cardLayout;
    public JPanel cardPanel;
    public JPanel homeTitle;

    public static final String SPRINT_PANEL = "SPRINT";
    public static final String BACKLOG_PANEL = "BACKLOG";
    public static final String BOARD_PANEL = "BOARD";

    public ScrumToolBarCards(CardLayout newCardLayout, JPanel newCardPanel){
        cardLayout = newCardLayout;
        cardPanel = new JPanel(cardLayout);

        homeTitle = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Scrum");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        // Add padding around the title
        homeTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, UIManager.getColor("Button.darkShadow")));
        homeTitle.add(titleLabel, BorderLayout.CENTER);

        cardPanel.add(createSPCard(), SPRINT_PANEL);
        cardPanel.add(createPBCard(), BACKLOG_PANEL);
        cardPanel.add(createSBCard(), BOARD_PANEL);

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
        JLabel subtitleLabel = new JLabel("Sprint Planning:");
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
        JLabel subtitleLabel = new JLabel("Product BackLog:");
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
        JLabel subtitleLabel = new JLabel("ScrumBoard: ");
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


}
