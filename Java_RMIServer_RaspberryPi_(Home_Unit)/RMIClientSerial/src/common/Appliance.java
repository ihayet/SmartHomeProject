/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.Serializable;

/**
 *
 * @author ISHRAK
 */
public abstract class Appliance implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    protected int id;
    protected String applianceName;
    protected int roomID;
    protected long houseID;
    
    protected int powerControlAbility;
    protected int status;
    protected int changeExecuted;
    
    //-------------------------------------------------------------------------
    public int getID()
    {
        return id;
    }
    public int getRoomID()
    {
        return roomID;
    }
    public long getHouseID()
    {
        return houseID;
    }
    
    //-------------------------------------------------------------------------
    public int getPowerControlAbility()
    {
        return powerControlAbility;
    }
    public int getStatus()
    {
        return status;
    }
    public int getChangeExecuted()
    {
        return changeExecuted;
    }
    
    //-------------------------------------------------------------------------
    public Appliance(int applianceID)
    {
        id = applianceID;
    }
    
    public void displayAppliance()
    {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Appliance ID: " + id);
        System.out.println("Name: " + applianceName);
        System.out.println("House ID: " + houseID);
        System.out.println("Room ID: " + roomID);
        System.out.print("Power Control Ability: ");
        if(powerControlAbility == 1)
        {
            System.out.println("true");
        }
        else
        {
            System.out.println("false");
        }
        System.out.println("Status: " + status);
        System.out.print("Change Executed: ");
        if(changeExecuted == 1)
        {
            System.out.println("true");
        }
        else
        {
            System.out.println("false");
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
    
    @Override
    public String toString()
    {
        String temp = id + " " + applianceName;
        
        return temp;
    }
}
