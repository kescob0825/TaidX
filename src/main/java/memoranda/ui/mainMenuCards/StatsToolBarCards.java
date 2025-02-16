package memoranda.ui.mainMenuCards;

import memoranda.ui.mainMenuCards.StatsSubMenuCards.TeamStats;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StatsToolBarCards extends JPanel{
    private String loggedInUserName;
    public CardLayout cardLayout;
    public JPanel cardPanel;
    public JPanel homeTitle;
    public static final String STATS_OVERVIEW = "STATS_OVERVIEW";
    public static final String IND_STATS = "IND_STATS";
    public static final String TEAM_STATS = "TEAM_STATS";
    private List<TeamMember> teamMembers = new ArrayList<>();
    private static final TeamStats teamStats = new TeamStats();
    // Inner class to display team stats
    private static class TeamMember {
        String name;
        String uxRole;
        int closedIssues;
        int iocaineDoses;
        int wikiPages;
        int issuesReported;
        int tasksClosed;
        int totalPoints;

    //True value when you highlight the tab
        public TeamMember(String name, String uxRole, int closedIssues, int iocaineDoses, int wikiPages, int issuesReported, int tasksClosed, int totalPoints) {
            this.name = name;
            this.uxRole = uxRole;
            this.closedIssues = closedIssues;
            this.iocaineDoses = iocaineDoses;
            this.wikiPages = wikiPages;
            this.issuesReported = issuesReported;
            this.tasksClosed = tasksClosed;
            this.totalPoints = totalPoints;
        }
    }

    public StatsToolBarCards(CardLayout newCardLayout, JPanel newCardPanel, String loggedInUserName){
        //focus only on username ^^
        this.loggedInUserName = loggedInUserName;

        cardLayout = newCardLayout;
        cardPanel = new JPanel(cardLayout);

        homeTitle = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Stats");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        // Add padding around the title
        homeTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, UIManager.getColor("Button.darkShadow")));
        homeTitle.add(titleLabel, BorderLayout.CENTER);

        // Populate team members
        populateTeamMembers();

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

        // Column headers
        String[] columnNames = {"Team", "Iocaine Drinker", "Cervantes", "Bug Hunter", "Night Shift", "Total Power"};

        // Data for the table
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (TeamMember member : teamMembers) {
            model.addRow(new Object[]{
                    member.name + " - " + member.uxRole,
                    member.iocaineDoses,
                    member.wikiPages,
                    member.issuesReported,
                    member.tasksClosed,
                    member.totalPoints
            });
        }

        // Create the table
        JTable table = new JTable(model);

        // Set column widths (optional, adjust as needed)
        table.getColumnModel().getColumn(0).setPreferredWidth(150); // Team column
        for (int i = 1; i < columnNames.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(80); // Other columns
        }

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(subtitlePanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPBCard() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel subtitlePanel = new JPanel();
        JLabel subtitleLabel = new JLabel("Individual Stats:");
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        subtitlePanel.add(subtitleLabel);
        panel.add(subtitlePanel, BorderLayout.NORTH);

        //columsn
        String[] columnNames = {"Name", "Tasks In Progress", "Tasks Closed", "User Stories Assigned", "Total Points"};
        DefaultTableModel individualModel = new DefaultTableModel(columnNames, 0);

        for (TeamMember member : teamMembers) {
            if (member.name.equalsIgnoreCase(loggedInUserName)) {
                individualModel.addRow(new Object[]{
                    member.name,
                    member.issuesReported,
                    member.tasksClosed,
                    member.wikiPages,
                    member.totalPoints
                });
            }
        }

        JTable individualTable = new JTable(individualModel);
        JScrollPane individualScrollPane = new JScrollPane(individualTable);
        //table style
        individualTable.setRowHeight(30);
        individualTable.setFont(new Font("Serif", Font.PLAIN, 16));
        individualTable.setGridColor(Color.LIGHT_GRAY);
        individualTable.setShowGrid(true);
        individualTable.setSelectionBackground(new Color(173, 216, 230));
        individualTable.setSelectionForeground(Color.BLACK);
        //table header
        JTableHeader header = individualTable.getTableHeader();
        header.setBackground(new Color(70, 130, 180)); // steel blue
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        //custom cell renderer
        individualTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(new Color(230, 240, 255));
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        });
        // highlights color for total points plus logic
        TableColumn pointsCol = individualTable.getColumnModel().getColumn(4);
        pointsCol.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                //if not selected and points > 50, change color
                if (value instanceof Number && !isSelected) {
                    int points = ((Number) value).intValue();
                    if (points > 50) {
                        c.setBackground(new Color(255, 240, 240)); // faint red
                    } else {
                        //revert
                        if (row % 2 == 0) {
                            c.setBackground(new Color(230, 240, 255));
                        } else {
                            c.setBackground(Color.WHITE);
                        }
                    }
                }
                return c;
            }
        });

        individualTable.setPreferredScrollableViewportSize(new Dimension(600, 200));
        panel.add(individualScrollPane, BorderLayout.CENTER);

        return panel;

    }
    private JPanel createSBCard() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel subtitlePanel = new JPanel();
        JLabel subtitleLabel = new JLabel("Team");
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        subtitlePanel.add(subtitleLabel);

        panel.add(subtitlePanel, BorderLayout.NORTH);
        panel.add(teamStats, BorderLayout.CENTER);
        return panel;
    }

    private Component createSpacer(int width) {
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(width, 1));
        spacer.setOpaque(false); // Make it transparent
        return spacer;
    }
    //dumby method for populating the members, will add API functionality where it will iterate through for loops and display the stats for team members
    private void populateTeamMembers() {
        teamMembers.add(new TeamMember("Jimmy Anderson", "UX", 12, 3, 5, 8, 20, 48));
        teamMembers.add(new TeamMember("Alexandra Mehlhase", "Product Owner", 5, 1, 2, 4, 10, 22));
        teamMembers.add(new TeamMember("Mohammed Abueida", "UX", 8, 2, 4, 6, 15, 35));
        teamMembers.add(new TeamMember("Noel", "UX", 3, 0, 1, 2, 5, 11));
        teamMembers.add(new TeamMember("SER", "UX", 7, 1, 3, 5, 12, 28));
        teamMembers.add(new TeamMember("edwinbmv", "UX", 15, 4, 7, 10, 25, 61));
        teamMembers.add(new TeamMember("jdlafond", "UX", 20, 5, 10, 12, 30, 77));
        teamMembers.add(new TeamMember("kescob12", "UX", 10, 2, 5, 7, 18, 42));
        teamMembers.add(new TeamMember("t36222", "UX", 6, 1, 2, 4, 11, 2000));
    }

    private void populateIndividual() {

    }
    public void refreshPanels() {
        //  TODO: JPanel.refreshPanel();
    }
}
