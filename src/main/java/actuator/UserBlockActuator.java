/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actuator;

import com.mycompany.tft.api.LockScreen;

/**
 *
 * @author AZAEL
 */
public class UserBlockActuator implements Actuator{

    @Override
    public void actuate() {
        LockScreen lockScreen = new LockScreen();
    }
    
}
