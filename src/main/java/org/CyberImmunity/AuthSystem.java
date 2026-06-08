package org.CyberImmunity;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;

public class AuthSystem
{
    Database database = new Database();
    AccountDataValidator accountDataValidator = new AccountDataValidator();
    SessionManager sessionManager = new SessionManager();

    AuthSystem ()
    {
        database.InitializeDatabase();
    }

    public AuthFeedback RegisterUser (String username, String password)
    {
        if (!accountDataValidator.ValidateUsername(username))
        {
            System.out.println("System % You cant use this username. Try something different.");
            return new AuthFeedback(false, "You cant use this username. Try something different.");
        };

        if (!accountDataValidator.ValidatePassword(password))
        {
            System.out.println("System % You cant use this password. This password is too weak.");
            return new AuthFeedback(false, "You cant use this password. This password is too weak.");
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        boolean requestResponse = database.TryRegisterUser(username, hashedPassword);

        if (requestResponse)
        {
            System.out.println("System % You have been successfully registered");
            return new AuthFeedback(true, sessionManager.CreateSession(username, password));
        }
        else
        {
            System.out.println("System % Couldnt register you. Maybe account with that username already exits.");
            return new AuthFeedback(false, "Couldnt register you. Maybe account with that username already exits.");
        }
    }

    public AuthFeedback Login (String username, String password)
    {
        if (!accountDataValidator.ValidateUsername(username))
        {
            System.out.println("System % Incorrect login or password.");
            return new AuthFeedback(false, "Incorrect login or password.");
        };

        if (!accountDataValidator.ValidatePassword(password))
        {
            System.out.println("System % Incorrect login or password.");
            return new AuthFeedback(false, "Incorrect login or password");
        }

        BruteForceTracker userTracker = SessionManager.activeAttempts.getOrDefault(username, new BruteForceTracker());
        if (userTracker.CheckIsBlocked())
        {
            System.out.println("System % Account is temporary blocked. Try again later.");
            return new AuthFeedback(false, "Account is temporary blocked. Try again later.");
        }

        BruteForceTracker ipTracker = SessionManager.activeAttempts.getOrDefault(username, new BruteForceTracker());
        if (ipTracker.CheckIsBlocked())
        {
            System.out.println("System % Your ip temporary blocked. Try again later.");
            return new AuthFeedback(false, "Your ip temporary blocked. Try again later.");
        }

        boolean requestAnswer = database.TryLoginUser(username, password);

        if (requestAnswer)
        {
            userTracker.Reset();
            ipTracker.Reset();
            System.out.println("System % You logged in successfully.");
            return new AuthFeedback(true, sessionManager.CreateSession(username, password));
        }
        else
        {
            userTracker.Increment();
            SessionManager.activeAttempts.put(username, userTracker);

            ipTracker.Increment();
            SessionManager.ipAttempts.put(username, ipTracker);

            System.out.println("System & Incorrect username or password");
            return new AuthFeedback(false, "Incorrect username or password");
        }
    }
}


