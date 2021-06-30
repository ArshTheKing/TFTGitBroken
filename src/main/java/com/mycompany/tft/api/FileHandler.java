/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.api;

import com.mycompany.tft.objects.Device;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author AZAEL
 */
public class FileHandler {
    private static String dirPath="c:\\\\BluetoothLock\\\\savedDevices";
    private static File deviceFile=new File("c:\\\\BluetoothLock\\\\savedDevices\\\\deviceFile.txt");
    private static File paramFile=new File("c:\\\\BluetoothLock\\\\savedDevices\\\\paramFile.txt");
    static {
        if(!deviceFile.exists()) 
        {
            (new File(dirPath)).mkdirs();
            try {
                deviceFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(!paramFile.exists()) 
        {
            (new File(dirPath)).mkdirs();
            try {
                paramFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static ArrayList<Device> readDevice(){
        ArrayList<Device> temp= new ArrayList<>();
        BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(deviceFile));
			String line = reader.readLine();
                        while (line != null) {
                                String[] split = line.split(";");
                                temp.add(new Device(split[0], split[1], split[2]));
                                line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return temp;
    }
    
    public static void writeDevice(Device dev){
        FileWriter fr;
        try {
            fr = new FileWriter(deviceFile, true);
            BufferedWriter br = new BufferedWriter(fr);
            br.append(dev.getId()+";"+dev.getName()+";"+dev.getMail()+System.lineSeparator());
            br.close();
            fr.close();
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static String[] readConfig(){
        String[] param=new String[0];
        return param;
    }
    
    public static void writeConfig(String[] param){
        
        
    }
    
    
}
