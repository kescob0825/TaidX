package memoranda.ui.mainMenuCards.homeSubMenuCards;

import memoranda.Start;
import memoranda.api.TaigaClient;
import memoranda.api.models.ProjectData;
import memoranda.api.models.ProjectRolesData;
import memoranda.ui.ExceptionDialog;
import memoranda.util.subscriber.Subscriber;
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
import java.util.*;
import java.util.List;

public class ConfigureProject extends JPanel implements Subscriber {
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

    private JSpinner orderSpinner;
    private JComboBox comboBoxPermissions;
    private JButton createButtonRoles = new JButton("Create");
    private JButton clearFormButtonRoles = new JButton("Clear Form");
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private JSONArray bulkCreateRolesJSONArray = new JSONArray();
    private GridLayout bulkInviteGrid = new GridLayout(5, 2);
    private JPanel bulkMemberInvite = new JPanel();

    private TextField bulkInviteTextField = new TextField();

    private JButton addBulkInviteButton = new JButton("Add");
    private JButton removeBulkInviteButton = new JButton("Remove Last");
    private JButton createButtonBulkInvite = new JButton("Create");
    private JButton clearFormButtonBulkInvite = new JButton("Clear Form");
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private GridLayout singleInviteGrid = new GridLayout(4, 2);
    private JPanel singleMemberInvite = new JPanel();

