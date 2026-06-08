package org.CyberImmunity;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AuthGUI extends JFrame
{
    private AuthSystem authSystem;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JPasswordField registrationPasswordField;
    private JPasswordField loginPasswordField;
    private JTextField registrationUserField;
    private JTextField loginUserField;
    private JLabel statusLabel;

    public AuthGUI ()
    {
        authSystem = new AuthSystem();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        setTitle("AuthSystem");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardPanel.add(CreateLoginPanel(), "login");
        cardPanel.add(CreateWelcomePanel(""), "welcome");

        add(cardPanel);
        setVisible(true);
    }

    private JPanel CreateLoginPanel ()
    {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        loginUserField = new JTextField();
        loginPasswordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        panel.add(new JLabel("Login: "));
        panel.add(loginUserField);
        panel.add(new JLabel("Password: "));
        panel.add(loginPasswordField);
        panel.add(loginButton);
        panel.add(registerButton);

        loginButton.addActionListener(action -> HandleLogin());
        registerButton.addActionListener(action -> HandleRegistration());

        return panel;
    }

    private JPanel CreateWelcomePanel (String username)
    {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
        JButton logoutButton = new JButton("Back");

        panel.add(welcomeLabel, BorderLayout.CENTER);
        panel.add(logoutButton, BorderLayout.SOUTH);

        logoutButton.addActionListener(action -> cardLayout.show(cardPanel, "login"));

        return panel;
    }

    private void HandleLogin () 
    {
        String username = loginUserField.getText();
        String password = new String(loginPasswordField.getPassword());
        AuthFeedback feedback = authSystem.Login(username, password);

        if (feedback.GetResponse())
        {
            System.out.println("Session token: " + feedback.GetMessage());
            cardPanel.add(CreateWelcomePanel(username), "welcome");
            cardLayout.show(cardPanel, "welcome");
        }
        else
        {
            JOptionPane.showMessageDialog(this, feedback.GetMessage());
        }
    }
    
    private void HandleRegistration () 
    {
        String username = loginUserField.getText();
        String password = new String(loginPasswordField.getPassword());
        AuthFeedback feedback = authSystem.RegisterUser(username, password);

        if (feedback.GetResponse())
        {
            System.out.println("Session token: " + feedback.GetMessage());
            cardPanel.add(CreateWelcomePanel(username), "welcome");
            cardLayout.show(cardPanel, "welcome");
        }
        else
        {
            JOptionPane.showMessageDialog(this, feedback.GetMessage());
        }
    }
}
