package memoranda.ui.mainMenuCards.homeSubMenuCards;

import memoranda.Start;
import memoranda.api.TaigaClient;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class ConfigureProject extends JPanel{
    JLabel subtitle = new JLabel("Project Name and Description are required fields. All others are optional.");
    JPanel panel1 = new JPanel();
    GridLayout addProjectGrid = new GridLayout(14,3,100,30);
    JLabel creationTemplate = new JLabel("Creation Template #: ");
    JComboBox comboBox1;
    JLabel projectName = new JLabel("Project Name: (Required)");
    JTextField projectNameTextField = new JTextField();
    JLabel projectDescription = new JLabel("Project Description: (Required)");
    JTextField projectDescriptionTextField = new JTextField();
    JLabel backlogActivated = new JLabel("Backlog Activated: ");
    JLabel issuesActivated = new JLabel("Issues Activated: ");
    JLabel kanbanActivated = new JLabel("Kanban Activated: ");
    JLabel isPrivate = new JLabel("Is Private: \n*(Private projects are only\n viewable and searchable\n with the project id.");
    JLabel wikiActivated = new JLabel("Wiki Activated: ");
    JLabel totalMilestones = new JLabel("Total Milestones: ");
    JSpinner spinner1;
    JLabel totalStoryPoints = new JLabel("Total Story Points: ");
    JSpinner spinner2;
    JLabel vtcLabel = new JLabel("Video Conference: ");
    JTextField videoConferenceTextField = new JTextField();
    JLabel vtcURLLabel = new JLabel("Video Conference URL: ");
    JTextField videoConferenceURLTextField = new JTextField();
    JButton createButton = new JButton("Create");
    JButton clearFormButton = new JButton("Clear Form");
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
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(subtitle, BorderLayout.NORTH);
        panel1.setLayout(addProjectGrid);
        panel1.setPreferredSize(new Dimension(800, 600));
        this.add(panel1, BorderLayout.CENTER);
        JRadioButton[] radioButtons = createRadioButtons();
        ButtonGroup[] buttonGroups = createButtonGroups(radioButtons);
        JLabel[] labels =  jLabels();
        spinner1 = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        spinner2 = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        String[] comboBox1Items = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        comboBox1 = new JComboBox(comboBox1Items);

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
                TaigaClient taigaClient = Start.getInjector().getInstance(TaigaClient.class);
                taigaClient.createNewProject(newProjectData);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


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

}
