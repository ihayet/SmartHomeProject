/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonExtension;

import remoteclass.DatabaseParameters;
import remoteclass.databaseModule;

/**
 *
 * @author ISHRAK
 */
public class House extends common.House
{    
    private static final long serialVersionUID = 1L;
    
    public House(long houseID,String oName,long cNumber,String dob,String bGroup,String p,String hName,String hAddress,String a,String c,int s,int nRooms,String l,String k,String sDiscovery,String dRegistration)
    {
        super(houseID);
        
        ownerName = oName;
        contactNumber = cNumber;
        dateOfBirth = dob;
        bloodGroup = bGroup;
        profession = p;
        
        houseName = hName;
        houseAddress = hAddress;
        area = a;
        city = c;
        size = s;
        numberOfRooms = nRooms;
        
        lock = l;
        key = k;
        
        serviceDiscovery = sDiscovery;
        dateOfRegistration = dRegistration;
        
        databaseModule newDatabaseModuleRoom = new databaseModule();
        
        roomArrayBasic = newDatabaseModuleRoom.getRoomInformation(houseID, numberOfRooms);
    }
}
