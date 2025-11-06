import React from 'react'
import { Box, IconButton, Tooltip } from '@mui/material'
import Brightness4Icon from '@mui/icons-material/Brightness4'
import Brightness7Icon from '@mui/icons-material/Brightness7'
import { useThemeMode } from '../context/ThemeModeContext'
import { useLocation } from 'react-router-dom'

const DarkModeToggle = () => {
  const { mode, toggleMode } = useThemeMode()
  const location = useLocation()

  // Don't render the toggle on the dashboard (root path)
  if (location && (location.pathname === '/' || location.pathname === '')) {
    return null
  }

  return (
    <Box sx={{ position: 'fixed', right: 12, top: 12, zIndex: 1500 }}>
      <Tooltip title={mode === 'light' ? 'Activer le mode sombre' : 'Désactiver le mode sombre'}>
        <IconButton onClick={toggleMode} color="inherit" size="large" sx={{ bgcolor: 'background.paper', boxShadow: '0 4px 10px rgba(0,0,0,0.08)' }}>
          {mode === 'light' ? <Brightness4Icon /> : <Brightness7Icon />}
        </IconButton>
      </Tooltip>
    </Box>
  )
}

export default DarkModeToggle
