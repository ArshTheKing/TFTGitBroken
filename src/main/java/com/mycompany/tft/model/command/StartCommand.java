/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.model.command;

import com.mycompany.tft.api.Sensor;
import com.mycompany.tft.objects.Device;

/**
 *
 * @author Azael
 */
public class StartCommand implements Command{

    private String key;
    private Sensor sensor;

    @Override
    public void setParameters(String... args) {
        key = args[0];
    }

    @Override
    public void execute() {
        this.sensor=Sensor.getInstance();
        sensor.setKey(key);
        sensor.start();
    }

    @Override
    public Object getResults() {
        return sensor.getState().toString();
    }
    
}
