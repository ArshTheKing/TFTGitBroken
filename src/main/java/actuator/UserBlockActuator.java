/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actuator;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AZAEL
 */
public class UserBlockActuator implements Actuator{

    @Override
    public void actuate() {
        try {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("C:\\Windows\\System32\\rundll32.exe user32.dll,LockWorkStation");
        } catch (IOException ex) {
            this.actuate();
        }
    }
    
}
