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
public class House extends common.House
{
    private static final long serialVersionUID = 1L;
    
    private Room[] roomArray;
    
    private int numberOfAppliances;
    
    public House(int houseID,common.House retrievedHouse)
    {
        super(houseID);
        setHouseInformation(retrievedHouse);
    }
    
    private void setHouseInformation(common.House retrievedHouse)
    {
        this.ownerName = retrievedHouse.getOwnerName();
        this.contactNumber = retrievedHouse.getContactNumber();
        this.dateOfBirth = retrievedHouse.getDateOfBirth();
        this.bloodGroup = retrievedHouse.getBloodGroup();
        this.profession = retrievedHouse.getProfession();
        
        this.houseName = retrievedHouse.getHouseName();
        this.houseAddress = retrievedHouse.getHouseAddress();
        this.area = retrievedHouse.getArea();
        this.city = retrievedHouse.getCity();
        this.size = retrievedHouse.getSize();
        this.numberOfRooms = retrievedHouse.getNumberOfRooms();
        
        this.lock = retrievedHouse.getLock();
        this.key = retrievedHouse.getKey();
        
        this.serviceDiscovery = retrievedHouse.getServiceDiscovery();
        this.dateOfRegistration = retrievedHouse.getDateOfRegistration();
        
        this.roomArray = (Room[])retrievedHouse.getRoomArrayBasic();
    }
    
    public int getNumberOfAppliances()
    {
        int sum = 0;
        
        if(roomArrayBasic != null)
        {
            for(common.Room r:roomArrayBasic)
            {
                sum = sum + r.getNumberOfAppliances();
            }
        }
        
        return sum;
    }
}
