/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.objects;

/**
 *
 * @author AZAEL
 */
public class Params {
    String interval;
    Device key;
    String mode;

    public Params(String interval, Device key, String mode) {
        this.interval = interval;
        this.key = key;
        this.mode = mode;
    }

    public String getInterval() {
        return interval;
    }

    public Device getKey() {
        return key;
    }

    public String getMode() {
        return mode;
    }

    @Override
    public String toString() {
        return "Params{" + "interval=" + interval + ", key=" + key + ", mode=" + mode + '}';
    }
    
    
}
