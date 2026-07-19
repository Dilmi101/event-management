package net.dilmi.registration_service;

import net.dilmi.registration_service.repository.RegistrationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class RegistrationServiceApplicationTests {

	@MockitoBean
	private RegistrationRepository registrationRepository;

	@Test
	void contextLoads() {
	}

}
