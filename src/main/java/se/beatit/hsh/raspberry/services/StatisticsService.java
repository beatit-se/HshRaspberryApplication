package se.beatit.hsh.raspberry.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.beatit.hsh.raspberry.listener.TempInListener;
import se.beatit.hsh.raspberry.listener.TempOutListener;
import se.beatit.hsh.raspberry.util.Logger;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import se.beatit.hsh.raspberry.listener.ElectricCabinetListener;

/**
 * Created by stefan on 12/27/17.
 */
@Service
public class StatisticsService extends TimerTask {

    private ElectricCabinetListener electricCabinetListener;
    private TempInListener tempInListener;
    private TempOutListener tempOutListener;

    private Float coldestInside;
    private Float warmestInside;
    private Float coldestOutside;
    private Float warmestOutside;
    private Long highestW;
    private Long lowestW;

    @Autowired
    public StatisticsService(ElectricCabinetListener electricCabinetListener, TempInListener tempInListener, TempOutListener tempOutListener) {
        this.electricCabinetListener = electricCabinetListener;
        this.tempInListener = tempInListener;
        this.tempOutListener = tempOutListener;

        Logger.log("HshRaspberryApplication starting ...");


        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(this, (60-LocalDateTime.now().getSecond())*1000, 60*1000);

        Logger.log("HshRaspberryApplication is now running and listens to GPIO #02 circuit.");
    }


    @Override
    public void run() {
        Logger.log("In! " + LocalDateTime.now().toString());

        if(coldestInside == null || coldestInside > tempInListener.getCurrentTemperature()) {
            coldestInside = tempInListener.getCurrentTemperature();
        }

        if(warmestInside == null || warmestInside < tempInListener.getCurrentTemperature()) {
            warmestInside = tempInListener.getCurrentTemperature();
        }

        if(coldestOutside == null || coldestOutside > tempOutListener.getCurrentTemperature()) {
            coldestOutside = tempOutListener.getCurrentTemperature();
        }

        if(warmestOutside == null || warmestOutside < tempOutListener.getCurrentTemperature()) {
            warmestOutside = tempOutListener.getCurrentTemperature();
        }

        long whSinceLast = electricCabinetListener.getWhSinceLast();
        long currentWUsage = (whSinceLast * 60);

        if(highestW == null || highestW < currentWUsage) {
            highestW = currentWUsage;
        }

        if(lowestW == null || lowestW < currentWUsage) {
            lowestW = currentWUsage;
        }

        Logger.log("W usage " + currentWUsage +
        " In temp: " + tempInListener.getCurrentTemperature() +
        " Out temp " + tempOutListener.getCurrentTemperature());
    }

    public Float getColdestInside() {
        return coldestInside;
    }

    public Float getWarmestInside() {
        return warmestInside;
    }

    public Float getColdestOutside() {
        return coldestOutside;
    }

    public Float getWarmestOutside() {
        return warmestOutside;
    }

    public Long getHighestW() {
        return highestW;
    }

    public Long getLowestW() {
        return lowestW;
    }
}
