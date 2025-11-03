import { Typography, Container, Paper } from '@mui/material';

const RapportStatistique = () => {
  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper sx={{ p: 2 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Rapports & Statistiques
        </Typography>
        <Typography variant="body1">
          Rapports et statistiques à venir...
        </Typography>
      </Paper>
    </Container>
  );
};

export default RapportStatistique;