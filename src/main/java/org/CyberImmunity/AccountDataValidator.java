package org.CyberImmunity;

import java.util.regex.Pattern;

public class AccountDataValidator
{
    public boolean ValidateUsername (String username)
    {
        String regex = "^[a-zA-Z0-9_-]{4,30}$";
        return Pattern.matches(regex, username);
    }

    public boolean ValidatePassword (String password)
    {
        if (password.length() < 8) return false;
        boolean hasUpper  = !password.equals(password.toLowerCase());
        boolean hasLower  = !password.equals(password.toUpperCase());
        boolean hasDigit  = password.matches(".*\\d.*");
        boolean hasSymbol = password.matches(".*[!@#$%^&*()_=+`~].*");
        boolean hasLetter = password.matches(".*[a-zA-Z].*");
        return (hasLetter && hasSymbol && hasDigit && hasLower && hasUpper);
    }
}
