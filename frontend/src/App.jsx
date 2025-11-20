import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import ProtectedRoute from './components/ProtectedRoute';
import SignIn from './pages/SignIn';
import SignUp from './pages/SignUp';
import DashboardTourist from './pages/DashboardTourist';
import Dashboard from './pages/Dashboard';
import Layout from './components/Layout';
import Sites from './pages/Sites';
import Itineraires from './pages/Itineraires';
import Guides from './pages/Guides';
import Evenements from './pages/Evenements';
import Historique from './pages/Historique';
import Rapports from './pages/Rapports';
import Utilisateurs from './pages/Utilisateurs';
import Home from './pages/Home';

// Custom route to handle admin/tourist home logic
function AppRoutes() {
  const { user } = useAuth() || {};
  // Utilise le champ role pour déterminer l'accès
  const isAdmin = user && user.role === 'admin';

  return (
    <Routes>
      <Route path="/signin" element={<SignIn />} />
      <Route path="/signup" element={<SignUp />} />

      <Route
        path="/app"
        element={
          <ProtectedRoute>
            {isAdmin ? <Dashboard /> : <Home />} {/* Redirige selon le rôle */}
          </ProtectedRoute>
        }
      />
      <Route path="/sites" element={<Sites />} />
      <Route path="/itineraires" element={<Itineraires />} />
      <Route path="/guides" element={<Guides />} />
      <Route path="/evenements" element={<Evenements />} />
      <Route path="/historique" element={<Historique />} />
      <Route path="/rapports" element={<Rapports />} />
      <Route path="/utilisateurs" element={<Utilisateurs />} />

      <Route path="/" element={<Navigate to="/signin" replace />} />
      <Route path="*" element={<Navigate to="/signin" replace />} />
    </Routes>
  );
}

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <AppRoutes />
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;