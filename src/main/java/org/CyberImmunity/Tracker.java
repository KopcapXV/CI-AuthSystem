package org.CyberImmunity;

public abstract class Tracker {

    int counter;

    public abstract void Increment ();

    public abstract void Reset();

    public int GetTracking ()
    {
        return counter;
    }
}
