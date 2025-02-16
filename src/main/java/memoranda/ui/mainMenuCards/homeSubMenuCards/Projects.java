package memoranda.ui.mainMenuCards.homeSubMenuCards;

import memoranda.Start;
import memoranda.api.TaigaClient;
import memoranda.api.models.MilestoneData;
import memoranda.api.models.ProjectData;
import memoranda.api.models.TaskNode;
import memoranda.api.models.UserStoryNode;


import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Projects extends JPanel{
    public JTabbedPane tabbedPane = new JTabbedPane();
    private final int FONT_SIZE = 16;
    public Projects() {
        try {
            projectInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void projectInit() throws IOException, IOException {
        TaigaClient taigaClient = Start.getInjector().getInstance(TaigaClient.class);
        if (taigaClient.getUserProfile() != null) {
            List<ProjectData> projects = taigaClient.getUserProfile().getProjectsList();
            for (ProjectData project : projects) {
                tabbedPane.addTab(project.getProjectName(), createProjectsCard(project));
            }
        }
        this.add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createProjectsCard(ProjectData projectData) {
        JPanel panel = new JPanel(new GridLayout(10,1, 0,0));
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
}
