package se.beatit.hsh.raspberry.listener;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.springframework.stereotype.Component;

/**
 *
@author Stefan Nilsson
 */
@Component
public class ElectricCabinetListener implements GpioPinListenerDigital {
    
    private PinState previousPinState = PinState.LOW;

    private long lastChangedState = 0L;
    private long currentUsage = 0L;
    
    private long wh = 0;
    
    public ElectricCabinetListener() {
        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput diodInput = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);

        // create and register gpio pin listener
        diodInput.addListener(this);
    }
    
    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        if(event.getState() != previousPinState) {
            previousPinState = event.getState();
            if(event.getState() == PinState.HIGH) {
                wh++;

                long now = System.currentTimeMillis();
                long delay = now - lastChangedState;
                currentUsage = (60000L / delay) * 60L;
                lastChangedState = now;
            }
        }
    }
    
    public long getWhSinceLast(){
        long lastWh = wh;
        wh = 0;
        return lastWh;
    }

    public long getCurrentUsage() {
        return currentUsage;
    }
           
}
