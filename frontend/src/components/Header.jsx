import React, { useState, useMemo } from 'react'
import {
  AppBar,
  Toolbar,
  Typography,
  IconButton,
  InputBase,
  Badge,
  Box,
  Menu,
  MenuItem,
  ListItemIcon,
  Divider,
} from '@mui/material'
import { useLocation } from 'react-router-dom'
import SearchIcon from '@mui/icons-material/Search'
import NotificationsIcon from '@mui/icons-material/Notifications'
import AccountCircle from '@mui/icons-material/AccountCircle'
import Brightness4Icon from '@mui/icons-material/Brightness4'
import Brightness7Icon from '@mui/icons-material/Brightness7'
import PersonIcon from '@mui/icons-material/Person'
import LogoutIcon from '@mui/icons-material/Logout'
import { Tooltip } from '@mui/material'
import { useThemeMode } from '../context/ThemeModeContext'
import { useAuth } from '../context/AuthContext'

const Header = () => {
  const [anchorEl, setAnchorEl] = useState(null)
  const { signOut } = useAuth()
  const location = useLocation()
  
  // Get page title based on current path
  const pageTitle = useMemo(() => {
    switch (location.pathname) {
      case '/app':
        return 'Dashboard'
      case '/app/sites':
        return 'Sites touristiques'
      case '/app/itineraires':
        return 'Itinéraires touristiques'
      case '/app/guides':
        return 'Guides touristiques'
      case '/app/evenements':
        return 'Événements'
      case '/app/historique':
        return 'Historique de visites'
      case '/app/rapports':
        return 'Rapport & Statistiques'
      case '/app/utilisateurs':
        return 'Utilisateurs'
      default:
        return 'Dashboard'
    }
  }, [location.pathname])

  const handleProfileClick = (event) => {
    setAnchorEl(event.currentTarget)
  }

  const handleClose = () => {
    setAnchorEl(null)
  }

  const handleLogout = () => {
    handleClose()
    signOut()
  }

  return (
    <AppBar
      position="fixed"
      sx={{
        width: `calc(100% - 250px)`,
        ml: '250px',
        backgroundColor: 'white',
        color: '#344767',
        boxShadow: '0 4px 6px -1px rgba(0,0,0,0.1)',
      }}
    >
      <Toolbar>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1, fontWeight: 'bold' }}>
          {pageTitle}
        </Typography>
        
        <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
          {/* Search Bar */}
          <Box
            sx={{
              display: 'flex',
              alignItems: 'center',
              backgroundColor: '#F8F9FA',
              borderRadius: 1,
              px: 2,
              py: 0.5,
            }}
          >
            <SearchIcon sx={{ color: '#67748E', mr: 1 }} />
            <InputBase
              placeholder="Search here"
              sx={{ color: '#67748E' }}
            />
          </Box>
          
          {/* Notifications */}
          <IconButton sx={{ color: '#344767' }}>
            <Badge badgeContent={4} color="error">
              <NotificationsIcon />
            </Badge>
          </IconButton>
          
          {/* Profile */}
          {/* Theme toggle */}
          <ThemeToggle />

          {/* Profile Menu */}
          <Box>
            <IconButton 
              onClick={handleProfileClick}
              sx={{ color: '#344767' }}
            >
              <AccountCircle />
            </IconButton>
            <Menu
              anchorEl={anchorEl}
              open={Boolean(anchorEl)}
              onClose={handleClose}
              onClick={handleClose}
              PaperProps={{
                elevation: 3,
                sx: {
                  mt: 1.5,
                  overflow: 'visible',
                  filter: 'drop-shadow(0px 2px 8px rgba(0,0,0,0.12))',
                  '& .MuiAvatar-root': {
                    width: 32,
                    height: 32,
                    ml: -0.5,
                    mr: 1,
                  },
                },
              }}
              transformOrigin={{ horizontal: 'right', vertical: 'top' }}
              anchorOrigin={{ horizontal: 'right', vertical: 'bottom' }}
            >
              <MenuItem onClick={handleClose}>
                <ListItemIcon>
                  <PersonIcon fontSize="small" sx={{ color: '#344767' }} />
                </ListItemIcon>
                Profile
              </MenuItem>
              <Divider />
              <MenuItem onClick={handleLogout}>
                <ListItemIcon>
                  <LogoutIcon fontSize="small" sx={{ color: '#344767' }} />
                </ListItemIcon>
                Déconnexion
              </MenuItem>
            </Menu>
          </Box>
        </Box>
      </Toolbar>
    </AppBar>
  )
}

export default Header

// Small inline component to keep Header tidy
function ThemeToggle() {
  const { mode, toggleMode } = useThemeMode()
  return (
    <Tooltip title={mode === 'light' ? 'Activer le mode sombre' : 'Désactiver le mode sombre'}>
      <IconButton onClick={toggleMode} sx={{ color: '#344767' }}>
        {mode === 'light' ? <Brightness4Icon /> : <Brightness7Icon />}
      </IconButton>
    </Tooltip>
  )
}