package org.CyberImmunity;

public class AuthFeedback
{
    private final boolean   response;
    private final String    message;

    public AuthFeedback (boolean response, String message)
    {
       this.response = response;
       this.message  = message;
    }

    public boolean GetResponse () { return response; }
    public String GetMessage() { return message; }
}
