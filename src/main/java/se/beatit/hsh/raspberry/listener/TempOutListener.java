package se.beatit.hsh.raspberry.listener;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Created by stefan on 12/29/17.
 */
@Component
@ConditionalOnProperty(value = "gpio-conf.enable-temperature", havingValue = "true", matchIfMissing = true)
public class TempOutListener extends TemperatureListener {
    protected TempOutListener() {
        super("28-0014111ab1ff", "outside");
    }
}
