package se.beatit.hsh.raspberry.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import se.beatit.hsh.raspberry.entities.Measurement;
import se.beatit.hsh.raspberry.listener.TempInListener;
import se.beatit.hsh.raspberry.listener.TempOutListener;
import se.beatit.hsh.raspberry.repositories.MeasurementRepository;
import se.beatit.hsh.raspberry.util.Logger;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import se.beatit.hsh.raspberry.listener.ElectricCabinetListener;

/**
 * Created by stefan on 12/27/17.
 */
@Service
@ConditionalOnProperty(value = "gpio-conf.statistics", havingValue = "true", matchIfMissing = true)
public class StatisticsService extends TimerTask {

    private ElectricCabinetListener electricCabinetListener;
    private TempInListener tempInListener;
    private TempOutListener tempOutListener;

    private MeasurementRepository measurementRepository;

    private Date latestMeasurement = null;

    @Autowired
    public StatisticsService(ElectricCabinetListener electricCabinetListener, TempInListener tempInListener,
                             TempOutListener tempOutListener, MeasurementRepository measurementRepository) {
        this.electricCabinetListener = electricCabinetListener;
        this.tempInListener = tempInListener;
        this.tempOutListener = tempOutListener;
        this.measurementRepository = measurementRepository;

        Logger.log("HshRaspberryApplication starting ...");

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(this, (60-LocalDateTime.now().getSecond())*1000, 60*1000);

        Logger.log("HshRaspberryApplication is now running and listens to GPIO #02 circuit.");
    }


    @Override
    public void run() {
        Logger.log("In! " + LocalDateTime.now().toString());

        if(latestMeasurement == null) {
            electricCabinetListener.getWhSinceLast();
            latestMeasurement = new Date();
            return;
        }

        Date now = new Date();
        long whSinceLast = electricCabinetListener.getWhSinceLast();
        long currentWUsage = whSinceLast * 60;

        Measurement measurement = new Measurement();
        measurement.setFromDate(latestMeasurement);
        measurement.setToDate(now);
        latestMeasurement = now;

        measurement.setInTemp(tempInListener.getCurrentTemperature());
        measurement.setOutTemp(tempOutListener.getCurrentTemperature());
        measurement.setWattHoursUsed(whSinceLast);
        measurement.setAvarageWatt(currentWUsage);

        measurementRepository.save(measurement);

        Logger.log("W usage " + currentWUsage +
            " In temp: " + tempInListener.getCurrentTemperature() +
            " Out temp " + tempOutListener.getCurrentTemperature());
    }

}
