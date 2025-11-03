import { Typography, Container, Paper } from '@mui/material';

const GuideTouristique = () => {
  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper sx={{ p: 2 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Guide Touristique
        </Typography>
        <Typography variant="body1">
          Informations sur les guides touristiques à venir...
        </Typography>
      </Paper>
    </Container>
  );
};

export default GuideTouristique;