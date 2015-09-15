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
import se.beatit.hsh.raspberry.util.UpdateSenderFailCallback;

/**
 *
 * @author stefan
 */
public class TemperatureListener implements UpdateSenderFailCallback {

    private String device;
    private String location;
    private String fileName;
    
    public TemperatureListener(String device, String location) {
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
    
    @Override
    public void failedToSendUpdate(long wh) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
