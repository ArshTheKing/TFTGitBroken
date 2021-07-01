/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.ctl;

import com.mycompany.tft.api.FileHandler;
import com.mycompany.tft.api.Sensor;
import com.mycompany.tft.gui.Config;
import com.mycompany.tft.gui.DeviceList;
import com.mycompany.tft.gui.MainFrame;
import com.mycompany.tft.model.command.SearchCommand;
import com.mycompany.tft.model.command.StartCommand;
import com.mycompany.tft.model.command.StopCommand;
import com.mycompany.tft.objects.Device;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.RemoteDevice;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author AZAEL
 */
public class Control {
    private static Control myself;
    private static MainFrame ui;
    private static ArrayList<Device> dev=new ArrayList<>();
    private static String[] param;
    private Device keyDevice;
    
    private Control(){
        this.myself=this;
        ui=new MainFrame(this);
        dev=FileHandler.readDevice();
        param=FileHandler.readConfig();
    }
    
    public static Control getInstance(){
        return (myself==null) ? new Control(): myself;
    }
    
    public static void searchDevice(){
        JDialog loading = new JDialog(ui);
        JPanel p1 = new JPanel(new BorderLayout());
        p1.setBackground(Color.red);
        p1.add(new JLabel("Please wait..."), BorderLayout.CENTER);
        loading.setUndecorated(true);
        loading.getContentPane().add(p1);
        loading.pack();
        /*Point location = ui.getLocation();
        Point loadingLocation=new Point();
        loadingLocation.setLocation(location.getX()+260, location.getY()+40);
        loading.setLocation(loadingLocation);*/
        loading.setLocationRelativeTo(ui);
        loading.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        loading.setModal(false);
        
        loading.setVisible(true);
        
        ui.setEnabled(false);
        SearchCommand sC = new SearchCommand();
        sC.setParameters();
        sC.execute();
        Object results = sC.getResults();
        if(results instanceof Exception) System.out.println("Exepcion: "+ ((Exception) results).getMessage());
        else {
        ArrayList<RemoteDevice> res=(ArrayList) results;
        ArrayList<Device> devices= new ArrayList<>();
        try {
            for (RemoteDevice dev : res) {
                Device device = new Device(dev.getBluetoothAddress(), dev.getFriendlyName(true), null);
                devices.add(device);
                
            }
            if(!res.isEmpty())new DeviceList(ui, true, devices,0);
            else searchDevice();
            
        } catch (IOException ex) {
            System.out.println("Exepcion en ctl.searchDevice");
        }
        
        }
        ui.setEnabled(true);
        loading.dispose();
    }
    
    public void saveDevice(Device dev){
       FileHandler.writeDevice(dev);
       this.dev.add(dev);
    }
    

    public void selectKeyDevice() {
        ArrayList<Device> readDevice = FileHandler.readDevice();
        new DeviceList(ui, true, getDevices(),1);
        ui.setEnabled(true);
        ui.toFront();
    }
    
    public void setKeyDevice(Device dev) {
        this.keyDevice=dev;
        
    }

    public void stopSensor() {
        ui.setEnabled(false);
        StopCommand stopCommand = new StopCommand();
        stopCommand.setParameters();
        stopCommand.execute();
        stopCommand.getResults();
        ui.setEnabled(true);
    }

    public void startSensor() {
        StartCommand sensor = new StartCommand();
        sensor.setParameters(keyDevice.getId().toString());
        sensor.execute();
        String results = (String) sensor.getResults();
        System.out.println(results);
    }

    public boolean isKeyDeviceSet() {
        return (keyDevice!=null)? true:false;
    }

    public void config() {
        Config config = new Config();
        config.setVisible(true);
        ui.setEnabled(false);
    }

    public void enableUI() {
        ui.setEnabled(true);
        ui.toFront();
    }

    public void saveConfig(String interval, Device selected, String option) {
        String[] name;
        if(selected!=null) name = new String[]{interval,selected.getId(),option};
        else name=new String[]{interval,"null",option};
        FileHandler.writeConfig(name);
    }

    public ArrayList<Device> getDevices() {
        if(dev.isEmpty()) dev = FileHandler.readDevice();
        return dev;
    }

    
}
