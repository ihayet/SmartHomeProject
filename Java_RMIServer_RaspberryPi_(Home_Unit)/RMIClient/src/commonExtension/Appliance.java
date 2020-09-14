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
    
    public Appliance(int applianceID)
    {
        super(applianceID);
    }
    
    public int sendCommand()
    {
        System.out.println(houseID + " " + roomID + " " + id + " " + applianceName + " " + powerControlAbility + " " + status + " " + changeExecuted);
        
        return 0;
    }
}
