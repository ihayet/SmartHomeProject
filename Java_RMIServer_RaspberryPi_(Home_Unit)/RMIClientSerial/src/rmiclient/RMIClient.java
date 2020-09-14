/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiclient;

import SerialComm.SerialComm;

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
    private static String host;
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NotBoundException
    {        
        host = args[0];
        
        SerialComm newComm = new SerialComm();
        newComm.initialize();
        
        newComm.th.start();
        System.out.println("Started");
        
        String policyLocation = "file:///" + System.getProperty("user.dir") + "/rmi.policy";
        System.out.println(policyLocation);
        
        System.setProperty("java.security.policy", policyLocation);
        System.setProperty("java.rmi.server.hostname",host);
        
        if(System.getSecurityManager() == null)
        {
            
            System.setSecurityManager(new SecurityManager());
        }
        
        try
        {
            String name = "remote swag";
            
            Registry registry = LocateRegistry.getRegistry(host,41127);
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
                rmiSync.sync(remoteObject,newComm,houseID,hExtension.getNumberOfAppliances());
                
                System.out.println("HALT");
                
                //rmiSync.buttonOverride(remoteObject,newComm.getInputLine());
                
                Thread.sleep(1000);
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
    
    public void sync(RemoteInterface remoteObject,SerialComm newComm,long hID,int numberOfAppliances) throws NotBoundException,IOException
    {
        common.Appliance[] newApplianceArray = null;
        
        try
        {
            newApplianceArray = remoteObject.syncAppliance(hID, numberOfAppliances);
            
            for(common.Appliance aCommon:newApplianceArray)
            {
                commonExtension.Appliance aCommonExtension = (commonExtension.Appliance)aCommon;
                
                if(aCommonExtension.sendCommand(newComm)==1)
                {
                    //remoteObject.successfulUpdate(hID,aCommonExtension.getRoomID(),aCommonExtension.getID());
                }
            }
        }
        catch(ConnectException ex)
        {
            System.out.println("Syncing - Exception");
            System.out.println("Attempting to reconnect");
            
            try
            {
                String name = "remote swag";
            
                Registry registry = LocateRegistry.getRegistry(host,41127);
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
    
    public void buttonOverride(RemoteInterface remoteObject,String inputLine)
    {
        int i,j,k,counter=0;
        char c;
        boolean complete = false;
        char[] bufferData = new char[10];
        int[] dataValue = new int[4];
        
        if(inputLine!=null)
        {
            for(i=0;i<4;i++,counter++)
            {
              for(k=0;k<10;k++)
              {
                bufferData[k] = '\0';
              }

              for(j=0;j<10;j++,counter++)
              {      
                c = inputLine.charAt(counter);

                if((c>=48 && c<=57) || c==',' || c=='!')
                {
                  bufferData[j] = c;

                  if(bufferData[j] == ',')
                  {
                    bufferData[j] = '\0';
                    break;
                  }
                  else if(bufferData[j] == '!')
                  {
                    bufferData[j] = '\0';
                    complete = true;
                    break;
                  }
                }
                else
                {
                  j--;
                }
              }
              
              String temp = new String(bufferData);
              dataValue[i] = Integer.parseInt(temp);

              if(complete == true)
              {
                break;
              }
            }
            
            try
            {
                remoteObject.buttonOverride(dataValue[0],dataValue[1],dataValue[2],dataValue[3]);
                System.out.println("Override: " + dataValue[0] + "," + dataValue[1] + "," + dataValue[2] + "," + dataValue[3]);
            }
            catch(RemoteException ex)
            {
                System.err.println(ex.toString());
            }   
        }
    }
}
