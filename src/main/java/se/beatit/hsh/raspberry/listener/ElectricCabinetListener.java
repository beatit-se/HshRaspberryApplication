package se.beatit.hsh.raspberry.listener;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import se.beatit.hsh.raspberry.util.UpdateSenderFailCallback;

/**
 *
@author Stefan Nilsson
 */
public class ElectricCabinetListener implements GpioPinListenerDigital, UpdateSenderFailCallback {
    
    private PinState previousPinState = PinState.LOW;
    
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
            }
        }
    }
    
    public long getWhSinceLast(){
        long lastWh = wh;
        wh = 0;
        return lastWh;
    }

    @Override
    public void failedToSendUpdate(long failedWh) {
        this.wh += failedWh;
    }
           
}
