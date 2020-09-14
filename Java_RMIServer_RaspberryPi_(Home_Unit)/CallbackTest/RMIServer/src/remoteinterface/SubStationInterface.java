/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteinterface;

import commonExtension.House;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.io.Serializable;

/**
 *
 * @author ISHRAK
 */
public interface SubStationInterface extends Remote, Serializable
{
    static final long serialVersionUID = 1L;
    
    public int testFunction(int x) throws RemoteException;
    public void receiveString(String data) throws RemoteException;
    public void updateAppliance(common.Appliance commonAppliance) throws RemoteException;
}
