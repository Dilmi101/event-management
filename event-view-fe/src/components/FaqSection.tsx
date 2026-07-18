import { useState } from 'react'

interface FaqItem {
  question: string
  answer: string
}

const faqs: FaqItem[] = [
  {
    question: 'What is Responsive Design?',
    answer: 'Responsive design ensures web pages render well on various devices and window sizes. It uses fluid grids, flexible images, and media queries to adapt the layout to the viewing environment. This event covers responsive design principles and best practices.',
  },
  {
    question: 'What are latest UX Developments?',
    answer: 'User experience (UX) continues to evolve with new tools and methodologies. Topics include AI-driven personalization, accessibility-first design, micro-interactions, and design system scalability. Our speakers will share cutting-edge insights from the field.',
  },
  {
    question: 'What things about Social Media will be discussed?',
    answer: 'We will explore social media marketing strategies, content creation, community management, and analytics. Learn how to leverage platforms effectively to build brand awareness and drive engagement.',
  },
]

export default function FaqSection() {
  const [open, setOpen] = useState<number>(0)

  return (
    <section id="faq">
      <div className="container">
        <div className="row">
          <div className="col-md-offset-2 col-md-8 col-sm-offset-1 col-sm-10 text-center">
            <div className="section-title">
              <h2>Do you have Questions?</h2>
              <p>Find answers to common questions below. Still have questions? Contact us!</p>
            </div>
          </div>
          <div className="col-md-offset-1 col-md-10 col-sm-offset-1 col-sm-10">
            <div className="panel-group" id="accordion" role="tablist">
              {faqs.map((faq, idx) => (
                <div key={idx} className="panel panel-default">
                  <div className="panel-heading" role="tab">
                    <h4 className="panel-title">
                      <a
                        className={open !== idx ? 'collapsed' : ''}
                        onClick={() => setOpen(open === idx ? -1 : idx)}
                      >
                        {faq.question}
                      </a>
                    </h4>
                  </div>
                  <div
                    className={`panel-collapse${open === idx ? ' in' : ''}`}
                    role="tabpanel"
                  >
                    <div className="panel-body">
                      <p>{faq.answer}</p>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}
