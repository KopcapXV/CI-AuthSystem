package org.CyberImmunity;

import javax.swing.*;

public class Main
{
    static void main(String[] args)
    {
        SwingUtilities.invokeLater(AuthGUI::new);

        AuthSystem authSystem = new AuthSystem();

        System.out.println("--------- REGISTRATION TEST ---------");
        authSystem.RegisterUser("admin_user1", "SafePass123!");
        authSystem.RegisterUser("hacker", "weak");

        System.out.println("--------- LOGIN TEST ---------");
        authSystem.Login("admin_user1", "SafePass123!");
        authSystem.Login("hacker", "weak");

        // SQLInjection simulation
        authSystem.Login("' OR '1'='1", "any_pass");

        // Bruteforce simulation
        for (short index = 0; index <= 1; index++)
        {
            authSystem.Login("admin_user1", "HackerTry123!");
        }
    }
}
