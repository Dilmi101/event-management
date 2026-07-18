import type { Event } from '../types/event'

interface Props {
  event: Event
}

export default function DetailSection({ event }: Props) {
  return (
    <section id="detail">
      <div className="container">
        <div className="row">
          <div className="col-md-4 col-sm-4">
            <i className="fa fa-group" />
            <h3>{event.capacity.toLocaleString()} Participants</h3>
            <p>Total capacity for this event. Join {event.seatsAvailable.toLocaleString()} other attendees.</p>
          </div>
          <div className="col-md-4 col-sm-4">
            <i className="fa fa-clock-o" />
            <h3>Event Day</h3>
            <p>Happening on {new Date(event.eventDate).toLocaleDateString('en-US', { weekday: 'long', month: 'long', day: 'numeric', year: 'numeric' })} at {event.eventTime}.</p>
          </div>
          <div className="col-md-4 col-sm-4">
            <i className="fa fa-microphone" />
            <h3>{event.venueCity || 'Great'} Location</h3>
            <p>Taking place in {event.venueCity || 'a premier venue'} at {event.venueName || 'the main hall'}.</p>
          </div>
        </div>
      </div>
    </section>
  )
}
