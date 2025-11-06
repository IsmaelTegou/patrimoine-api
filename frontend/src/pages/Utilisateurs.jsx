import React from 'react'
import { Typography, Paper, Box } from '@mui/material'

const Utilisateurs = () => {
  return (
    <Box>
      <Typography variant="h4" gutterBottom>Utilisateurs</Typography>
      <Paper sx={{ p: 2 }}>
        <Typography>Gestion des utilisateurs (liste, rôles, actions).</Typography>
      </Paper>
    </Box>
  )
}

export default Utilisateurs
