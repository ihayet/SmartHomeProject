/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonExtension;

import SerialComm.SerialComm;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ISHRAK
 */
public class Appliance extends common.Appliance
{
    private static final long serialVersionUID = 1L;
    
    public Appliance(int applianceID)
    {
        super(applianceID);
    }
    
    public int sendCommand(SerialComm newComm)
    {
        System.out.println(houseID + " " + roomID + " " + id + " " + applianceName + " " + powerControlAbility + " " + status + " " + changeExecuted);
        
        if(changeExecuted == 0)
        {
            String temp = houseID + "," + roomID + "," + id + "," + status + "!";
        
            try 
            {
                newComm.out(temp);
                
                return 1;
            } 
            catch (IOException ex) 
            {
                System.err.println(ex.toString());
                
                return 0;
            }
            
        }
        
        return 0;
    }
}
