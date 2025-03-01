package memoranda.ui.mainMenuCards.issueSubMenuCards;
import memoranda.Start;
import memoranda.api.TaigaClient;
import memoranda.api.models.*;
import memoranda.api.models.issueattributes.IssuePriority;
import memoranda.api.models.issueattributes.IssueSeverity;
import memoranda.api.models.issueattributes.IssueStatus;
import memoranda.api.models.issueattributes.IssueType;
import memoranda.util.TaigaJsonData;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CreateIssue extends JPanel {
    public static JTabbedPane projectTabs;
    private final TaigaClient taigaClient = Start.getInjector().getInstance(TaigaClient.class);
    private List <IssuePriority> priorities;
    private List <IssueSeverity> severities;
    private List <IssueStatus> statuses;
    private List <IssueType> types;
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
                    projectTabs.setPreferredSize(new Dimension(800, 700));
                }
                for (int i = 0; i < taigaClient.getProjectsList().size(); i++) {
                    ProjectData project = projects.get(i);
                    panels[i] = createIssues(project);
                    panels[i].setVisible(true);
                    projectTabs.addTab(project.getProjectName(), panels[i]);
                }
            }
            this.add(projectTabs, BorderLayout.CENTER);
        }
    }

    private JPanel createIssues(ProjectData projectData) {

        priorities = projectData.getIssuePriorityList();
        severities = projectData.getIssueSeverityList();
        statuses = projectData.getIssueStatusList();
        types = projectData.getIssueTypeList();

        // Main panel
        JPanel panel = new JPanel(new GridLayout());
        panel.setLayout(new GridLayout(12, 1, 0, 20));
        panel.setBorder(new EmptyBorder(10, 60, 10, 40));
        //panel.setBackground(Color.RED);

        // Define a font for the labels
        Font labelFont = new Font("Arial", Font.PLAIN, 18);

        // Create form components
        JTextField descriptionField = new JTextField();
        JTextField blockedNoteField = new JTextField();
        blockedNoteField.setVisible(false);
        JCheckBox isBlockedCheckBox = new JCheckBox();
        JCheckBox isClosedCheckBox = new JCheckBox();
        JLabel blockedNoteLabel = new JLabel("Blocked Note:");
        blockedNoteLabel.setVisible(false);

        // Create combo boxes for dropdowns
        JComboBox<String> priorityComboBox = new JComboBox<>(new String[]{"", "Low", "Medium", "High"});
        JComboBox<Integer> severityComboBox = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4, 5});
        JComboBox<Integer> statusComboBox = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7});
        JTextField subjectField = new JTextField();
        JTextField tagsField = new JTextField(); // Comma-separated tags
        JComboBox<Integer> typeComboBox = new JComboBox<>(new Integer[]{0, 1, 2, 3});

        // Create a button to submit the form
        JButton submitButton = new JButton("Submit");

        // Create labels and set their font
        JLabel subjectLabel = new JLabel("*Subject:");
        subjectLabel.setFont(labelFont);
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(labelFont);
        JLabel priorityLabel = new JLabel("Priority:");
        priorityLabel.setFont(labelFont);
        JLabel severityLabel = new JLabel("Severity:");
        severityLabel.setFont(labelFont);
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(labelFont);
        JLabel tagsLabel = new JLabel("Tags (comma-separated):");
        tagsLabel.setFont(labelFont);
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(labelFont);
        JLabel isClosedLabel = new JLabel("Closed:");
        isClosedLabel.setFont(labelFont);
        JLabel isBlockedLabel = new JLabel("Blocked:");
        isBlockedLabel.setFont(labelFont);
        blockedNoteLabel.setFont(labelFont);

        // Add components to the panel
        panel.add(subjectLabel);
        panel.add(subjectField);
        panel.add(descriptionLabel);
        panel.add(descriptionField);
        panel.add(priorityLabel);
        panel.add(priorityComboBox);
        panel.add(severityLabel);
        panel.add(severityComboBox);
        panel.add(statusLabel);
        panel.add(statusComboBox);
        panel.add(tagsLabel);
        panel.add(tagsField);
        panel.add(typeLabel);
        panel.add(typeComboBox);
        panel.add(isClosedLabel);
        panel.add(isClosedCheckBox);
        panel.add(isBlockedLabel);
        panel.add(isBlockedCheckBox);
        panel.add(blockedNoteLabel);
        panel.add(blockedNoteField);
        panel.add(submitButton);

        isBlockedCheckBox.addItemListener(e -> {
            blockedNoteField.setVisible(e.getStateChange() == ItemEvent.SELECTED);
            blockedNoteLabel.setVisible(e.getStateChange() == ItemEvent.SELECTED);
            panel.revalidate(); // Revalidate to update layout
            panel.repaint();    // Repaint to refresh the UI
        });

        // Add action listener to the button using a lambda expression
        submitButton.addActionListener(e -> {

            // Check if the subject field is empty
            if (subjectField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        panel,
                        "Subject is required. Please enter a subject.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return; // Exit the action listener if the subject is empty
            }

            // Show confirmation dialog
            int response = JOptionPane.showConfirmDialog(
                    panel,
                    "Are you sure you want to create this issue?",
                    "Confirm issue Creation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (response == JOptionPane.YES_OPTION) {
                // Extract values from the form
                String description = descriptionField.getText();
                String blockedNote = blockedNoteField.getText();
                boolean isBlocked = isBlockedCheckBox.isSelected();
                boolean isClosed = isClosedCheckBox.isSelected();
                int severity = (Integer) severityComboBox.getSelectedItem();
                int status = (Integer) statusComboBox.getSelectedItem();
                String subject = subjectField.getText();
                String[] tags = tagsField.getText().split(",");
                int type = (Integer) typeComboBox.getSelectedItem();

                String priorityString = (String) priorityComboBox.getSelectedItem();
                int priority = switch (Objects.requireNonNull(priorityString)) {
                    case "Medium" -> 3;
                    case "High" -> 5;
                    case "Low" -> 1;
                    default -> 0;
                };

                // Create a JSON object
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("project", projectData.getProjectId());
                jsonObject.put("subject", subject);
                if (!description.isEmpty()) {
                    jsonObject.put("description", description);
                }
                if (isBlocked) {
                    jsonObject.put("is_blocked", true);
                    jsonObject.put("blocked_note", blockedNote);
                }

                jsonObject.put("is_closed", isClosed);
                if (priorities != null && !priorities.isEmpty() && priority != 0) {
                    jsonObject.put("priority", setPriorityID(priority));
                }
                if (severities != null && !severities.isEmpty() && severity != 0) {
                    jsonObject.put("severity", setSeverityID(severity));
                }
                if (statuses != null && !statuses.isEmpty() && status != 0) {
                    jsonObject.put("status", setStatusID(status));
                }
                if (types != null && !types.isEmpty() && type != 0) {
                    jsonObject.put("subject", setTypeID(type));
                }
                // Add tags as a JSON array
                JSONArray jsonTags = new JSONArray();
                JSONArray jsonWatchers = new JSONArray();
                if (tags.length > 0) {
                    for (String tag : tags) {
                        if (tag.trim().isEmpty()) {
                            continue;
                        }
                        jsonTags.put(tag.trim());
                        //System.out.println(tag.trim());
                    }
                    if (!Arrays.toString(tags).trim().isEmpty()) {
                        jsonObject.put("tags", jsonTags);
                    }
                }
                if (!jsonWatchers.isEmpty()) {
                    jsonObject.put("watchers", jsonWatchers);
                }
                // Print the JSON object
                System.out.println(jsonObject);

                // Send Json Body to taiga client
                try {
                    taigaClient.createNewIssue(jsonObject);
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                // Reset fields after submission
                descriptionField.setText("");
                blockedNoteField.setText("");
                isBlockedCheckBox.setSelected(false);
                isClosedCheckBox.setSelected(false);
                priorityComboBox.setSelectedIndex(0);
                severityComboBox.setSelectedIndex(0);
                statusComboBox.setSelectedIndex(0);
                subjectField.setText("");
                tagsField.setText("");
                typeComboBox.setSelectedIndex(0);
            }
        });
        return panel;
    }

    private int setPriorityID(int priorityOrder) {

        int i = 0;
        for (IssuePriority priority: this.priorities) {
            if (priority.getOrder() == priorityOrder) {
                return priority.getID();
            }
            i++;
        }
        return this.priorities.get(i).getID();
    }

    private int setSeverityID(int severityOrder) {

        int i = 0;
        for (IssueSeverity severity: this.severities) {
            if (severity.getOrder() == severityOrder) {
                return severity.getID();
            }
            i++;
        }
        return this.severities.get(i).getID();
    }

    private int setStatusID(int statusOrder) {

        int i = 0;
        for (IssueStatus status: this.statuses) {
            if (status.getOrder() == statusOrder) {
                return status.getID();
            }
            i++;
        }
        return this.statuses.get(i).getID();
    }

    private int setTypeID(int typeOrder) {

        int i = 0;
        for (IssueType type: this.types) {
            if (type.getOrder() == typeOrder) {
                return type.getID();
            }
            i++;
        }
        return this.types.get(i).getID();
    }
}