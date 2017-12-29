package se.beatit.hsh.raspberry.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.beatit.hsh.raspberry.listener.ElectricCabinetListener;
import se.beatit.hsh.raspberry.listener.TempInListener;
import se.beatit.hsh.raspberry.listener.TempOutListener;
import se.beatit.hsh.raspberry.services.StatisticsService;

/**
 * Created by stefan on 12/27/17.
 */
@RestController
@RequestMapping("/home")
public class Home {

    private StatisticsService mainService;
    private ElectricCabinetListener electricCabinetListener;
    private TempInListener tempInListener;
    private TempOutListener tempOutListener;

    @Autowired
    public Home(StatisticsService mainService, ElectricCabinetListener electricCabinetListener,
                TempInListener tempInListener, TempOutListener tempOutListener) {
        this.electricCabinetListener = electricCabinetListener;
        this.tempInListener = tempInListener;
        this.tempOutListener = tempOutListener;
        this.mainService = mainService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String getHome() {
        return "<h1>Current Watt usage " + electricCabinetListener.getCurrentUsage()  + "</h1>" +
                "<h2>(Max: " + mainService.getHighestW() + " Min: " + mainService.getLowestW() + ")<h2>" +
                "<h1>Current Inside temp " + tempInListener.getCurrentTemperature()  + "</h1>" +
                "<h2>(Max: " + mainService.getWarmestInside() + " Min: " + mainService.getColdestInside() + ")<h2>" +
                "<h1>Current Outside temp " + tempOutListener.getCurrentTemperature()  + "</h1>" +
                "<h2>(Max: " + mainService.getWarmestOutside() + " Min: " + mainService.getColdestOutside() + ")<h2>";
    }
}
