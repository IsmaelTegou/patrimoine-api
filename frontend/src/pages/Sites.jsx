import React, { useState } from 'react'
import {
  Typography,
  Paper,
  Box,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TablePagination,
  Button,
  IconButton,
  Chip,
} from '@mui/material'
import AddIcon from '@mui/icons-material/Add'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import PlaceIcon from '@mui/icons-material/Place'

const sitesMock = [
  { id: 1, nom: 'Chutes de la Lobé', ville: 'Kribi', region: 'Sud', type: 'Naturel', visites: 1200 },
  { id: 2, nom: 'Mont Cameroun', ville: 'Buéa', region: 'Sud-Ouest', type: 'Naturel', visites: 850 },
  { id: 3, nom: 'Mosquée de Yagoua', ville: 'Yagoua', region: 'Extrême-Nord', type: 'Culturel', visites: 300 },
  { id: 4, nom: 'Palais des rois Bamoun', ville: 'Foumban', region: 'Ouest', type: 'Historique', visites: 750 },
  { id: 5, nom: 'Parc de Waza', ville: 'Waza', region: 'Extrême-Nord', type: 'Naturel', visites: 920 },
  { id: 6, nom: 'Musée Maritime', ville: 'Douala', region: 'Littoral', type: 'Culturel', visites: 480 },
  { id: 7, nom: 'Chefferie de Bafut', ville: 'Bafut', region: 'Nord-Ouest', type: 'Historique', visites: 650 },
  { id: 8, nom: 'Pics de Rhumsiki', ville: 'Rhumsiki', region: 'Extrême-Nord', type: 'Naturel', visites: 400 },
]

const Sites = () => {
  const [page, setPage] = useState(0)
  const [rowsPerPage, setRowsPerPage] = useState(5)

  const handleChangePage = (event, newPage) => {
    setPage(newPage)
  }

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10))
    setPage(0)
  }

  const getTypeColor = (type) => {
    switch (type) {
      case 'Naturel':
        return 'success'
      case 'Culturel':
        return 'primary'
      case 'Historique':
        return 'warning'
      default:
        return 'default'
    }
  }

  return (
    <Box>
      {/* Header with title and add button */}
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
        <Typography variant="h4" sx={{ fontWeight: 'bold', color: '#344767' }}>
          Sites touristiques
        </Typography>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          sx={{
            bgcolor: '#1A73E8',
            '&:hover': { bgcolor: '#1557B0' },
            borderRadius: 2,
            textTransform: 'none',
          }}
        >
          Ajouter un site
        </Button>
      </Box>

      {/* Sites table */}
      <Paper sx={{ width: '100%', overflow: 'hidden', borderRadius: 3, boxShadow: '0 4px 6px -1px rgba(0,0,0,0.1)' }}>
        <TableContainer>
          <Table>
            <TableHead>
              <TableRow sx={{ bgcolor: '#F8F9FA' }}>
                <TableCell>Nom du site</TableCell>
                <TableCell>Ville</TableCell>
                <TableCell>Région</TableCell>
                <TableCell>Type</TableCell>
                <TableCell align="right">Visites</TableCell>
                <TableCell align="right">Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {sitesMock
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((site) => (
                  <TableRow key={site.id} hover>
                    <TableCell>
                      <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                        <PlaceIcon sx={{ color: '#1A73E8' }} />
                        {site.nom}
                      </Box>
                    </TableCell>
                    <TableCell>{site.ville}</TableCell>
                    <TableCell>{site.region}</TableCell>
                    <TableCell>
                      <Chip 
                        label={site.type} 
                        size="small" 
                        color={getTypeColor(site.type)}
                        sx={{ fontWeight: 500 }}
                      />
                    </TableCell>
                    <TableCell align="right">{site.visites.toLocaleString()}</TableCell>
                    <TableCell align="right">
                      <IconButton size="small" sx={{ color: '#1A73E8' }}>
                        <EditIcon />
                      </IconButton>
                      <IconButton size="small" sx={{ color: '#F44335' }}>
                        <DeleteIcon />
                      </IconButton>
                    </TableCell>
                  </TableRow>
                ))}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={[5, 10, 25]}
          component="div"
          count={sitesMock.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
          labelRowsPerPage="Lignes par page"
          labelDisplayedRows={({ from, to, count }) => `${from}-${to} sur ${count}`}
        />
      </Paper>
    </Box>
  )
}

export default Sites
