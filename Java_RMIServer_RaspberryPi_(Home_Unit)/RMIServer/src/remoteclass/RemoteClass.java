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

import commonExtension.House;
import java.rmi.server.RMISocketFactory;

/**
 *
 * @author ISHRAK
 */
public class RemoteClass implements RemoteInterface
{
    /*private String host = "localhost:3306";
    private String dbName = "testdb";
    private String userName = "root";
    private String passWord = "admin";*/
    
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
        
        return newDatabaseModuleHouse.getHouseInformation(hID, DatabaseParameters.host, DatabaseParameters.dbName, DatabaseParameters.userName, DatabaseParameters.passWord);
    }
    
    public common.Appliance[] syncAppliance(long hID,int numberOfAppliances) throws RemoteException
    {
        databaseModule newDatabaseModuleSyncProcess = new databaseModule();
        
        return newDatabaseModuleSyncProcess.syncAppliance(hID, numberOfAppliances, DatabaseParameters.host, DatabaseParameters.dbName, DatabaseParameters.userName, DatabaseParameters.passWord);
    }
    
    public void successfulUpdate(long hID,int rID,int appID) throws RemoteException
    {
        databaseModule newDatabaseModuleSuccessfulUpdate = new databaseModule();
        
        newDatabaseModuleSuccessfulUpdate.successfulUpdate(hID, rID, appID, DatabaseParameters.host, DatabaseParameters.dbName, DatabaseParameters.userName, DatabaseParameters.passWord);
    }
    
    public void buttonOverride(long hID,int rID,int appID,int status) throws RemoteException
    {
        databaseModule newDatabaseModuleButtonOverride = new databaseModule();
        
        newDatabaseModuleButtonOverride.buttonOverride(hID, rID, appID, status, DatabaseParameters.host, DatabaseParameters.dbName, DatabaseParameters.userName, DatabaseParameters.passWord);
    }
    
    public static void main(String[] args)
    {
        String policyLocation = "file:///" + System.getProperty("user.dir") + "/rmi.policy";
        System.out.println(policyLocation);
        
        System.setProperty("java.security.policy", policyLocation);
        System.setProperty("java.rmi.server.hostname","192.168.2.4");
        
        if(System.getSecurityManager() == null)
        {
            System.setSecurityManager(new SecurityManager());
        }
        
        try
        {
            RMISocketFactory.setSocketFactory(new MyRMISocketFactory());
            
            String name = "Base Station";
            RemoteInterface remoteObject = new RemoteClass();
            
            RemoteInterface stub = (RemoteInterface)UnicastRemoteObject.exportObject(remoteObject,0);
            
            Registry registry = LocateRegistry.createRegistry(41127);
            registry.rebind(name, stub); 
            
            System.out.println("Remote Class registered");
        }
        catch(Exception e)
        {
            System.out.println("Remote Class Exception");
            e.printStackTrace();
        }
    }
}
