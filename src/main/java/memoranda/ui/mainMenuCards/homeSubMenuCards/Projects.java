package memoranda.ui.mainMenuCards.homeSubMenuCards;

import memoranda.Start;
import memoranda.api.TaigaClient;
import memoranda.api.models.MilestoneData;
import memoranda.api.models.ProjectData;
import memoranda.api.models.TaskNode;
import memoranda.api.models.UserStoryNode;
import memoranda.ui.ExceptionDialog;
import memoranda.util.TaigaJsonData;
import memoranda.util.subscriber.Subscriber;


import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Projects extends JPanel implements Subscriber {
    public static JTabbedPane tabbedPaneProjects;
    private final int FONT_SIZE = 16;
    private final TaigaClient taigaClient = Start.getInjector().getInstance(TaigaClient.class);
    private JPanel panels[];
    public Projects() {

        try {
            projectInit();
            taigaClient.register(this);
        }
        catch (Exception ex) {
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
                if (tabbedPaneProjects != null) {
                    for (JPanel panel : panels) {
                        panel.setVisible(false);
                        tabbedPaneProjects.remove(panel);
                    }
                } else {
                    tabbedPaneProjects = new JTabbedPane();
                }
                for (int i = 0; i < taigaClient.getProjectsList().size(); i++) {
                    ProjectData project = projects.get(i);
                    panels[i] = createProjectsCard(project);
                    panels[i].setVisible(true);
                    tabbedPaneProjects.addTab(project.getProjectName(), panels[i]);
                }
            }
            this.add(tabbedPaneProjects, BorderLayout.CENTER);
        }
    }

    private JPanel createProjectsCard(ProjectData projectData) {
        JPanel panel = new JPanel(new GridLayout(10, 1));
        panel.add(createPanel(projectData.getProjectName(), "", 36, 1));
        panel.add(createPanel("Project ID: ",
                String.valueOf(projectData.getProjectId()), FONT_SIZE, 2));
        panel.add(createPanel("Description: ",
                projectData.getProjectDescription(), FONT_SIZE, 2));
        panel.add(createPanel("Project Owner: ",
                projectData.getProjectOwner(), FONT_SIZE, 2));
        panel.add(createPanel("Total Activity: ",
                String.valueOf(projectData.getTotalActivity()), FONT_SIZE, 2));
        panel.add(createPanel("Current Sprint: ",
                getCurrentMilestone(projectData.getProjectSprints()), FONT_SIZE, 2));
        panel.add(createPanel("Open User Stories: ",
                String.valueOf(getOpenUsSum(projectData.getProjectUserStoryList())), FONT_SIZE, 2));
        panel.add(createPanel("Closed User Stories: ", String.valueOf((projectData.getProjectUserStoryList().size()
                - getOpenUsSum(projectData.getProjectUserStoryList()))), FONT_SIZE, 2));
        panel.add(createPanel("Open Tasks: ",
                String.valueOf(getOpenTasksSum(projectData.getProjectTaskList())), FONT_SIZE, 2));
        panel.add(createPanel("Closed Tasks: ",
                String.valueOf((projectData.getProjectTaskList().size() -
                        getOpenTasksSum(projectData.getProjectTaskList()))), FONT_SIZE, 2));
        return panel;
    }

    private int getOpenUsSum(List<UserStoryNode> projectUserStoryList) {
        int openUSSum = 0;
        for (UserStoryNode us : projectUserStoryList) {
            if (us.getFinishDate().equals("null") ) {
                openUSSum++;
            }
        }
        return openUSSum;
    }

    private String getCurrentMilestone(List<MilestoneData> projectSprints) {
        String currentMilestone = "";
        for (MilestoneData sprint : projectSprints) {
            if (sprint.isMilestoneClosed() == false) {
                currentMilestone = sprint.getSprintName();
            }
        }
        return currentMilestone;
    }

    private int getOpenTasksSum(List<TaskNode> projectTaskList) {
        int openTaskSum = 0;
        for (TaskNode task : projectTaskList) {
            if (!Objects.equals(task.getTaskStatus(), "Closed")) {
                openTaskSum++;
            }
        }

        return openTaskSum;
    }

    private JPanel createPanel(String text, String subText, int size, int col) {
        JPanel panel = new JPanel(new GridLayout(1,col));
        JLabel label = new JLabel(text);
        JLabel textArea = new JLabel(subText);
        label.setFont(new Font("Arial", Font.BOLD, size));
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(label);
        panel.add(textArea);
        return panel;
    }

    @Override
    public void update() throws IOException {
        SwingUtilities.invokeLater(() -> {
            try {
                projectInit();
                for (JPanel panel : panels) {
                    panel.revalidate();
                    panel.repaint();
                }
                tabbedPaneProjects.revalidate();
                tabbedPaneProjects.repaint();
                this.revalidate();
                this.repaint();
            } catch (Exception e) {
                 e.printStackTrace();
            }
        });
    }
}
