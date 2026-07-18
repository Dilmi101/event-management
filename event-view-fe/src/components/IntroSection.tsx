import type { Event } from '../types/event'

interface Props {
  event: Event
}

function formatDate(dateStr: string): string {
  const d = new Date(dateStr)
  return d.toLocaleDateString('en-US', { month: 'long', day: 'numeric', year: 'numeric' })
}

function formatTime(timeStr: string): string {
  const [h, m] = timeStr.split(':')
  const hour = parseInt(h, 10)
  const ampm = hour >= 12 ? 'PM' : 'AM'
  const h12 = hour % 12 || 12
  return `${h12}:${m} ${ampm}`
}

export default function IntroSection({ event }: Props) {
  return (
    <section id="intro">
      <div className="container">
        <div className="row">
          <div className="col-md-12 col-sm-12">
            <h3>{formatDate(event.eventDate)} at {formatTime(event.eventTime)} in {event.venueCity || 'TBD'}</h3>
            <h1>{event.title}</h1>
            <a href="#overview" className="btn btn-lg btn-default smoothScroll hidden-xs" onClick={(e) => { e.preventDefault(); document.getElementById('overview')?.scrollIntoView({ behavior: 'smooth' }) }}>
              LEARN MORE
            </a>
            <a href="#register" className="btn btn-lg btn-danger" onClick={(e) => { e.preventDefault(); document.getElementById('register')?.scrollIntoView({ behavior: 'smooth' }) }}>
              REGISTER NOW
            </a>
          </div>
        </div>
      </div>
    </section>
  )
}
