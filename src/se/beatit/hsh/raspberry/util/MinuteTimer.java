package se.beatit.hsh.raspberry.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Stefan Nilsson
 */
public class MinuteTimer {
    private String lastMinute;
    private static SimpleDateFormat minuteDateFormatter; 
    
    public MinuteTimer() {        
        minuteDateFormatter = new SimpleDateFormat("mm");
        minuteDateFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Stockholm"));
        
        lastMinute = minuteDateFormatter.format(new Date());
    }
    
    
    public boolean newMinuteStarted() {
        String now = minuteDateFormatter.format(new Date());
        if(!lastMinute.equals(now)) {
            lastMinute = now;
            return true;
        }
        return false;
    }
}
