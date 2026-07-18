package net.dilmi.event_service;

import net.dilmi.event_service.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class EventServiceApplicationTests {

	@MockitoBean
	private EventRepository eventRepository;

	@Test
	void contextLoads() {
	}

}
