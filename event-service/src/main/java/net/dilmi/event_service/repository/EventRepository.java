package net.dilmi.event_service.repository;

import net.dilmi.event_service.model.Event;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends CrudRepository<Event, UUID> {

    @Modifying
    @Query("UPDATE events SET seats_available = seats_available - :ticketCount, updated_at = NOW() WHERE event_id = :eventId AND seats_available >= :ticketCount")
    int reserveSeats(@Param("eventId") UUID eventId, @Param("ticketCount") int ticketCount);

    @Modifying
    @Query("UPDATE events SET seats_available = seats_available + :ticketCount, updated_at = NOW() WHERE event_id = :eventId")
    void releaseSeats(@Param("eventId") UUID eventId, @Param("ticketCount") int ticketCount);

    @Modifying
    @Query("UPDATE events SET low_seats_notified_at = NOW() WHERE event_id = :eventId AND low_seats_notified_at IS NULL")
    int markLowSeatsNotified(@Param("eventId") UUID eventId);

    @Modifying
    @Query("UPDATE events SET low_seats_notified_at = NULL WHERE event_id = :eventId")
    void clearLowSeatsNotified(@Param("eventId") UUID eventId);
}
