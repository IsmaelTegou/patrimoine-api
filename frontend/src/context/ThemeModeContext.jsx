import React, { createContext, useContext, useMemo, useState } from 'react'
import { ThemeProvider, createTheme } from '@mui/material/styles'
import CssBaseline from '@mui/material/CssBaseline'

const ThemeModeContext = createContext(null)

export const useThemeMode = () => useContext(ThemeModeContext)

export const ThemeModeProvider = ({ children }) => {
  const [mode, setMode] = useState(() => localStorage.getItem('themeMode') || 'light')

  const toggleMode = () => {
    setMode((prev) => {
      const next = prev === 'light' ? 'dark' : 'light'
      localStorage.setItem('themeMode', next)
      return next
    })
  }

  const theme = useMemo(() =>
    createTheme({
      palette: {
        mode,
        primary: {
          main: '#1A73E8',
        },
        background: mode === 'light' ? { default: '#F0F2F5', paper: '#FFFFFF' } : { default: '#0B0F14', paper: '#0f1720' },
        text: mode === 'light' ? { primary: '#344767', secondary: '#67748E' } : { primary: '#E6EEF8', secondary: '#AAB6C8' },
      },
      typography: {
        fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
      },
    }),
    [mode]
  )

  return (
    <ThemeModeContext.Provider value={{ mode, toggleMode }}>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        {children}
      </ThemeProvider>
    </ThemeModeContext.Provider>
  )
}

export default ThemeModeContext
