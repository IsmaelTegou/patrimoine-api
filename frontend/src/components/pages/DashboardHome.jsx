import { Container, Grid, Paper, Typography, TextField, Stack, Chip } from '@mui/material';

const DashboardHome = () => {
  return (
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
              sx={{ width: 300 }}
            />
          </Paper>
        </Grid>

        <Grid item xs={12} sm={6} md={3}>
          <Paper sx={{ p: 2 }}>
            <Typography variant="h6">Sites touristiques</Typography>
            <Typography variant="h4">45</Typography>
            <Typography variant="body2" color="text.secondary">
              Sites enregistrés
            </Typography>
          </Paper>
        </Grid>

        <Grid item xs={12} sm={6} md={3}>
          <Paper sx={{ p: 2 }}>
            <Typography variant="h6">Visiteurs</Typography>
            <Typography variant="h4">2,300</Typography>
            <Chip label="+15% ce mois" color="success" size="small" />
          </Paper>
        </Grid>

        <Grid item xs={12} sm={6} md={3}>
          <Paper sx={{ p: 2 }}>
            <Typography variant="h6">Guides</Typography>
            <Typography variant="h4">28</Typography>
            <Typography variant="body2" color="text.secondary">
              Guides actifs
            </Typography>
          </Paper>
        </Grid>

        <Grid item xs={12} sm={6} md={3}>
          <Paper sx={{ p: 2 }}>
            <Typography variant="h6">Événements</Typography>
            <Typography variant="h4">12</Typography>
            <Typography variant="body2" color="text.secondary">
              Événements à venir
            </Typography>
          </Paper>
        </Grid>

        <Grid item xs={12} md={8}>
          <Paper sx={{ p: 2 }}>
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
          <Paper sx={{ p: 2 }}>
            <Typography variant="h6" gutterBottom>
              Activités récentes
            </Typography>
            {[
              { id: 'visit', text: 'Nouvelle visite enregistrée' },
              { id: 'guide', text: 'Guide ajouté' },
              { id: 'event', text: 'Événement créé' }
            ].map((activity) => (
              <Typography key={activity.id} variant="body2" sx={{ mt: 1 }}>
                • {activity.text}
              </Typography>
            ))}
          </Paper>
        </Grid>
      </Grid>
    </Container>
  );
};

export default DashboardHome;