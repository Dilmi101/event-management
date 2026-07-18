import type { Sponsor } from '../types/event'

interface Props {
  sponsors: Sponsor[]
}

export default function SponsorsSection({ sponsors }: Props) {
  return (
    <section id="sponsors">
      <div className="container">
        <div className="row">
          <div className="col-md-12 col-sm-12">
            <div className="section-title">
              <h2>Our Sponsors</h2>
              <p>{sponsors.length > 0 ? 'Proudly supported by our sponsors.' : 'Sponsor information coming soon.'}</p>
            </div>
          </div>
          {sponsors.map(sponsor => (
            <div key={sponsor.sponsorId} className="col-md-3 col-sm-6 col-xs-6 sponsor-logo">
              <img
                src={sponsor.logoUrl || 'https://via.placeholder.com/200x100?text=Sponsor'}
                className="img-responsive"
                alt={sponsor.name}
              />
            </div>
          ))}
        </div>
      </div>
    </section>
  )
}
