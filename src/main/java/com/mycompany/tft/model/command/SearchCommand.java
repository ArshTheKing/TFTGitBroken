/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.model.command;

import com.mycompany.tft.api.SearchDevice;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.BluetoothStateException;

/**
 *
 * @author AZAEL
 */
public class SearchCommand implements Command{
    Exception exception;
    private ArrayList search;
    
    
    @Override
    public void setParameters(String... args) {
        exception=null;
        search=null;
    }

    @Override
    public void execute() {
        try {
            search = SearchDevice.search();
        } catch (BluetoothStateException ex) {
            exception=ex;
        } catch (InterruptedException ex) {
            exception=ex;
        }
    }

    @Override
    public Object getResults() {
        return (exception==null) ? search : exception;
    }
    
}
