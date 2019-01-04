package se.beatit.hsh.raspberry.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@ConditionalOnProperty(value = "gpio-conf.statistics", havingValue = "true", matchIfMissing = true)
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
        ZonedDateTime zdtStartOfToday = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0);
        ZonedDateTime zdtStartOfMonth = ZonedDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);

        Date startOfToday = new Date(zdtStartOfToday.toInstant().toEpochMilli());
        Date startOfMonth = new Date(zdtStartOfMonth.toInstant().toEpochMilli());
        Date now = new Date();


        Logger.log("Getting data from " + SimpleDateFormat.getDateInstance().format(startOfToday) + " to " + SimpleDateFormat.getDateInstance().format(now));

        Page<Measurement> minTempResult = measurementRepository.findMinOutTemp(startOfToday, now, createPageRequest());
        Measurement minOutTemp = minTempResult.iterator().next();

        Page<Measurement> maxTempResult = measurementRepository.findMaxOutTemp(startOfToday, now, createPageRequest());
        Measurement maxOutTemp = maxTempResult.iterator().next();

        Page<Measurement> minInTempResult = measurementRepository.findMinInTemp(startOfToday, now, createPageRequest());
        Measurement minInTemp = minInTempResult.iterator().next();

        Page<Measurement> maxInTempResult = measurementRepository.findMaxInTemp(startOfToday, now, createPageRequest());
        Measurement maxInTemp = maxInTempResult.iterator().next();

        Page<Measurement> minWattUsageResult = measurementRepository.findMinWattUsage(startOfToday, now, createPageRequest());
        Measurement minWat = minWattUsageResult.iterator().next();

        Page<Measurement> maxWattUsageResult = measurementRepository.findMaxWattUsage(startOfToday, now, createPageRequest());
        Measurement maxWat = maxWattUsageResult.iterator().next();

        Long wattHoursUsed = measurementRepository.findWattHoursUsed(startOfToday, now);
        Long averageWattUsage = measurementRepository.getAverageWattUsage(startOfToday, now);
        Float averageOutTemp = measurementRepository.getAverageOutTemp(startOfToday, now);
        Float averageInTemp = measurementRepository.getAverageInTemp(startOfToday, now);

        return "<h1>Current Outside temp " + tempOutListener.getCurrentTemperature()  + "</h1>" +
                "<h3>(Max: " + maxOutTemp.getOutTemp() + ", min: " + minOutTemp.getOutTemp() + ", average: " + averageOutTemp + ")<h3>" +
                "<h1>Current Inside temp " + tempInListener.getCurrentTemperature()  + "</h1>" +
                "<h3>(Max: " + maxInTemp.getInTemp() + ", min: " + minInTemp.getInTemp() + ", average: " + averageInTemp + ")<h3>" +
                "<h1>Current Watt usage " + electricCabinetListener.getCurrentUsage()  + "</h1>" +
                "<h3>(Max: " + maxWat.getAvarageWatt() + ", min: " + minWat.getAvarageWatt() + ", average: " + averageWattUsage + ", watt hours used: " + wattHoursUsed + ")<h3>" +
                "---------------------------------------------------------" +
                "<h3>Watt hours used this month: " + measurementRepository.findWattHoursUsed(startOfMonth, now) + "</h3>" +
                "<h3>Average outside temp: " + measurementRepository.getAverageOutTemp(startOfMonth, now) + "</h3>";
    }

    private Pageable createPageRequest() {
        return new PageRequest(0, 1);
    }
}
