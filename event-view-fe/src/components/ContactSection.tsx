import { useState } from 'react'
import { submitContact } from '../api/eventApi'

export default function ContactSection() {
  const [form, setForm] = useState({ name: '', email: '', message: '' })
  const [submitting, setSubmitting] = useState(false)
  const [done, setDone] = useState(false)
  const [error, setError] = useState('')

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError('')
    setSubmitting(true)
    try {
      await submitContact(form)
      setDone(true)
      setForm({ name: '', email: '', message: '' })
    } catch {
      setError('Message failed to send. Please try again.')
    } finally {
      setSubmitting(false)
    }
  }

  return (
    <section id="contact">
      <div className="container">
        <div className="row">
          <div className="col-md-offset-1 col-md-5 col-sm-6">
            <div className="contact_des">
              <h3>New Event</h3>
              <p>Have questions, feedback, or want to partner with us? We'd love to hear from you. Send us a message and we'll get back to you as soon as possible.</p>
              <a href="#" className="btn btn-danger">DOWNLOAD NOW</a>
            </div>
          </div>
          <div className="col-md-5 col-sm-6">
            <div className="contact_detail">
              <div className="section-title">
                <h2>Keep in touch</h2>
              </div>
              {done ? (
                <div className="success-message">Message sent successfully!</div>
              ) : (
                <form onSubmit={handleSubmit}>
                  <input
                    name="name"
                    type="text"
                    className="form-control"
                    id="name"
                    placeholder="Name"
                    required
                    value={form.name}
                    onChange={e => setForm(p => ({ ...p, name: e.target.value }))}
                  />
                  <input
                    name="email"
                    type="email"
                    className="form-control"
                    id="email"
                    placeholder="Email"
                    required
                    value={form.email}
                    onChange={e => setForm(p => ({ ...p, email: e.target.value }))}
                  />
                  <textarea
                    name="message"
                    rows={5}
                    className="form-control"
                    id="message"
                    placeholder="Message"
                    required
                    value={form.message}
                    onChange={e => setForm(p => ({ ...p, message: e.target.value }))}
                  />
                  {error && <p style={{ color: '#f2545f' }}>{error}</p>}
                  <div className="col-md-6 col-sm-10">
                    <input
                      name="submit"
                      type="submit"
                      className="form-control"
                      id="submit"
                      value={submitting ? 'SENDING...' : 'SEND'}
                      disabled={submitting}
                    />
                  </div>
                </form>
              )}
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}
