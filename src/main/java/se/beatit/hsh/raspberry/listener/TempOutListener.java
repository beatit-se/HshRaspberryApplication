package se.beatit.hsh.raspberry.listener;

import org.springframework.stereotype.Component;

/**
 * Created by stefan on 12/29/17.
 */
@Component
public class TempOutListener extends TemperatureListener {
    protected TempOutListener() {
        super("28-0014111ab1ff", "outside");
    }
}
