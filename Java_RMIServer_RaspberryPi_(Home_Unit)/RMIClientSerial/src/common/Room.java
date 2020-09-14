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
public abstract class Room implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    //Room Information - start
    protected int id;
    protected long houseID;
    protected String roomName;
    protected int numberOfAppliances;
    //Room Information - end
    
    //Operational Information - start
    
    protected Appliance[] applianceArrayBasic;
    
    //Operational Information - end
   
    //-------------------------------------------------------------------------
    public int getID()
    {
        return id;
    }
    public long getHouseID()
    {
        return houseID;
    }
    public String getRoomName()
    {
        return roomName;
    }
    public int getNumberOfAppliances()
    {
        return numberOfAppliances;
    }
    
    //-------------------------------------------------------------------------
    public Room(int roomID)
    {
        id = roomID;
    }
    public Appliance[] getApplianceArrayBasic()
    {
        return applianceArrayBasic;
    }
    
    public void displayRoom()
    {
        System.out.println("********************");
        System.out.println("Room ID: " + id);
        System.out.println("House ID: " + houseID);
        System.out.println("Name: " + roomName);
        System.out.println("Number of Appliances: " + numberOfAppliances);
        System.out.println("********************");
    }
    
    @Override
    public String toString()
    {
        String temp = id + " " + roomName;
        
        return temp;
    }
}
