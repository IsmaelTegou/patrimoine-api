import React, { useState } from 'react'
import DashboardCards from '../components/DashboardCards'
import Sidebar from '../components/Sidebar'
import Header from '../components/Header'
import Sites from './Sites'
import Itineraires from './Itineraires'
import Guides from './Guides'
import Evenements from './Evenements'
import Historique from './Historique'
import Rapports from './Rapports'
import Utilisateurs from './Utilisateurs'
import { Box } from '@mui/material'

const Dashboard = () => {
  const [selected, setSelected] = useState('dashboard')

  // Fonction pour afficher le contenu selon l'option sélectionnée
  const renderContent = () => {
    switch (selected) {
      case 'sites':
        return <Sites />
      case 'itineraires':
        return <Itineraires />
      case 'guides':
        return <Guides />
      case 'evenements':
        return <Evenements />
      case 'historique':
        return <Historique />
      case 'rapports':
        return <Rapports />
      case 'utilisateurs':
        return <Utilisateurs />
      default:
        return <DashboardCards />
    }
  }

  return (
    <Box sx={{ display: 'flex' }}>
      <Sidebar onSelect={setSelected} selected={selected} />
      <Box sx={{ flex: 1 }}>
        <Header />
        {renderContent()}
      </Box>
    </Box>
  )
}

export default Dashboard
