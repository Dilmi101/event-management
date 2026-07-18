import { Routes, Route, Navigate } from 'react-router-dom'
import EventPage from './components/EventPage'
import './App.css'

function App() {
  return (
    <Routes>
      <Route path="/event/:eventId" element={<EventPage />} />
      <Route path="*" element={<Navigate to="/event/demo" replace />} />
    </Routes>
  )
}

export default App
