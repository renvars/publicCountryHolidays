package io.codelex.myswaggyapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.codelex.myswaggyapp.domain.Holiday;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;


import java.io.File;
import java.io.IOException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MyswaggyappApplicationTests {

    public static MockWebServer mockBackEnd;
    @Autowired
    public AppController appController;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:8080",
                mockBackEnd.getPort());
    }


    @Test
    void getCorrectIntForHolidayCount() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Holiday[] mockHolidaysJSON = objectMapper.readValue(new File("src/test/java/utils/HolidaysLV_y2000.json"), Holiday[].class);
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockHolidaysJSON))
                .addHeader("Content-Type", "application/json"));

        int actualResult = appController.getHolidays("LV", "2000-2000");
        System.out.println("");
        Assertions.assertEquals(mockHolidaysJSON.length, actualResult);
    }

    @Test
    void testIfIncorrectDataThrowsError() {
        //One value under 1922
        Assertions.assertThrows(ResponseStatusException.class, () -> appController.getHolidays("LV", "1100-2011"));
        //End date before start date
        Assertions.assertThrows(ResponseStatusException.class, () -> appController.getHolidays("LV", "1950-1930"));
        //Only one year
        Assertions.assertThrows(ResponseStatusException.class, () -> appController.getHolidays("LV", "1950"));
        //More than 2 years
        Assertions.assertThrows(ResponseStatusException.class, () -> appController.getHolidays("LV", "1950-1966-2000"));
    }
}
