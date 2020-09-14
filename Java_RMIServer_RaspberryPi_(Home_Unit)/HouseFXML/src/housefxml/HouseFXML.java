/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package housefxml;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 *
 * @author ISHRAK
 */
public class HouseFXML extends Application
{
    @Override
    public void start(Stage stage) throws Exception 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        FXMLDocumentController controller = loader.getController();
        
        Thread th = new Thread(new RMIThread(controller.getAppText()),"Remote Method Invocation-Thread");
        th.start();
    }
    
    private class RMIThread implements Runnable
    {
        TextArea tArea = null;
        
        public RMIThread(TextArea t)
        {
            tArea = t;
        }
        
        @Override
        public void run()
        {
            try 
            {
                FXMLDocumentController.initRMI(1,tArea);
            }
            catch (URISyntaxException ex)
            {
                Logger.getLogger(HouseFXML.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(HouseFXML.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
}
