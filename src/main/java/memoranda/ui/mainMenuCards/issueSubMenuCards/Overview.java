package memoranda.ui.mainMenuCards.issueSubMenuCards;

import memoranda.Start;
import memoranda.api.TaigaClient;
import memoranda.api.models.ProjectData;
import memoranda.util.TaigaJsonData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class Overview extends JPanel {

    private final TaigaClient taigaClient = Start.getInjector().getInstance(TaigaClient.class);
    public static JTabbedPane tabbedProjectPanel;
    public JPanel mainCenterPanel;
    private JPanel projectPanel[];

    public Overview() {

        setLayout(new BorderLayout());
        initProjectPanel();
        mainCenterPanel = new JPanel(new BorderLayout());
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

        mainCenterPanel.add(topPanel, BorderLayout.NORTH);

        // Center Panel for Issue List (Placeholder)
        JPanel centerPanel = new JPanel(new BorderLayout());
        JTextArea issueList = new JTextArea("There are no issues to report!");
        issueList.setEditable(false);
        issueList.setAlignmentX(CENTER_ALIGNMENT);
        centerPanel.add(issueList, BorderLayout.CENTER);

        // Add a scroll pane for the issue list
        JScrollPane scrollPane = new JScrollPane(issueList);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainCenterPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainCenterPanel, BorderLayout.CENTER);
    }

    public synchronized void initProjectPanel() {
        if (TaigaJsonData.doesConfigFileExists()) {
            if (projectPanel == null) {
                projectPanel = new JPanel[taigaClient.getProjectsList().size()];
            }
            if (taigaClient.getUserProfile() != null) {
                List<ProjectData> projects = taigaClient.getUserProfile().getProjectsList();
                if (tabbedProjectPanel != null) {
                    for (JPanel panel : projectPanel) {
                        panel.setVisible(false);
                        tabbedProjectPanel.remove(panel);
                    }
                } else {
                    tabbedProjectPanel = new JTabbedPane();
                }
                for (int i = 0; i < taigaClient.getProjectsList().size(); i++) {
                    ProjectData project = projects.get(i);
                    //projectPanel[i] = createProjectsCard(project);
                    //projectPanel[i].setVisible(true);
                    tabbedProjectPanel.addTab(project.getProjectName(), projectPanel[i]);
                }
            }
            this.add(tabbedProjectPanel, BorderLayout.NORTH);
        }
    }
}
