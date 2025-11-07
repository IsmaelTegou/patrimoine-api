import React, { useState } from 'react'
import {
  Box,
  Paper,
  Typography,
  TextField,
  Button,
  Link as MuiLink,
  InputAdornment,
  IconButton,
} from '@mui/material'
import Visibility from '@mui/icons-material/Visibility'
import VisibilityOff from '@mui/icons-material/VisibilityOff'
import GoogleIcon from '@mui/icons-material/Google'
import WindowsIcon from '@mui/icons-material/Window'
import bgSignIn from '../assets/images/fond.jpg'
import logo from '../assets/images/logo.jpg'
import { useNavigate, Link } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

const SignIn = () => {
  const navigate = useNavigate()
  const { signIn } = useAuth()
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState(null)
  const [loading, setLoading] = useState(false)
  const [showPassword, setShowPassword] = useState(false)

  const handleTogglePassword = () => {
    setShowPassword(prev => !prev)
  }

  const handleGoogleSignIn = async () => {
    setLoading(true)
    try {
      // TODO: call auth provider
      navigate('/app')
    } finally { setLoading(false) }
  }

  const handleMicrosoftSignIn = async () => {
    setLoading(true)
    try {
      // TODO: call MS auth
      navigate('/app')
    } finally { setLoading(false) }
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError(null)
    setLoading(true)
    try {
      await signIn({ email, password })
      navigate('/app')
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <Box
      sx={{
        minHeight: '100vh',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        bgcolor: '#F0F2F5',
        px: 2,
      }}
    >
      {/* Left: form (50%) */}
      <Box sx={{ flex: '1 1 50%', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
        <Paper
          sx={{
            width: '100%',
            maxWidth: '360px',
            p: 3,
            borderRadius: 3,
            boxShadow: '0 8px 16px rgba(0,0,0,0.06)',
            bgcolor: 'white',
          }}
          elevation={0}
          component="form"
          onSubmit={handleSubmit}
        >
          {/* logo */}
          <Box sx={{ display: 'flex', justifyContent: 'center', mb: 2 }}>
            <img src={logo} alt="logo" style={{ width: 84, height: 'auto', borderRadius: '25%' }} />
          </Box>
        <Typography 
          variant="h5" 
          sx={{ 
            mb: 3, 
            fontWeight: 600,
            textAlign: 'center',
            color: '#1A73E8'
          }}
        >
          Se connecter
        </Typography>
        {error && (
          <Typography 
            color="error" 
            sx={{ 
              mb: 2,
              fontSize: '0.875rem',
              textAlign: 'center',
              bgcolor: '#FFF3F3',
              p: 1,
              borderRadius: 1
            }}
          >
            {error}
          </Typography>
        )}
        <TextField 
          label="Email" 
          size="small"
          fullWidth 
          sx={{ 
            mb: 2,
            '& .MuiOutlinedInput-root': {
              borderRadius: 3,
              backgroundColor: '#FAFBFF'
            }
          }} 
          value={email} 
          onChange={(e) => setEmail(e.target.value)} 
        />
        <TextField 
          label="Mot de passe" 
          type={showPassword ? "text" : "password"}
          size="small"
          fullWidth 
          sx={{ 
            mb: 0,
            '& .MuiOutlinedInput-root': {
              borderRadius: 3,
              backgroundColor: '#FAFBFF'
            }
          }} 
          value={password} 
          onChange={(e) => setPassword(e.target.value)}
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <IconButton
                  onClick={handleTogglePassword}
                  edge="end"
                  size="small"
                >
                  {showPassword ? <VisibilityOff /> : <Visibility />}
                </IconButton>
              </InputAdornment>
            ),
          }}
        />
        <Box sx={{ mt: 0, mb: 1, textAlign: 'left' }}>
          <MuiLink 
            component={Link} 
            to="/reset-password" 
            sx={{ 
              fontSize: '0.875rem',
              textDecoration: 'none',
              color: '#1A73E8',
              '&:hover': {
                textDecoration: 'underline'
              }
            }}
          >
            Mot de passe oublié?
          </MuiLink>
        </Box>
        <Button 
          type="submit" 
          variant="contained" 
          color="primary" 
          fullWidth 
          disabled={loading}
          sx={{ 
            py: 1,
            borderRadius: 3,
            textTransform: 'none',
            fontSize: '0.875rem',
            fontWeight: 600,
            boxShadow: 'none',
            '&:hover': {
              boxShadow: 'none'
            }
          }}
        >
          {loading ? 'Connexion...' : 'Se connecter'}
        </Button>

        <Box sx={{ 
          display: 'flex', 
          gap: 4, 
          justifyContent: 'center',
          width: '100%',
          mt: 3,
          mb: 2
        }}>
          <Box sx={{ 
            display: 'flex', 
            flexDirection: 'column', 
            alignItems: 'center',
            gap: 1 
          }}>
            <IconButton
              onClick={handleGoogleSignIn}
              sx={{ 
                width: 56,
                height: 56,
                color: '#DB4437',
                bgcolor: 'rgba(219, 68, 55, 0.1)',
                '&:hover': { 
                  bgcolor: 'rgba(219, 68, 55, 0.2)',
                  transform: 'scale(1.1)',
                  transition: 'transform 0.2s'
                }
              }}
            >
              <GoogleIcon sx={{ fontSize: 28 }} />
            </IconButton>
            <Typography
              variant="caption"
              sx={{
                color: '#DB4437',
                fontWeight: 500
              }}
            >
              Google
            </Typography>
          </Box>
          
          <Box sx={{ 
            display: 'flex', 
            flexDirection: 'column', 
            alignItems: 'center',
            gap: 1 
          }}>
            <IconButton
              onClick={handleMicrosoftSignIn}
              sx={{ 
                width: 56,
                height: 56,
                color: '#00A4EF',
                bgcolor: 'rgba(0, 164, 239, 0.1)',
                '&:hover': { 
                  bgcolor: 'rgba(0, 164, 239, 0.2)',
                  transform: 'scale(1.1)',
                  transition: 'transform 0.2s'
                }
              }}
            >
              <WindowsIcon sx={{ fontSize: 28 }} />
            </IconButton>
            <Typography
              variant="caption"
              sx={{
                color: '#00A4EF',
                fontWeight: 500
              }}
            >
              Microsoft
            </Typography>
          </Box>
        </Box>

        <Box sx={{ mt: 2, textAlign: 'center' }}>
          <Typography variant="body2" color="text.secondary" component="span">
            Vous n'avez pas de compte?{' '}
          </Typography>
          <MuiLink 
            component={Link} 
            to="/signup"
            sx={{ 
              fontWeight: 500,
              textDecoration: 'none',
              '&:hover': {
                textDecoration: 'underline'
              }
            }}
          >
            S'inscrire
          </MuiLink>
        </Box>
        </Paper>
      </Box>

      {/* Right: background image (50%) - hidden on small screens */}
      <Box
        sx={{
          flex: '1 1 50%',
          display: { xs: 'none', sm: 'none', md: 'block' },
          height: '70vh',
          maxWidth: '50%',
          mx: 2,
        }}
      >
        <Box
          sx={{
            height: '100%',
            width: '100%',
            backgroundImage: `url(${bgSignIn})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            borderRadius: 2,
            borderBottomLeftRadius: 24,
            borderBottomRightRadius: 24,
            boxShadow: '0 8px 24px rgba(0,0,0,0.08)',
          }}
        />
      </Box>
    </Box>
  )
}

export default SignIn
