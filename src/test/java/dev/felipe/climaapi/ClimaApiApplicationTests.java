package dev.felipe.climaapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "openweather.api.key=dummy-key-for-test")
class ClimaApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