    private TextField singleInviteTextField = new TextField();
    private JButton singleInviteButton = new JButton("Invite");
    private JButton clearFormButtonSingleInvite = new JButton("Clear Form");
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private JComboBox<String>[] comboBoxRoles = new JComboBox[2];
    private JComboBox<String>[] comboBoxProjects = new JComboBox[3];
    private final int BULK_INVITE = 0;
    private final int SINGLE_INVITE = 1;
    private final int CREATE_ROLES = 2;
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
        comboBoxSelectProject();
        createComboBoxRoles();
        orderSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 10));
        String[] comboBoxPermissionsItems = {"", "Edit", "View"};
        comboBoxPermissions = new JComboBox(comboBoxPermissionsItems);
        createButtonRoles.setSize(new Dimension(100, 30));
        addItemsToPanel(createRolesPanel, createLabel("Role Name: (Required)"), roleNameTextField);
        addItemsToPanel(createRolesPanel, createLabel("Order: "), orderSpinner);
        addItemsToPanel(createRolesPanel, createLabel("Select Project: "), comboBoxProjects[CREATE_ROLES]);
        addItemsToPanel(createRolesPanel, createLabel("Permissions: (Required)"), comboBoxPermissions);
        addItemsToPanel(createRolesPanel, createButtonRoles, clearFormButtonRoles);
        //Bulk create
        bulkMemberInvite.setLayout(bulkInviteGrid);
        createButtonBulkInvite.setSize(new Dimension(100, 30));
        addItemsToPanel(bulkMemberInvite, createLabel("*Select Project: "), comboBoxProjects[BULK_INVITE]);
        addItemsToPanel(bulkMemberInvite, createLabel("*Select Role: "), comboBoxRoles[BULK_INVITE]);
        addItemsToPanel(bulkMemberInvite, createLabel("*Username or Email: "), bulkInviteTextField);
        addItemsToPanel(bulkMemberInvite, addBulkInviteButton, removeBulkInviteButton);
        addItemsToPanel(bulkMemberInvite, createButtonBulkInvite, clearFormButtonBulkInvite);
        //Single create
        singleMemberInvite.setLayout(singleInviteGrid);
        singleInviteButton.setSize(new Dimension(100, 30));
        clearFormButtonSingleInvite.setMaximumSize(new Dimension(100, 30));
        addItemsToPanel(singleMemberInvite,
                createLabel("*Select Project: "), comboBoxProjects[SINGLE_INVITE]);
        addItemsToPanel(singleMemberInvite,
                createLabel("*Select Role: "), comboBoxRoles[SINGLE_INVITE]);
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
                if (Double.parseDouble(spinner2.getValue().toString()) != 0) {
                    newProjectData.put("total_story_points", Double.parseDouble(spinner2.getValue().toString()));
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
                if (roleNameTextField.getText().isEmpty() || Objects.requireNonNull(comboBoxProjects[CREATE_ROLES].getSelectedItem()).toString().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Required Fields Missing.");
                    return;
                }
                JSONObject newProjectRolesData = new JSONObject();
                newProjectRolesData.put("name", roleNameTextField.getText());
                for (int i = 0; i < comboBoxProjects[CREATE_ROLES].getItemCount(); i++){
                    if (comboBoxProjects[CREATE_ROLES].getSelectedItem().equals(comboBoxProjects[CREATE_ROLES].getItemAt(i))){
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
                        || Objects.requireNonNull(comboBoxRoles[BULK_INVITE].getSelectedItem()).toString().isEmpty()
                        || comboBoxRoles[BULK_INVITE] == null
                        || Objects.requireNonNull(comboBoxProjects[BULK_INVITE].getSelectedItem()).toString().isEmpty()
                        || comboBoxProjects[BULK_INVITE] == null
                ){
                    JOptionPane.showMessageDialog(null, "Required Fields Missing.");
                    return;
                }
                for (ProjectData projectData : taigaClient.getProjectsList()) {
                    for (ProjectRolesData projectRolesData : projectData.getProjectRolesList()) {
                        if (comboBoxRoles[SINGLE_INVITE].getSelectedItem().equals(projectData.getProjectName() + "/ #:" +
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
                        || Objects.requireNonNull(comboBoxProjects[BULK_INVITE].getSelectedItem()).toString().isEmpty()
                        || comboBoxProjects[BULK_INVITE] == null

                ){
                    JOptionPane.showMessageDialog(null, "Required Fields Missing.");
                    return;
                }
                bulkAddData.put("bulk_memberships", bulkCreateRolesJSONArray);
                for (int i = 0; i < comboBoxProjects[BULK_INVITE].getItemCount(); i++){
                    if (comboBoxProjects[BULK_INVITE].getSelectedItem().equals(comboBoxProjects[BULK_INVITE].getItemAt(i))){
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
                        || Objects.requireNonNull(comboBoxProjects[SINGLE_INVITE].getSelectedItem()).toString().isEmpty()
                        || comboBoxProjects[SINGLE_INVITE] == null
                        || Objects.requireNonNull(comboBoxRoles[SINGLE_INVITE].getSelectedItem()).toString().isEmpty()
                        || comboBoxRoles[SINGLE_INVITE] == null
                ){
                    JOptionPane.showMessageDialog(null, "Required Fields Missing.");
                    return;
                }
                for (int i = 0; i < comboBoxProjects[SINGLE_INVITE].getItemCount(); i++){
                    if (comboBoxProjects[SINGLE_INVITE].getSelectedItem().equals(comboBoxProjects[SINGLE_INVITE].getItemAt(i))){
                        singleInviteData.put("project", taigaClient.getProjectsList().get(i).getProjectId());
                    }
                }
                for (ProjectData projectData : taigaClient.getProjectsList()) {
                    for (ProjectRolesData projectRolesData : projectData.getProjectRolesList()) {
                        if (comboBoxRoles[SINGLE_INVITE].getSelectedItem().equals(projectData.getProjectName() + "/ #:" +
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
        taigaClient.register(this);
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

    private void comboBoxSelectProject() throws IOException {
        for(int i = 0; i < 3; i++) {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.removeAllElements();
            for (ProjectData projectData : Start.getInjector().getInstance(TaigaClient.class).getProjectsList()) {
                String project = projectData.getProjectName() + "\t #:" + projectData.getProjectId();
                model.addElement(project);
            }
            JComboBox<String> comboBox = new JComboBox<>(model);
            comboBox.setPreferredSize(new Dimension(100, 50));
            comboBoxProjects[i] = comboBox;
        }
    }

    public void createComboBoxRoles() throws IOException {
        for(int i = 0; i < 2; i++) {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.removeAllElements();
            for (ProjectData projectData : Start.getInjector().getInstance(TaigaClient.class).getProjectsList()) {
                for (ProjectRolesData projectRolesData : projectData.getProjectRolesList()) {
                    model.addElement(projectData.getProjectName() + "/ #:" +
                            projectData.getProjectId() + "\tRole: " + projectRolesData.get_role_name() + "/ #:" +
                            projectRolesData.get_role_id());
                }
            }
            JComboBox<String> comboBox = new JComboBox<>(model);
            comboBoxRoles[i] = comboBox;
        }
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
        if (comboBoxProjects[CREATE_ROLES] != null ) {
            comboBoxProjects[CREATE_ROLES].setSelectedIndex(0);
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
        if (comboBoxProjects[BULK_INVITE] != null || comboBoxRoles[BULK_INVITE] != null) {
            bulkInviteTextField.setText("");
            comboBoxProjects[BULK_INVITE].setSelectedIndex(0);
            comboBoxRoles[BULK_INVITE].setSelectedIndex(0);
        }
    }

    public void resetSingleInvite() {
        singleInviteTextField.setText("");
        if (comboBoxProjects[SINGLE_INVITE] != null || comboBoxRoles[SINGLE_INVITE] != null) {
            comboBoxProjects[SINGLE_INVITE].setSelectedIndex(0);
            comboBoxRoles[SINGLE_INVITE].setSelectedIndex(0);
        }
    }

    @Override
    public void update() throws IOException {
        SwingUtilities.invokeLater(() -> {
            try {
                List<ProjectData> projectsList = taigaClient.getProjectsList();

                for (JComboBox<String> comboBox : comboBoxRoles) {
                    DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) comboBox.getModel();
                    model.removeAllElements();
                    for (ProjectData projectData : projectsList) {
                        for (ProjectRolesData projectRolesData : projectData.getProjectRolesList()) {
                            model.addElement(projectData.getProjectName() + "/ #:" +
                                    projectData.getProjectId() + "\tRole: " +
                                    projectRolesData.get_role_name() + "/ #:" +
                                    projectRolesData.get_role_id());
                        }
                    }
                }
                for (JComboBox<String> comboBox : comboBoxProjects) {
                    DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) comboBox.getModel();
                    model.removeAllElements();
                    for (ProjectData projectData : projectsList) {
                        String project = projectData.getProjectName() + "\t #:" + projectData.getProjectId();
                        model.addElement(project);
                    }
                }
                for(int i = 0; i < 3; i++) {
                    comboBoxProjects[i].revalidate();
                    comboBoxProjects[i].repaint();
                    if (comboBoxProjects[i].getParent() != null) {
                        comboBoxProjects[i].getParent().revalidate();
                        comboBoxProjects[i].getParent().repaint();
                    }
                }
                for(int i = 0; i < 2; i++) {
                    comboBoxRoles[i].revalidate();
                    comboBoxRoles[i].repaint();
                    if (comboBoxRoles[i].getParent() != null) {
                        comboBoxRoles[i].getParent().revalidate();
                        comboBoxRoles[i].getParent().repaint();
                    }
                }
            } catch (Exception e) {
                new ExceptionDialog(e);
            }
        });
    }
}
