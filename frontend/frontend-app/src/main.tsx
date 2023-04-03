import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './index.css'

export const GlobalURL=`http://localhost:8080`;


ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
)
