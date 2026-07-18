import { useState, useMemo } from 'react'
import type { Session } from '../types/event'

interface Props {
  sessions: Session[]
}

export default function ProgramSection({ sessions }: Props) {
  const days = useMemo(() => {
    const d = new Set<number>()
    sessions.forEach(s => d.add(s.day))
    return Array.from(d).sort()
  }, [sessions])

  const [activeDay, setActiveDay] = useState(days.length > 0 ? days[0] : 1)

  const daySessions = sessions.filter(s => s.day === activeDay)

  return (
    <section id="program">
      <div className="container">
        <div className="row">
          <div className="col-md-12 col-sm-12">
            <div className="section-title">
              <h2>Our Programs</h2>
              <p>{sessions.length > 0 ? 'Browse the event schedule by day.' : 'Program details coming soon.'}</p>
            </div>
          </div>
          {sessions.length > 0 && (
            <div className="col-md-10 col-sm-10">
              <ul className="nav nav-tabs" role="tablist">
                {days.map(day => (
                  <li key={day} className={activeDay === day ? 'active' : ''} role="presentation">
                    <a onClick={() => setActiveDay(day)}>
                      {day === 1 ? 'FIRST' : day === 2 ? 'SECOND' : 'THIRD'} DAY
                    </a>
                  </li>
                ))}
              </ul>
              <div className="tab-content">
                {daySessions.map((session, idx) => (
                  <div key={session.sessionId}>
                    <div className="col-md-2 col-sm-2">
                      <img
                        src={`https://via.placeholder.com/150?text=${session.speakerName.charAt(0)}`}
                        className="img-responsive"
                        alt={session.speakerName}
                      />
                    </div>
                    <div className="col-md-10 col-sm-10">
                      <h6>
                        <span><i className="fa fa-clock-o" /> {session.startTime}</span>
                        {session.room && (
                          <span><i className="fa fa-map-marker" /> {session.room}</span>
                        )}
                      </h6>
                      <h3>{session.sessionTitle}</h3>
                      <h4>By {session.speakerName}</h4>
                    </div>
                    {idx < daySessions.length - 1 && (
                      <div className="program-divider col-md-12 col-sm-12" />
                    )}
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      </div>
    </section>
  )
}
