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
public class Room extends common.Room
{    
    private static final long serialVersionUID = 1L;
    
    public Room(int roomID,String rName,long hID,int nAppliances)
    {
        super(roomID);
        
        roomName = rName;
        houseID = hID;
        numberOfAppliances = nAppliances;
        
        databaseModule newDatabaseModuleAppliance = new databaseModule();
        applianceArrayBasic = newDatabaseModuleAppliance.getApplianceInformation(hID,numberOfAppliances,roomID);
    }
}
