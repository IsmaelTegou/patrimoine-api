import React, { useState, useEffect } from 'react'
import {
  Drawer,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Typography,
  Box,
  Divider,
  IconButton,
  Tooltip,
} from '@mui/material'
import DashboardIcon from '@mui/icons-material/Dashboard'
import PlaceIcon from '@mui/icons-material/Place'
import RouteIcon from '@mui/icons-material/AltRoute'
import GuideIcon from '@mui/icons-material/VolunteerActivism'
import EventIcon from '@mui/icons-material/Event'
import HistoryIcon from '@mui/icons-material/History'
import BarChartIcon from '@mui/icons-material/BarChart'
import PeopleIcon from '@mui/icons-material/People'
import { useLocation, useNavigate } from 'react-router-dom'
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft'
import ChevronRightIcon from '@mui/icons-material/ChevronRight'

const EXPANDED_WIDTH = 260
const COLLAPSED_WIDTH = 64
const STORAGE_KEY = 'patrimoine_sidebar_collapsed'

// Use /app as base for protected routes so root can redirect to /signin
const items = [
  { text: 'Dashboard', icon: DashboardIcon, path: '/app' },
  { text: 'Sites touristiques', icon: PlaceIcon, path: '/app/sites' },
  { text: "Itinéraires touristiques", icon: RouteIcon, path: '/app/itineraires' },
  { text: 'Guides touristiques', icon: GuideIcon, path: '/app/guides' },
  { text: 'Événements', icon: EventIcon, path: '/app/evenements' },
  { text: 'Historique de visites', icon: HistoryIcon, path: '/app/historique' },
  { text: 'Rapport & Statistiques', icon: BarChartIcon, path: '/app/rapports' },
  { text: 'Utilisateurs', icon: PeopleIcon, path: '/app/utilisateurs' },
]

const Sidebar = () => {
  const location = useLocation()
  const navigate = useNavigate()
  const [collapsed, setCollapsed] = useState(() => {
    try {
      const stored = localStorage.getItem(STORAGE_KEY)
      return stored ? JSON.parse(stored) : false
    } catch (e) {
      return false
    }
  })

  // Sync CSS variable so Layout and Header can read the width
  useEffect(() => {
    const w = collapsed ? COLLAPSED_WIDTH : EXPANDED_WIDTH
    document.documentElement.style.setProperty('--sidebar-width', `${w}px`)
    try {
      localStorage.setItem(STORAGE_KEY, JSON.stringify(collapsed))
    } catch (e) {
      // ignore storage errors
    }
  }, [collapsed])

  return (
    <Drawer
      sx={{
        width: collapsed ? COLLAPSED_WIDTH : EXPANDED_WIDTH,
        flexShrink: 0,
        '& .MuiDrawer-paper': {
          width: collapsed ? COLLAPSED_WIDTH : EXPANDED_WIDTH,
          boxSizing: 'border-box',
          background: 'linear-gradient(195deg, #1A73E8, #0D47A1)',
          color: 'white',
          transition: 'width 200ms ease',
        },
      }}
      variant="permanent"
      anchor="left"
    >
      <Box sx={{ p: 1.5, display: 'flex', alignItems: 'center', justifyContent: collapsed ? 'center' : 'space-between' }}>
        <Typography variant="h6" sx={{ fontWeight: '700', color: '#FFFDE7', display: collapsed ? 'none' : 'block', letterSpacing: 0.2 }}>
          Patrimoine
        </Typography>
        <Tooltip title={collapsed ? 'Développer la barre' : 'Réduire la barre'}>
          <IconButton
            onClick={() => setCollapsed((c) => !c)}
            sx={{ color: '#FFFDE7', ml: collapsed ? 0 : 1, bgcolor: 'transparent' }}
            size="small"
            aria-label={collapsed ? 'Développer la sidebar' : 'Réduire la sidebar'}
          >
            {collapsed ? <ChevronRightIcon /> : <ChevronLeftIcon />}
          </IconButton>
        </Tooltip>
      </Box>

      <Divider sx={{ backgroundColor: 'rgba(255,255,255,0.12)' }} />

      <List sx={{ mt: 2 }}>
        {items.map(({ text, icon, path }) => {
          const Icon = icon
          // For Dashboard (/app) check exact path
          // For other options, check exact or subroutes
          const selected = path === '/app'
            ? location.pathname === '/app'
            : location.pathname === path || location.pathname.startsWith(path + '/')
          return (
            <ListItem
              key={text}
              button
              selected={selected}
              onClick={() => navigate(path)}
              sx={{
                display: 'flex',
                alignItems: 'center',
                gap: 1,
                '&:hover': { backgroundColor: 'rgba(255,255,255,0.08)' },
                backgroundColor: selected ? 'rgba(255,215,64,0.06)' : 'transparent',
                borderLeft: selected ? '4px solid rgba(255,215,64,0.95)' : '4px solid transparent',
                pl: selected ? (collapsed ? 1 : 2) : (collapsed ? 1 : 1.5),
                transition: 'background-color 150ms ease',
              }}
            >
              <Tooltip title={collapsed ? text : ''} placement="right" arrow disableHoverListener={!collapsed}>
                <ListItemIcon sx={{ color: selected ? 'rgba(255,215,64,1)' : '#fff', minWidth: 40, justifyContent: 'center' }}>
                  <Icon />
                </ListItemIcon>
              </Tooltip>
              <ListItemText primary={text} sx={{ display: collapsed ? 'none' : 'block', color: '#FFFFFFDE' }} />
            </ListItem>
          )
        })}
      </List>
    </Drawer>
  )
}

export default Sidebar