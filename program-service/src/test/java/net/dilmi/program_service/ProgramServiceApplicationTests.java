package net.dilmi.program_service;

import net.dilmi.program_service.repository.SessionRepository;
import net.dilmi.program_service.repository.TrackRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class ProgramServiceApplicationTests {

	@MockitoBean
	private TrackRepository trackRepository;

	@MockitoBean
	private SessionRepository sessionRepository;

	@Test
	void contextLoads() {
	}

}
