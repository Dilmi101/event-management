import type { Event } from '../types/event'

interface Props {
  event: Event
}

export default function VenueSection({ event }: Props) {
  return (
    <section id="venue">
      <div className="container">
        <div className="row">
          <div className="col-md-offset-1 col-md-5 col-sm-8">
            <h2>Venue</h2>
            <p>The event will be held at the following location. We look forward to welcoming you!</p>
            {event.venueName && <h4>{event.venueName}</h4>}
            {event.venueStreet && <h4>{event.venueStreet}</h4>}
            {event.venueCity && <h4>{event.venueCity}</h4>}
            {event.venuePhone && <h4>Tel: {event.venuePhone}</h4>}
          </div>
        </div>
      </div>
    </section>
  )
}
