package se.beatit.hsh.raspberry;

import java.util.ArrayList;
import java.util.List;
import se.beatit.hsh.raspberry.util.MinuteTimer;
import se.beatit.hsh.raspberry.util.UpdateSender;
import se.beatit.hsh.raspberry.util.Logger;
import se.beatit.hsh.raspberry.listener.ElectricCabinetListener;
import se.beatit.hsh.raspberry.listener.TemperatureListener;
import se.beatit.hsh.raspberry.restclient.HshRestClient;

/**
 *
 * @author Stefan Nilsson
 */
public class HshRaspberryApplication  {
   
   private static final int MAX_SEND_RETRIES = 5;
   private static final int SEND_RETRY_DELAY = 10000;
    
    public static void main(String args[]) throws InterruptedException {
        
        Logger.log("HshRaspberryApplication starting ...");
        
        if(args.length < 2) {
            Logger.log("Name of home and HshServer url must be specified as arguments.");
            System.exit(0);
        }
        
        
        String home = args[0];
        String restBaseUri = args[1];
        
        HshRestClient ioTRestClient = new HshRestClient(restBaseUri);
        ioTRestClient.createHome(home);
        ioTRestClient.close();

        ElectricCabinetListener elskapListener = new ElectricCabinetListener();
        
        List<TemperatureListener> tempListeners = new ArrayList<TemperatureListener>();
        tempListeners.add(new TemperatureListener("28-0014111ab1ff", "outside"));
        tempListeners.add(new TemperatureListener("28-0014118b77ff", "inside"));
        
        MinuteTimer minuteTimer = new MinuteTimer();
        
        Logger.log("HshRaspberryApplication is now running and listens to GPIO #02 circuit.");
        
        // Keep program running until user/system aborts
        while(true) {
            try {
                Thread.sleep(1000);
                
                if(minuteTimer.newMinuteStarted()) {
                    UpdateSender sender = new UpdateSender(home, restBaseUri, elskapListener, 
                            MAX_SEND_RETRIES, SEND_RETRY_DELAY);
                    sender.sendUpdate(elskapListener.getWhSinceLast());
                    for(TemperatureListener t: tempListeners) {
                        Logger.log("Got temperature: " + t.getCurrentTemperature() + " for location " + t.getLocation());
                    }
                }
            } catch(Exception e) {
                Logger.log("Unexpected error ocurred " + e.toString());
            }
        }       
    }
}
