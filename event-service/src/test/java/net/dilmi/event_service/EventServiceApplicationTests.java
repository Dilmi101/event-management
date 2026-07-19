package net.dilmi.event_service;

import net.dilmi.event_service.repository.ContactMessageRepository;
import net.dilmi.event_service.repository.EventRepository;
import net.dilmi.event_service.repository.SponsorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class EventServiceApplicationTests {

	@MockitoBean
	private EventRepository eventRepository;

	@MockitoBean
	private SponsorRepository sponsorRepository;

	@MockitoBean
	private ContactMessageRepository contactMessageRepository;

	@Test
	void contextLoads() {
	}

}
