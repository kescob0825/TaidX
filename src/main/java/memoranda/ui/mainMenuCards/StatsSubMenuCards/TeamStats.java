package memoranda.ui.mainMenuCards.StatsSubMenuCards;

import memoranda.Start;
import memoranda.api.TaigaClient;
import memoranda.api.models.*;
import memoranda.ui.App;
import memoranda.ui.mainMenuCards.homeSubMenuCards.ConfigureProject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TeamStats extends JPanel {

    private static final int NUM_NON_TEAM_MEMBERS = 3;
    private static TaigaClient client;
    public final static int NULL_VAL = 100;
    private static java.util.List<ProjectData> projectList;
    private static List<UserStoryNode> userStoryList;
    private static List<MilestoneData> sprint;
    // Placeholder Data from the actual Taiga website,
    // will use this until backend is fully implemented
    private static final Map<String, Integer> collaboratorPoints = new HashMap<>();
    private static final Map<String, Integer> collaboratorPointsTest = new HashMap<>();

    private static LeftPanel leftPanel;
    private static CenterPanel centerPanel;
    private static RightPanel rightPanel;

    public TeamStats() {

        try {
            client = Start.getInjector().getInstance(TaigaClient.class);
            projectList = client.getProjectsList();
            userStoryList = client.getUserStories();
            populateMap();
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(1400, 500));

            leftPanel = new LeftPanel();
            centerPanel = new CenterPanel();
            rightPanel = new RightPanel();
        }
        catch (IOException ioe) {
            ioe.fillInStackTrace();
        }
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }

    private void populateMap() {
        collaboratorPoints.put("jdlafond", 21);
        collaboratorPoints.put("kescob12", 6);
        collaboratorPoints.put("edwinbmv", 8);
        collaboratorPoints.put("Jimmy Anderson", 4);
        collaboratorPoints.put("t36222", 5);
        collaboratorPoints.put("Mohammed Abueida", 2);
        collaboratorPointsTest.put("jdlafond", 0);
        collaboratorPointsTest.put("kescob12", 0);
        collaboratorPointsTest.put("edwinbmv", 0);
        collaboratorPointsTest.put("Jimmy Anderson", 0);
        collaboratorPointsTest.put("t36222", 0);
        collaboratorPointsTest.put("Mohammed Abueida", 0);
    }

    private static int getOpenUsSum(List<UserStoryNode> projectUserStoryList) {
        int openUSSum = 0;
        for (UserStoryNode us : projectUserStoryList) {
            if (us.getFinishDate().equals("null") ) {
                openUSSum++;
            }
        }
        return openUSSum;
    }

    private static String getCurrentMilestone(List<MilestoneData> projectSprints) {
        String currentMilestone = "";
        for (MilestoneData sprint : projectSprints) {
            if (sprint.isMilestoneClosed() == false) {
                currentMilestone = sprint.getSprintName();
            }
        }
        return currentMilestone;
    }

    private static int getOpenTasksSum(List<TaskNode> projectTaskList) {
        int openTaskSum = 0;
        for (TaskNode task : projectTaskList) {
            if (!Objects.equals(task.getTaskStatus(), "Closed")) {
                openTaskSum++;
            }
        }

        return openTaskSum;
    }

    private static class LeftPanel extends JPanel {

        JButton[] buttons = new JButton[20];
        public final int LEFT_PANEL_WIDTH = 300;
        JButton newProjectB = new JButton("+ New Project");
        JLabel projectsTitleH = new JLabel("All Projects");
        JPanel projectsTitleP = new JPanel();
        JPanel projectsAllP = new JPanel(new GridLayout(20, 1));

        private LeftPanel() {

            setBackground(Color.RED);
            setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, NULL_VAL));
            setLayout(new BorderLayout());

            projectsTitleH.setFont(new Font("Arial", Font.BOLD, 23));
            projectsTitleP.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, 35));
            projectsTitleP.add(projectsTitleH);
            //projectsTitleP.setBackground(Color.GREEN);

            newProjectB.addActionListener(this::newProjectB_actionPerformed);

            projectsAllP.setBorder(new EmptyBorder(20, 15, 0, 15));
            int i = 0;
            for (ProjectData data : projectList) {
                String name = data.getProjectName();
                JButton dataB = new JButton(name);
                dataB.setFont(new Font("Arial", Font.PLAIN, 15));
                dataB.addActionListener( e -> {
                    projectB_actionPerformed(e, name);
                });
                buttons[i++] = dataB;
                projectsAllP.add(dataB);
            }
            if (projectList.size() < 20) {
                newProjectB.setFont(new Font("Arial", Font.PLAIN, 15));
                projectsAllP.add(newProjectB);
            }
            //projectsAllP.setBackground(Color.BLACK);

            add(projectsTitleP, BorderLayout.NORTH);
            add(projectsAllP, BorderLayout.CENTER);
        }

        private void projectB_actionPerformed(ActionEvent e, String name) {

            try {
                projectList = client.getProjectsList();
                userStoryList = client.getUserStories();
            }
            catch (IOException ioe) {
                ioe.fillInStackTrace();
            }
            ProjectData target = null;
            for (ProjectData data: projectList) {
                if (data.getProjectName().equals(name)) {
                    target = data;
                    break;
                }
            }
            if (target == null)
                return;
            centerPanel.changeProject(target);
            rightPanel.changeProject(target);
        }

        private void newProjectB_actionPerformed(ActionEvent e) {
            ConfigureProject config = new ConfigureProject();
            JDialog configureProjectDlg = new JDialog(App.getFrame());
            configureProjectDlg.add(config);
            configureProjectDlg.pack();
            int x = App.getFrame().getX() + (App.getFrame().getWidth() - configureProjectDlg.getWidth()) / 2;
            int y = App.getFrame().getY() + (App.getFrame().getHeight() - configureProjectDlg.getHeight()) / 2;
            configureProjectDlg.setLocation(x, y);
            configureProjectDlg.setLocationRelativeTo(App.getFrame());
            SwingUtilities.invokeLater(() -> {
                try {
                    configureProjectDlg.setAlwaysOnTop(true);
                    configureProjectDlg.setVisible(true);
                } catch (Exception ex) {
                    ex.fillInStackTrace();
                }
            });
        }
    }

    private static class CenterPanel extends JPanel {

        JLabel titleH = new JLabel("Information");
        JPanel titleP = new JPanel();

        JLabel projectOwnerH = new JLabel();
        JPanel projectOwnerP = new JPanel(new BorderLayout());

        JLabel isPrivateH = new JLabel();
        JPanel isPrivateP = new JPanel(new BorderLayout());

        JLabel currentSprintH = new JLabel();
        JPanel currentSprintP = new JPanel(new BorderLayout());

        JLabel numOfUSH = new JLabel();
        JPanel numOfUSP = new JPanel(new BorderLayout());

        JLabel projectDescH = new JLabel();
        JPanel projectDescP = new JPanel(new BorderLayout());

        JPanel descP = new JPanel(new GridLayout(5, 1));
        JPanel topPanel = new JPanel(new BorderLayout());

        private CenterPanel() {

            //setBackground(Color.WHITE);
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(1000, NULL_VAL));
            if (projectList == null || projectList.isEmpty()) {
                return;
            }
            ProjectData data = projectList.get(0);
            Font descFront = new Font("Arial", Font.PLAIN, 18);

            titleH.setFont(new Font("Arial", Font.BOLD, 23));
            titleP.setPreferredSize(new Dimension(NULL_VAL, 35));
            //titleP.setBackground(Color.BLUE);
            titleP.add(titleH);

            isPrivateH.setFont(descFront);
            isPrivateP.add(isPrivateH);
            descP.add(isPrivateP, BorderLayout.WEST);

            projectOwnerH.setFont(descFront);
            projectOwnerP.add(projectOwnerH);
            descP.add(projectOwnerP, BorderLayout.WEST);

            currentSprintH.setFont(descFront);
            currentSprintP.add(currentSprintH);
            descP.add(currentSprintP, BorderLayout.WEST);

            numOfUSH.setFont(descFront);
            numOfUSP.add(numOfUSH);
            descP.add(numOfUSP, BorderLayout.WEST);
            //descP.setBackground(Color.RED);

            projectDescH.setFont(descFront);
            projectDescP.add(projectDescH);
            descP.add(projectDescP, BorderLayout.WEST);


            //topPanel.setBackground(Color.GREEN);
            descP.setBorder(new EmptyBorder(30, 20, 20, 0));
            topPanel.setPreferredSize(new Dimension(NULL_VAL, 250));
            topPanel.add(titleP, BorderLayout.NORTH);
            topPanel.add(descP, BorderLayout.CENTER);

            add(topPanel, BorderLayout.NORTH);
            titleH.setText(data.getProjectName());
            projectOwnerH.setText("Owner: " + data.getProjectOwner());
            isPrivateH.setText((data.isPrivate())? "Private" : "Public");
            projectDescH.setText("Description: " + data.getProjectDescription());
            currentSprintH.setText("Current Sprint: ");
            numOfUSH.setText(getOpenUsSum(data.getProjectUserStoryList()) + " User Stories");
            if (data.getProjectSprints() == null || data.getProjectSprints().isEmpty()) {
                return;
            }
            currentSprintH.setText("Current Sprint: " +
                    data.getProjectSprints().get(0).getSprintName());
            numOfUSH.setText(data.getProjectSprints().get(0).getNumUserStories() + " User Stories");
        }

        public void changeProject(ProjectData target) {

            titleH.setText(target.getProjectName());
            projectOwnerH.setText("Owner: " + target.getProjectOwner());
            isPrivateH.setText((target.isPrivate())? "Private" : "Public");
            projectDescH.setText("Description: " + target.getProjectDescription());
            try {
                currentSprintH.setText("Current Sprint: " +
                        target.getProjectSprints().get(0).getSprintName());
                numOfUSH.setText(getOpenUsSum(target.getProjectUserStoryList()) +
                        " User Stories");
            }
            catch (IndexOutOfBoundsException ioob) {
                currentSprintH.setText("Current Sprint: Sprint 1");
                numOfUSH.setText(getOpenUsSum(target.getProjectUserStoryList()) +
                        " User Stories");
                ioob.fillInStackTrace();
            }
        }
    }
    private static class RightPanel extends JPanel {

        public final int RIGHT_PANEL_WIDTH = 300;
        JLabel titleH = new JLabel("Contribution");
        JPanel titleP = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel collaboratorPanel = new JPanel(new GridLayout(20, 1));
        double upperBound;
        double lowerBound;
        private RightPanel() {

            setLayout(new BorderLayout());
            //setBackground(Color.GREEN);

            titleH.setFont(new Font("Arial", Font.BOLD, 23));
            titleH.setPreferredSize(new Dimension(RIGHT_PANEL_WIDTH, 25));
            titleP.setBorder(new EmptyBorder(13, 60, 0, 0));
            titleP.add(titleH);
            //titleP.setBackground(Color.RED);
            topPanel.add(titleP, BorderLayout.NORTH);
            collaboratorPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
            add(topPanel, BorderLayout.NORTH);
            if (projectList.isEmpty()) {
                return;
            }
            int i = 0;
            ProjectData data = projectList.get(0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (data.getProjectSprints().isEmpty()) {
                return;
            }
            MilestoneData sprint = data.getProjectSprints().get(0);
            //LocalDate startDate = LocalDate.parse(sprint.getSprintEstStart(), formatter);
            LocalDate endDate = LocalDate.parse(sprint.getSprintEstFinish(), formatter);
            LocalDate currentDate = LocalDate.now();
            //long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
            long daysLeft = ChronoUnit.DAYS.between(currentDate, endDate);
            double averagePoints = (double)sprint.getTotal_sprint_points()
                    / (daysLeft*(data.getMemberIds().length - NUM_NON_TEAM_MEMBERS));
            upperBound = averagePoints + (averagePoints * 0.30);
            lowerBound = averagePoints - (averagePoints * 0.30);
            populateCollaborators(data, collaboratorPoints);
        }

        public void changeProject(ProjectData target) {
            remove(collaboratorPanel);
            collaboratorPanel = new JPanel(new GridLayout(20, 1));
            collaboratorPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
            add(collaboratorPanel);

            if (!target.getProjectName().equals("Ruebezahl_spring25A"))
                populateCollaborators(target, collaboratorPointsTest);
            else populateCollaborators(target, collaboratorPoints);

        }
        private void populateCollaborators(ProjectData target, Map<String, Integer> map) {

            for (String username : map.keySet()) {
                String contribution;
                if (map.get(username) > upperBound)
                    contribution = ": Over-Contribution";
                else if (map.get(username) < lowerBound)
                    contribution = ": Under-Contribution";
                else contribution = ": Good-Contribution";
                JLabel memberIDH = new JLabel(username + contribution);
                JPanel memberIDP = new JPanel(new BorderLayout());
                memberIDP.add(memberIDH, BorderLayout.WEST);
                memberIDH.setFont(new Font("Arial", Font.PLAIN, 18));
                collaboratorPanel.add(memberIDP);
                //memberIDP.setBackground(Color.RED);
            }
        }
    }
}
