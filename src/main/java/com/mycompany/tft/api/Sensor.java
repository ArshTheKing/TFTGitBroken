/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.api;

import com.mycompany.tft.ctl.Control;
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
    private String key;
    private int time;
    private static  Sensor myself;
    private boolean exit;

    private Sensor(String key, int time) {
        super();
        this.key = key;
        this.time = time*1000;
    }
    public static Sensor getInstance(){
        return (myself== null) ? myself=new Sensor(null, 1): myself;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setTime(int time) {
        this.time = time;
    }
    
    

    public void run() {
        exit=false;
        while (!exit) {            
            try {
                System.out.println("SLEEP");
                sleep(time);
            } catch (InterruptedException ex) {
                System.out.println("Error en el sensor");
            }
                System.out.println("Busqueda iniciada");
                searchKeyDevice();
        }
    }

    private void searchKeyDevice() {
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
            if(remoteDevice.getBluetoothAddress().equals(key)) 
            {System.out.println("Detectado");
                return;
            }
        }
        System.out.println("No detectado");
    }

    public void exit() {
        exit=true;
        myself=null;
    }
    
    
}
