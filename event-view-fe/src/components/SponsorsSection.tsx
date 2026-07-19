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
                onError={e => { e.currentTarget.src = 'data:image/svg+xml,%3Csvg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 100 100%22%3E%3Ccircle cx=%2250%22 cy=%2230%22 r=%2220%22 fill=%22%23ccc%22/%3E%3Cellipse cx=%2250%22 cy=%2270%22 rx=%2235%22 ry=%2225%22 fill=%22%23ccc%22/%3E%3C/svg%3E' }}
              />
            </div>
          ))}
        </div>
      </div>
    </section>
  )
}
