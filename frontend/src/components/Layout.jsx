import React from 'react'
import Box from '@mui/material/Box'
import { Outlet } from 'react-router-dom'
import Sidebar from './Sidebar'
import Header from './Header'

const Layout = () => {
  return (
    <Box sx={{ display: 'flex' }}>
      <Sidebar />
      <Header />
      <Box component="main" sx={{ flexGrow: 1, ml: 'var(0, 250px)', mt: 10, p: 3 }}>
        <Outlet />
      </Box>
    </Box>
  )
}

export default Layout
