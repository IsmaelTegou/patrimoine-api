import React from 'react'
import {
  Drawer,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Typography,
  Box,
  Divider,
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

const drawerWidth = 250

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

  return (
    <Drawer
      sx={{
        width: drawerWidth,
        flexShrink: 0,
        '& .MuiDrawer-paper': {
          width: drawerWidth,
          boxSizing: 'border-box',
          background: 'linear-gradient(195deg, #1A73E8, #0D47A1)',
          color: 'white',
        },
      }}
      variant="permanent"
      anchor="left"
    >
      <Box sx={{ p: 2 }}>
        <Typography variant="h6" sx={{ fontWeight: 'bold', color: 'white' }}>
          Patrimoine
        </Typography>
      </Box>

      <Divider sx={{ backgroundColor: 'rgba(255,255,255,0.2)' }} />

      <List sx={{ mt: 2 }}>
        {items.map(({ text, icon, path }) => {
          const Icon = icon
          // Pour Dashboard (/app), vérifie uniquement le chemin exact
          // Pour les autres options, vérifie le chemin exact ou les sous-routes
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
                backgroundColor: selected ? 'rgba(255,255,255,0.08)' : 'transparent',
                borderLeft: selected ? '4px solid rgba(255,215,64,0.95)' : '4px solid transparent',
                pl: selected ? 2 : 1.5,
              }}
            >
              <ListItemIcon sx={{ color: selected ? 'rgba(255,215,64,0.98)' : 'white', minWidth: 40 }}>
                <Icon />
              </ListItemIcon>
              <ListItemText primary={text} />
            </ListItem>
          )
        })}
      </List>
    </Drawer>
  )
}

export default Sidebar