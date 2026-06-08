package org.CyberImmunity;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class Database
{
    private final String DB_URL = "jdbc:sqlite:users.db";

    public void InitializeDatabase ()
    {
        try (Connection connection = DriverManager.getConnection(DB_URL))
        {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT)");
        }
        catch (SQLException exception)
        {
            System.out.println("DATABASE ERROR : SQL Init database exception " + exception + ".");
        }
    }

    public boolean TryRegisterUser (String username, String password)
    {
        try (Connection connection = DriverManager.getConnection(DB_URL))
        {
            String sqlStatement = "INSERT INTO users(username, password) VALUES(?, ?)";
            PreparedStatement prpStatement = connection.prepareStatement(sqlStatement);
            prpStatement.setString(1, username);
            prpStatement.setString(2, password);
            prpStatement.executeUpdate();
            System.out.println("DATABASE MSG : User " + username + " added successfully.");
            return true;
        }
        catch (SQLException exception)
        {
            System.out.println("DATABASE ERROR : User already exists.");
            return false;
        }
    }

    public boolean TryLoginUser (String username, String password)
    {
        try (Connection connection = DriverManager.getConnection(DB_URL))
        {
            String sqlStatement = "SELECT password FROM users WHERE username = ?";
            PreparedStatement prpStatement = connection.prepareStatement(sqlStatement);
            prpStatement.setString(1, username);
            ResultSet resultSet = prpStatement.executeQuery();

            if (resultSet.next())
            {
                String hash = resultSet.getString("password");
                if (BCrypt.checkpw(password, hash))
                {
                    System.out.println("DATABASE MSG : User found.");
                    return true;
                }
            }

            return false;
        }
        catch (SQLException exception)
        {
            System.out.println("DATABASE ERROR : Unknown interruption.");
            return false;
        }
    }
}
