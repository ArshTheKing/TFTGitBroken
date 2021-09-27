/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.api;

import com.mycompany.tft.ctl.Control;
import actuator.Actuator;
import actuator.NetworkShutdownActuator;
import actuator.ShutdownActuator;
import actuator.UserBlockActuator;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AZAEL
 */
public class DataSensor extends Thread{
    private static  DataSensor myself;
    private Actuator action;
    private boolean exit;
    private InputStream deviceStream;
    private float batteryLvl;

    private DataSensor(int actuator) {
        super();
        setAction(actuator);
    }
    public static DataSensor getInstance(){
        return (myself==null) ? myself=new DataSensor(2): myself;
    }

    private void setAction(int actuator) {
        switch(actuator){
            case 0:this.action=new NetworkShutdownActuator();
             break;
            case 1:this.action=new ShutdownActuator();
             break;
            case 2:this.action=new UserBlockActuator();
             break;
        }
    }
    
    public void setKey(InputStream stream) {
        this.deviceStream = (InputStream) stream;
    }

    
    public void setMode(int mode) {
        setAction(mode);
    }
    

    public void run() {
        try {
            while(true){
                byte[] b= new byte[1024];
                int read = deviceStream.read(b,0,4);
                String data= new String(b);
                System.out.println(data.equals("exit"));
                System.out.println("exit");
                System.out.println(data);
                if(data.contentEquals("exit")) {
                    action.actuate();
                    sleep();
                    continue;
                }
                if(read==-1) disconected();
                System.out.println(data);
                /*Float tmp = Float.parseFloat(data);
                if (tmp!=batteryLvl) {
                    batteryLvl=tmp;
                    Control.getInstance().updateBattery((int) batteryLvl);
                }*/
            }
        } catch (IOException ex) {
            Logger.getLogger(DataSensor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sleep() {
        this.suspend();
    }

    public void begin(InputStream stream) {
        Thread.State state=getState();
        if(state.equals(Thread.State.NEW)) this.start();
        else {
            this.deviceStream=stream;
            this.resume();
        }
    }

    private void disconected() {
<<<<<<< Updated upstream
        System.out.println("Device dc");
        action.actuate();
        this.sleep();
        
        
=======
        if(enable){
            //action.actuate();
            this.sleep();
        } else {
            Control.getInstance().cleanConnection();
        }
>>>>>>> Stashed changes
    }


<<<<<<< Updated upstream
    
    
=======
>>>>>>> Stashed changes
}
