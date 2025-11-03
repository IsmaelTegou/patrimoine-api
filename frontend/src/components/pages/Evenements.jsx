import { Typography, Container, Paper } from '@mui/material';

const Evenements = () => {
  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper sx={{ p: 2 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Événements
        </Typography>
        <Typography variant="body1">
          Liste des événements à venir...
        </Typography>
      </Paper>
    </Container>
  );
};

export default Evenements;