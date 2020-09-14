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
public class Room extends common.Room
{
    private static final long serialVersionUID = 1L;
    
    private Appliance[] applianceArray;
    
    public Room(int roomID)
    {
        super(roomID);
        
        this.applianceArray = (Appliance[])this.getApplianceArrayBasic();
    }
}
