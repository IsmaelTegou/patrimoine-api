import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import { ThemeModeProvider } from './context/ThemeModeContext'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <ThemeModeProvider>
      <div>
        <App />
      </div>
    </ThemeModeProvider>
  </React.StrictMode>
);