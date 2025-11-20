import React from 'react'
import {
  Grid,
  Card,
  CardContent,
  Typography,
  Box,
  LinearProgress,
  List,
  ListItem,
  ListItemText,
  Chip,
} from '@mui/material'
import TrendingUp from '@mui/icons-material/TrendingUp'
import TrendingDown from '@mui/icons-material/TrendingDown'
import People from '@mui/icons-material/People'
import ShoppingCart from '@mui/icons-material/ShoppingCart'
import Assignment from '@mui/icons-material/Assignment'
import Comment from '@mui/icons-material/Comment'
import AttachMoney from '@mui/icons-material/AttachMoney'

const cards = [
  {
    title: 'Visiteurs',
    value: 1240,
    icon: <People sx={{ color: '#1976d2', fontSize: 40 }} />, 
    trend: 'up',
    percent: 12,
    color: '#E3F2FD',
  },
  {
    title: 'Réservations',
    value: 320,
    icon: <ShoppingCart sx={{ color: '#388e3c', fontSize: 40 }} />, 
    trend: 'down',
    percent: 5,
    color: '#E8F5E9',
  },
  {
    title: 'Commentaires',
    value: 87,
    icon: <Comment sx={{ color: '#fbc02d', fontSize: 40 }} />, 
    trend: 'up',
    percent: 8,
    color: '#FFFDE7',
  },
  {
    title: 'Revenus',
    value: '2 500 €',
    icon: <AttachMoney sx={{ color: '#d32f2f', fontSize: 40 }} />, 
    trend: 'up',
    percent: 15,
    color: '#FFEBEE',
  },
]

const DashboardCards = () => {
  return (
    <Box sx={{ flexGrow: 1, p: 2, mt: 8 }}>
      <Grid container spacing={3}>
        {cards.map((card, idx) => (
          <Grid item xs={12} sm={6} md={3} key={card.title}>
            <Card sx={{ background: card.color, borderRadius: 4, boxShadow: 3 }}>
              <CardContent>
                <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                  {card.icon}
                  <Box sx={{ ml: 2 }}>
                    <Typography variant="h6" sx={{ fontWeight: 700 }}>{card.title}</Typography>
                    <Typography variant="h4" sx={{ fontWeight: 800, color: '#222' }}>{card.value}</Typography>
                  </Box>
                </Box>
                <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                  <Chip
                    icon={card.trend === 'up' ? <TrendingUp sx={{ color: '#388e3c' }} /> : <TrendingDown sx={{ color: '#d32f2f' }} />}
                    label={card.trend === 'up' ? `+${card.percent}%` : `-${card.percent}%`}
                    sx={{ bgcolor: card.trend === 'up' ? '#C8E6C9' : '#FFCDD2', color: card.trend === 'up' ? '#388e3c' : '#d32f2f', fontWeight: 700 }}
                  />
                  <LinearProgress variant="determinate" value={card.percent * 5} sx={{ width: 100, height: 8, borderRadius: 5, bgcolor: '#eee' }} />
                </Box>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Box>
  )
}

export default DashboardCards