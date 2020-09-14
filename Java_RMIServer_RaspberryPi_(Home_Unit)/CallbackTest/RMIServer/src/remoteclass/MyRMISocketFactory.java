package remoteclass;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ISHRAK
 */
public class MyRMISocketFactory  extends RMISocketFactory
{
    private static final int PREFERRED_PORT = 41127;
    
    @Override
    public ServerSocket createServerSocket(int port) throws IOException
    {
        return new ServerSocket(PREFERRED_PORT);
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException 
    {
        return RMISocketFactory.getDefaultSocketFactory().createSocket(host, port);
    }
}
