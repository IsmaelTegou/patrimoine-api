import { useState, useEffect } from 'react'
import {
  Box,
  Container,
  Paper,
  TextField,
  Button,
  Typography,
  Checkbox,
  FormControlLabel,
  Alert,
  Avatar,
} from '@mui/material'
import LockOutlinedIcon from '@mui/icons-material/LockOutlined'
import { AuthLayout } from './AuthLayout'
import ThemeToggle from './ThemeToggle'
import backgroundImage from '../assets/images/fondauth.jpg'


const Login = ({ onLogin, onShowSignup, notification, clearNotification }) => {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [rememberMe, setRememberMe] = useState(false)

  const handleSubmit = (e) => {
    e.preventDefault()
    // Simulation de connexion - en production, vous vérifieriez les identifiants
    if (email && password) {
      onLogin()
    }
  }

  useEffect(() => {
    if (notification && clearNotification) {
      const t = setTimeout(() => clearNotification(), 4000)
      return () => clearTimeout(t)
    }
  }, [notification, clearNotification])

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
            backgroundColor: (theme) => 
              theme.palette.mode === 'dark' 
                ? theme.palette.background.paper 
                : 'rgba(255, 255, 255, 0.9)',
            backdropFilter: 'blur(10px)',
          }}
        >
        <Box
          component="img"
          src="/src/assets/images/logo.jpg"
          alt="Logo" className="login-logo"
        />

        {notification && (
          <Alert 
            severity={notification.type} 
            sx={{ width: '100%', mb: 2 }}
          >
            {notification.message}
          </Alert>
        )}

        <Box component="form" onSubmit={handleSubmit} sx={{ mt: 1 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            id="email"
            label="Email"
            name="email"
            autoComplete="email"
            autoFocus
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
            autoComplete="current-password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <FormControlLabel
            control={
              <Checkbox
                value="remember"
                color="primary"
                checked={rememberMe}
                onChange={(e) => setRememberMe(e.target.checked)}
              />
            }
            label="Se souvenir de moi"
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Connexion
          </Button>
          <Typography variant="body2" align="center">
            Vous n'avez pas de compte?{' '}
            <Button
              component="a"
              variant="text"
              onClick={(e) => {
                e.preventDefault()
                onShowSignup?.()
              }}
            >
              Inscription
            </Button>
          </Typography>
        </Box>
      </Paper>
    </Container>
    </AuthLayout>
  )
}

export default Login