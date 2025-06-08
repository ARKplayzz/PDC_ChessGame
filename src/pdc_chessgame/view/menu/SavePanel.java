/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame.view.menu;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import pdc_chessgame.ChessGame;
import pdc_chessgame.Database;

/**
 *
 * @author Andrew
 */
public class SavePanel extends JPanel {

    private final Database database;
    private final ChessGame controller;
    private final Runnable backCallback;

    private final JTextField usernameField;
    private final JLabel usernameStatus;
    private final JLabel usernameStatus2;
    private final JButton searchButton;
    private final JButton backButton;
    private final JPanel savesPanel;

    public SavePanel(Database database, ChessGame controller, Runnable backCallback) 
    {
        this.database = database;
        this.controller = controller;
        this.backCallback = backCallback;

        this.usernameField = new JTextField(10);
        this.usernameStatus = new JLabel();
        this.usernameStatus2 = new JLabel();
        this.searchButton = new JButton("Search Saves");
        this.backButton = new JButton("Back");
        this.savesPanel = new JPanel();

        initializePanel();
        setupEventHandlers();
        updateUserStatus();
    }

    private void initializePanel() 
    {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));

        JLabel title = new JLabel("Load Save");
        title.setForeground(new Color(153, 233, 255));
        title.setFont(new Font("Helvetica", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.PAGE_START);

        // username info panel
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setBackground(new Color(30, 30, 30));
        userPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(new Color(153, 233, 255));
        userLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField.setHorizontalAlignment(JTextField.CENTER);
        usernameField.setFont(new Font("Helvetica", Font.BOLD, 16));
        usernameField.setMaximumSize(new Dimension(200, 30));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        setupStatusLabels();
        usernameStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameStatus2.setAlignmentX(Component.CENTER_ALIGNMENT);

        userPanel.add(userLabel);
        userPanel.add(Box.createVerticalStrut(5));
        userPanel.add(usernameField);
        userPanel.add(Box.createVerticalStrut(5));
        userPanel.add(usernameStatus);
        userPanel.add(usernameStatus2);

        // Search button
        setupButton(searchButton);
        searchButton.setPreferredSize(new Dimension(0, 38));
        searchButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBackground(new Color(30, 30, 30));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        searchPanel.add(searchButton);

        // Saves panel
        savesPanel.setLayout(new BoxLayout(savesPanel, BoxLayout.Y_AXIS));
        savesPanel.setBackground(new Color(30, 30, 30));
        JScrollPane scrollPane = new JScrollPane(savesPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(153, 233, 255), 2),
            "Your Saves ",
            0, 0, null, new Color(153, 233, 255)
        ));
        scrollPane.setOpaque(false);
        scrollPane.setPreferredSize(new Dimension(350, 180));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        // panel to hold username, search button and saves panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(30, 30, 30));
        centerPanel.add(userPanel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(searchPanel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(scrollPane);

        add(centerPanel, BorderLayout.CENTER);

        // Back button at the bottom
        JPanel buttonGrid = new JPanel(new GridLayout(1, 1, 0, 1));
        buttonGrid.setBackground(new Color(30, 30, 30));
        setupButton(backButton);
        backButton.setPreferredSize(new Dimension(0, 38));
        backButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        buttonGrid.add(backButton);
        add(buttonGrid, BorderLayout.SOUTH);
    }

    private void setupStatusLabels() 
    {
        JLabel[] statusLabels = {this.usernameStatus, this.usernameStatus2};
        for (JLabel label : statusLabels) 
        {
            label.setForeground(new Color(153, 233, 255));
            label.setFont(new Font("Helvetica", Font.PLAIN, 14));
            label.setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    private void setupButton(JButton button) 
    {
        Color normalBg = new Color(40, 40, 40);
        Color hoverBg = new Color(50, 50, 50);
        Color textColor = new Color(153, 233, 255);

        button.setBackground(normalBg);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setMargin(new Insets(2, 2, 2, 2));
        button.setFont(new Font("Helvetica", Font.BOLD, 16));

        button.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) 
            {
                button.setBackground(hoverBg);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) 
            {
                button.setBackground(normalBg);
            }
        });
    }

    private void setupEventHandlers() 
    {
        usernameField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
        {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateUserStatus(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateUserStatus(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateUserStatus(); }
        });

        searchButton.addActionListener(e -> refreshSaves());
        backButton.addActionListener(e -> backCallback.run());
    }

    private void updateUserStatus() 
    {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) 
        {
            usernameStatus.setText("Please Enter your Username");
            usernameStatus2.setText("");
        } 
        else if (username.contains(" ")) 
        {
            usernameStatus.setText("Please ensure no spaces");
            usernameStatus2.setText("are used within your username");
        } 
        else if (username.length() < 4) 
        {
            usernameStatus.setText("Username must be");
            usernameStatus2.setText("more than 4 Characters");
        } 
        else if (username.length() > 16) 
        {
            usernameStatus.setText("Username must be");
            usernameStatus2.setText("less than 16 Characters");
        } 
        else if (!database.playerExists(username)) 
        {
            usernameStatus.setText(username + " has not played before");
            usernameStatus2.setText("(No saves will be found)");
        } 
        else 
        {
            usernameStatus.setText("Welcome, " + username + "!");
            usernameStatus2.setText("Search for your saves...");
        }
    }

    // refresh the saves list 
    public void refreshSaves() 
    {
        savesPanel.removeAll();
        String username = usernameField.getText().trim();
        
        if (username.isEmpty() || username.contains(" ") || username.length() < 4 || username.length() > 16) 
        {
            JLabel msg = new JLabel("Enter a valid username to search saves.");
            msg.setForeground(new Color(153, 233, 255));
            msg.setAlignmentX(Component.CENTER_ALIGNMENT);
            savesPanel.add(msg);
        } 
        else 
        {
            List<Database.SaveInfo> saves = database.getSavesForUser(username);
            
            if (saves.isEmpty()) 
            {
                JLabel noSaves = new JLabel("No saves found for this user.");
                noSaves.setForeground(new Color(153, 233, 255));
                noSaves.setAlignmentX(Component.CENTER_ALIGNMENT);
                savesPanel.add(noSaves);
            } 
            else 
            {
                for (Database.SaveInfo save : saves) 
                {
                    JButton saveBtn = new JButton(
                        String.format("%s vs %s | %s", save.player1, save.player2, save.saveFile)
                    );
                    setupButton(saveBtn);
                    saveBtn.setFont(new Font("Helvetica", Font.PLAIN, 14));
                    saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
                    saveBtn.addActionListener(e -> handleLoadSave(save.directory));
                    savesPanel.add(saveBtn);
                    savesPanel.add(Box.createVerticalStrut(5));
                }
            }
        }
        savesPanel.revalidate();
        savesPanel.repaint();
    }

    // loading the save and start the game
    private void handleLoadSave(String saveFile) 
    {
        boolean loaded = controller.loadGameFromSaveFile(saveFile);
        if (!loaded) 
        {
            JOptionPane.showMessageDialog(this, "This Game Save is not present on your computer", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        else 
        {
            refreshSaves(); // Refresh saves list after loading/removing a save
            backCallback.run();
        }
    }
}
