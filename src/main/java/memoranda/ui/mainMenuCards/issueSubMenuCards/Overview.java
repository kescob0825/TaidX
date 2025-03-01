package memoranda.ui.mainMenuCards.issueSubMenuCards;

import memoranda.Start;
import memoranda.api.TaigaClient;
import memoranda.api.models.IssuesData;
import memoranda.api.models.ProjectData;
import memoranda.api.models.issueattributes.IssuePriority;
import memoranda.api.models.issueattributes.IssueSeverity;
import memoranda.api.models.issueattributes.IssueStatus;
import memoranda.api.models.issueattributes.IssueType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

public class Overview extends JPanel {

    private final TaigaClient taigaClient = Start.getInjector().getInstance(TaigaClient.class);
    private final List<ProjectData> projects;
    public JPanel mainCenterPanel;

    public Overview() {

        mainCenterPanel = new JPanel(new BorderLayout());
        projects = taigaClient.getProjectsList();

        setLayout(new BorderLayout());
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
        tagsButton.addActionListener(this::tagsButton);
        topPanel.add(tagsButton);
        topPanel.add(Box.createHorizontalStrut(5)); // Add some space

        // New Issue Button
        JButton newIssueButton = new JButton("+ New Issue");
        topPanel.add(Box.createHorizontalGlue()); // Push button to the right
        topPanel.add(newIssueButton);

        mainCenterPanel.add(topPanel, BorderLayout.NORTH);

        // Center Panel for Issue List (Placeholder)
        JPanel centerPanel = new JPanel(new BorderLayout());
        DefaultTableModel tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] columnNames = {"Subject", "Assigned To", "Status"};
        tableModel.setColumnIdentifiers(columnNames);

        try {
            List<IssuesData> allIssues = projects.stream()
                    .flatMap(p -> p.getProjectIssuesList().stream())
                    .collect(Collectors.toList());

            if (allIssues.isEmpty()) {
                tableModel.addRow(new Object[]{"There are no issues to report!", "", ""});
            } else {
                for (IssuesData issue : allIssues) {
                    tableModel.addRow(new Object[]{
                        issue.getSubject(),
                        issue.getProjectName(),
                        issue.getStatus()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            tableModel.addRow(new Object[]{"Error loading issues: " + e.getMessage(), "", ""});
        }

        JTable issueTable = new JTable(tableModel);
        issueTable.setAutoCreateRowSorter(true); 
        issueTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(issueTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainCenterPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainCenterPanel, BorderLayout.CENTER);
    }

    private void tagsButton(ActionEvent e) {

        for (ProjectData project:projects) {

            System.out.println("ProjectName: " + project.getProjectName());
            List<IssuesData> issues = project.getProjectIssuesList();

            if (issues == null || issues.isEmpty()) {
                break;
            }

            int i = 0;
            for (IssuesData issue: issues) {
                System.out.println("Issue Name " + i + ": " + issue.getSubject());
                System.out.println("Status: " + issue.getStatus());
                System.out.println("Priority: " + issue.getPriority());
                System.out.println("Severity: " + issue.getSeverity());
                System.out.println("Type: " + issue.getType());
                i++;
            }


        }
    }
}
