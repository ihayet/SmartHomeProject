/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteinterface;

import commonExtension.House;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ISHRAK
 */
public interface RemoteInterface extends Remote
{
    public int testFunction(int x) throws RemoteException;
    public common.House retrieveHouseInformation(long hID) throws RemoteException;
    public common.Appliance[] syncAppliance(long hID,int numberOfAppliances) throws RemoteException;
    public void successfulUpdate(long hID,int rID,int appID) throws RemoteException;
    public void buttonOverride(long hID,int rID,int appID,int status) throws RemoteException;
}
