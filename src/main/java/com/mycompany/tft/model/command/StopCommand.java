/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.model.command;

import com.mycompany.tft.api.Sensor;

/**
 *
 * @author Azael
 */
public class StopCommand implements Command{

    private Sensor instance;

    @Override
    public void setParameters(String... args) {
        
    }

    @Override
    public void execute() {
        instance = Sensor.getInstance();
        instance.exit();
    }

    @Override
    public Object getResults() {
        return instance.isAlive();
    }
    
}
