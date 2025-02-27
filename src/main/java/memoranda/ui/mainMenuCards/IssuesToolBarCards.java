package memoranda.ui.mainMenuCards;

import memoranda.ui.mainMenuCards.issueSubMenuCards.Overview;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class IssuesToolBarCards extends JPanel{
    public CardLayout cardLayout;
    public JPanel cardPanel;
    public JPanel homeTitle;

    public static final String ISSUE_OVERVIEW = "ISSUE_OVERVIEW";
    public static final String CREATE_ISSUE = "CREATE_ISSUE";
    public static final String CLOSE_ISSUE = "CLOSE_ISSUE";

    public IssuesToolBarCards(CardLayout newCardLayout, JPanel newCardPanel){
        cardLayout = newCardLayout;
        cardPanel = new JPanel(cardLayout);

        homeTitle = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Issues");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        // Add padding around the title
        homeTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, UIManager.getColor("Button.darkShadow")));
        homeTitle.add(titleLabel, BorderLayout.CENTER);

        cardPanel.add(createSPCard(), ISSUE_OVERVIEW);
        cardPanel.add(createPBCard(), CREATE_ISSUE);
        cardPanel.add(createSBCard(), CLOSE_ISSUE);

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

        Overview panel = new Overview();

        return panel;
    }

    private JPanel createPBCard() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel subtitlePanel = new JPanel();
        JLabel subtitleLabel = new JLabel("Create Issue:");
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
        JLabel subtitleLabel = new JLabel("Close Issue: ");
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
