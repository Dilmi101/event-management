import type { Event, Sponsor, Speaker, Session, RegistrationRequest, ContactMessage } from '../types/event';

const EVENT_SERVICE_URL = 'http://localhost:8081';
const PROGRAM_SERVICE_URL = 'http://localhost:8082';
const REGISTRATION_SERVICE_URL = 'http://localhost:8083';

export async function fetchEvent(eventId: string): Promise<Event> {
  const res = await fetch(`${EVENT_SERVICE_URL}/api/events/${eventId}`);
  if (!res.ok) throw new Error(`Failed to fetch event: ${res.status}`);
  return res.json();
}

export async function fetchSponsorsByEvent(eventId: string): Promise<Sponsor[]> {
  const res = await fetch(`${EVENT_SERVICE_URL}/api/sponsors`);
  if (!res.ok) throw new Error(`Failed to fetch sponsors: ${res.status}`);
  const all: Sponsor[] = await res.json();
  return all.filter(s => s.eventId === eventId);
}

export async function fetchSpeakersByEvent(eventId: string): Promise<Speaker[]> {
  const res = await fetch(`${PROGRAM_SERVICE_URL}/api/speakers`);
  if (!res.ok) throw new Error(`Failed to fetch speakers: ${res.status}`);
  const all: Speaker[] = await res.json();
  return all.filter(s => s.eventId === eventId);
}

export async function fetchSessionsByEvent(eventId: string): Promise<Session[]> {
  const res = await fetch(`${PROGRAM_SERVICE_URL}/api/sessions`);
  if (!res.ok) throw new Error(`Failed to fetch sessions: ${res.status}`);
  const all: Session[] = await res.json();
  return all.filter(s => s.eventId === eventId);
}

export async function submitRegistration(data: RegistrationRequest): Promise<void> {
  const res = await fetch(`${REGISTRATION_SERVICE_URL}/api/registrations`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error(`Registration failed: ${res.status}`);
}

export async function submitContact(data: ContactMessage): Promise<void> {
  const res = await fetch(`${EVENT_SERVICE_URL}/api/contact`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error(`Contact submission failed: ${res.status}`);
}
