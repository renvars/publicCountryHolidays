package io.codelex.myswaggyapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.codelex.myswaggyapp.domain.Holiday;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MyswaggyappApplicationTests {

	public static MockWebServer mockBackEnd;
	private AppService appService;

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
		appService = new AppService();
	}


	@Test
	void getCorrectIntForHolidayCount() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Holiday[] mockHolidaysJSON = objectMapper.readValue(new File("src/main/java/io/codelex/myswaggyapp/domain/HolidaysLV_y2000.json"), Holiday[].class);
		mockBackEnd.enqueue(new MockResponse()
				.setBody(objectMapper.writeValueAsString(mockHolidaysJSON))
				.addHeader("Content-Type", "application/json"));

		Flux<Holiday> mockHoliday = Flux.fromStream(Arrays.stream(appService.requestHolidays("LV",2000)).toList().stream());
		StepVerifier.create(mockHoliday)
				.recordWith(ArrayList::new)
				.expectNextCount(mockHolidaysJSON.length)
				.expectComplete()
				.verify();
	}

	@Test
	void testIfIncorrectDataThrowsError(){
		//One value under 1922
		Assertions.assertThrows(ResponseStatusException.class,()->appService.getHolidays("LV","1100"));
		//End date before start date
		Assertions.assertThrows(ResponseStatusException.class,()->appService.getHolidays("LV","1950-1930"));
	}
}
