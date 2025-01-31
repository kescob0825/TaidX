package memoranda.ui;

import memoranda.Start;
import memoranda.api.Credentials;
import memoranda.api.TaigaClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.Image;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import memoranda.util.Local;

public class TaigaLoginDialog extends JDialog {

    public boolean CANCELLED = true;
    private TaigaClient client;
    GridBagConstraints gbc;
    JPanel topPanel = new JPanel(new FlowLayout());
    JLabel taigaHeader = new JLabel();
    JPanel centerPanel = new JPanel(new GridBagLayout());
    public JTextField userNameField = new JTextField();
    JLabel userNameLabel = new JLabel();
    private JPasswordField passwordField = new JPasswordField();
    JLabel passwordLabel = new JLabel();
    JButton forgotPasswordButton = new JButton();
    JButton createAccountButton = new JButton();
    JPanel bottomPanel = new JPanel();
    JButton loginButton = new JButton();
    JButton cancelButton = new JButton();
    JFrame frame;
    @Inject
    public TaigaLoginDialog(JFrame frame) {
        this.frame = frame;
        this.setTitle(Local.getString("Taiga Login"));
        this.client = Start.getInjector().getInstance(TaigaClient.class);
        try {
            jbInit();
            pack();
        }
        catch(Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    void jbInit() throws Exception {
        this.setResizable(false);
        topPanel.setBorder(new EmptyBorder(new Insets(0, 5, 0, 5)));
        topPanel.setBackground(UIManager.getColor("control"));
        ImageIcon taigaLogo = new ImageIcon(Objects.requireNonNull(App.class.getResource("/ui/taiga_banner.jpg")));
        int bannerH = 110, bannerW = 380;
        Image originalImage = taigaLogo.getImage();
        Image resizedImage = originalImage.getScaledInstance(bannerW, bannerH, Image.SCALE_SMOOTH);
        ImageIcon resizedTaigaLogo = new ImageIcon(resizedImage);
        topPanel.setSize(bannerW, bannerH);
        taigaHeader.setSize(bannerW, bannerH);
        taigaHeader.setIcon(resizedTaigaLogo);
        topPanel.add(taigaHeader);
        // Username label
        centerPanel.setBorder(new EtchedBorder());
        userNameLabel.setText(Local.getString("Username"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        centerPanel.add(userNameLabel, gbc);
        // Username text field
        userNameField.setPreferredSize(new Dimension(270, 20));
        gbc = new GridBagConstraints();
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(userNameField, gbc);
        // Password label
        centerPanel.setBorder(new EtchedBorder());
        passwordLabel.setText(Local.getString("Password"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        centerPanel.add(passwordLabel, gbc);
        // Password text field
        gbc = new GridBagConstraints();
        gbc.gridwidth = 20;
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(passwordField, gbc);
        // Forgot password button
        gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 4;
        forgotPasswordButton.setText(Local.getString("Forgot Password"));
        forgotPasswordButton.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(forgotPasswordButton, gbc);
        forgotPasswordButton.addActionListener(this::forgotPasswordButton_actionPerformed);
        // Create Account button (DELETE THIS BLOCK IF WERE NOT IMPLEMENTING THIS)
        //bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        createAccountButton.setPreferredSize(new Dimension(150, 25));
        createAccountButton.setText(Local.getString("Create Account"));
        createAccountButton.addActionListener(this::createAccountButton_actionPerformed);
        // Login button
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        loginButton.setMaximumSize(new Dimension(100, 25));
        loginButton.setMinimumSize(new Dimension(100, 25));
        loginButton.setPreferredSize(new Dimension(100, 25));
        loginButton.setText(Local.getString("Login"));
        loginButton.addActionListener(this::loginButton_actionPerformed);
        this.getRootPane().setDefaultButton(loginButton);
        // Cancel button
        cancelButton.setPreferredSize(new Dimension(100, 25));
        cancelButton.setText(Local.getString("Cancel"));
        cancelButton.addActionListener(this::cancelButton_actionPerformed);
        bottomPanel.add(createAccountButton);
        bottomPanel.add(loginButton);
        bottomPanel.add(cancelButton);

        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(topPanel, "North");

        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        getContentPane().add(centerPanel, "Center");

        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(bottomPanel, "South");

        this.setSize(400, 310);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    void loginButton_actionPerformed(ActionEvent e) {

        try {
            loginMember();
            this.dispose();
        }
        catch (IllegalStateException | IOException isioe) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password. Please double-check your credentials.",
                    "Authentication Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
        catch (InvalidUsernameException iune) {
            iune.fillInStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "No username provided. Please make sure to provide a valid username.",
                    "Authentication Failed",
                    JOptionPane.ERROR_MESSAGE);
            iune.fillInStackTrace();
        }
        catch (InvalidPasswordException ipwe) {
            ipwe.fillInStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "No password provided. Please make sure to provide a valid password.",
                    "Authentication Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    void cancelButton_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    void forgotPasswordButton_actionPerformed(ActionEvent e) {
        try {
            Desktop.getDesktop().browse(new URI("https://tree.taiga.io/forgot-password"));
        } catch (IOException | URISyntaxException iourie) {
            iourie.fillInStackTrace();
        }
    }

    void createAccountButton_actionPerformed(ActionEvent e) {
        try {
            Desktop.getDesktop().browse(new URI("https://tree.taiga.io/register"));
        } catch (IOException | URISyntaxException iourie) {
            iourie.fillInStackTrace();
        }
    }

    public void loginMember() throws IOException {

        if (this.userNameField.getText().isEmpty())
            throw new InvalidUsernameException("No username provided");
        if (this.passwordField.getText().isEmpty())
            throw new InvalidPasswordException("No password provided");

        String username = this.userNameField.getText();
        String password = this.passwordField.getText();
        try {
            //////////////////////////////////////////////////////////////////////////////////////
            // TODO: ********REMOVE BEFORE DEPLOYED TO MASTER ONLY FOR TESTING**********
            if(Objects.equals(this.userNameField.getText(), "test") && Objects.equals(this.passwordField.getText(), "test")) {
                Credentials creds = new Credentials();
                username = creds.getUsername();
                password = creds.getPassword();
            }
            //////////////////////////////////////////////////////////////////////////////////////
            client.authenticateClient(username, password);
            //////////////////////////////////////////////////////////////////////////////////////
            // TODO: Print statement below is added for debugging purpose
            System.out.println("Authentication successful. Token: " + client.getAuthToken());
            //////////////////////////////////////////////////////////////////////////////////////
            // Stack trace will be maintained. Throwing another to trigger error handler.
            if (client.getAuthToken().isEmpty()) {
                throw new IOException();
            }
        } catch (IllegalStateException | IOException isioe) {
            throw isioe;
        }
    }

    // Exception classes
    private static class InvalidUsernameException extends IllegalArgumentException {
        public InvalidUsernameException(String message) {
            super(message);
        }
    }
    private static class InvalidPasswordException extends IllegalArgumentException {
        public InvalidPasswordException(String message) {
            super(message);
        }
    }
}
