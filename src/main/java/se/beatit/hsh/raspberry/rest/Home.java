package se.beatit.hsh.raspberry.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.beatit.hsh.raspberry.entities.Measurement;
import se.beatit.hsh.raspberry.listener.ElectricCabinetListener;
import se.beatit.hsh.raspberry.listener.TempInListener;
import se.beatit.hsh.raspberry.listener.TempOutListener;
import se.beatit.hsh.raspberry.repositories.MeasurementRepository;
import se.beatit.hsh.raspberry.util.Logger;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by stefan on 12/27/17.
 */
@RestController
@RequestMapping("/home")
public class Home {

    private MeasurementRepository measurementRepository;
    private ElectricCabinetListener electricCabinetListener;
    private TempInListener tempInListener;
    private TempOutListener tempOutListener;

    @Autowired
    public Home(MeasurementRepository measurementRepository,
                ElectricCabinetListener electricCabinetListener,
                TempInListener tempInListener, TempOutListener tempOutListener) {
        this.electricCabinetListener = electricCabinetListener;
        this.tempInListener = tempInListener;
        this.tempOutListener = tempOutListener;
        this.measurementRepository = measurementRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String getHome() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0);
        Date from = new Date(zonedDateTime.toInstant().toEpochMilli());
        Date to = new Date();

        Logger.log("Getting data from " + SimpleDateFormat.getDateInstance().format(from) + " to " + SimpleDateFormat.getDateInstance().format(to));

        Page<Measurement> minTempResult = measurementRepository.findMinOutTemp(from, to, createPageRequest());
        Measurement minOutTemp = minTempResult.iterator().next();

        Page<Measurement> maxTempResult = measurementRepository.findMaxOutTemp(from, to, createPageRequest());
        Measurement maxOutTemp = maxTempResult.iterator().next();

        /*
        Page<Measurement> minInTempResult = measurementRepository.findMinInTemp(from, to, createPageRequest());
        Measurement minInTemp = minInTempResult.iterator().next();

        Page<Measurement> maxInTempResult = measurementRepository.findMaxInTemp(from, to, createPageRequest());
        Measurement maxInTemp = maxInTempResult.iterator().next();
        */

        Page<Measurement> minWattUsageResult = measurementRepository.findMinWattUsage(from, to, createPageRequest());
        Measurement minWat = minWattUsageResult.iterator().next();

        Page<Measurement> maxWattUsageResult = measurementRepository.findMaxWattUsage(from, to, createPageRequest());
        Measurement maxWat = maxWattUsageResult.iterator().next();

        return "<h1>Current Watt usage " + electricCabinetListener.getCurrentUsage()  + "</h1>" +
                "<h2>(Max: " + maxWat.getAvarageWatt() + " Min: " + minWat.getAvarageWatt() + ")<h2>" +
                "<h1>Current Inside temp " + tempInListener.getCurrentTemperature()  + "</h1>" +
               // "<h2>(Max: " + mainService.getWarmestInside() + " Min: " + mainService.getColdestInside() + ")<h2>" +
                "<h1>Current Outside temp " + tempOutListener.getCurrentTemperature()  + "</h1>" +
                "<h2>(Max: " + maxOutTemp.getOutTemp() + " Min: " + minOutTemp.getOutTemp() + ")<h2>";
    }

    private Pageable createPageRequest() {
        return new PageRequest(0, 1);
    }
}
