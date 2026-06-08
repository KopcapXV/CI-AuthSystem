package org.CyberImmunity;

import java.time.LocalDateTime;

public class BruteForceTracker extends Tracker
{
    private LocalDateTime   timeBlockUntil = LocalDateTime.MIN;

    @Override
    public void Increment ()
    {
        counter++;
        System.out.println("BRUTEFORCE TRACKER: incremented.");
        if (counter >= 5)
        {
            timeBlockUntil = LocalDateTime.now().plusMinutes(5);
        }
    }

    @Override
    public void Reset ()
    {
        counter = 0;
        timeBlockUntil = LocalDateTime.MIN;
    }

    boolean CheckIsBlocked ()
    {
        return LocalDateTime.now().isBefore(timeBlockUntil);
    }
}
