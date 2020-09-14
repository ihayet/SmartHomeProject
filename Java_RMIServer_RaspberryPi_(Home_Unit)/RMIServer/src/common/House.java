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
public abstract class House implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    protected long id;
    
    //Owner Information - start
    
    protected String ownerName;
    protected long contactNumber;
    protected String dateOfBirth;
    protected String bloodGroup;
    protected String profession;
    
    //Owner Information - end
    
    //-------------------------------------------------------------------------
    
    //House Information - start
    
    protected String houseName;
    protected String houseAddress;
    protected String area;
    protected String city;
    protected int size;
    protected int numberOfRooms;
    
    //House Information - end
    
    //-------------------------------------------------------------------------
    
    //Security Information - start
    
    protected String lock;
    protected String key;
    
    //Security Information - end
    
    //-------------------------------------------------------------------------
    
    //Business Intelligence - start
    
    protected String serviceDiscovery;
    protected String dateOfRegistration;
    
    //Business Intelligence - end
    
    //-------------------------------------------------------------------------
    
    //Operational Information - start

    protected Room[] roomArrayBasic;
    
    //Operational Information - end
    
    
    //-------------------------------------------------------------------------
    public long getID()
    {
        return id;
    }
    public String getOwnerName()
    {
        return ownerName;
    }
    public long getContactNumber()
    {
        return contactNumber;
    }
    public String getDateOfBirth()
    {
        return dateOfBirth;
    }
    public String getBloodGroup()
    {
        return bloodGroup;
    }
    public String getProfession()
    {
        return profession;
    }
    
    //-------------------------------------------------------------------------
    public String getHouseName()
    {
        return houseName;
    }
    public String getHouseAddress()
    {
        return houseAddress;
    }
    public String getArea()
    {
        return area;
    }
    public String getCity()
    {
        return city;
    }
    public int getSize()
    {
        return size;
    }
    public int getNumberOfRooms()
    {
        return numberOfRooms;
    }
    
    //-------------------------------------------------------------------------
    public String getLock()
    {
        return lock;
    }
    public String getKey()
    {
        return key;
    }
    public boolean checkLock(String lock)
    {
        return false;
    }
    public boolean checkKey(String key)
    {
        return false;
    }
    
    //-------------------------------------------------------------------------
    public String getServiceDiscovery()
    {
        return serviceDiscovery;
    }
    public String getDateOfRegistration()
    {
        return dateOfRegistration;
    }
    
    //-------------------------------------------------------------------------

    
    //-------------------------------------------------------------------------
    public House(long houseID)
    {
        id = houseID;
    }
    public Room[] getRoomArrayBasic()
    {
        return roomArrayBasic;
    }
    
    public void displayHouse()
    {
        System.out.println("#########################################");
        
        System.out.println("House ID: " + id + "\n");
        
        System.out.println("Owner Name: " + ownerName);
        System.out.println("ContactNumber: " + contactNumber);
        System.out.println("Date Of Birth: " + dateOfBirth);
        System.out.println("Blood Group: " + bloodGroup);
        System.out.println("Profession: " + profession + "\n");
        
        System.out.println("House Name: " + houseName);
        System.out.println("House Address: " + houseAddress);
        System.out.println("Area: " + area);
        System.out.println("City: " + city);
        System.out.println("Size: " + size);
        System.out.println("Number of Rooms: " + numberOfRooms + "\n");
        
        System.out.println("Lock: " + lock);
        System.out.println("Key: " + key + "\n");
        
        System.out.println("Service Discovery: " + serviceDiscovery);
        System.out.println("Date of Registration: " + dateOfRegistration);
        
        System.out.println("#########################################");
    }
    
    @Override
    public String toString()
    {
        String temp = id + " " + houseName;
        
        return temp;
    }
}
