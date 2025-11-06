import React, { useState } from 'react'
import {
  Box,
  Paper,
  Typography,
  TextField,
  Button,
  Link as MuiLink,
  InputAdornment,
} from '@mui/material'
import bgSignUp from '../assets/images/fondauth1.jpg'
import logo from '../assets/images/logo.jpg'
import { useNavigate, Link } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

const SignUp = () => {
  const navigate = useNavigate()
  const { signUp } = useAuth()
  const [lastName, setLastName] = useState('')
  const [firstName, setFirstName] = useState('')
  const [email, setEmail] = useState('')
  const [phone, setPhone] = useState('')
  const [password, setPassword] = useState('')
  const [confirm, setConfirm] = useState('')
  const [error, setError] = useState(null)
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError(null)
    if (password !== confirm) {
      setError('Les mots de passe ne correspondent pas')
      return
    }
    setLoading(true)
    try {
      const fullName = `${lastName} ${firstName}`.trim()
      await signUp({ name: fullName, email, password, phone })
      // redirect user to sign-in after successful registration
      navigate('/signin')
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <Box sx={{ minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center', bgcolor: '#F0F2F5', px: 2 }}>
      {/* Left: form */}
      <Box sx={{ flex: '1 1 50%', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
        <Paper
          sx={{
            width: '100%',
            maxWidth: '400px',
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
            <img src={logo} alt="logo" style={{ width: 96, height: 'auto', borderRadius: '25%' }} />
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
          Créer un compte
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
          label="Nom" 
          size="small"
          fullWidth 
          sx={{ 
            mb: 2,
            '& .MuiOutlinedInput-root': {
              borderRadius: 3,
              backgroundColor: '#FAFBFF'
            }
          }} 
          value={lastName} 
          onChange={(e) => setLastName(e.target.value)} 
        />

        <TextField 
          label="Prénom" 
          size="small"
          fullWidth 
          sx={{ 
            mb: 2,
            '& .MuiOutlinedInput-root': {
              borderRadius: 3,
              backgroundColor: '#FAFBFF'
            }
          }} 
          value={firstName} 
          onChange={(e) => setFirstName(e.target.value)} 
        />

        <TextField 
          label="Email" 
          size="small"
          type="email"
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
          label="Numéro de téléphone"
          size="small"
          fullWidth
          sx={{ mb: 2, '& .MuiOutlinedInput-root': { borderRadius: 3 } }}
          value={phone}
          onChange={(e) => setPhone(e.target.value)}
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">🇨🇲&nbsp;+237</InputAdornment>
            ),
          }}
        />

        <TextField 
          label="Mot de passe" 
          type="password" 
          size="small"
          fullWidth 
          sx={{ 
            mb: 2,
            '& .MuiOutlinedInput-root': {
              borderRadius: 3,
              backgroundColor: '#FAFBFF'
            }
          }} 
          value={password} 
          onChange={(e) => setPassword(e.target.value)} 
        />

        <TextField 
          label="Confirmer le mot de passe" 
          type="password" 
          size="small"
          fullWidth 
          sx={{ 
            mb: 3,
            '& .MuiOutlinedInput-root': {
              borderRadius: 3,
              backgroundColor: '#FAFBFF'
            }
          }} 
          value={confirm} 
          onChange={(e) => setConfirm(e.target.value)} 
        />
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
          {loading ? 'Création...' : "S'inscrire"}
        </Button>
        <Box sx={{ mt: 3, textAlign: 'center' }}>
          <Typography variant="body2" color="text.secondary" component="span">
            Vous avez déjà un compte?{' '}
          </Typography>
          <MuiLink 
            component={Link} 
            to="/signin"
            sx={{ 
              fontWeight: 500,
              textDecoration: 'none',
              '&:hover': {
                textDecoration: 'underline'
              }
            }}
          >
            Se connecter
          </MuiLink>
        </Box>
        </Paper>
      </Box>

      {/* Right: background image */}
      <Box sx={{ flex: '1 1 50%', display: { xs: 'none', sm: 'none', md: 'block' }, height: '70vh', maxWidth: '50%', mx: 2 }}>
        <Box
          sx={{
            height: '100%',
            width: '100%',
            backgroundImage: `url(${bgSignUp})`,
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

export default SignUp
