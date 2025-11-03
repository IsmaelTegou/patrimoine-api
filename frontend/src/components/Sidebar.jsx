import {
  Drawer,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  IconButton,
  Box,
  Divider,
} from '@mui/material'
import {
  Dashboard as DashboardIcon,
  Place as PlaceIcon,
  Route as RouteIcon,
  Person as PersonIcon,
  Event as EventIcon,
  History as HistoryIcon,
  Assessment as AssessmentIcon,
  Group as GroupIcon,
  ExitToApp as ExitToAppIcon,
  Menu as MenuIcon,
} from '@mui/icons-material'
import { useState } from 'react'
import ThemeToggle from './ThemeToggle'

const drawerWidth = 240

const menuItems = [
  { id: 'dashboard', label: 'Tableau de bord', icon: DashboardIcon },
  { id: 'sites', label: 'Sites touristiques', icon: PlaceIcon },
  { id: 'itineraires', label: 'Itinéraires touristiques', icon: RouteIcon },
  { id: 'guides', label: 'Guide touristique', icon: PersonIcon },
  { id: 'evenements', label: 'Evénements', icon: EventIcon },
  { id: 'historique', label: 'Historique de visite', icon: HistoryIcon },
  { id: 'rapports', label: 'Rapport & Statistique', icon: AssessmentIcon },
  { id: 'utilisateurs', label: 'Utilisateurs', icon: GroupIcon },
]

const Sidebar = ({ onLogout, currentPage, onPageChange }) => {
  const [mobileOpen, setMobileOpen] = useState(false)

  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen)
  }
  const drawer = (
    <Box sx={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
       <Divider />

      <List>
          {menuItems.map(({ id, label, icon }) => {
            const Icon = icon;
            return (
              <ListItem 
                button 
                key={id}
                selected={currentPage === id}
                onClick={() => {
                  onPageChange(id);
                  setMobileOpen(false);
                }}
              >
                <ListItemIcon>
                  <Icon />
                </ListItemIcon>
                <ListItemText primary={label} />
              </ListItem>
            );
          })}
      </List>

      <Box sx={{ mt: 'auto', p: 2 }}>
        <ListItem 
          button 
          onClick={onLogout}
          sx={{
            bgcolor: 'primary.main',
            color: 'primary.contrastText',
            '&:hover': {
              bgcolor: 'primary.dark',
            },
          }}
        >
          <ListItemIcon sx={{ color: 'inherit' }}>
            <ExitToAppIcon />
          </ListItemIcon>
          <ListItemText primary="Déconnexion" />
        </ListItem>
      </Box>
    </Box>
  )

  return (
    
    <>
      <IconButton
        color="inherit"
        aria-label="Ouvrir le menu de navigation"
        aria-expanded={mobileOpen}
        aria-controls="navigation-drawer"
        edge="start"
        onClick={handleDrawerToggle}
        sx={{ mr: 2, display: { sm: 'none' } }}
      >
        <MenuIcon />
      </IconButton>

      <Box
        component="nav"
        aria-label="Menu principal"
        sx={{ width: { sm: drawerWidth }, flexShrink: { sm: 0 } }}
      >
        <Drawer
          id="navigation-drawer"
          variant="temporary"
          open={mobileOpen}
          onClose={handleDrawerToggle}
          ModalProps={{
            keepMounted: true, // Better open performance on mobile.
          }}
          sx={{
            display: { xs: 'block', sm: 'none' },
            '& .MuiDrawer-paper': { boxSizing: 'border-box', width: drawerWidth },
          }}
          aria-label="Menu de navigation mobile"
        >
          {drawer}
        </Drawer>
        
        <Drawer
          variant="permanent"
          sx={{
            display: { xs: 'none', sm: 'block' },
            '& .MuiDrawer-paper': { boxSizing: 'border-box', width: drawerWidth },
          }}
          open
        >
          {drawer}
        </Drawer>
      </Box>
    </>
  )
}

export default Sidebar