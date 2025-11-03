import { Typography, Container, Paper } from '@mui/material';

const ItinerairesTouristiques = () => {
  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper sx={{ p: 2 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Itinéraires Touristiques
        </Typography>
        <Typography variant="body1">
          Liste des itinéraires touristiques à venir...
        </Typography>
      </Paper>
    </Container>
  );
};

export default ItinerairesTouristiques;