package io.codelex.myswaggyapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.codelex.myswaggyapp.domain.Holiday;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AppService {
    private final WebClient webClient;

    public AppService() {
        this.webClient = WebClient.create("https://date.nager.at/api/v3/");
    }

    public int getHolidays(String countryCode, String years) throws JsonProcessingException {
        List<Integer> yearList = parseYears(years);
        int result = 0;
        for (Integer year:yearList) {
            result += requestHolidays(countryCode,year).length;
        }
        return result;
    }
    public Holiday[] requestHolidays(String countryCode, int year) throws JsonProcessingException {
        Holiday[] responseJson = webClient.get()
                .uri("PublicHolidays/{year}/{countryCode}",year,countryCode)
                .retrieve()
                .bodyToMono(Holiday[].class)
                .block();
        return responseJson;
    }

    public List<Integer> parseYears(String years){
        List<Integer> result = new ArrayList<>();
        if(years.contains("-")){
            String[] splitYears = years.split("-");
            int startYear = Integer.parseInt(splitYears[0]);
            int endYear = Integer.parseInt(splitYears[1]);
            if(startYear > endYear || startYear < 1922){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            for (int i = startYear; i <= endYear ; i++) {
                result.add(i);
            }
        }else{
            if(years.length() > 4){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            result.add(Integer.parseInt(years));
        }
        return result;
    }
}
