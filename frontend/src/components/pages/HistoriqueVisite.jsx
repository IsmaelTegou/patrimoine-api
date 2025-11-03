import { Typography, Container, Paper } from '@mui/material';

const HistoriqueVisite = () => {
  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper sx={{ p: 2 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Historique des Visites
        </Typography>
        <Typography variant="body1">
          Historique des visites à venir...
        </Typography>
      </Paper>
    </Container>
  );
};

export default HistoriqueVisite;