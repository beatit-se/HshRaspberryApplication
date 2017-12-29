package se.beatit.hsh.raspberry.listener;

import org.springframework.stereotype.Component;

/**
 * Created by stefan on 12/29/17.
 */
@Component
public class TempInListener extends TemperatureListener {
    protected TempInListener() {
        super("28-0014118b77ff", "inside");
    }
}
