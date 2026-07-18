import type { Speaker } from '../types/event'

interface Props {
  speakers: Speaker[]
}

export default function SpeakersSection({ speakers }: Props) {
  if (speakers.length === 0) {
    return (
      <section id="speakers">
        <div className="container">
          <div className="row">
            <div className="col-md-12 col-sm-12">
              <div className="section-title">
                <h2>Creative Speakers</h2>
                <p>Speaker lineup coming soon. Stay tuned for updates!</p>
              </div>
            </div>
          </div>
        </div>
      </section>
    )
  }

  return (
    <section id="speakers">
      <div className="container">
        <div className="row">
          <div className="col-md-12 col-sm-12">
            <div className="section-title">
              <h2>Creative Speakers</h2>
              <p>Meet our lineup of expert speakers.</p>
            </div>
          </div>
          {speakers.map(speaker => (
            <div key={speaker.speakerId} className="col-md-3 col-sm-3 speaker-item">
              <div className="speakers-wrapper">
                <img
                  src={speaker.photoUrl || 'https://via.placeholder.com/400x300?text=Speaker'}
                  className="img-responsive"
                  alt={speaker.name}
                />
                <div className="speakers-thumb">
                  <h3>{speaker.name}</h3>
                  <h6>{speaker.role || 'Speaker'}</h6>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  )
}
