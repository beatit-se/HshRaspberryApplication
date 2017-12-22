package se.beatit.hsh.raspberry.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Stefan Nilsson
 */
public class Logger {
  
    private static final SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    
    static {
        Logger.simpleDateFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Stockholm"));
    }
    
    public static void log(String text) {
        System.out.println(simpleDateFormatter.format(new Date()) + ": " + text);
    }
}
