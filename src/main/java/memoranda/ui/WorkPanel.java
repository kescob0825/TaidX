package memoranda.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.*;

import memoranda.ui.mainMenuCards.HomeToolBarCards;
import memoranda.ui.mainMenuCards.IssuesToolBarCards;
import memoranda.ui.mainMenuCards.ScrumToolBarCards;
import memoranda.ui.mainMenuCards.StatsToolBarCards;
import memoranda.util.Context;
import memoranda.util.Local;

import static memoranda.ui.App.*;
import static memoranda.ui.mainMenuCards.HomeToolBarCards.*;
import static memoranda.ui.mainMenuCards.ScrumToolBarCards.*;
import static memoranda.ui.mainMenuCards.IssuesToolBarCards.*;
import static memoranda.ui.mainMenuCards.StatsToolBarCards.*;

/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: WorkPanel.java,v 1.9 2004/04/05 10:05:44 alexeya Exp $*/
public class WorkPanel extends JPanel {
	BorderLayout borderLayout1 = new BorderLayout();
	JToolBar toolBar = new JToolBar();
	JPanel panel = new JPanel();

	CardLayout cardLayout;
	CardLayout mainCardLayout;
	private HomeToolBarCards homeCards;
	private ScrumToolBarCards scrumCards;
	private IssuesToolBarCards issuesCards;
	private StatsToolBarCards statsCards;

	// Define colors for the menu
	public JButton homeB = createMenuButton("Home", "/ui/icons/taiga50.png");
	public JButton scrumB = createMenuButton("Scrum", "/ui/icons/scrum.png");
	public JButton issuesB = createMenuButton("Issues", "/ui/icons/bug.png");
	public JButton statsB = createMenuButton("Stats", "/ui/icons/stats.png");

	private JButton currentB = null;
	private JButton lastB = null;
	JPanel homeSubToolBar;
	JPanel scrumSubToolBar;
	JPanel issuesSubToolBar;
	JPanel statsSubToolBar;

	public boolean isScrumExpanded = false;
	public boolean isIssuesExpanded = false;
	public boolean isHomeExpanded = false;
	public boolean isStatsExpanded = false;

	/**
	 * Buttons for the submenus
	 */
	//Home
	JButton overviewButton = createSubmenuButton("Overview");
	JButton profileButton = createSubmenuButton("Profile");
	JButton projectsButton = createSubmenuButton("Projects");
	JButton configureButton = createSubmenuButton("Configure Projects");
	//Scrum
	JButton sprintButton = createSubmenuButton("Sprint Planning");
	JButton backlogButton = createSubmenuButton("Product Backlog");
	JButton boardButton = createSubmenuButton("Scrum Board");
	//Issues
	JButton issuesOverviewButton = createSubmenuButton("Overview");
	JButton createIssueButton = createSubmenuButton("Create Issue");
	JButton closeIssueButton = createSubmenuButton("Close Issue");
	//Stats
	JButton statsOverviewButton = createSubmenuButton("Overview");
	JButton individualStatsButton = createSubmenuButton("Individual Stats");
	JButton teamStatsButton = createSubmenuButton("Team Stats");

	public WorkPanel() {
		try {
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}

	void jbInit() throws Exception {
		cardLayout = new CardLayout();
		panel = new JPanel(cardLayout);
		homeCards = new HomeToolBarCards(cardLayout, panel);
		scrumCards = new ScrumToolBarCards(cardLayout, panel);
		issuesCards = new IssuesToolBarCards(cardLayout, panel);
		statsCards = new StatsToolBarCards(cardLayout, panel);

		mainCardLayout = new CardLayout();
		this.setLayout(borderLayout1);

		AppFrame.rightPanel.setLayout(mainCardLayout);
		AppFrame.rightPanel.add(homeCards, "HOME");
		AppFrame.rightPanel.add(scrumCards, "SCRUM");
		AppFrame.rightPanel.add(issuesCards, "ISSUES");
		AppFrame.rightPanel.add(statsCards, "STATS");

		// Configure toolbar
		toolBar.setOrientation(JToolBar.VERTICAL);
		toolBar.setBorderPainted(false);
		toolBar.setFloatable(false);

		toolBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.LIGHT_GRAY));

		//configure subToolBar's
		homeSubToolBar = createHomeSubmenu();
		homeSubToolBar.setVisible(false);

