/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiclient;

import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;

import remoteinterface.RemoteInterface;

import java.io.IOException;
import java.rmi.ConnectException;

/**
 *
 * @author ISHRAK
 */
public class RMIClient
{
    private static long houseID = 1;
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NotBoundException
    {        
        String policyLocation = "file:///" + System.getProperty("user.dir") + "/rmi.policy";//FXMLDocumentController.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        System.out.println(policyLocation);
        
        System.setProperty("java.security.policy", policyLocation);
        System.setProperty("java.rmi.server.hostname","192.168.2.5");
        
        if(System.getSecurityManager() == null)
        {
            System.setSecurityManager(new SecurityManager());
        }
        
        try
        {
            String name = "remote swag";
            
            Registry registry = LocateRegistry.getRegistry("192.168.2.5",41127);
            RemoteInterface remoteObject = (RemoteInterface)registry.lookup(name);

            int x = remoteObject.testFunction(12);
            
            System.out.println(x);
            
            common.House h = remoteObject.retrieveHouseInformation(houseID);
            commonExtension.House hExtension = (commonExtension.House)h;
            h.displayHouse();
            
            int i,j;
            
            for(i=0;i<h.getNumberOfRooms();i++)
            {
                h.getRoomArrayBasic()[i].displayRoom();
                
                for(j=0;j<h.getRoomArrayBasic()[i].getNumberOfAppliances();j++)
                {
                    h.getRoomArrayBasic()[i].getApplianceArrayBasic()[j].displayAppliance();
                }
            }
            
            RMIClient rmiSync = new RMIClient();
            
            while(true)
            {
                rmiSync.sync(remoteObject,houseID,hExtension.getNumberOfAppliances());
                
                System.out.println("HALT");
                
                Thread.sleep(3000);
            }
        }
        catch(RemoteException e)
        {
            System.out.println("Client exception");
            e.printStackTrace();
        }
        catch(IndexOutOfBoundsException ex)
        {
            System.out.println("Check number of rooms - Exception");
        }
        catch(Exception ex)
        {
            System.out.println("Client exception");
            ex.printStackTrace();
        }
    }
    
    public void sync(RemoteInterface remoteObject,long hID,int numberOfAppliances) throws NotBoundException,IOException
    {
        common.Appliance[] newApplianceArray = null;
        
        try
        {
            newApplianceArray = remoteObject.syncAppliance(hID, numberOfAppliances);
            
            for(common.Appliance aCommon:newApplianceArray)
            {
                commonExtension.Appliance aCommonExtension = (commonExtension.Appliance)aCommon;
                
                aCommonExtension.sendCommand();
            }
        }
        catch(ConnectException ex)
        {
            System.out.println("Syncing - Exception");
            System.out.println("Attempting to reconnect");
            
            try
            {
                String name = "remote swag";
            
                Registry registry = LocateRegistry.getRegistry("localhost",41127);
                remoteObject = (RemoteInterface)registry.lookup(name);
                
                System.out.println("Reconnected");
            }
            catch(RemoteException exNested)
            {
                System.out.println("Nested Sycning - Exception");
                exNested.printStackTrace();
            }
        }
        catch(RemoteException ex)
        {
            System.out.println("Syncing - Exception");
            ex.printStackTrace();
        }
        finally
        {
            newApplianceArray = null;
        }
    }
}
