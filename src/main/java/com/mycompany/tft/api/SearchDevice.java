/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.api;

import com.intel.bluetooth.RemoteDeviceHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.microedition.io.Connection;
import javax.microedition.io.Connector;

/**
 *
 * @author AZAEL
 */
public class SearchDevice {
    public static final ArrayList/*<RemoteDevice>*/ devicesDiscovered = new ArrayList();
    
    public static ArrayList search() throws BluetoothStateException, InterruptedException {
        final Object inquiryCompletedEvent = new Object();

        devicesDiscovered.clear();

        DiscoveryListener listener = new DiscoveryListener() {

            public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
                try {
                    System.out.println("Device " + btDevice.getBluetoothAddress() + " found");
                    //Establecermos una conexion basica para verificar la presencia del dispositivo
                    Connection open = Connector.open("btspp://"+btDevice.getBluetoothAddress()+":1;master=false;authenticate=false;encrypt=false;");
                } catch (IOException ex) {
                    String substring = ex.getLocalizedMessage().substring(ex.getLocalizedMessage().indexOf("[")+1,ex.getLocalizedMessage().indexOf("]"));
                    if(substring.equals(10064+"")) 
                    devicesDiscovered.add(btDevice);
                }
            }

            public void inquiryCompleted(int discType) {
                System.out.println("Device Inquiry completed!");
                synchronized(inquiryCompletedEvent){
                    inquiryCompletedEvent.notifyAll();
                }
            }

            public void serviceSearchCompleted(int transID, int respCode) {
            }

            public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
            }
        };

        synchronized(inquiryCompletedEvent) {
            boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, listener);
            if (started) {
                System.out.println("wait for device inquiry to complete...");
                inquiryCompletedEvent.wait();
                System.out.println(devicesDiscovered.size() +  " device(s) found");
            }
        }
        return devicesDiscovered;
    }
}