		scrumSubToolBar = createScrumSubmenu();
		scrumSubToolBar.setVisible(false);

		issuesSubToolBar = createIssuesSubmenu();
		issuesSubToolBar.setVisible(false);

		statsSubToolBar = createStatsSubmenu();
		statsSubToolBar.setVisible(false);

		panel.setLayout(cardLayout);

		homeB.addActionListener(this::homeB_actionPerformed);
		scrumB.addActionListener(this::scrumB_actionPerformed);
		issuesB.addActionListener(this::issuesB_actionPerformed);
		statsB.addActionListener(this::statsB_actionPerformed);

		this.add(toolBar, BorderLayout.WEST);
		this.add(panel, BorderLayout.CENTER);

		toolBar.add(homeB);
		toolBar.add(scrumB);
		toolBar.add(issuesB);
		toolBar.add(statsB);

		// Set initial selection
		currentB = homeB;
		setCurrentButton(homeB);
		AppFrame.rightPanel.revalidate();
		AppFrame.rightPanel.repaint();
	}

	private JButton createMenuButton(String text, String iconPath) {
		JButton button = new JButton();
		button.setMaximumSize(new Dimension(200, 60));
		button.setMinimumSize(new Dimension(200, 60));
		button.setPreferredSize(new Dimension(200, 60));

		button.setFont(new Font("Dialog", Font.BOLD, 12));
		button.setText(Local.getString(text));
		button.setIcon(new ImageIcon(Objects.requireNonNull(
				AppFrame.class.getResource(iconPath))));

		button.setBorderPainted(false);
		button.setContentAreaFilled(true);
		button.setFocusPainted(false);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setIconTextGap(10); // space between icon and text

		return button;
	}

	private JButton createSubmenuButton(String text) {
		JButton button = new JButton(text);
		button.setMaximumSize(new Dimension(200, 50));
		button.setPreferredSize(new Dimension(200, 50));
		button.setBorderPainted(false);
		button.setContentAreaFilled(true);
		button.setFocusPainted(false);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		button.setForeground(UIManager.getColor("Button.foreground").darker());
		return button;
	}

	private JPanel createHomeSubmenu() {
		JPanel submenu = new JPanel();
		submenu.setLayout(new BoxLayout(submenu, BoxLayout.Y_AXIS));
		submenu.setOpaque(true);

		overviewButton.addActionListener(this::homeOverview_actionPerformed);
		profileButton.addActionListener(this::homeProfile_actionPerformed);
		projectsButton.addActionListener(this::homeProjects_actionPerformed);
		configureButton.addActionListener(this::homeConfigure_actionPerformed);
		
		submenu.add(overviewButton);
		submenu.add(profileButton);
		submenu.add(projectsButton);
		submenu.add(configureButton);
		return submenu;
	}

	private JPanel createScrumSubmenu() {
		JPanel submenu = new JPanel();
		submenu.setLayout(new BoxLayout(submenu, BoxLayout.Y_AXIS));
		submenu.setOpaque(true);

		sprintButton.addActionListener(this::scrumSprint_actionPerformed);
		backlogButton.addActionListener(this::scrumBacklog_actionPerformed);
		boardButton.addActionListener(this::scrumBoard_actionPerformed);
		
		submenu.add(sprintButton);
		submenu.add(backlogButton);
		submenu.add(boardButton);
		return submenu;
	}

	private JPanel createIssuesSubmenu() {
		JPanel submenu = new JPanel();
		submenu.setLayout(new BoxLayout(submenu, BoxLayout.Y_AXIS));
		submenu.setOpaque(true);

		issuesOverviewButton.addActionListener(this::issuesOverview_actionPerformed);
		createIssueButton.addActionListener(this::issuesCreate_actionPerformed);
		closeIssueButton.addActionListener(this::issuesClose_actionPerformed);
		
		submenu.add(issuesOverviewButton);
		submenu.add(createIssueButton);
		submenu.add(closeIssueButton);
		return submenu;
	}

	private JPanel createStatsSubmenu() {
		JPanel submenu = new JPanel();
		submenu.setLayout(new BoxLayout(submenu, BoxLayout.Y_AXIS));
		submenu.setOpaque(true);

		statsOverviewButton.addActionListener(this::statsOverview_actionPerformed);
		individualStatsButton.addActionListener(this::statsIndividual_actionPerformed);
		teamStatsButton.addActionListener(this::statsTeam_actionPerformed);
		
		submenu.add(statsOverviewButton);
		submenu.add(individualStatsButton);
		submenu.add(teamStatsButton);
		return submenu;
	}

	/**
	 * Main Menu Actions
	 *
	 */
	public void homeB_actionPerformed (ActionEvent e){
		mainCardLayout.show(AppFrame.rightPanel, "HOME");
		isHomeExpanded = !isHomeExpanded;
		homeSubToolBar.setVisible(isHomeExpanded);

		// Remove and re-add components to refresh the layout
		toolBar.removeAll();

		toolBar.add(homeB);
		if (isHomeExpanded) {
			toolBar.add(homeSubToolBar);
		}
		toolBar.add(scrumB);
		toolBar.add(issuesB);
		toolBar.add(statsB);

		toolBar.revalidate();
		toolBar.repaint();

		if (isHomeExpanded) {
			homeB.setBackground(UIManager.getColor("control"));
		} else {
			homeB.setBackground(UIManager.getColor("Button.shadow"));
		}
		setCurrentButton(homeB);
		Context.put("CURRENT_PANEL", "HOME");
	}

	public void scrumB_actionPerformed (ActionEvent e){
		mainCardLayout.show(AppFrame.rightPanel, "SCRUM");
		isScrumExpanded = !isScrumExpanded;
		scrumSubToolBar.setVisible(isScrumExpanded);

		// Remove and re-add components to refresh the layout
		toolBar.removeAll();

		toolBar.add(homeB);
		toolBar.add(scrumB);
		if (isScrumExpanded) {
			toolBar.add(scrumSubToolBar);
		}
		toolBar.add(issuesB);
		toolBar.add(statsB);

		toolBar.revalidate();
		toolBar.repaint();

		if (isScrumExpanded) {
			scrumB.setBackground(UIManager.getColor("control"));
		} else {
			scrumB.setBackground(UIManager.getColor("Button.shadow"));
		}
		setCurrentButton(scrumB);
		Context.put("CURRENT_PANEL", "SCRUM");
	}

	public void issuesB_actionPerformed (ActionEvent e){
		mainCardLayout.show(AppFrame.rightPanel, "ISSUES");
		isIssuesExpanded = !isIssuesExpanded;
		issuesSubToolBar.setVisible(isIssuesExpanded);

		// Remove and re-add components to refresh the layout
		toolBar.removeAll();

		toolBar.add(homeB);
		toolBar.add(scrumB);
		toolBar.add(issuesB);
		if (isIssuesExpanded) {
			toolBar.add(issuesSubToolBar);
		}
		toolBar.add(statsB);

		toolBar.revalidate();
		toolBar.repaint();

		if (isIssuesExpanded) {
			issuesB.setBackground(UIManager.getColor("control"));
		} else {
			issuesB.setBackground(UIManager.getColor("Button.highlight"));
		}
		setCurrentButton(issuesB);
		Context.put("CURRENT_PANEL", "ISSUES");
	}

	public void statsB_actionPerformed (ActionEvent e){
		mainCardLayout.show(AppFrame.rightPanel, "STATS");
		isStatsExpanded = !isStatsExpanded;
		statsSubToolBar.setVisible(isStatsExpanded);

		// Remove and re-add components to refresh the layout
		toolBar.removeAll();

		toolBar.add(homeB);
		toolBar.add(scrumB);
		toolBar.add(issuesB);
		toolBar.add(statsB);
		if (isStatsExpanded) {
			toolBar.add(statsSubToolBar);
		}

		toolBar.revalidate();
		toolBar.repaint();

		if (isStatsExpanded) {
			statsB.setBackground(UIManager.getColor("control"));
		} else {
			statsB.setBackground(UIManager.getColor("Button.highlight"));
		}
		setCurrentButton(statsB);
		Context.put("CURRENT_PANEL", "STATS");
	}


	/**
	 * SubMenu Actions
	 *
	 */
	public void homeOverview_actionPerformed (ActionEvent e){
		homeCards.showCard(OVERVIEW_PANEL);
		setCurrentButton(overviewButton);
		Context.put("CURRENT_PANEL", OVERVIEW_PANEL);
	}
	public void homeProfile_actionPerformed (ActionEvent e){
		homeCards.showCard(PROFILE_PANEL);
		setCurrentButton(profileButton);
		Context.put("CURRENT_PANEL", PROFILE_PANEL);
	}
	public void homeProjects_actionPerformed (ActionEvent e){
		homeCards.showCard(PROJECTS_PANEL);
		setCurrentButton(projectsButton);
		Context.put("CURRENT_PANEL", PROJECTS_PANEL);
	}
	public void homeConfigure_actionPerformed (ActionEvent e){
		homeCards.showCard(CONFIGURE_PANEL);
		setCurrentButton(configureButton);
		Context.put("CURRENT_PANEL", CONFIGURE_PANEL);
	}
	public void scrumSprint_actionPerformed (ActionEvent e){
		scrumCards.showCard(SPRINT_PANEL);
		setCurrentButton(sprintButton);
		Context.put("CURRENT_PANEL", "SPRINT");
	}
	public void scrumBacklog_actionPerformed (ActionEvent e){
		scrumCards.showCard(BACKLOG_PANEL);
		setCurrentButton(backlogButton);
		Context.put("CURRENT_PANEL", "BACKLOG");
	}
	public void scrumBoard_actionPerformed (ActionEvent e){
		scrumCards.showCard(BOARD_PANEL);
		setCurrentButton(boardButton);
		Context.put("CURRENT_PANEL", "BOARD");
	}
	public void issuesOverview_actionPerformed (ActionEvent e){
		issuesCards.showCard(ISSUE_OVERVIEW);
		setCurrentButton(issuesOverviewButton);
		Context.put("CURRENT_PANEL", "ISSUES_OVERVIEW");
	}
	public void issuesCreate_actionPerformed (ActionEvent e){
		issuesCards.showCard(CREATE_ISSUE);
		setCurrentButton(createIssueButton);
		Context.put("CURRENT_PANEL", "CREATE_ISSUES");
	}
	public void issuesClose_actionPerformed (ActionEvent e){
		issuesCards.showCard(CLOSE_ISSUE);
		setCurrentButton(closeIssueButton);
		Context.put("CURRENT_PANEL", "CLOSE_ISSUES");
	}
	public void statsOverview_actionPerformed (ActionEvent e){
		statsCards.showCard(STATS_OVERVIEW);
		setCurrentButton(statsOverviewButton);
		Context.put("CURRENT_PANEL", "STATS_OVERVIEW");
	}
	public void statsIndividual_actionPerformed (ActionEvent e){
		statsCards.showCard(IND_STATS);
		setCurrentButton(individualStatsButton);
		Context.put("CURRENT_PANEL", "IND_STATS");
	}
	public void statsTeam_actionPerformed (ActionEvent e){
		statsCards.showCard(TEAM_STATS);
		setCurrentButton(teamStatsButton);
		Context.put("CURRENT_PANEL", "TEAM_STATS");
	}

	private void initState(){
		toolBar.removeAll();
		toolBar.add(homeB);
		if (isHomeExpanded) {
			toolBar.add(homeSubToolBar);
		}
		toolBar.add(scrumB);
		if (isScrumExpanded) {
			toolBar.add(scrumSubToolBar);
		}
		toolBar.add(issuesB);
		if (isIssuesExpanded) {
			toolBar.add(issuesSubToolBar);
		}
		toolBar.add(statsB);
		if (isStatsExpanded) {
			toolBar.add(statsSubToolBar);
		}
		toolBar.revalidate();
		toolBar.repaint();
	}



	void setCurrentButton (JButton cb){
		currentB.setBackground(UIManager.getColor("control"));

		if(cb == homeB || cb == scrumB || cb == issuesB || cb == statsB) {
//			System.out.println("Home: "+isHomeExpanded);
//			System.out.println("Scrum: "+isScrumExpanded);
//			System.out.println("Issues: "+isIssuesExpanded);
//			System.out.println("Stats: "+isStatsExpanded);
//			System.out.println();
			if(isHomeExpanded || isScrumExpanded || isIssuesExpanded || isStatsExpanded){
				initState();
			}
		}
		if(App.getFrame() != null && App.getFrame().workPanel != null) {
			updateLookAndFeel2(getFrame().workPanel);
		}
		SwingUtilities.invokeLater(() -> {
			try {
				currentB = cb;
				currentB.setBackground(UIManager.getColor("Button.highlight"));
			} catch (Exception e) {
				new ExceptionDialog(e);
			}
		});
	}

}
