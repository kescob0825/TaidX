package memoranda.ui.mainMenuCards;

import javax.swing.*;
// import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import memoranda.Start;
import memoranda.api.TaigaClient;
import memoranda.api.models.*;
import memoranda.ui.ExceptionDialog;
import memoranda.util.subscriber.Subscriber;
// import org.checkerframework.common.returnsreceiver.qual.This;
// import memoranda.api.modules.TaigaProject;
// import memoranda.ui.App;

import java.awt.*;
// import java.awt.datatransfer.DataFlavor;
// import java.awt.datatransfer.StringSelection;
// import java.awt.datatransfer.Transferable;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ScrumToolBarCards extends JPanel implements Subscriber {
    public CardLayout cardLayout;
    public JPanel cardPanel;
    public JPanel homeTitle;
    private final ConcurrentHashMap<String, TaskNode> addedTasks = new ConcurrentHashMap<>();
    private TaigaClient taigaClient;

    public static final String BACKLOG_PANEL = "BACKLOG";
    public static final String BOARD_PANEL = "BOARD";
    private JTabbedPane  tabbedPane;
    private JScrollPane scrollPane;
    private JPanel panel;

    public ScrumToolBarCards(CardLayout newCardLayout, JPanel newCardPanel) throws IOException {
        taigaClient = Start.getInjector().getInstance(TaigaClient.class);
        cardLayout = newCardLayout;
        cardPanel = new JPanel(cardLayout);
        tabbedPane =  new JTabbedPane();
        homeTitle = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Scrum");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel = new JPanel(new BorderLayout());
        // Add padding around the title
        homeTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, UIManager.getColor("Button.darkShadow")));
        homeTitle.add(titleLabel, BorderLayout.CENTER);

        cardPanel.add(createPBCard(), BACKLOG_PANEL);
        cardPanel.add(createSBCard(), BOARD_PANEL);

        setLayout(new BorderLayout());
        add(homeTitle, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);
        taigaClient.register(this);
    }

    public void showCard(String cardName) {
        cardLayout.show(cardPanel, cardName);
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    // ************** The methods below work on the product Backlog tab *************** //

    /**
     * This method creates the panel for the product backlog.
     * @return panel
          * @throws IOException 
          */
         private JPanel createPBCard() throws IOException {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(5, 5, 5, 5)); // Add a small border for padding

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Backlog");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);

        // Top Panel (Search, Filters, and Add User Story Button)
        JPanel topPanel = new JPanel(new BorderLayout());

        // Search and Filters Panel
        JPanel searchFilterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton filtersButton = new JButton("Filters");
        JTextField searchField = new JTextField(20);
        searchField.setText("Enter Backlog information");
        JButton tagsButton = new JButton("Tags");
        searchFilterPanel.add(filtersButton);
        searchFilterPanel.add(searchField);
        searchFilterPanel.add(tagsButton);

        // Add User Story Button
        JPanel addUserStoryPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addUserStoryButton = new JButton("+ USER STORY");
        addUserStoryPanel.add(addUserStoryButton);

        topPanel.add(searchFilterPanel, BorderLayout.WEST);
        topPanel.add(addUserStoryPanel, BorderLayout.EAST);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"","PROJECT", "ID", "USER STORY", "STATUS", ""}; // Added columns and empty columns for spacing/icons
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };
        JTable backlogTable = new JTable(model);

        // Customize table appearance
        backlogTable.setShowGrid(false); // Hide grid lines
        backlogTable.setRowHeight(30); // Set a comfortable row height

        List<Object[]> data = getBacklogData();
        for (Object[] row : data) {
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(backlogTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(topPanel, BorderLayout.PAGE_START);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    
    private List<Object[]> getBacklogData() throws IOException {
        List<Object[]> data = new ArrayList<>();

        TaigaClient taigaClient = Start.getInjector().getInstance(TaigaClient.class);
        List<ProjectData> projects = taigaClient.getProjectsList();

        try {
            for (ProjectData project : projects) {
                for (UserStoryNode userStory : project.getProjectUserStoryList()) {
                    if (userStory.getMilestoneId() == 0) {
                        data.add(new Object[]{
                            "\u22EE", project.getProjectName(), project.getProjectId(),
                            "#" + userStory.getRefNumber() + " " + userStory.getUserStorySubject(),
                            userStory.getStatus(),
                            "\u2056"
                        });
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return data;
    }


    // *************** Methods below here works on the ScrumBoard Panel in the Scrum Board tab *************** //

    /**
     * This method creates the panel for the scrum board.
     * @return panel
     */
    private JScrollPane createSBCard() throws IOException {
        if (tabbedPane.getTabCount() > 0) {
            tabbedPane.removeAll();
            panel.removeAll();
        }
        for (ProjectData project : taigaClient.getProjectsList()) {
            tabbedPane.addTab(project.getProjectName(), createScrumBoard(project));
        }
        panel.add(tabbedPane, BorderLayout.CENTER);
        scrollPane = new JScrollPane(panel);
        return scrollPane;
    }

    /**
     * This method works with the creation of the panel for the columns.
     * a vertical space is left for the Milestone Data
     * @return scrollPane
     */
    private JScrollPane createScrumBoard(ProjectData project) throws IOException {
        JPanel boardPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //This portion deals the size for the Milestone Data table
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        boardPanel.add(createMilestoneTableCard(), gbc);


        //This portion deals with the size for the Scrum Board panel
        gbc.insets = new Insets(20,5,5,5);
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        String[] columns = {"USER STORY", "NEW", "IN PROGRESS", "READY FOR TEST", "CLOSED", "NEEDS INFO"};
        for (int i = 0; i < columns.length; i++) {
            gbc.gridx = i;
            JPanel columnContent;
            if(columns[i].equals("USER STORY")) {
                columnContent = createUserStoryColumn(project);
            } else {
                columnContent = createColumPanel(project, columns[i]);
            }

            JScrollPane columnScroll = new JScrollPane(columnContent);
            columnScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            columnScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            columnScroll.setBorder(BorderFactory.createTitledBorder(columns[i]));

            columnScroll.setPreferredSize(new Dimension(200, 400));

            boardPanel.add(columnScroll, gbc);
        }

        JScrollPane mainScrollPane = new JScrollPane(boardPanel);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return mainScrollPane;
    }

    /**
     * This method works with the column for User Stories only.
     * @return userStoryPanel
     */
    private JPanel createUserStoryColumn(ProjectData project){
        JPanel userStoryPanel = new JPanel();
        userStoryPanel.setLayout(new BoxLayout(userStoryPanel, BoxLayout.Y_AXIS));
        userStoryPanel.setBorder(BorderFactory.createTitledBorder("USER STORY"));
        userStoryPanel.setBackground(new Color(240, 240, 240));

        MilestoneData currentMilestone = null;
        for (MilestoneData milestone : project.getProjectSprints()) {
            if (!milestone.isMilestoneClosed()) {
                currentMilestone = milestone;
                break;
            }
        }
        if (currentMilestone == null) {
            userStoryPanel.add(createUSCard(0,"No User Stories"));
            return userStoryPanel;
        }
        for (UserStoryNode userStory : project.getProjectUserStoryList()) {
            if (userStory.getMilestoneId() != currentMilestone.getMilestone_id()) {
                continue;
            }
            userStoryPanel.add(createUSCard(userStory.getRefNumber(), userStory.getUserStorySubject()));
        }

        userStoryPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return userStoryPanel;
    }

    /**
     * This method works with the columns for the tasks
     * @return panel
     */
    private JPanel createColumPanel(ProjectData project, String title){
        addedTasks.clear();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setBackground(new Color(240, 240, 240));

        MilestoneData currentMilestone = null;
        for (MilestoneData milestone : project.getProjectSprints()) {
            if (!milestone.isMilestoneClosed()) {
                currentMilestone = milestone;
                break;
            }
        }
        if (currentMilestone == null) {
            panel.add(createUSCard(0,"No Tasks"));
            return panel;
        }

        //populate column
        switch (title) {
            case "NEW" -> {
                for (UserStoryNode userStory : project.getProjectUserStoryList()) {
                    for (TaskNode task : userStory.getUserStoryTaskList()) {
                        String taskKey = task.getTaskId() + "-" + task.getMilestoneId();
                        if (addedTasks.putIfAbsent(taskKey, task) == null) {
                            if (task.getTaskStatus().equals("New") &&
                                    currentMilestone.getMilestone_id() == task.getMilestoneId()) {
                                panel.add(createTaskCard(task));
                            }
                        }
                    }
                }
            }
            case "IN PROGRESS" -> {
                for (UserStoryNode userStory : project.getProjectUserStoryList()) {
                    for (TaskNode task : userStory.getUserStoryTaskList()) {
                        String taskKey = task.getTaskId() + "-" + task.getMilestoneId();
                        if (addedTasks.putIfAbsent(taskKey, task) == null) {
                            if (task.getTaskStatus().equals("In progress") &&
                                    currentMilestone.getMilestone_id() == task.getMilestoneId()) {
                                panel.add(createTaskCard(task));
                            }
                        }
                    }
                }
            }
            case "READY FOR TEST" -> {
                for (UserStoryNode userStory : project.getProjectUserStoryList()) {
                    for (TaskNode task : userStory.getUserStoryTaskList()) {
                        String taskKey = task.getTaskId() + "-" + task.getMilestoneId();
                        if (addedTasks.putIfAbsent(taskKey, task) == null) {
                            if (task.getTaskStatus().equals("Ready for test") &&
                                    currentMilestone.getMilestone_id() == task.getMilestoneId()) {
                                panel.add(createTaskCard(task));
                            }
                        }
                    }
                }
            }
            case "CLOSED" -> {
                for (UserStoryNode userStory : project.getProjectUserStoryList()) {
                    for (TaskNode task : userStory.getUserStoryTaskList()) {
                        String taskKey = task.getTaskId() + "-" + task.getMilestoneId();
                        if (addedTasks.putIfAbsent(taskKey, task) == null) {
                            if (task.getTaskStatus().equals("Closed") &&
                                    currentMilestone.getMilestone_id() == task.getMilestoneId()) {
                                panel.add(createTaskCard(task));
                            }
                        }
                    }
                }
            }
            case "NEEDS INFO" -> {
                for (UserStoryNode userStory : project.getProjectUserStoryList()) {
                    for (TaskNode task : userStory.getUserStoryTaskList()) {
                        String taskKey = task.getTaskId() + "-" + task.getMilestoneId();
                        if (addedTasks.putIfAbsent(taskKey, task) == null) {
                            if (task.getTaskStatus().equals("Needs Info") &&
                                    currentMilestone.getMilestone_id() == task.getMilestoneId()) {
                                panel.add(createTaskCard(task));
                            }
                        }
                    }
                }
            }
            default -> {
                for (int i = 0; i < 5; i++) {
                    panel.add(createEmptyTaskCard());
                }
            }
        }
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return panel;
    }

    /**
     * This method is for the User Story Card creation
     * @param usTitle name for the user story
     * @return card
     */
    private JPanel createUSCard(int usNum, String usTitle) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(100, 80));
        card.setBorder(BorderFactory.createTitledBorder("US"+ usNum));
        JTextArea text = new JTextArea(usTitle);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setEditable(false);
        card.add(text, BorderLayout.CENTER);
        return card;
    }

    /**
    * This method works with the click and drag functionality for tasks columns.
    *
    * @return card
    */
    private JPanel createTaskCard(TaskNode task) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(UIManager.getColor("control"));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        card.setPreferredSize(new Dimension(120, 100));
        card.setMaximumSize(new Dimension(200, 120));

        //this is for the box inside the task box
        JPanel subtaskPanel = new JPanel();
        subtaskPanel.setLayout(new BoxLayout(subtaskPanel, BoxLayout.Y_AXIS));
        subtaskPanel.setBorder(BorderFactory.createTitledBorder("US"+ task.getUserStoryRef()
                + " " + "Task" + task.getTaskRef()));

        JTextArea text = new JTextArea("Subject: "+ task.getTaskSubject());
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setEditable(false);
        JLabel ownerText = new JLabel("Owner: "+ task.getOwnerName());

        subtaskPanel.add(text);
        subtaskPanel.add(ownerText, BorderLayout.SOUTH);

        card.add(subtaskPanel, BorderLayout.CENTER);

        return card;
    }


    // *************** The methods below here works on the Milestone Data table in the Scrum Board tab *************** //

    /**
     * Creates empty task card as placeholder.
     * @return card
     */
    private JPanel createEmptyTaskCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(0, 0, 0, 0));
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        card.setPreferredSize(new Dimension(160, 80));
        return card;
    }

    /**
     * This method creates the Milestone Data panel
     * @return panel
     */
    private static JPanel createMilestoneTableCard() throws IOException {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel subtitlePanel = new JPanel();
        JLabel subtitleLabel = new JLabel("Milestone Data:");
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        subtitlePanel.add(subtitleLabel);

        // Create the table
        JTable milestoneTable = createMilestoneTable();
        JScrollPane scrollPane = new JScrollPane(milestoneTable); // Add scroll pane
        scrollPane.setPreferredSize(new Dimension(600, 80));

        // Create the chart
        JFreeChart chart = createMilestoneChart();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 200)); // Adjust size as needed





        panel.add(subtitlePanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER); // Add scrollable table
        panel.add(chartPanel, BorderLayout.SOUTH);
        return panel;
    }
    
    /**
     * this method creates the table inside the Milestone data panel and populates it.
     * @return table
     */
    private static JTable createMilestoneTable() throws IOException {
        // Define column names
        String[] columnNames = {"ID", "Sprint", "Project ID", "User Stories", "Complete","Not Complete", "Points Complete", "Points Not Complete", "Total Points","Start", "Finish", "Closed"};

        // Create a DefaultTableModel
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Create the table
        JTable table = new JTable(model);

        // Populate the table
        List<MilestoneData> milestoneDataList = null;
        try {
            milestoneDataList = getMilestoneData();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching milestone data: " + e.getMessage(),
                    "Data Fetch Error", JOptionPane.ERROR_MESSAGE);
        }

        if (milestoneDataList != null) {
            for (MilestoneData data : milestoneDataList) {
                Object[] rowData = {
                        data.getMilestone_id(),
                        data.getSprintName(),
                        data.getProjectId(),
                        data.getNumUserStories(),
                        data.getNumUserStoriesComplete(),
                        data.getNumUserStoriesNotComplete(),
                        data.getTotal_points_complete(),
                        data.getTotalPointsNotCompleted(),
                        data.getTotal_sprint_points(),
                        data.getSprintEstStart(),
                        data.getSprintEstFinish(),
                        data.isMilestoneClosed()
                };
                model.addRow(rowData);
            }
        }

        // Set some table properties
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        return table;
    }


    /**
     * This method works with the logic to pull the data for the milestone data from taiga
     * @return milestones 
    */
    private static List<MilestoneData> getMilestoneData() throws IOException {
        try {
            TaigaClient taigaClient = Start.getInjector().getInstance(TaigaClient.class);
            List<ProjectData> projects = taigaClient.getProjectsList();
            List<MilestoneData> milestones = new ArrayList<>();

            for (ProjectData project : projects) {
                milestones.addAll(project.getProjectSprints());
            }
            return milestones;
        } catch (Exception e) {
            e.printStackTrace(); // Or handle the exception as appropriate
            return new ArrayList<>(); // Return an empty list or null
        }
    }

    /**
     * This method creates a chart and displays it on the ScrumToolBarCard
     * @return JFreeChart
     */
    private static JFreeChart createMilestoneChart() throws IOException {
        // Create a dataset for the chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Get the milestone data
        List<MilestoneData> milestoneDataList = getMilestoneData();

        // Populate the dataset with data from the milestones
        if (milestoneDataList != null) {
            for (MilestoneData data : milestoneDataList) {
                //  Add "Points Complete" and "Points Not Complete" user stories
                dataset.addValue(data.getTotal_points_complete(), "Points Complete", data.getSprintName());
                dataset.addValue(data.getTotalPointsNotCompleted(), "Points Not Complete", data.getSprintName());
            }
        }

        //create charts comments include basic legend if we decide to make more charts
        JFreeChart chart = ChartFactory.createBarChart(
                "Milestone Progress", // Chart title
                "Sprint",             // Category axis label
                "Points",     // Value axis label
                dataset,            // Dataset
                PlotOrientation.VERTICAL, // Plot orientation
                true,               // Include legend
                true,               // Tooltips
                false               // URLs
        );
        //  CHANGE BAR COLORS HERE
        CategoryPlot plot = chart.getCategoryPlot();

        // Set the color for "Points Complete"
        plot.getRenderer().setSeriesPaint(0, new Color(0, 153, 51));

        // Set the color for "Points Not Complete"
        plot.getRenderer().setSeriesPaint(1, new Color(204, 0, 0));
        return chart;
    }


    public void refreshPanels() {
        //  TODO: JPanel.refreshPanel();
    }

    @Override
    public void update() throws IOException {
        SwingUtilities.invokeLater(() -> {
            try {
                cardPanel.remove(scrollPane);
                cardPanel.setVisible(false);
                scrollPane = createSBCard();
                scrollPane.revalidate();
                scrollPane.repaint();
                tabbedPane.revalidate();
                tabbedPane.repaint();
                panel.revalidate();
                panel.repaint();
                cardPanel.add(scrollPane, BOARD_PANEL);
                cardPanel.revalidate();
                cardPanel.repaint();
                cardPanel.setVisible(true);
                this.revalidate();
                this.repaint();
            } catch (Exception e) {
                new ExceptionDialog(e);
            }
        });
    }
}
