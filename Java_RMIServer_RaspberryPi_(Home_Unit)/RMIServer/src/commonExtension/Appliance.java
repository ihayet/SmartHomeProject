/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonExtension;

/**
 *
 * @author ISHRAK
 */
public class Appliance extends common.Appliance
{
    private static final long serialVersionUID = 1L;
    
    public Appliance(int applianceID,String aName,int rID,long hID,int pControl,int s,int cExecuted)
    {
        super(applianceID);
        
        applianceName = aName;
        roomID = rID;
        houseID = hID;
        
        powerControlAbility = pControl;
        status = s;
        changeExecuted = cExecuted;
    }
}
