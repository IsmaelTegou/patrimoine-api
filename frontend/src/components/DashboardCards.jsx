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

const DashboardCards = () => {
  return (
    <Box sx={{ flexGrow: 1, p: 1, mt: 0 }}>
      <Grid container spacing={3}>
        
        {/* Website Views */}
        <Grid item xs={12} md={6} lg={4}>
          <Card sx={{ borderRadius: 2, boxShadow: '0 4px 6px -1px rgba(0,0,0,0.1)' }}>
            <CardContent>
              <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold', color: '#344767' }}>
                Website Views
              </Typography>
              <Typography variant="body2" color="text.secondary" gutterBottom>
                Last Campaign Performance
              </Typography>
              <Box sx={{ mt: 2, p: 2, backgroundColor: '#F8F9FA', borderRadius: 1 }}>
                <Typography variant="body2" color="text.secondary">
                  Campaign sent 2 days ago
                </Typography>
              </Box>
            </CardContent>
          </Card>
        </Grid>

        {/* Projects */}
        <Grid item xs={12} md={6} lg={4}>
          <Card sx={{ borderRadius: 2, boxShadow: '0 4px 6px -1px rgba(0,0,0,0.1)' }}>
            <CardContent>
              <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold', color: '#344767' }}>
                Projects
              </Typography>
              <Box sx={{ display: 'flex', alignItems: 'center', mt: 2 }}>
                <Chip label="30 done" color="success" variant="filled" />
                <Typography variant="body2" color="text.secondary" sx={{ ml: 1 }}>
                  this month
                </Typography>
              </Box>
            </CardContent>
          </Card>
        </Grid>

        {/* Comments */}
        <Grid item xs={12} md={6} lg={4}>
          <Card sx={{ borderRadius: 2, boxShadow: '0 4px 6px -1px rgba(0,0,0,0.1)' }}>
            <CardContent>
              <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold', color: '#344767' }}>
                Comments
              </Typography>
              <List dense>
                <ListItem>
                  <TrendingUp sx={{ color: '#4CAF50', mr: 1 }} />
                  <ListItemText primary="+55% than last week" />
                </ListItem>
                <ListItem>
                  <TrendingUp sx={{ color: '#4CAF50', mr: 1 }} />
                  <ListItemText primary="+3% than last month" />
                </ListItem>
              </List>
            </CardContent>
          </Card>
        </Grid>

        {/* Today's Users */}
        <Grid item xs={12} md={6} lg={3}>
          <Card sx={{ borderRadius: 2, boxShadow: '0 4px 6px -1px rgba(0,0,0,0.1)' }}>
            <CardContent>
              <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold', color: '#344767' }}>
                Today's Users
              </Typography>
              <Typography variant="h4" sx={{ fontWeight: 'bold', color: '#1A73E8' }}>
                2,300
              </Typography>
              <Box sx={{ display: 'flex', alignItems: 'center', mt: 1 }}>
                <TrendingUp sx={{ color: '#4CAF50', fontSize: 16, mr: 0.5 }} />
                <Typography variant="body2" color="text.secondary">
                  +1% than yesterday
                </Typography>
              </Box>
            </CardContent>
          </Card>
        </Grid>

        {/* App Timeline */}
        <Grid item xs={12} md={6} lg={3}>
          <Card sx={{ borderRadius: 2, boxShadow: '0 4px 6px -1px rgba(0,0,0,0.1)' }}>
            <CardContent>
              <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold', color: '#344767' }}>
                App
              </Typography>
              <List dense>
                {['May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'].map((month) => (
                  <ListItem key={month} sx={{ py: 0.5 }}>
                    <ListItemText primary={month} />
                  </ListItem>
                ))}
              </List>
            </CardContent>
          </Card>
        </Grid>

        {/* Daily Sales */}
        <Grid item xs={12} md={6} lg={3}>
          <Card sx={{ borderRadius: 2, boxShadow: '0 4px 6px -1px rgba(0,0,0,0.1)' }}>
            <CardContent>
              <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold', color: '#344767' }}>
                Daily Sales
              </Typography>
              <Typography variant="body2" color="text.secondary">
                (+15%) increase in today sales.
              </Typography>
              <Box sx={{ mt: 2 }}>
                <LinearProgress variant="determinate" value={75} sx={{ height: 8, borderRadius: 4 }} />
              </Box>
              <Typography variant="caption" color="text.secondary" sx={{ mt: 1, display: 'block' }}>
                updated 4 min ago
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        {/* Revenue */}
        <Grid item xs={12} md={6} lg={3}>
          <Card sx={{ borderRadius: 2, boxShadow: '0 4px 6px -1px rgba(0,0,0,0.1)' }}>
            <CardContent>
              <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold', color: '#344767' }}>
                Revenue
              </Typography>
              <Typography variant="h4" sx={{ fontWeight: 'bold', color: '#1A73E8' }}>
                34k
              </Typography>
              <Typography variant="caption" color="text.secondary">
                Just updated
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        {/* Followers */}
        <Grid item xs={12} md={6} lg={3}>
          <Card sx={{ borderRadius: 2, boxShadow: '0 4px 6px -1px rgba(0,0,0,0.1)' }}>
            <CardContent>
              <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold', color: '#344767' }}>
                Followers
              </Typography>
              <Typography variant="h4" sx={{ fontWeight: 'bold', color: '#1A73E8' }}>
                +91
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        {/* Completed Tasks */}
        <Grid item xs={12} md={6} lg={3}>
          <Card sx={{ borderRadius: 2, boxShadow: '0 4px 6px -1px rgba(0,0,0,0.1)' }}>
            <CardContent>
              <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold', color: '#344767' }}>
                Completed Tasks
              </Typography>
              <Typography variant="body2" color="text.secondary" gutterBottom>
                Last Campaign Performance
              </Typography>
              <Typography variant="caption" color="text.secondary">
                just updated
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        {/* Orders Overview */}
        <Grid item xs={12} md={6} lg={3}>
          <Card sx={{ borderRadius: 2, boxShadow: '0 4px 6px -1px rgba(0,0,0,0.1)' }}>
            <CardContent>
              <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold', color: '#344767' }}>
                Orders overview
              </Typography>
              <Box sx={{ display: 'flex', alignItems: 'center', mt: 1 }}>
                <TrendingUp sx={{ color: '#4CAF50', mr: 1 }} />
                <Typography variant="body1" sx={{ fontWeight: 'bold', color: '#4CAF50' }}>
                  +24%
                </Typography>
                <Typography variant="body2" color="text.secondary" sx={{ ml: 1 }}>
                  this month
                </Typography>
              </Box>
            </CardContent>
          </Card>
        </Grid>

        {/* Design Changes */}
        <Grid item xs={12} md={6} lg={3}>
          <Card sx={{ borderRadius: 2, boxShadow: '0 4px 6px -1px rgba(0,0,0,0.1)' }}>
            <CardContent>
              <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold', color: '#344767' }}>
                $2400, Design changes
              </Typography>
              <Typography variant="body2" color="text.secondary">
                22 DEC 7:20 PM
              </Typography>
            </CardContent>
          </Card>
        </Grid>

      </Grid>
    </Box>
  )
}

export default DashboardCards