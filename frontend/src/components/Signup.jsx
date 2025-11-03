import { useState } from 'react'
import {
  Box,
  Container,
  Paper,
  TextField,
  Button,
  Typography,
} from '@mui/material'
import ThemeToggle from './ThemeToggle'
import { AuthLayout } from './AuthLayout'
import backgroundImage from '../assets/images/fondauth2.jpg.png'

const Signup = ({ onCreateAccount, onShowLogin }) => {
  const [nom, setNom] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [cpassword, setCpassword] = useState('')

  const handleSubmit = (e) => {
    e.preventDefault()
    // Simulation - en production vous valideriez/appelez une API
    if (!email || !password) {
      onCreateAccount?.(false, 'Email et mot de passe requis')
      return
    }

    if (password !== cpassword) {
      onCreateAccount?.(false, 'Les mots de passe ne correspondent pas')
      return
    }

    // Ici on simule une création réussie
    onCreateAccount?.(true, 'Compte créé avec succès')
    // Optionnel: reset fields
    setNom('')
    setEmail('')
    setPassword('')
    setCpassword('')
  }

  return (
    <AuthLayout image={backgroundImage}>
      <Box sx={{ position: 'absolute', top: 15, right: 21 }}>
        <ThemeToggle />
      </Box>
      <Container component="main" maxWidth="xs">
        <Paper
          elevation={6}
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            padding: 3,
          }}
        >
        <Box
          component="img"
          src="/src/assets/images/logo.jpg"
          alt="Logo" className="login-logo"
        />
          <Box component="form" onSubmit={handleSubmit} sx={{ mt: 1 }}>
            <TextField
              margin="normal"
              required
              fullWidth
              id="nom"
              label="Nom d'utilisateur"
              name="nom
              autoComplete="nom
              autoFocus
              value={nom}
              onChange={(e) => setNom(e.target.value)}
            />
              
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email"
              name="email"
              autoComplete="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />

            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Mot de passe"
              type="password"
              id="password"
              autoComplete="new-password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />

            <TextField
              margin="normal"
              required
              fullWidth
              name="cpassword"
              label="Confirmer le mot de passe"
              type="password"
              id="cpassword"
              autoComplete="new-password"
              value={cpassword}
              onChange={(e) => setCpassword(e.target.value)}
            />

            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Créer un compte
            </Button>
            <Typography variant="body2" align="center">
              Avez-vous déjà un compte?{' '}
              <Button
                component="a"
                variant="text"
                onClick={(e) => {
                  e.preventDefault();
                  onShowLogin?.();
                }}
              >
                Connexion
              </Button>
            </Typography>
          </Box>
        </Paper>
      </Container>
    </AuthLayout>
  )
}

export default Signup