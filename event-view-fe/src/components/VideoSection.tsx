export default function VideoSection() {
  return (
    <section id="video">
      <div className="container">
        <div className="row">
          <div className="col-md-6 col-sm-10">
            <h2>Watch Video</h2>
            <h3>Get a sneak peek of what this event has to offer.</h3>
            <p>Join industry leaders and experts as they share insights, trends, and best practices. This event is designed to inspire and equip you with the knowledge you need to succeed.</p>
          </div>
          <div className="col-md-6 col-sm-10">
            <div className="embed-responsive embed-responsive-16by9">
              <iframe
                width="560"
                height="315"
                src="https://www.youtube.com/embed/XDPwXQjAlB0"
                title="Event Video"
                allowFullScreen
                style={{ border: 'none', maxWidth: '100%' }}
              />
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}
