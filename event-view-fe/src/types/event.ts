export interface Event {
  eventId: string;
  title: string;
  description: string | null;
  venueName: string | null;
  venueStreet: string | null;
  venueCity: string | null;
  venuePhone: string | null;
  eventDate: string;
  eventTime: string;
  ticketPrice: number;
  capacity: number;
  seatsAvailable: number;
  createdAt: string;
  updatedAt: string;
}

export interface Sponsor {
  sponsorId: string;
  eventId: string;
  name: string;
  logoUrl: string | null;
  createdAt: string;
}

export interface Speaker {
  speakerId: string;
  eventId: string;
  name: string;
  role: string | null;
  bio: string | null;
  photoUrl: string | null;
  createdAt: string;
}

export interface Session {
  sessionId: string;
  trackId: string;
  eventId: string;
  day: number;
  sessionTitle: string;
  speakerName: string;
  startTime: string;
  endTime: string;
  room: string | null;
  createdAt: string;
}

export interface RegistrationRequest {
  eventId: string;
  firstName: string;
  lastName: string;
  phone: string;
  email: string;
  ticketCount: number;
}

export interface ContactMessage {
  name: string;
  email: string;
  message: string;
}
