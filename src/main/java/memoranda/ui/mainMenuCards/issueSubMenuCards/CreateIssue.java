package memoranda.ui.mainMenuCards.issueSubMenuCards;
import memoranda.Start;
import memoranda.api.TaigaClient;
import memoranda.api.models.*;
import memoranda.ui.ExceptionDialog;
import memoranda.util.TaigaJsonData;
import memoranda.util.subscriber.Subscriber;


import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CreateIssue extends JPanel {
    public static JTabbedPane projectTabs;
    private final int FONT_SIZE = 16;
    private final TaigaClient taigaClient = Start.getInjector().getInstance(TaigaClient.class);
    private JPanel panels[];

    public CreateIssue() {

        try {
            projectInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public synchronized void projectInit() {
        if (TaigaJsonData.doesConfigFileExists()) {
            if (panels == null) {
                panels = new JPanel[taigaClient.getProjectsList().size()];
            }
            if (taigaClient.getUserProfile() != null) {
                List<ProjectData> projects = taigaClient.getUserProfile().getProjectsList();
                if (projectTabs != null) {
                    for (JPanel panel : panels) {
                        panel.setVisible(false);
                        projectTabs.remove(panel);
                    }
                } else {
                    projectTabs = new JTabbedPane();
                    projectTabs.setPreferredSize(new Dimension(800, 500));
                }
                for (int i = 0; i < taigaClient.getProjectsList().size(); i++) {
                    ProjectData project = projects.get(i);
                    panels[i] = createProjectsCard(project);
                    panels[i].setVisible(true);
                    projectTabs.addTab(project.getProjectName(), panels[i]);
                }
            }
            this.add(projectTabs, BorderLayout.CENTER);
        }
    }

    private JPanel createProjectsCard(ProjectData projectData) {
        JPanel panel = new JPanel(new GridLayout(10, 1));
        //panel.setBackground(Color.RED);
        panel.setBorder(new EmptyBorder(10, 35, 10, 50));
        for (int i = 0; i < 10; i++) {
            JLabel label = new JLabel("Example: ");
            label.setFont(new Font("Arial", Font.BOLD, 15));
            JTextField textField = new JTextField(50);
            JPanel innerPanel = new JPanel(new BorderLayout());
            innerPanel.setBorder(new EmptyBorder(7, 20, 7, 20));
            innerPanel.add(label, BorderLayout.WEST);
            innerPanel.add(textField, BorderLayout.EAST);
            panel.add(innerPanel);
        }
        return panel;
    }
}