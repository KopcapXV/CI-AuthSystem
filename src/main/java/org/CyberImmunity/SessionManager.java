package org.CyberImmunity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager
{
    public final Map <String, Session>                   activeSessions  = new HashMap<>();
    public static final Map <String, BruteForceTracker>  activeAttempts  = new HashMap<>();
    public static final Map <String, BruteForceTracker>  ipAttempts      = new HashMap<>();

    public String CreateSession (String username, String ip)
    {
        String token = UUID.randomUUID().toString();
        LocalDateTime createdAtStamp = LocalDateTime.now();
        LocalDateTime expiresAtStamp = LocalDateTime.now().plusMinutes(30);
        Session session = new Session(username, ip, createdAtStamp, expiresAtStamp);
        activeSessions.put(token, session);
        return token;
    }

    public boolean ValidateSession (String token, String ip)
    {
        Session session = activeSessions.get(token);

        if (session == null)
        {
            return false;
        }

        if (session.ipAddress.equals(ip))
        {
            return false;
        }

        if (session.expiresAt.isBefore(LocalDateTime.now()))
        {
            activeSessions.remove(token);
            return false;
        }

        return true;
    }
}
