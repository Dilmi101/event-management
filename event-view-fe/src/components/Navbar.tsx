import { useState } from 'react'

const sections = [
  { id: 'intro', label: 'Intro' },
  { id: 'overview', label: 'Overview' },
  { id: 'speakers', label: 'Speakers' },
  { id: 'program', label: 'Programs' },
  { id: 'register', label: 'Register' },
  { id: 'venue', label: 'Venue' },
  { id: 'sponsors', label: 'Sponsors' },
  { id: 'contact', label: 'Contact' },
]

export default function Navbar() {
  const [expanded, setExpanded] = useState(false)
  const [activeId, setActiveId] = useState('intro')

  const scrollTo = (id: string) => {
    setActiveId(id)
    setExpanded(false)
    const el = document.getElementById(id)
    if (el) {
      el.scrollIntoView({ behavior: 'smooth' })
    }
  }

  return (
    <nav className="navbar navbar-fixed-top custom-navbar" role="navigation">
      <div className="container">
        <div className="navbar-header">
          <button
            className="navbar-toggle"
            onClick={() => setExpanded(e => !e)}
          >
            <span className="icon-bar" />
            <span className="icon-bar" />
            <span className="icon-bar" />
          </button>
          <a href="#intro" className="navbar-brand" onClick={(e) => { e.preventDefault(); scrollTo('intro') }}>
            New Event
          </a>
        </div>
        <div className={`collapse navbar-collapse${expanded ? ' in' : ''}`}>
          <ul className="nav navbar-nav navbar-right">
            {sections.map(s => (
              <li key={s.id} className={activeId === s.id ? 'active' : ''}>
                <a
                  href={`#${s.id}`}
                  onClick={(e) => { e.preventDefault(); scrollTo(s.id) }}
                >
                  {s.label}
                </a>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </nav>
  )
}
