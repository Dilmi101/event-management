import { useState } from 'react'
import { submitRegistration } from '../api/eventApi'
import { sendAnalytics } from '../services/analyticsService'

interface Props {
  eventId: string
}

export default function RegisterSection({ eventId }: Props) {
  const [form, setForm] = useState({ firstName: '', lastName: '', phone: '', email: '' })
  const [submitting, setSubmitting] = useState(false)
  const [done, setDone] = useState(false)
  const [error, setError] = useState('')

const handleSubmit = async (e: React.FormEvent) => {
  e.preventDefault()
  setError('')
  setSubmitting(true)

  try {
    await submitRegistration({ ...form, eventId, ticketCount: 1 })

    // Send analytics only after successful registration
    await sendAnalytics(
      "registration_submit",
      eventId
    )

    setDone(true)
    setForm({
      firstName: '',
      lastName: '',
      phone: '',
      email: ''
    })

  } catch {
    setError('Registration failed. Please try again.')
  } finally {
    setSubmitting(false)
  }
}

  return (
    <section id="register">
      <div className="container">
        <div className="row">
          <div className="col-md-7 col-sm-7">
            <h2>Register Here</h2>
            <h3>Secure your spot now. Limited seats available!</h3>
            <p>Fill in the registration form to reserve your place at this event. We will send you a confirmation email with event details.</p>
          </div>
          <div className="col-md-5 col-sm-5">
            {done ? (
              <div className="success-message">
                Registration successful! Check your email for confirmation.
              </div>
            ) : (
              <form onSubmit={handleSubmit}>
                <input
                  name="firstname"
                  type="text"
                  className="form-control"
                  placeholder="First Name"
                  required
                  value={form.firstName}
                  onChange={e => setForm(p => ({ ...p, firstName: e.target.value }))}
                />
                <input
                  name="lastname"
                  type="text"
                  className="form-control"
                  placeholder="Last Name"
                  required
                  value={form.lastName}
                  onChange={e => setForm(p => ({ ...p, lastName: e.target.value }))}
                />
                <input
                  name="phone"
                  type="tel"
                  className="form-control"
                  placeholder="Phone Number"
                  value={form.phone}
                  onChange={e => setForm(p => ({ ...p, phone: e.target.value }))}
                />
                <input
                  name="email"
                  type="email"
                  className="form-control"
                  placeholder="Email Address"
                  required
                  value={form.email}
                  onChange={e => setForm(p => ({ ...p, email: e.target.value }))}
                />
                {error && <p style={{ color: '#f2545f' }}>{error}</p>}
                <div className="col-md-offset-6 col-md-6 col-sm-offset-1 col-sm-10">
                  <input
                    name="submit"
                    type="submit"
                    className="form-control"
                    id="submit"
                    value={submitting ? 'REGISTERING...' : 'REGISTER'}
                    disabled={submitting}
                  />
                </div>
              </form>
            )}
          </div>
        </div>
      </div>
    </section>
  )
}
