/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteclass;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import remoteinterface.RemoteInterface;
import remoteinterface.SubStationInterface;

import commonExtension.House;
import java.lang.instrument.Instrumentation;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.rmi.server.RMISocketFactory;
import java.util.Enumeration;
import java.util.HashMap;

import java.util.Scanner;

/**
 *
 * @author ISHRAK
 */
public class RemoteClass implements RemoteInterface
{
    /*private String host = "localhost:3306";
    private String dbName = "smart_home_system";
    private String userName = "root";
    private String passWord = "admin";*/
    
    private static String baseStationName = "Base Station";
    private static RemoteInterface remoteObject = null, stub = null;
    private static Registry registry = null;
    
    private static HashMap<Long, SubStationInterface> subStationContainer = null;
    
    public RemoteClass()
    {
        super();
    }
    
    public int testFunction(int x) throws RemoteException
    {
        return x*10;
    }
    
    public common.House retrieveHouseInformation(long hID) throws RemoteException
    {
        databaseModule newDatabaseModuleHouse = new databaseModule();
        
        return newDatabaseModuleHouse.getHouseInformation(hID);
    }
    
    public void successfulUpdate(long hID,int rID,int appID) throws RemoteException
    {
        databaseModule newDatabaseModuleSuccessfulUpdate = new databaseModule();
        
        newDatabaseModuleSuccessfulUpdate.successfulUpdate(hID, rID, appID);
    }
    
    public void buttonOverride(long hID,int rID,int appID,int status) throws RemoteException
    {
        databaseModule newDatabaseModuleButtonOverride = new databaseModule();
        
        newDatabaseModuleButtonOverride.buttonOverride(hID, rID, appID, status);
    }
    
    public void receiveSubStation(long houseID, Object subStationStub) throws RemoteException
    {
        try
        {
            SubStationInterface subStationObject = (SubStationInterface)subStationStub;
            subStationContainer.put(new Long(houseID), subStationObject);
            
            System.out.println("SubStation Stub received");
            System.out.println(subStationObject.toString());
            
            System.out.println(subStationObject.testFunction(22));
        }
        catch(Exception e)
        {
            System.out.println("SubStation Reception - Exception");
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws java.net.ConnectException
    {
        Scanner input = new Scanner(System.in);
        int anotherTest = 1;
        int test;
        
        subStationContainer = new HashMap<Long, SubStationInterface>();
        
        String policyLocation = "file:///" + System.getProperty("user.dir") + "/rmi.policy";
        System.out.println(policyLocation);
        
        System.setProperty("java.security.policy", policyLocation);
        System.setProperty("java.rmi.server.hostname","192.168.2.2");
        
        if(System.getSecurityManager() == null)
        {
            System.setSecurityManager(new SecurityManager());
        }
        
        try
        {
            initRegistry(baseStationName);
            
            test = 0;
            
            while(true)
            {
                try
                {
                    test = input.nextInt();   
                }
                catch(Exception e)
                {
                    System.out.println(e.getMessage());
                }
                
                if(test == 2)
                {
                    try
                    {
                        SubStationInterface subStationObject = (SubStationInterface)subStationContainer.get(new Long(1));
                    
                        System.out.println(subStationObject.testFunction(15));

                        anotherTest = 1 - anotherTest;
                        commonExtension.Appliance testApp = new commonExtension.Appliance(1,"appliance1",1,1,1,anotherTest,0);
                        subStationObject.updateAppliance(testApp);
                    }
                    catch(Exception e)
                    {
                        System.out.println(e.getMessage());
                        
                        subStationContainer.remove(new Long(1));
                    }
                }
                else if(test == 3)
                {
                    String testString = input.nextLine();
                    
                    SubStationInterface subStationObject = (SubStationInterface)subStationContainer.get(new Long(1));
                    
                    subStationObject.receiveString(testString);
                }
                else if(test == 4)
                {
                    try
                    {
                        int val = input.nextInt();
                        
                        SubStationInterface subStationObject = (SubStationInterface)subStationContainer.get(new Long(1));
                    
                        System.out.println(subStationObject.testFunction(15));
                        
                        commonExtension.Appliance testApp = new commonExtension.Appliance(2,"appliance1",1,1,1,val,0);
                        subStationObject.updateAppliance(testApp);
                    }
                    catch(Exception e)
                    {
                        System.out.println(e.getMessage());
                        
                        subStationContainer.remove(new Long(1));
                    }
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Remote Class Exception");
            e.printStackTrace();
        }
    }
    
    public static void initRegistry(String registryEntry)
    {
        try
        {
            RMISocketFactory.setSocketFactory(new MyRMISocketFactory());

            remoteObject = new RemoteClass();

            stub = (RemoteInterface)UnicastRemoteObject.exportObject(remoteObject,0);

            registry = LocateRegistry.createRegistry(41127);
            registry.rebind(registryEntry, stub); 

            System.out.println();
            System.out.println("Remote Class registered");
            System.out.println();
        }
        catch(Exception e)
        {
            System.out.println("Remote class registration - Exception");
            e.printStackTrace();
        }
    }
}
