/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SerialComm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;

import java.util.Enumeration;   
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ISHRAK
 */
public class SerialComm implements SerialPortEventListener
{
    SerialPort serialPort;
    
    private static final String portName = "/dev/ttyACM0";//"COM3";
    
    private BufferedReader input;
    private OutputStream output;
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;
    
    public Thread th = new Thread(new waitThread(),"Wait Thread");
    
    private String inputLine;
    
    public void initialize()
    {
        System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");
        
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        
        while(portEnum.hasMoreElements())
        {
            CommPortIdentifier currPortId = (CommPortIdentifier)portEnum.nextElement();
            
            if(currPortId.getName().equals(portName))
            {
                portId = currPortId;
                break;
            }
        }
        if(portId == null)
        {
            System.out.println("Could not find COM port");
        }
        
        try
        {
            serialPort = (SerialPort)portId.open(this.getClass().getName(),TIME_OUT);
            
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();
            
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        }
        catch(Exception e)
        {
            System.err.println(e.toString());
        }
    }
    
    public synchronized void close()
    {
        if(serialPort != null)
        {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }
    
    public synchronized void serialEvent(SerialPortEvent oEvent)
    {
        if(oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        {
            try
            {
                inputLine = input.readLine();
                System.out.println(inputLine);   
            }
            catch(Exception e)
            {
                System.err.println(e.toString());
            }
        }
    }
    
    public String getInputLine()
    {
        return inputLine;
    }
    
    public synchronized void out(String s) throws IOException
    {
        output.write(s.getBytes());
    }
    
    /*public static void main(String[] args) throws Exception
    {
        SerialComm newComm = new SerialComm();
        newComm.initialize();
        
        newComm.th.start();
        System.out.println("Started");
    }*/
    
    private class waitThread implements Runnable
    {
        @Override
        public void run()
        {
            try 
            {
                Thread.sleep(100000);
            }
            catch (InterruptedException ex) 
            {
                Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
