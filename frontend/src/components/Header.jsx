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
      sx={(theme) => ({
        width: `calc(100% - var(--sidebar-width, 250px))`,
        ml: 'var(--sidebar-width, 250px)',
        boxShadow: 'none',
        borderBottom: '1px solid',
        borderColor: theme.palette.mode === 'dark' ? 'rgba(255,255,255,0.1)' : 'rgba(0,0,0,0.08)',
        backdropFilter: 'blur(6px)',
        backgroundColor: theme.palette.mode === 'dark' 
          ? 'rgba(22, 28, 36, 0.95)'
          : 'rgba(255, 255, 255, 0.95)',
        color: theme.palette.text.primary,
      })}
    >
      <Toolbar sx={{ minHeight: '70px !important' }}>
        <Typography 
          variant="h6" 
          component="div" 
          sx={(theme) => ({ 
            flexGrow: 1, 
            fontWeight: 700,
            fontSize: '1.25rem',
            color: theme.palette.primary.main,
            letterSpacing: '-0.025em'
          })}
        >
          {pageTitle}
        </Typography>
        
        <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
          {/* Search Bar */}
          <Box
            sx={(theme) => ({
              display: 'flex',
              alignItems: 'center',
              backgroundColor: theme.palette.mode === 'dark' 
                ? 'rgba(255, 255, 255, 0.05)'
                : 'rgba(0, 0, 0, 0.04)',
              borderRadius: '8px',
              px: 2,
              py: 0.75,
              transition: 'all 0.2s ease-in-out',
              '&:hover': {
                backgroundColor: theme.palette.mode === 'dark'
                  ? 'rgba(255, 255, 255, 0.08)'
                  : 'rgba(0, 0, 0, 0.07)',
                boxShadow: theme.palette.mode === 'dark'
                  ? '0 2px 4px rgba(0,0,0,0.3)'
                  : '0 2px 4px rgba(0,0,0,0.05)'
              }
            })}
          >
            <SearchIcon sx={(theme) => ({ 
              color: theme.palette.text.secondary,
              mr: 1 
            })} />
            <InputBase
              placeholder="Rechercher..."
              sx={(theme) => ({ 
                color: theme.palette.text.primary,
                fontSize: '0.875rem',
                '&::placeholder': {
                  color: theme.palette.text.secondary,
                  opacity: 0.7
                },
                '& .MuiInputBase-input': {
                  color: theme.palette.text.primary,
                }
              })}
            />
          </Box>
          
          {/* Notifications */}
          <IconButton 
            sx={(theme) => ({ 
              color: theme.palette.text.primary,
              backgroundColor: theme.palette.mode === 'dark' 
                ? 'rgba(255, 255, 255, 0.05)'
                : 'rgba(0, 0, 0, 0.04)',
              '&:hover': {
                backgroundColor: theme.palette.mode === 'dark'
                  ? 'rgba(255, 255, 255, 0.08)'
                  : 'rgba(0, 0, 0, 0.07)',
              },
              width: 40,
              height: 40
            })}
          >
            <Badge 
              badgeContent={4} 
              color="error"
              sx={{
                '& .MuiBadge-badge': {
                  backgroundColor: '#FF4842',
                  color: 'white',
                  fontWeight: 600,
                  fontSize: '0.75rem'
                }
              }}
            >
              <NotificationsIcon sx={{ fontSize: 20 }} />
            </Badge>
          </IconButton>
          
          {/* Profile */}
          {/* Theme toggle */}
          <IconButton
            sx={{ 
              color: '#344767',
              backgroundColor: '#F8F9FA',
              '&:hover': {
                backgroundColor: '#F0F2F5'
              },
              width: 40,
              height: 40
            }}
          >
            <ThemeToggle />
          </IconButton>

          {/* Profile Menu */}
          <Box>
            <IconButton 
              onClick={handleProfileClick}
              sx={{ 
                color: '#344767',
                backgroundColor: '#F8F9FA',
                '&:hover': {
                  backgroundColor: '#F0F2F5'
                },
                width: 40,
                height: 40
              }}
            >
              <AccountCircle sx={{ fontSize: 20 }} />
            </IconButton>
            <Menu
              anchorEl={anchorEl}
              open={Boolean(anchorEl)}
              onClose={handleClose}
              onClick={handleClose}
              PaperProps={{
                elevation: 0,
                sx: (theme) => ({
                  mt: 1.5,
                  overflow: 'visible',
                  filter: theme.palette.mode === 'dark'
                    ? 'drop-shadow(0px 4px 12px rgba(0,0,0,0.3))'
                    : 'drop-shadow(0px 4px 12px rgba(0,0,0,0.1))',
                  borderRadius: '12px',
                  minWidth: 200,
                  bgcolor: theme.palette.background.paper,
                  border: '1px solid',
                  borderColor: theme.palette.mode === 'dark'
                    ? 'rgba(255,255,255,0.1)'
                    : 'rgba(0,0,0,0.08)',
                  '&:before': {
                    content: '""',
                    display: 'block',
                    position: 'absolute',
                    top: 0,
                    right: 14,
                    width: 10,
                    height: 10,
                    bgcolor: 'inherit',
                    borderTop: '1px solid',
                    borderLeft: '1px solid',
                    borderColor: 'inherit',
                    transform: 'translateY(-50%) rotate(45deg)',
                    zIndex: 0,
                  },
                }),
              }}
              transformOrigin={{ horizontal: 'right', vertical: 'top' }}
              anchorOrigin={{ horizontal: 'right', vertical: 'bottom' }}
            >
              <MenuItem onClick={handleClose} sx={{ py: 1.5 }}>
                <ListItemIcon>
                  <PersonIcon fontSize="small" sx={{ color: '#1A73E8' }} />
                </ListItemIcon>
                <Typography 
                  variant="body2" 
                  sx={(theme) => ({ 
                    color: theme.palette.text.primary, 
                    fontWeight: 500 
                  })}
                >
                  Profile
                </Typography>
              </MenuItem>
              <Divider sx={(theme) => ({ 
                my: 1,
                borderColor: theme.palette.mode === 'dark' 
                  ? 'rgba(255,255,255,0.1)' 
                  : 'rgba(0,0,0,0.08)'
              })} />
              <MenuItem onClick={handleLogout} sx={{ py: 1.5 }}>
                <ListItemIcon>
                  <LogoutIcon fontSize="small" sx={{ color: '#FF4842' }} />
                </ListItemIcon>
                <Typography 
                  variant="body2" 
                  sx={(theme) => ({ 
                    color: theme.palette.text.primary, 
                    fontWeight: 500 
                  })}
                >
                  Déconnexion
                </Typography>
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
    <Tooltip 
      title={mode === 'light' ? 'Activer le mode sombre' : 'Désactiver le mode sombre'}
      arrow
      placement="bottom"
    >
      <Box onClick={toggleMode} sx={{ display: 'flex', alignItems: 'center', cursor: 'pointer' }}>
        {mode === 'light' ? 
          <Brightness4Icon sx={{ fontSize: 20 }} /> : 
          <Brightness7Icon sx={{ fontSize: 20 }} />
        }
      </Box>
    </Tooltip>
  )
}