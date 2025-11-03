import { useState } from 'react'
import Login from './components/Login'
import Signup from './components/Signup'
import Dashboard from './components/Dashboard'
import ThemeProvider from './theme/ThemeProvider'
import './assets/images/logo.jpg'

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false)
  const [authView, setAuthView] = useState('login') // 'login' | 'signup'
  const [notification, setNotification] = useState(null)

  const handleLogin = () => {
    setIsLoggedIn(true)
  }

  const handleLogout = () => {
    setIsLoggedIn(false)
    setAuthView('login') // Assure que l'utilisateur revient toujours à la page de connexion
  }

  const handleCreateAccount = (success, message) => {
    setNotification({ type: success ? 'success' : 'error', message })
    setAuthView('login')
  }

  const renderAuthContent = () => {
    if (isLoggedIn) {
      return <Dashboard onLogout={handleLogout} />;
    }
    
    if (authView === 'login') {
      return (
        <Login
          onLogin={handleLogin}
          onShowSignup={() => setAuthView('signup')}
          notification={notification}
          clearNotification={() => setNotification(null)}
        />
      );
    }
    
    return (
      <Signup 
        onCreateAccount={handleCreateAccount} 
        onShowLogin={() => setAuthView('login')} 
      />
    );
  };

  console.log('App rendering...');
  return (
    <>
      <ThemeProvider>
        {renderAuthContent()}
      </ThemeProvider>
    </>
  )
}

export default App