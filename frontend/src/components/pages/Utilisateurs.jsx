import { Typography, Container, Paper } from '@mui/material';

const Utilisateurs = () => {
  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper sx={{ p: 2 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Gestion des Utilisateurs
        </Typography>
        <Typography variant="body1">
          Liste des utilisateurs à venir...
        </Typography>
      </Paper>
    </Container>
  );
};

export default Utilisateurs;