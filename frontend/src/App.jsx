import React from 'react'
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import { AuthProvider } from './context/AuthContext'
import ProtectedRoute from './components/ProtectedRoute'
import SignIn from './pages/SignIn'
import SignUp from './pages/SignUp'
import Dashboard from './pages/Dashboard'
import Layout from './components/Layout'
import Sites from './pages/Sites'
import Itineraires from './pages/Itineraires'
import Guides from './pages/Guides'
import Evenements from './pages/Evenements'
import Historique from './pages/Historique'
import Rapports from './pages/Rapports'
import Utilisateurs from './pages/Utilisateurs'

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route path="/signin" element={<SignIn />} />
          <Route path="/signup" element={<SignUp />} />

          {/* Protected app routes live under /app - root '/' redirects to sign in */}
          <Route
            path="/app"
            element={
              <ProtectedRoute>
                <Layout />
              </ProtectedRoute>
            }
          >
            <Route index element={<Dashboard />} />
            <Route path="sites" element={<Sites />} />
            <Route path="itineraires" element={<Itineraires />} />
            <Route path="guides" element={<Guides />} />
            <Route path="evenements" element={<Evenements />} />
            <Route path="historique" element={<Historique />} />
            <Route path="rapports" element={<Rapports />} />
            <Route path="utilisateurs" element={<Utilisateurs />} />
          </Route>

          <Route path="/" element={<Navigate to="/signin" replace />} />
          <Route path="*" element={<Navigate to="/signin" replace />} />
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  )
}

export default App