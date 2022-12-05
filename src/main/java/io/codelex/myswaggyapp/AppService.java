package io.codelex.myswaggyapp;


import io.codelex.myswaggyapp.domain.Holiday;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.IntStream;

@Service
public class AppService {
    private final WebClient webClient;

    public AppService() {
        this.webClient = WebClient.create("https://date.nager.at/api/v3/");
    }

    public int getHolidays(String countryCode, String years) {
        IntStream yearList = parseYears(years);
        return yearList.map(now -> requestHolidays(countryCode, now).length).reduce(0, Integer::sum);
    }

    public Holiday[] requestHolidays(String countryCode, int year) {
        return webClient.get()
                .uri("PublicHolidays/{year}/{countryCode}", year, countryCode)
                .retrieve()
                .bodyToMono(Holiday[].class)
                .block();
    }

    public IntStream parseYears(String years) {
        String pattern = "^\\d{4}-\\d{4}$";
        if (years.matches(pattern)) {
            String[] splitYears = years.split("-");
            int startYear = Integer.parseInt(splitYears[0]);
            int endYear = Integer.parseInt(splitYears[1]);
            if (startYear > endYear || startYear < 1922) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            return IntStream.range(startYear, endYear + 1);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
