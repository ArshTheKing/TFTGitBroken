
import com.intel.bluetooth.RemoteDeviceHelper;
import com.mycompany.tft.api.FileHandler;
import com.mycompany.tft.model.command.*;
import com.mycompany.tft.ctl.Control;
import com.mycompany.tft.objects.Device;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.RemoteDevice;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AZAEL
 */
public class Main {
    public static void main(String[] args) throws IOException{
        
        Control instance = Control.getInstance();
        
        /*FileHandler fileHandler = new FileHandler();
        fileHandler.writeDevice(new Device("1", "algo", "distinto"));
        SearchCommand sC = new SearchCommand();
        sC.setParameters(args);
        sC.execute();
        ArrayList<RemoteDevice> results =(ArrayList) sC.getResults();
        for (RemoteDevice dev : results) {
        System.out.println(dev.getFriendlyName(true)+" loacated");
        }*/
        
        
    }
}
