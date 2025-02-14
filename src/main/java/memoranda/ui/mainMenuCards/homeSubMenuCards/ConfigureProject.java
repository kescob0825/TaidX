package memoranda.ui.mainMenuCards.homeSubMenuCards;

import memoranda.Start;
import memoranda.api.TaigaClient;
import memoranda.api.models.ProjectData;
import memoranda.api.models.ProjectRolesData;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Objects;

public class ConfigureProject extends JPanel{
    /**
     * Create Project Form
     * @throws Exception if an error occurs during the creation of the form
     */
    ///////////////////////////////////////////////////////////////////////////////////////////
    public JTabbedPane tabbedPane = new JTabbedPane();
    private JPanel panel1 = new JPanel();
    private GridLayout addProjectGrid = new GridLayout(14,3,100,30);
    private JLabel creationTemplate = new JLabel("Creation Template #: ");
    private JComboBox comboBox1;
    private JLabel projectName = new JLabel("*Project Name: ");
    private JTextField projectNameTextField = new JTextField();
    private JLabel projectDescription = new JLabel("*Description: ");
    private JTextField projectDescriptionTextField = new JTextField();
    private JLabel backlogActivated = new JLabel("Backlog Activated: ");
    private JLabel issuesActivated = new JLabel("Issues Activated: ");
    private JLabel kanbanActivated = new JLabel("Kanban Activated: ");
    private JLabel isPrivate = new JLabel("Is Private: ");
    private JLabel wikiActivated = new JLabel("Wiki Activated: ");
    private JLabel totalMilestones = new JLabel("Total Milestones: ");
    private JSpinner spinner1;
    private JLabel totalStoryPoints = new JLabel("Total Story Points: ");
    private JSpinner spinner2;
    private JLabel vtcLabel = new JLabel("Video Conference: ");
    private JTextField videoConferenceTextField = new JTextField();
    private JLabel vtcURLLabel = new JLabel("Video Conference URL: ");
    private JTextField videoConferenceURLTextField = new JTextField();
    private JRadioButton[] radioButtons;
    private JButton createButton = new JButton("Create");
    private JButton clearFormButton = new JButton("Clear Form");
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private GridLayout createRolesGrid = new GridLayout(5, 2);
    private JPanel createRolesPanel = new JPanel();
    private TextField roleNameTextField = new TextField();
    private JComboBox comboBoxRolesProjects;
    private JSpinner orderSpinner;
    private JComboBox comboBoxPermissions;
    private JButton createButtonRoles = new JButton("Create");
    private JButton clearFormButtonRoles = new JButton("Clear Form");
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private JSONArray bulkCreateRolesJSONArray = new JSONArray();
    private GridLayout bulkInviteGrid = new GridLayout(5, 2);
    private JPanel bulkMemberInvite = new JPanel();
    private JComboBox  comboBoxBulkInviteProjects;
    private TextField bulkInviteTextField = new TextField();
    private JComboBox comboBoxBulkRolesList;
    private JButton addBulkInviteButton = new JButton("Add");
    private JButton removeBulkInviteButton = new JButton("Remove Last");
    private JButton createButtonBulkInvite = new JButton("Create");
    private JButton clearFormButtonBulkInvite = new JButton("Clear Form");
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private GridLayout singleInviteGrid = new GridLayout(4, 2);
    private JPanel singleMemberInvite = new JPanel();
    private JComboBox comboBoxSingleInviteProjects;
    private JComboBox comboBoxSingleInviteRoles;
    private TextField singleInviteTextField = new TextField();
    private JButton singleInviteButton = new JButton("Invite");
    private JButton clearFormButtonSingleInvite = new JButton("Clear Form");
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    TaigaClient taigaClient = Start.getInjector().getInstance(TaigaClient.class);
    public ConfigureProject(){
        try {
            projectInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    void projectInit() throws Exception {
        this.setLayout(new BorderLayout());
        panel1.setLayout(addProjectGrid);
        radioButtons = createRadioButtons();
        ButtonGroup[] buttonGroups = createButtonGroups(radioButtons);
        JLabel[] labels =  jLabels();
        spinner1 = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        spinner2 = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100.0, 1));
        String[] comboBox1Items = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        comboBox1 = new JComboBox(comboBox1Items);
        addItemsToPanel(creationTemplate, comboBox1, new JLabel(""));
        addItemsToPanel(projectName, projectNameTextField, new JLabel(""));
        addItemsToPanel(projectDescription, projectDescriptionTextField, new JLabel(""));
        for (int i = 0; i < 5; i++) {
            addItemsToPanel(labels[i],radioButtons[i*2],radioButtons[(i*2)+1]);
        }
        addItemsToPanel(totalMilestones, spinner1, new JLabel(""));
        addItemsToPanel(totalStoryPoints, spinner2, new JLabel(""));
        addItemsToPanel(vtcLabel, videoConferenceTextField, new JLabel(""));
        addItemsToPanel(vtcURLLabel, videoConferenceURLTextField, new JLabel(""));
        addItemsToPanel(new JLabel(""), createButton, clearFormButton);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //create roles
        createRolesPanel.setLayout(createRolesGrid);
        comboBoxRolesProjects = comboBoxSelectProject();
        orderSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 10));
        String[] comboBoxPermissionsItems = {"", "Edit", "View"};
        comboBoxPermissions = new JComboBox(comboBoxPermissionsItems);
        createButtonRoles.setSize(new Dimension(100, 30));
        addItemsToPanel(createRolesPanel, createLabel("Role Name: (Required)"), roleNameTextField);
        addItemsToPanel(createRolesPanel, createLabel("Order: "), orderSpinner);
        addItemsToPanel(createRolesPanel, createLabel("Select Project: "), comboBoxRolesProjects);
        addItemsToPanel(createRolesPanel, createLabel("Permissions: (Required)"), comboBoxPermissions);
        addItemsToPanel(createRolesPanel, createButtonRoles, clearFormButtonRoles);
        //Bulk create
        bulkMemberInvite.setLayout(bulkInviteGrid);
        comboBoxBulkInviteProjects = comboBoxSelectProject();
        comboBoxBulkRolesList = createComboBoxRoles();
        createButtonBulkInvite.setSize(new Dimension(100, 30));
        addItemsToPanel(bulkMemberInvite, createLabel("*Select Project: "), comboBoxBulkInviteProjects);
        addItemsToPanel(bulkMemberInvite, createLabel("*Select Role: "), comboBoxBulkRolesList);
        addItemsToPanel(bulkMemberInvite, createLabel("*Username or Email: "), bulkInviteTextField);
        addItemsToPanel(bulkMemberInvite, addBulkInviteButton, removeBulkInviteButton);
        addItemsToPanel(bulkMemberInvite, createButtonBulkInvite, clearFormButtonBulkInvite);
        //Single create
        singleMemberInvite.setLayout(singleInviteGrid);
        comboBoxSingleInviteProjects = comboBoxSelectProject();
        comboBoxSingleInviteRoles = createComboBoxRoles();
        singleInviteButton.setSize(new Dimension(100, 30));
        clearFormButtonSingleInvite.setMaximumSize(new Dimension(100, 30));
        addItemsToPanel(singleMemberInvite,
                createLabel("*Select Project: "), comboBoxSingleInviteProjects);
        addItemsToPanel(singleMemberInvite,
                createLabel("*Select Role: "), comboBoxSingleInviteRoles);
        addItemsToPanel(singleMemberInvite,
                createLabel("*Username or Email: "), singleInviteTextField);
        addItemsToPanel(singleMemberInvite,
                singleInviteButton, clearFormButtonSingleInvite);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        tabbedPane.add("Create Project", panel1);
        tabbedPane.add("Create Roles", createRolesPanel);
        tabbedPane.add("Bulk Invite", bulkMemberInvite);
        tabbedPane.add("Single Invite", singleMemberInvite);
        this.add(tabbedPane, BorderLayout.CENTER);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        createButton.addActionListener(e -> {
            try {
                if (projectNameTextField.getText().isEmpty() || projectDescriptionTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Project Name and Description are required fields.");
                    return;
                }
                JSONObject newProjectData = new JSONObject();
                if (!comboBox1.getSelectedItem().toString().isEmpty()) {
                    newProjectData.put("creation_template", comboBox1.getSelectedItem());
                }
                newProjectData.put("name", projectNameTextField.getText());
                newProjectData.put("description", projectDescriptionTextField.getText());
                if (radioButtons[0].isSelected()) {
                    newProjectData.put("is_backlog_activated", false);
                }
                else if  (radioButtons[1].isSelected()) {
                    newProjectData.put("is_backlog_activated", true);
                }
                if (radioButtons[2].isSelected()) {
                    newProjectData.put("is_issues_activated", false);
                }
                else if  (radioButtons[3].isSelected()) {
                    newProjectData.put("is_issues_activated", true);
                }
                if (radioButtons[4].isSelected()) {
                    newProjectData.put("is_kanban_activated", false);
                }
                else if  (radioButtons[5].isSelected()) {
                    newProjectData.put("is_kanban_activated", true);
                }
                if (radioButtons[6].isSelected()) {
                    newProjectData.put("is_private", false);
                }
                else if  (radioButtons[7].isSelected()) {
                    newProjectData.put("is_private", true);
                }
                if (radioButtons[8].isSelected()) {
                    newProjectData.put("is_wiki_activated", false);
                }
                else if  (radioButtons[9].isSelected()) {
                    newProjectData.put("is_wiki_activated", true);
                }
                if (Integer.parseInt(spinner1.getValue().toString()) != 0) {
                    newProjectData.put("total_milestones", Integer.parseInt(spinner1.getValue().toString()));
                }
                if (Integer.parseInt(spinner2.getValue().toString()) != 0) {
                    newProjectData.put("total_story_points", (double)Integer.parseInt(spinner2.getValue().toString()));
                }
                if (!videoConferenceTextField.getText().isEmpty()) {
                    newProjectData.put("videoconferencing", videoConferenceTextField.getText());
                }
                if (!videoConferenceURLTextField.getText().isEmpty()) {
                    newProjectData.put("videoconferencing_url", videoConferenceURLTextField.getText());
                }
                resetCreateProject();
                taigaClient.createNewProject(newProjectData);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        clearFormButton.addActionListener(e -> {
            resetCreateProject();
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        createButtonRoles.addActionListener(e -> {
            try{
                if (roleNameTextField.getText().isEmpty() || Objects.requireNonNull(comboBoxRolesProjects.getSelectedItem()).toString().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Required Fields Missing.");
                    return;
                }
                JSONObject newProjectRolesData = new JSONObject();
                newProjectRolesData.put("name", roleNameTextField.getText());
                for (int i = 0; i < comboBoxRolesProjects.getItemCount(); i++){
                    if (comboBoxRolesProjects.getSelectedItem().equals(comboBoxRolesProjects.getItemAt(i))){
                        newProjectRolesData.put("project", taigaClient.getProjectsList().get(i).getProjectId());
                    }
                }
                if (Integer.parseInt(orderSpinner.getValue().toString()) != 0){
                    newProjectRolesData.put("order", Integer.parseInt(orderSpinner.getValue().toString()));
                }
                if (comboBoxPermissions.getSelectedItem().equals("Edit")){
                    newProjectRolesData.put("permissions", ProjectRolesData.edit_permission);
                }
                else if (comboBoxPermissions.getSelectedItem().equals("View")){
                    newProjectRolesData.put("permissions", ProjectRolesData.view_permission);
                }
                resetCreateProjectRoles();
                taigaClient.setProjectRolesClient(newProjectRolesData);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        clearFormButtonRoles.addActionListener(e -> {
            resetCreateProjectRoles();
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        addBulkInviteButton.addActionListener(e -> {
            try{
                JSONObject bulkAddData = new JSONObject();
                if (bulkInviteTextField.getText().isEmpty()
                        || Objects.requireNonNull(comboBoxBulkRolesList.getSelectedItem()).toString().isEmpty()
                        || comboBoxBulkRolesList == null
                        || Objects.requireNonNull(comboBoxBulkInviteProjects.getSelectedItem()).toString().isEmpty()
                        || comboBoxBulkInviteProjects == null
                ){
                    JOptionPane.showMessageDialog(null, "Required Fields Missing.");
                    return;
                }
                for (ProjectData projectData : taigaClient.getProjectsList()) {
                    for (ProjectRolesData projectRolesData : projectData.getProjectRolesList()) {
                        if (comboBoxSingleInviteRoles.getSelectedItem().equals(projectData.getProjectName() + "/ #:" +
                                projectData.getProjectId()+ "\tRole: " + projectRolesData.get_role_name() + "/ #:" +
                                projectRolesData.get_role_id())){
                            bulkAddData.put("role", projectRolesData.get_role_id());
                        }
                    }
                }
                bulkAddData.put("username", bulkInviteTextField.getText());
                bulkCreateRolesJSONArray.put(bulkAddData);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        removeBulkInviteButton.addActionListener(e -> {
            if (bulkCreateRolesJSONArray.isEmpty()){
                JOptionPane.showMessageDialog(null, "No Roles to Remove.");
                return;
            }
            bulkCreateRolesJSONArray.remove(bulkCreateRolesJSONArray.length() - 1);
        });
        createButtonBulkInvite.addActionListener(e -> {
            try{
                JSONObject bulkAddData = new JSONObject();
                if (bulkCreateRolesJSONArray.isEmpty()
                        || Objects.requireNonNull(comboBoxBulkInviteProjects.getSelectedItem()).toString().isEmpty()
                        || comboBoxBulkInviteProjects == null

                ){
                    JOptionPane.showMessageDialog(null, "Required Fields Missing.");
                    return;
                }
                bulkAddData.put("bulk_memberships", bulkCreateRolesJSONArray);
                for (int i = 0; i < comboBoxBulkInviteProjects.getItemCount(); i++){
                    if (comboBoxBulkInviteProjects.getSelectedItem().equals(comboBoxBulkInviteProjects.getItemAt(i))){
                        bulkAddData.put("project", taigaClient.getProjectsList().get(i).getProjectId());
                    }
                }
                resetBulkInvite();
                taigaClient.sendBulkInvite(bulkAddData);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        clearFormButtonBulkInvite.addActionListener(e -> {
            resetBulkInvite();
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        singleInviteButton.addActionListener(e -> {
            try{
                JSONObject singleInviteData = new JSONObject();
                if (singleInviteTextField.getText().isEmpty()
                        || Objects.requireNonNull(comboBoxSingleInviteProjects.getSelectedItem()).toString().isEmpty()
                        || comboBoxSingleInviteProjects == null
                        || Objects.requireNonNull(comboBoxSingleInviteRoles.getSelectedItem()).toString().isEmpty()
                        || comboBoxSingleInviteRoles == null
                ){
                    JOptionPane.showMessageDialog(null, "Required Fields Missing.");
                    return;
                }
                for (int i = 0; i < comboBoxSingleInviteProjects.getItemCount(); i++){
                    if (comboBoxSingleInviteProjects.getSelectedItem().equals(comboBoxSingleInviteProjects.getItemAt(i))){
                        singleInviteData.put("project", taigaClient.getProjectsList().get(i).getProjectId());
                    }
                }
                for (ProjectData projectData : taigaClient.getProjectsList()) {
                    for (ProjectRolesData projectRolesData : projectData.getProjectRolesList()) {
                        if (comboBoxSingleInviteRoles.getSelectedItem().equals(projectData.getProjectName() + "/ #:" +
                                projectData.getProjectId()+ "\tRole: " + projectRolesData.get_role_name() + "/ #:" +
                                projectRolesData.get_role_id())){
                            singleInviteData.put("role", projectRolesData.get_role_id());
                        }
                    }
                }
                singleInviteData.put("username", singleInviteTextField.getText());
                resetSingleInvite();
                taigaClient.sendInvite(singleInviteData);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        clearFormButtonSingleInvite.addActionListener(e -> {
            resetSingleInvite();
        });
    }

    private JRadioButton[] createRadioButtons() {
        JRadioButton[] radioButtons = new JRadioButton[10];
        for (int i = 0; i < 10; i++) {
            if (i % 2 != 0) {
                radioButtons[i] = new JRadioButton("true", true);
                radioButtons[i].setSelected(false);

            } else{
                radioButtons[i] = new JRadioButton("false", false);
                radioButtons[i].setSelected(false);
            }
            radioButtons[i].setPreferredSize(new Dimension(100, 50));
        }
        return radioButtons;
    }
    private ButtonGroup[] createButtonGroups(JRadioButton[] radioButtons) {
        ButtonGroup[] buttonGroups = new ButtonGroup[5];
        for(int i = 0; i < 10; i += 2) {
            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(radioButtons[i]);
            buttonGroup.add(radioButtons[i+1]);
            buttonGroups[i/2] = buttonGroup;
        }
        return buttonGroups;
    }

    private JLabel[] jLabels() {
        JLabel[] labels = new JLabel[5];
        labels[0] = backlogActivated;
        labels[1] = issuesActivated;
        labels[2] = kanbanActivated;
        labels[3] = isPrivate;
        labels[4] = wikiActivated;
        return labels;
    }
    private void addItemsToPanel(Component col0, Component col1, Component col2) {
        col0.setPreferredSize(new Dimension(100, 50));
        col1.setPreferredSize(new Dimension(100, 50));
        col2.setPreferredSize(new Dimension(100, 50));
        panel1.add(col0);
        panel1.add(col1);
        panel1.add(col2);
    }

    private void addItemsToPanel(JPanel panel, Component col0, Component col1) {
        JPanel smallPanel1 = placeCenter(col0);
        JPanel smallPanel2 = placeCenter(col1);
        col0.setPreferredSize(new Dimension(300, 50));
        col1.setPreferredSize(new Dimension(150, 50));
        col0.setMaximumSize(new Dimension(300, 50));
        col1.setMaximumSize(new Dimension(150, 50));
        panel.add(smallPanel1);
        panel.add(smallPanel2);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(300, 75));
        label.setFont(new Font("Arial", Font.BOLD, 16));
        return label;
    }

    private JComboBox<String> comboBoxSelectProject() throws IOException {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (ProjectData projectData : Start.getInjector().getInstance(TaigaClient.class).getProjectsList()) {
            String project = projectData.getProjectName() + "\t #:" + projectData.getProjectId();
            model.addElement(project);
        }
        JComboBox<String> comboBox = new JComboBox<>(model);
        comboBox.setPreferredSize(new Dimension(100, 50));
        return comboBox;
    }

    public JComboBox<String> createComboBoxRoles() throws IOException {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (ProjectData projectData : Start.getInjector().getInstance(TaigaClient.class).getProjectsList()) {
            for (ProjectRolesData projectRolesData : projectData.getProjectRolesList()) {
                model.addElement(projectData.getProjectName() + "/ #:" +
                        projectData.getProjectId()+ "\tRole: " + projectRolesData.get_role_name() + "/ #:" +
                        projectRolesData.get_role_id());
            }
        }
        return new JComboBox<>(model);
    }

    public JPanel placeCenter(Component comp) {
        JPanel  centerPanel = new JPanel(new FlowLayout());
        centerPanel.add(comp, FlowLayout.LEFT);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return centerPanel;
    }

    public void resetCreateProject() {
        projectNameTextField.setText("");
        projectDescriptionTextField.setText("");
        for (int i = 0; i < 10; i++) {
            radioButtons[i].setSelected(false);
        }
        spinner1.setValue(0);
        spinner2.setValue(0);
        videoConferenceTextField.setText("");
        videoConferenceURLTextField.setText("");
        comboBox1.setSelectedIndex(0);
    }
    public void resetCreateProjectRoles() {
        roleNameTextField.setText("");
        orderSpinner.setValue(0);
        if (comboBoxRolesProjects != null ) {
            comboBoxRolesProjects.setSelectedIndex(0);
        }
        if (comboBoxPermissions != null) {
            comboBoxPermissions.setSelectedIndex(0);
        }
    }

    public void resetBulkInvite() {
        if (bulkCreateRolesJSONArray.isEmpty()){
            bulkInviteTextField.setText("");
            return;
        }
        if (comboBoxBulkInviteProjects != null || comboBoxBulkRolesList != null) {
            bulkInviteTextField.setText("");
            comboBoxBulkInviteProjects.setSelectedIndex(0);
            comboBoxBulkRolesList.setSelectedIndex(0);
        }
    }

    public void resetSingleInvite() {
        singleInviteTextField.setText("");
        if (comboBoxSingleInviteProjects != null || comboBoxSingleInviteRoles != null) {
            comboBoxSingleInviteProjects.setSelectedIndex(0);
            comboBoxSingleInviteRoles.setSelectedIndex(0);
        }
    }

}
