import type { Event } from '../types/event'

interface Props {
  event: Event
}

export default function OverviewSection({ event }: Props) {
  return (
    <section id="overview">
      <div className="container">
        <div className="row">
          <div className="col-md-6 col-sm-6">
            <h3>{event.title}</h3>
            <p>{event.description || 'No description available.'}</p>
            <p>Tickets: ${Number(event.ticketPrice).toFixed(2)} &middot; {event.seatsAvailable} of {event.capacity} seats remaining</p>
          </div>
          <div className="col-md-6 col-sm-6">
            <img
              src="https://images.unsplash.com/photo-1505373877841-8d25f7d46678?w=600"
              className="img-responsive"
              alt="Overview"
            />
          </div>
        </div>
      </div>
    </section>
  )
}
