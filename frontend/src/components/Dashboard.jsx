import { useState } from 'react'
import Sidebar from './Sidebar'
import Topbar from './Topbar'
import SitesTouristiques from './pages/SitesTouristiques'
import ItinerairesTouristiques from './pages/ItinerairesTouristiques'
import GuideTouristique from './pages/GuideTouristique'
import Evenements from './pages/Evenements'
import HistoriqueVisite from './pages/HistoriqueVisite'
import RapportStatistique from './pages/RapportStatistique'
import Utilisateurs from './pages/Utilisateurs'
import {
  Box,
  Container,
  Grid,
  Paper,
  Typography,
  TextField,
  Card,
  CardContent,
  List,
  ListItem,
  ListItemText,
  Stack,
  Chip
} from '@mui/material'
import { styled } from '@mui/material/styles'

const StyledCard = styled(Card)(() => ({
  height: '100%',
  display: 'flex',
  flexDirection: 'column',
  '& .MuiCardContent-root': {
    flexGrow: 1,
  },
}))

const Dashboard = ({ onLogout }) => {
  const [currentPage, setCurrentPage] = useState('dashboard')

  const renderDashboardContent = () => (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Grid container spacing={3}>
        <Grid item xs={12}>
          <Paper
            sx={{
              p: 2,
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'space-between'
            }}
          >
            <Typography variant="h4" component="h1">
              Tableau de bord
            </Typography>
            <TextField
              placeholder="Rechercher..."
              variant="outlined"
              size="small"
              aria-label="Rechercher dans le tableau de bord"
              sx={{ width: 300 }}
            />
          </Paper>
        </Grid>

        <Grid item xs={12} sm={6} md={3}>
          <StyledCard>
            <CardContent>
              <Typography variant="h6">Sites touristiques</Typography>
              <Typography variant="h4">45</Typography>
              <Typography variant="body2" color="text.secondary">
                Sites enregistrés
              </Typography>
            </CardContent>
          </StyledCard>
        </Grid>

        <Grid item xs={12} sm={6} md={3}>
          <StyledCard>
            <CardContent>
              <Typography variant="h6">Visiteurs</Typography>
              <Typography variant="h4">2,300</Typography>
              <Chip label="+15% ce mois" color="success" size="small" />
            </CardContent>
          </StyledCard>
        </Grid>

        <Grid item xs={12} sm={6} md={3}>
          <StyledCard>
            <CardContent>
              <Typography variant="h6">Guides</Typography>
              <Typography variant="h4">28</Typography>
              <Typography variant="body2" color="text.secondary">
                Guides actifs
              </Typography>
            </CardContent>
          </StyledCard>
        </Grid>

        <Grid item xs={12} sm={6} md={3}>
          <StyledCard>
            <CardContent>
              <Typography variant="h6">Événements</Typography>
              <Typography variant="h4">12</Typography>
              <Typography variant="body2" color="text.secondary">
                Événements à venir
              </Typography>
            </CardContent>
          </StyledCard>
        </Grid>

        <Grid item xs={12} md={8}>
          <Paper sx={{ p: 2, height: '100%' }}>
            <Typography variant="h6" gutterBottom>
              Statistiques des visites
            </Typography>
            <Stack direction="row" spacing={2} sx={{ mt: 2 }}>
              {['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Juin', 'Juil', 'Août'].map((month) => (
                <Chip key={month} label={month} variant="outlined" />
              ))}
            </Stack>
          </Paper>
        </Grid>

        <Grid item xs={12} md={4}>
          <Paper sx={{ p: 2, height: '100%' }}>
            <Typography variant="h6" gutterBottom>
              Activités récentes
            </Typography>
            <List>
              <ListItem>
                <ListItemText 
                  primary="Nouvelle visite enregistrée"
                  secondary="Il y a 5 minutes"
                />
              </ListItem>
              <ListItem>
                <ListItemText 
                  primary="Nouveau guide ajouté"
                  secondary="Il y a 2 heures"
                />
              </ListItem>
              <ListItem>
                <ListItemText 
                  primary="Événement créé"
                  secondary="Il y a 1 jour"
                />
              </ListItem>
            </List>
          </Paper>
        </Grid>
      </Grid>
    </Container>
  )

  const renderPage = () => {
    switch (currentPage) {
      case 'sites':
        return <SitesTouristiques />;
      case 'itineraires':
        return <ItinerairesTouristiques />;
      case 'guides':
        return <GuideTouristique />;
      case 'evenements':
        return <Evenements />;
      case 'historique':
        return <HistoriqueVisite />;
      case 'rapports':
        return <RapportStatistique />;
      case 'utilisateurs':
        return <Utilisateurs />;
      default:
        return renderDashboardContent();
    }
  }

  return (
    <Box sx={{ display: 'flex' }} role="application" aria-label="Tableau de bord">
      <Sidebar 
        onLogout={onLogout}
        currentPage={currentPage}
        onPageChange={setCurrentPage}
      />
      <Box
        component="main"
        aria-label="Contenu principal"
        sx={{
          flexGrow: 1,
          height: '100vh',
          overflow: 'auto',
          backgroundColor: (theme) => theme.palette.grey[100],
        }}
      >
        {renderPage()}
      </Box>
    </Box>
  )
}

export default Dashboard