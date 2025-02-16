package memoranda.ui.mainMenuCards;

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
        JPanel panel = new JPanel(new BorderLayout());

        // Top Panel for Filters, Search, and New Issue Button
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add some padding

        // Filters Button
        JButton filtersButton = new JButton("Filters");
        topPanel.add(filtersButton);
        topPanel.add(Box.createHorizontalStrut(5)); // Add some space

        // Search Bar
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, searchField.getPreferredSize().height));
        topPanel.add(searchField);
        topPanel.add(Box.createHorizontalStrut(5)); // Add some space

        // Tags Toggle
        JToggleButton tagsButton = new JToggleButton("Tags");
        topPanel.add(tagsButton);
        topPanel.add(Box.createHorizontalStrut(5)); // Add some space

        // New Issue Button
        JButton newIssueButton = new JButton("+ New Issue");
        topPanel.add(Box.createHorizontalGlue()); // Push button to the right
        topPanel.add(newIssueButton);

        panel.add(topPanel, BorderLayout.NORTH);

        // Center Panel for Issue List (Placeholder)
        JPanel centerPanel = new JPanel(new BorderLayout());
        JTextArea issueList = new JTextArea("There are no issues to report!");
        issueList.setEditable(false);
        issueList.setAlignmentX(CENTER_ALIGNMENT);
        centerPanel.add(issueList, BorderLayout.CENTER);

        // Add a scroll pane for the issue list
        JScrollPane scrollPane = new JScrollPane(issueList);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(centerPanel, BorderLayout.CENTER);

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
