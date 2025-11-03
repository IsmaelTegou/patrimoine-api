import { Typography, Container, Paper } from '@mui/material';

const SitesTouristiques = () => {
  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper sx={{ p: 2 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Sites Touristiques
        </Typography>
        <Typography variant="body1">
          Liste des sites touristiques à venir...
        </Typography>
      </Paper>
    </Container>
  );
};

export default SitesTouristiques;