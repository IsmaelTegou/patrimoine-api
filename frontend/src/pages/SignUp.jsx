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
import bgSignUp from '../assets/images/fondauth1.jpg'
import logo from '../assets/images/logo.jpg'
import { useNavigate, Link } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import GoogleIcon from '@mui/icons-material/Google'
import WindowsIcon from '@mui/icons-material/Window'
import LanguageIcon from '@mui/icons-material/Language'
import Visibility from '@mui/icons-material/Visibility'
import VisibilityOff from '@mui/icons-material/VisibilityOff'
import { Divider, MenuItem, Select, FormControl, InputLabel, Stack } from '@mui/material'

const SignUp = () => {
  const navigate = useNavigate()
  const { signUp } = useAuth()
  const [lang, setLang] = useState('fr')
  const [lastName, setLastName] = useState('')
  const [firstName, setFirstName] = useState('')
  const [email, setEmail] = useState('')
  const [phone, setPhone] = useState('')
  const [password, setPassword] = useState('')
  const [confirm, setConfirm] = useState('')
  const [error, setError] = useState(null)
  const [loading, setLoading] = useState(false)
  const [showPassword, setShowPassword] = useState(false)
  const [showConfirmPassword, setShowConfirmPassword] = useState(false)

  const handleTogglePassword = () => {
    setShowPassword(prev => !prev)
  }

  const handleToggleConfirmPassword = () => {
    setShowConfirmPassword(prev => !prev)
  }

  const t = {
    fr: {
      create: "Créer un compte",
      signup: "S'inscrire",
      already: 'Vous avez déjà un compte?',
      signin: 'Se connecter',
      forgot: 'Mot de passe oublié?',
      google: 'Continuer avec Google',
      microsoft: 'Continuer avec Microsoft',
    },
    en: {
      create: 'Create an account',
      signup: 'Sign up',
      already: 'Already have an account?',
      signin: 'Sign in',
      forgot: 'Forgot password?',
      google: 'Continue with Google',
      microsoft: 'Continue with Microsoft',
    }
  }

  const tr = t[lang] || t.fr

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

  const handleGoogleSignIn = async () => {
    // placeholder for real Google sign-in flow
    setLoading(true)
    try {
      // TODO: call auth provider
      navigate('/signin')
    } finally { setLoading(false) }
  }

  const handleMicrosoftSignIn = async () => {
    setLoading(true)
    try {
      // TODO: call MS auth
      navigate('/signin')
    } finally { setLoading(false) }
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
            mb: 2, 
            fontWeight: 600,
            width: '100%',
            textAlign: 'center',
            color: '#1A73E8'
          }}
        >
          {tr.create}
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
        <Box sx={{ display: 'flex', gap: 2, mb: 1 }}>
          <TextField 
            label="Nom" 
            size="small"
            fullWidth 
            sx={{ 
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
              '& .MuiOutlinedInput-root': {
                borderRadius: 3,
                backgroundColor: '#FAFBFF'
              }
            }} 
            value={firstName} 
            onChange={(e) => setFirstName(e.target.value)} 
          />
        </Box>

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

        <Box sx={{ display: 'flex', gap: 2, mb: 1, alignItems: 'center' }}>
          <TextField
            label="Numéro de téléphone"
            size="small"
            fullWidth
            sx={{ '& .MuiOutlinedInput-root': { borderRadius: 3, width: '90%' } }}
            value={phone}
            onChange={(e) => setPhone(e.target.value)}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">🇨🇲&nbsp;+237</InputAdornment>
              ),
            }}
          />
          <FormControl size="small">
            <Select
              value={lang}
              onChange={(e) => setLang(e.target.value)}
              variant="standard"
                sx={{ 
                width: '10%',
                fontSize: '0.875rem',
                '.MuiSelect-select': { 
                  display: 'flex', 
                  alignItems: 'center',
                  gap: 1,
                  py: 0.5,
                  ml: 1,
                }
              }}
              IconComponent={LanguageIcon}
            >
              <MenuItem value="fr">Fr</MenuItem>
              <MenuItem value="en">En</MenuItem>
            </Select>
          </FormControl>
        </Box>

        <TextField 
          label="Mot de passe" 
          type={showPassword ? "text" : "password"}
          size="small"
          fullWidth 
          sx={{ 
            mb: 1,
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

        <TextField 
          label="Confirmer le mot de passe" 
          type={showConfirmPassword ? "text" : "password"}
          size="small"
          fullWidth 
          sx={{ 
            mb: 1,
            '& .MuiOutlinedInput-root': {
              borderRadius: 3,
              backgroundColor: '#FAFBFF'
            }
          }} 
          value={confirm} 
          onChange={(e) => setConfirm(e.target.value)}
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <IconButton
                  onClick={handleToggleConfirmPassword}
                  edge="end"
                  size="small"
                >
                  {showConfirmPassword ? <VisibilityOff /> : <Visibility />}
                </IconButton>
              </InputAdornment>
            ),
          }}
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
          {loading ? (lang === 'fr' ? 'Création...' : 'Creating...') : tr.signup}
        </Button>
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 0, gap: 1 }}>
          <Box sx={{ 
              display: 'flex', 
              gap: 4, 
              justifyContent: 'center',
              width: '100%',
              mt: 2,
              mb: 1
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
        </Box>
        
        <Box sx={{ mt: 0, textAlign: 'center' }}>
          <Typography variant="body2" color="text.secondary" component="span">
            {tr.already}{' '}
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
            {tr.signin}
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
