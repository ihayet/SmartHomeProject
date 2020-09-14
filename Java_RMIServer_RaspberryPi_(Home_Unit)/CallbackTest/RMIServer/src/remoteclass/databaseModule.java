/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteclass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import commonExtension.House;
import commonExtension.Room;
import commonExtension.Appliance;

/**
 *
 * @author ISHRAK
 */
public class databaseModule
{
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    public Connection setConnection(String socket, String dbName, String userName, String passWord)
    {
        Connection connection = null;
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
            connection = DriverManager.getConnection("jdbc:mysql://" + socket + "/" + dbName + "?user=" + userName + "&password=" + passWord);
        }
        catch(Exception ex)
        {
            System.out.println("Set connection - Exception");
            ex.printStackTrace();
        }
        finally
        {
            return connection;
        }
    }
    
    public common.House getHouseInformation(long hID)
    {
        common.House newHouse = null;
        
        try
        {
            if(connection == null)
            {
                connection = setConnection(DatabaseParameters.hostSocket,DatabaseParameters.dbName,DatabaseParameters.userName,DatabaseParameters.passWord);
            }

            preparedStatement = connection.prepareStatement("select * from house where house.id=?");
            preparedStatement.setLong(1,hID);
            
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next())
            {
                long id = resultSet.getLong("id");
                String ownerName = resultSet.getString("owner_name");
                long contactNumber = resultSet.getLong("contact_number");
                String dateOfBirth = resultSet.getString("date_of_birth");
                String bloodGroup = resultSet.getString("blood_group");
                String profession = resultSet.getString("profession");

                String houseName = resultSet.getString("house_name");
                String houseAddress = resultSet.getString("address");
                String area = resultSet.getString("area");
                String city = resultSet.getString("city");
                int size = resultSet.getInt("size");
                int numberOfRooms = resultSet.getInt("number_of_rooms");

                String lock = resultSet.getString("lock_phrase");
                String key = resultSet.getString("key_phrase");

                String serviceDiscovery = resultSet.getString("service_discovery");
                String dateOfRegistration = resultSet.getString("date_of_registration");
                
                newHouse = new House(id,ownerName,contactNumber,dateOfBirth,bloodGroup,profession,houseName,houseAddress,area,city,size,numberOfRooms,lock,key,serviceDiscovery,dateOfRegistration);
            }
        }
        catch(Exception ex)
        {
            System.out.println("House Information - Exception");
            ex.printStackTrace();
        }
        finally
        {
            closeResources();
            
            return newHouse;
        }
    }
    
    public common.Room[] getRoomInformation(long hID,int numberOfRooms)
    {
        common.Room[] roomArray = new common.Room[numberOfRooms];
        
        if(connection == null)
        {
            connection = setConnection(DatabaseParameters.hostSocket,DatabaseParameters.dbName,DatabaseParameters.userName,DatabaseParameters.passWord);
        }
        
        try
        {
            preparedStatement = connection.prepareStatement("select * from room where room.house_id=?");
            preparedStatement.setLong(1,hID);
            
            resultSet = preparedStatement.executeQuery();
            
            int counter = 0;
            
            while(resultSet.next())
            {
                int id = resultSet.getInt("id");
                long houseID = resultSet.getLong("house_id");
                String name = resultSet.getString("name");
                int numberOfAppliances = resultSet.getInt("number_of_appliances");
                
                roomArray[counter] = new Room(id,name,houseID,numberOfAppliances);
                counter++;
            }
        }
        catch(Exception ex)
        {
            System.out.println("Room Information - Exception");
            ex.printStackTrace();
        }
        finally
        {
            closeResources();
            
            return roomArray;
        }
    }
    
    public common.Appliance[] getApplianceInformation(long hID,int numberOfAppliances,int rID)
    {
        common.Appliance[] applianceArray = new common.Appliance[numberOfAppliances];
        
        if(connection == null)
        {
            connection = setConnection(DatabaseParameters.hostSocket,DatabaseParameters.dbName,DatabaseParameters.userName,DatabaseParameters.passWord);
        }
        
        try
        {
            preparedStatement = connection.prepareStatement("select * from appliance where appliance.house_id=? and appliance.room_id=?");
            preparedStatement.setLong(1,hID);
            preparedStatement.setInt(2,rID);
            
            resultSet = preparedStatement.executeQuery();
            
            int counter = 0;
            
            while(resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int roomID = resultSet.getInt("room_id");
                long houseID = resultSet.getLong("house_id");
                int powerControl = resultSet.getInt("power_control");
                int status = resultSet.getInt("status");
                int changeExecuted = resultSet.getInt("change_executed");
                
                applianceArray[counter] = new Appliance(id,name,roomID,houseID,powerControl,status,changeExecuted);
                counter++;
            }
        }
        catch(Exception ex)
        {
            System.out.println("Appliance Information - Exception");
            ex.printStackTrace();
        }
        finally
        {
            closeResources();
            
            return applianceArray;
        }
    }
    
    public common.Appliance[] syncAppliance(long hID,int numberOfAppliances)
    {
        common.Appliance[] newApplianceArray = new common.Appliance[numberOfAppliances];
        
        if(connection == null)
        {
            connection = setConnection(DatabaseParameters.hostSocket,DatabaseParameters.dbName,DatabaseParameters.userName,DatabaseParameters.passWord);
        }
         
        try
        {
            preparedStatement = connection.prepareStatement("select * from appliance where appliance.house_id=?");
            preparedStatement.setLong(1,hID);
            
            resultSet = preparedStatement.executeQuery();
            
            int counter = 0;
            
            while(resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int roomID = resultSet.getInt("room_id");
                long houseID = resultSet.getLong("house_id");
                int powerControl = resultSet.getInt("power_control");
                int status = resultSet.getInt("status");
                int change_executed = resultSet.getInt("change_executed");
                
                newApplianceArray[counter] = new Appliance(id,name,roomID,hID,powerControl,status,change_executed);
                counter++;
            }
        }
        catch(ArrayIndexOutOfBoundsException ex)
        {
            System.out.println("Please restart house " + hID);
        }
        catch(Exception ex)
        {
            System.out.println("Sycning - Exception");
            ex.printStackTrace();
        }
        finally
        {
            closeResources();
            
            return newApplianceArray;
        }
    }
    
    public void successfulUpdate(long hID,int rID,int appID)
    {
        if(connection==null)
        {
            connection = setConnection(DatabaseParameters.hostSocket,DatabaseParameters.dbName,DatabaseParameters.userName,DatabaseParameters.passWord);
        }
        
        try
        {
            System.out.println("Successful Update - House: " + hID + " - Room: " + rID + " Appliance: " + appID);
            
            preparedStatement = connection.prepareStatement("update appliance set change_executed=? where appliance.house_id=? and appliance.room_id=? and appliance.id=?");
            
            preparedStatement.setInt(1,1);
            preparedStatement.setLong(2,hID);
            preparedStatement.setInt(3,rID);
            preparedStatement.setInt(4,appID);
            
            preparedStatement.execute();
        }
        catch(Exception ex)
        {
            System.err.println(ex.toString());
        }
        finally
        {
            closeResources();
        }
    }
    
    public void buttonOverride(long hID,int rID,int appID,int status)
    {
        if(connection==null)
        {
            connection = setConnection(DatabaseParameters.hostSocket,DatabaseParameters.dbName,DatabaseParameters.userName,DatabaseParameters.passWord);
        }
        
        try
        {          
            System.out.println("Button Override");
            
            preparedStatement = connection.prepareStatement("update appliance set status=? where appliance.id=? and appliance.room_id=? and appliance.house_id=?");
            
            preparedStatement.setInt(1,status);
            preparedStatement.setLong(2,hID);
            preparedStatement.setInt(3,rID);
            preparedStatement.setInt(4,appID);
            
            preparedStatement.execute();
        }
        catch(Exception ex)
        {
            System.err.println(ex.toString());
        }
        finally
        {
            closeResources();
        }
    }
    
    public void closeResources()
    {
        try
        {
            if(resultSet != null)
            {
                resultSet.close();
            }
            if(preparedStatement != null)
            {
                preparedStatement.close();
            }
            if(connection != null)
            {
                connection.close();
            }
        }
        catch(Exception ex)
        {
            System.out.println("Close - Exception");
            ex.printStackTrace();
        }
    }
}
