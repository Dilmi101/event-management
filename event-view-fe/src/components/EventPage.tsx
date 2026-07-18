import { useEffect, useState, useCallback } from 'react'
import { useParams } from 'react-router-dom'
import type { Event, Sponsor, Speaker, Session } from '../types/event'
import { fetchEvent, fetchSponsorsByEvent, fetchSpeakersByEvent, fetchSessionsByEvent } from '../api/eventApi'
import Navbar from './Navbar'
import IntroSection from './IntroSection'
import OverviewSection from './OverviewSection'
import DetailSection from './DetailSection'
import VideoSection from './VideoSection'
import SpeakersSection from './SpeakersSection'
import ProgramSection from './ProgramSection'
import RegisterSection from './RegisterSection'
import FaqSection from './FaqSection'
import VenueSection from './VenueSection'
import SponsorsSection from './SponsorsSection'
import ContactSection from './ContactSection'
import FooterSection from './FooterSection'

export default function EventPage() {
  const { eventId } = useParams<{ eventId: string }>()
  const [event, setEvent] = useState<Event | null>(null)
  const [sponsors, setSponsors] = useState<Sponsor[]>([])
  const [speakers, setSpeakers] = useState<Speaker[]>([])
  const [sessions, setSessions] = useState<Session[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  const loadData = useCallback(async () => {
    if (!eventId) return
    setLoading(true)
    setError('')
    try {
      const [ev, sp, sk, ss] = await Promise.all([
        fetchEvent(eventId),
        fetchSponsorsByEvent(eventId),
        fetchSpeakersByEvent(eventId),
        fetchSessionsByEvent(eventId),
      ])
      setEvent(ev)
      setSponsors(sp)
      setSpeakers(sk)
      setSessions(ss)
    } catch {
      setError('Failed to load event data. Make sure the backend services are running.')
    } finally {
      setLoading(false)
    }
  }, [eventId])

  useEffect(() => {
    loadData()
  }, [loadData])

  if (!eventId) {
    return (
      <div className="error-container">
        <h2>No event ID provided</h2>
        <p>Visit /event/:eventId to view an event.</p>
      </div>
    )
  }

  if (loading) {
    return (
      <div className="loading-container">
        <div className="loading-spinner" />
        <p>Loading event details...</p>
      </div>
    )
  }

  if (error || !event) {
    return (
      <div className="error-container">
        <h2>Error</h2>
        <p>{error || 'Event not found'}</p>
        <button onClick={loadData} style={{ marginTop: '1rem', padding: '10px 20px', cursor: 'pointer' }}>
          Retry
        </button>
      </div>
    )
  }

  return (
    <div>
      <Navbar />
      <IntroSection event={event} />
      <OverviewSection event={event} />
      <DetailSection event={event} />
      <VideoSection />
      <SpeakersSection speakers={speakers} />
      <ProgramSection sessions={sessions} />
      <RegisterSection eventId={eventId} />
      <FaqSection />
      <VenueSection event={event} />
      <SponsorsSection sponsors={sponsors} />
      <ContactSection />
      <FooterSection />
      <div className="go-top" onClick={() => window.scrollTo({ top: 0, behavior: 'smooth' })}>
        <i className="fa fa-angle-up" />
      </div>
    </div>
  )
}
