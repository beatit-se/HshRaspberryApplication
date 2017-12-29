/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.beatit.hsh.raspberry.listener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author stefan
 */
public abstract class TemperatureListener {

    private String device;
    private String location;
    private String fileName;
    
    protected TemperatureListener(String device, String location) {
        this.device = device;
        this.location = location;
        this.fileName = "/sys/bus/w1/devices/" + device + "/w1_slave";
    }
    
    public String getLocation() {
        return location;
    }
    
    public float getCurrentTemperature() {
        String temperature;
        
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            try (
                    BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String line1 = bufferedReader.readLine();
                if(line1 != null && line1.endsWith("YES")) {
                    String line2 = bufferedReader.readLine();
                    temperature = line2.substring(line2.indexOf("=")+1);
                    return Float.parseFloat(temperature)/1000f;
                } else {
                    System.out.println("Device has no valid temperature");
                }
                
            }            
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                   
        }
        return 0.0F;
    }
    
}
