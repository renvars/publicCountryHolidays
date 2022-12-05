package io.codelex.myswaggyapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/myswaggyapp")
public class AppController {

    private final AppService appService;

    @Autowired
    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping(path = "/holidaycount/{countryCode}/{years}")
    public int getHolidays(@PathVariable String countryCode, @PathVariable String years) {
        return appService.getHolidays(countryCode, years);
    }
}
