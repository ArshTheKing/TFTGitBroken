/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.api;

import com.mycompany.tft.objects.Device;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.RemoteDevice;

/**
 *
 * @author AZAEL
 */
public class Sensor extends Thread{
    private Device key;
    private int time;

    public Sensor(Device key, int time) {
        super();
        this.key = key;
        this.time = time;
    }
    

    public void run() {
        ArrayList<RemoteDevice> search = new ArrayList<>(); 
        try {
            search =(ArrayList<RemoteDevice>) SearchDevice.search();
        } catch (BluetoothStateException ex) {
            this.run();
        } catch (InterruptedException ex) {
            this.run();
        }
        if(search!=null)
        for (RemoteDevice remoteDevice : search) {
            
        }
    }
    
    
}
