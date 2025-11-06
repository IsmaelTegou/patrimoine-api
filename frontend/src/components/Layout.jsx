import React from 'react'
import Box from '@mui/material/Box'
import { Outlet } from 'react-router-dom'
import Sidebar from './Sidebar'
import Header from './Header'

const drawerWidth = 2

const Layout = () => {
  return (
    <Box sx={{ display: 'flex' }}>
      <Sidebar />
      <Header />
      <Box component="main" sx={{ flexGrow: 1, ml: `${drawerWidth}px`, mt: 8, p: 3 }}>
        <Outlet />
      </Box>
    </Box>
  )
}

export default Layout
