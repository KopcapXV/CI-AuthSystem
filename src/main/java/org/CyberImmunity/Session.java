package org.CyberImmunity;

import java.time.LocalDateTime;

public class Session
{
    String          username;
    String          ipAddress;
    LocalDateTime   createdAt;
    LocalDateTime   expiresAt;

    Session (String username, String ipAddress, LocalDateTime createdAt, LocalDateTime expiresAt)
    {
        this.username  = username;
        this.ipAddress = ipAddress;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }
}
