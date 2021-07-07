/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.ctl;

import com.mycompany.tft.api.FileHandler;
import com.mycompany.tft.api.MailSender;
import com.mycompany.tft.gui.Config;
import com.mycompany.tft.gui.DeviceList;
import com.mycompany.tft.gui.MainFrame;
import com.mycompany.tft.model.command.*;
import com.mycompany.tft.objects.Device;
import com.mycompany.tft.objects.Params;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author AZAEL
 */
public class Control {
    private static Control myself;
    private static MainFrame ui;
    private static ArrayList<Device> dev=new ArrayList<>();
    private Device keyDevice;
    private Params params;
    
    private Control(){
        this.myself=this;
        ui=new MainFrame(this);
        try {
            dev=FileHandler.readDevice();
            params= FileHandler.readConfig();
            keyDevice=params.getKey();
            if(keyDevice!=null)
            {
                ui.setKeyDeviceTag(keyDevice.getName());
                MailSender.getInstance().setMail(keyDevice.getMail());
            }
            else ui.setKeyDeviceTag("Ninguno");
        } catch (IOException ex) {
            System.out.println("Error al leer los archivos");
        }
    }
    
    public static Control getInstance(){
        return (myself==null) ? new Control(): myself;
    }
    
    public void searchDevice() {
        ui.setStatusTag("Searching devices");
        
        SearchCommand sC = new SearchCommand();
        sC.setParameters();
        new Thread(){
            public void run() {
                super.run();
                try {
                    sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
                }
                sC.execute();
            }
        }.start();
        new DeviceList(ui, true, new ArrayList<Device>(), 0);
        
        ui.setEnabled(false);
        /*Object results =sC.getResults();
        if(results instanceof Exception) System.out.println("Exepcion: "+ ((Exception) results).getMessage());
        if(!((ArrayList) results).isEmpty()) 
                new DeviceList(ui, true, (ArrayList<Device>) results,0);
        else {
            ui.setStatusTag("Ningun dispositivo encontrado");
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }*/
        ui.setStatusTag("Ocioso");
        ui.setEnabled(true);
    }
    
    public void saveDevice(Device dev){
       RegisterCommand rc = new RegisterCommand();
       rc.setParameters(dev);
       rc.execute();
       rc.getResults();
       this.dev.add(dev);
    }
    

    public void selectKeyDevice() {
        ReadCommand rc = new ReadCommand();
        rc.setParameters();
        rc.execute();
        rc.getResults();
        new DeviceList(ui, true, getDevices(),1);
        ui.setEnabled(true);
        ui.toFront();
    }
    
    public void setKeyDevice(Device dev) {
        this.keyDevice=dev;
        ui.setKeyDeviceTag(dev.getName());
        MailSender.getInstance().setMail(keyDevice.getMail());
    }

    public void stopSensor() {
        ui.setStatusTag("Parando Sensor");
        ui.setEnabled(false);
        StopCommand stopCommand = new StopCommand();
        stopCommand.setParameters();
        stopCommand.execute();
        stopCommand.getResults();
        ui.setStatusTag("Ocioso");
        ui.setEnabled(true);
    }

    public void startSensor() {
        StartCommand sensor = new StartCommand();
        sensor.setParameters(keyDevice,Integer.parseInt(params.getInterval()),Integer.parseInt(params.getMode()));
        sensor.execute();
        ui.setStatusTag("Sensor Activado");
    }

    public boolean isKeyDeviceSet() {
        return (keyDevice!=null)? true:false;
    }

    public void config() {
        Params readConfig;
        try {
            readConfig = FileHandler.readConfig();
            Config config = new Config(readConfig);
            config.setVisible(true);
            ui.setEnabled(false);
        } catch (IOException ex) {
            config();
        }
    }

    public void saveConfig(String interval, Device selected, String option) {
        params=new Params(interval, keyDevice, option);
        UpdateCommand uc = new UpdateCommand();
        uc.setParameters(interval,selected,option);
        uc.execute();
        ui.setEnabled(true);
        MailSender.getInstance().setActuator(Integer.parseInt(option));
    }


    public ArrayList<Device> getDevices() {
        if(dev.isEmpty()) try {
            dev = FileHandler.readDevice();
        } catch (IOException ex) {
            System.out.println("Error al leer los archivos");
        }
        return dev;
    }
    
    public void enableUI() {
        ui.setEnabled(true);
        ui.toFront();
    }
}
