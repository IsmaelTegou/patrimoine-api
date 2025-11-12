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
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Grid,
  MenuItem,
  FormControl,
  InputLabel,
  Select,
  InputAdornment,
  Chip,
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
} from '@mui/material'
import AddIcon from '@mui/icons-material/Add'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import MapIcon from '@mui/icons-material/Map'
import RouteIcon from '@mui/icons-material/Route'
import TimelineIcon from '@mui/icons-material/Timeline'
import AccessTimeIcon from '@mui/icons-material/AccessTime'
import SearchIcon from '@mui/icons-material/Search'
import ClearIcon from '@mui/icons-material/Clear'
import CloseIcon from '@mui/icons-material/Close'
import DragIndicatorIcon from '@mui/icons-material/DragIndicator'

// Données de test
const sitesMock = [
  { id: 1, nom: 'Cathédrale Saint' },
  { id: 2, nom: 'Musée National' },
  { id: 3, nom: 'Site Archéologique' },
  { id: 4, nom: 'Place du Marché' },
  { id: 5, nom: 'Galerie d\'Art' },
]

const itinerairesMock = [
  {
    id: 1,
    nom: 'Circuit Historique',
    description: 'Découverte des monuments historiques',
    dureeHeures: 3,
    distanceKm: 4.5,
    difficulte: 'Facile',
    etapes: [
      { siteId: 1, dureeMinutes: 45, description: 'Visite guidée de la cathédrale' },
      { siteId: 2, dureeMinutes: 90, description: 'Exploration du musée' },
      { siteId: 4, dureeMinutes: 45, description: 'Pause et découverte du marché' }
    ]
  },
  {
    id: 2,
    nom: 'Parcours Culturel',
    description: 'Immersion dans l\'art et la culture',
    dureeHeures: 4,
    distanceKm: 3.2,
    difficulte: 'Modéré',
    etapes: [
      { siteId: 5, dureeMinutes: 60, description: 'Visite de la galerie' },
      { siteId: 2, dureeMinutes: 120, description: 'Visite approfondie du musée' },
      { siteId: 3, dureeMinutes: 60, description: 'Découverte du site archéologique' }
    ]
  }
]

const difficultes = ['Facile', 'Modéré', 'Difficile']

const initialItineraire = {
  nom: '',
  description: '',
  dureeHeures: '',
  distanceKm: '',
  difficulte: 'Facile',
  etapes: []
}

const Itineraires = () => {
  const [itineraires, setItineraires] = useState(itinerairesMock)
  const [page, setPage] = useState(0)
  const [rowsPerPage, setRowsPerPage] = useState(5)
  const [openDialog, setOpenDialog] = useState(false)
  const [isEditing, setIsEditing] = useState(false)
  const [editingId, setEditingId] = useState(null)
  const [current, setCurrent] = useState(initialItineraire)
  const [searchQuery, setSearchQuery] = useState('')
  const [difficulteFilter, setDifficulteFilter] = useState('all')
  const [confirmDeleteOpen, setConfirmDeleteOpen] = useState(false)
  const [itemToDelete, setItemToDelete] = useState(null)
  const [etapeDialog, setEtapeDialog] = useState(false)
  const [currentEtape, setCurrentEtape] = useState({ siteId: '', dureeMinutes: '', description: '' })
  const [editingEtapeIndex, setEditingEtapeIndex] = useState(-1)

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setCurrent(prev => ({
      ...prev,
      [name]: name === 'dureeHeures' || name === 'distanceKm' ? (value === '' ? '' : Number(value)) : value
    }))
  }

  const handleEtapeChange = (e) => {
    const { name, value } = e.target
    setCurrentEtape(prev => ({
      ...prev,
      [name]: name === 'dureeMinutes' ? (value === '' ? '' : Number(value)) : value
    }))
  }

  const openNew = () => {
    setCurrent(initialItineraire)
    setIsEditing(false)
    setEditingId(null)
    setOpenDialog(true)
  }

  const handleSubmit = () => {
    const payload = {
      ...current,
      id: isEditing && editingId != null ? editingId : itineraires.length + 1
    }
    if (isEditing && editingId != null) {
      setItineraires(prev => prev.map(it => it.id === editingId ? { ...it, ...payload } : it))
    } else {
      setItineraires(prev => [...prev, payload])
    }
    setOpenDialog(false)
  }

  const handleEdit = (it) => {
    setCurrent({ ...it })
    setIsEditing(true)
    setEditingId(it.id)
    setOpenDialog(true)
  }

  const handleDeleteClick = (it) => {
    setItemToDelete(it)
    setConfirmDeleteOpen(true)
  }

  const handleConfirmDelete = () => {
    if (itemToDelete) {
      setItineraires(prev => prev.filter(it => it.id !== itemToDelete.id))
    }
    setItemToDelete(null)
    setConfirmDeleteOpen(false)
  }

  const handleSearchChange = (e) => { setSearchQuery(e.target.value); setPage(0) }
  const handleClearSearch = () => { setSearchQuery(''); setPage(0) }
  const handleDifficulteFilter = (e) => { setDifficulteFilter(e.target.value); setPage(0) }

  // Gestion des étapes
  const openEtapeDialog = (index = -1) => {
    if (index >= 0) {
      setCurrentEtape({ ...current.etapes[index] })
      setEditingEtapeIndex(index)
    } else {
      setCurrentEtape({ siteId: '', dureeMinutes: '', description: '' })
      setEditingEtapeIndex(-1)
    }
    setEtapeDialog(true)
  }

  const handleEtapeSubmit = () => {
    const newEtapes = [...current.etapes]
    if (editingEtapeIndex >= 0) {
      newEtapes[editingEtapeIndex] = currentEtape
    } else {
      newEtapes.push(currentEtape)
    }
    setCurrent(prev => ({ ...prev, etapes: newEtapes }))
    setEtapeDialog(false)
  }

  const handleDeleteEtape = (index) => {
    setCurrent(prev => ({
      ...prev,
      etapes: prev.etapes.filter((_, i) => i !== index)
    }))
  }

  const filtered = itineraires.filter(it => {
    const q = searchQuery.trim().toLowerCase()
    const diffMatch = difficulteFilter === 'all' || it.difficulte === difficulteFilter
    const searchMatch = !q || [it.nom, it.description].filter(Boolean).some(f => String(f).toLowerCase().includes(q))
    return diffMatch && searchMatch
  })

  const getTotalDuree = (etapes) => {
    return etapes.reduce((acc, etape) => acc + etape.dureeMinutes, 0)
  }

  return (
    <Box>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3, gap: 2 }}>
        <Box sx={{ display: 'flex', gap: 2, alignItems: 'center', flex: 1 }}>
          <TextField
            placeholder="Rechercher un itinéraire..."
            value={searchQuery}
            onChange={handleSearchChange}
            size="small"
            sx={(theme) => ({
              flex: 1,
              maxWidth: 480,
              '& .MuiOutlinedInput-root': {
                borderRadius: 2,
                backgroundColor: theme.palette.mode === 'dark' ? 'rgba(255, 255, 255, 0.05)' : '#fff',
                '&:hover': {
                  backgroundColor: theme.palette.mode === 'dark' ? 'rgba(255, 255, 255, 0.1)' : '#fff'
                },
                '& fieldset': {
                  borderColor: 'transparent'
                },
                '&:hover fieldset': {
                  borderColor: theme.palette.mode === 'dark'
                    ? 'rgba(255,255,255,0.1)'
                    : 'rgba(26,115,232,0.08)'
                },
                '&.Mui-focused fieldset': {
                  borderColor: 'primary.main',
                  boxShadow: theme.palette.mode === 'dark'
                    ? '0 0 0 4px rgba(26,115,232,0.15)'
                    : '0 0 0 4px rgba(26,115,232,0.06)'
                }
              }
            })}
            InputProps={{
              startAdornment: (<InputAdornment position="start"><SearchIcon/></InputAdornment>),
              endAdornment: searchQuery ? (
                <InputAdornment position="end"><IconButton size="small" onClick={handleClearSearch}><ClearIcon fontSize="small"/></IconButton></InputAdornment>
              ) : null,
            }}
          />

          <FormControl size="small" sx={(theme) => ({
            minWidth: 200,
            borderRadius: 2,
            '& .MuiOutlinedInput-root': {
              backgroundColor: theme.palette.mode === 'dark' ? 'rgba(255, 255, 255, 0.05)' : '#fff',
              '&:hover': {
                backgroundColor: theme.palette.mode === 'dark' ? 'rgba(255, 255, 255, 0.1)' : '#fff'
              },
              '& fieldset': {
                borderColor: 'transparent'
              },
              '&:hover fieldset': {
                borderColor: theme.palette.mode === 'dark'
                  ? 'rgba(255,255,255,0.1)'
                  : 'rgba(26,115,232,0.08)'
              }
            },
            '& .MuiInputLabel-root': {
              color: theme.palette.text.secondary
            }
          })}>
            <InputLabel>Difficulté</InputLabel>
            <Select value={difficulteFilter} label="Difficulté" onChange={handleDifficulteFilter}>
              <MenuItem value="all">Toutes</MenuItem>
              {difficultes.map(d => <MenuItem key={d} value={d}>{d}</MenuItem>)}
            </Select>
          </FormControl>
        </Box>

        <Button variant="contained" startIcon={<AddIcon/>} onClick={openNew}
          sx={{ bgcolor: '#1A73E8', '&:hover':{bgcolor:'#1557B0'}, borderRadius:2, textTransform:'none' }}>
          Ajouter un itinéraire
        </Button>
      </Box>

      {/* Dialog add/edit itinéraire */}
      <Dialog open={openDialog} onClose={() => setOpenDialog(false)} maxWidth="md" fullWidth>
        <DialogTitle>
          {isEditing ? 'Modifier l\'itinéraire' : 'Ajouter un itinéraire'}
          <IconButton onClick={() => setOpenDialog(false)} sx={{ position: 'absolute', right: 8, top: 8 }}>
            <CloseIcon />
          </IconButton>
        </DialogTitle>
        <DialogContent dividers>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField fullWidth label="Nom de l'itinéraire" name="nom" value={current.nom} onChange={handleInputChange} required margin="normal" />
            </Grid>

            <Grid item xs={12}>
              <TextField fullWidth label="Description" name="description" value={current.description} onChange={handleInputChange} multiline rows={3} margin="normal" />
            </Grid>

            <Grid item xs={12} sm={4}>
              <TextField fullWidth label="Durée (heures)" name="dureeHeures" type="number" value={current.dureeHeures} onChange={handleInputChange} margin="normal" />
            </Grid>
            <Grid item xs={12} sm={4}>
              <TextField fullWidth label="Distance (km)" name="distanceKm" type="number" value={current.distanceKm} onChange={handleInputChange} margin="normal" />
            </Grid>
            <Grid item xs={12} sm={4}>
              <FormControl fullWidth margin="normal">
                <InputLabel>Difficulté</InputLabel>
                <Select name="difficulte" value={current.difficulte} label="Difficulté" onChange={handleInputChange}>
                  {difficultes.map(d => <MenuItem key={d} value={d}>{d}</MenuItem>)}
                </Select>
              </FormControl>
            </Grid>

            <Grid item xs={12}>
              <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
                <Typography variant="subtitle1" component="div">Étapes</Typography>
                <Button startIcon={<AddIcon/>} onClick={() => openEtapeDialog()}>Ajouter une étape</Button>
              </Box>
              
              <List>
                {current.etapes.map((etape, index) => (
                  <ListItem key={index} sx={{ bgcolor: 'background.paper', mb: 1, borderRadius: 1 }}>
                    <DragIndicatorIcon sx={{ mr: 2, color: 'text.secondary' }} />
                    <ListItemText
                      primary={sitesMock.find(s => s.id === etape.siteId)?.nom}
                      secondary={
                        <React.Fragment>
                          <Typography component="span" variant="body2" color="text.secondary">
                            {etape.dureeMinutes} min - 
                          </Typography>
                          {" " + etape.description}
                        </React.Fragment>
                      }
                    />
                    <ListItemSecondaryAction>
                      <IconButton edge="end" onClick={() => openEtapeDialog(index)} sx={{ mr: 1 }}>
                        <EditIcon />
                      </IconButton>
                      <IconButton edge="end" onClick={() => handleDeleteEtape(index)}>
                        <DeleteIcon />
                      </IconButton>
                    </ListItemSecondaryAction>
                  </ListItem>
                ))}
              </List>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenDialog(false)}>Annuler</Button>
          <Button variant="contained" onClick={handleSubmit}>Enregistrer</Button>
        </DialogActions>
      </Dialog>

      {/* Dialog add/edit étape */}
      <Dialog open={etapeDialog} onClose={() => setEtapeDialog(false)} maxWidth="sm" fullWidth>
        <DialogTitle>
          {editingEtapeIndex >= 0 ? 'Modifier l\'étape' : 'Ajouter une étape'}
          <IconButton onClick={() => setEtapeDialog(false)} sx={{ position: 'absolute', right: 8, top: 8 }}>
            <CloseIcon />
          </IconButton>
        </DialogTitle>
        <DialogContent dividers>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <FormControl fullWidth margin="normal">
                <InputLabel>Site</InputLabel>
                <Select name="siteId" value={currentEtape.siteId} label="Site" onChange={handleEtapeChange}>
                  {sitesMock.map(s => <MenuItem key={s.id} value={s.id}>{s.nom}</MenuItem>)}
                </Select>
              </FormControl>
            </Grid>
            
            <Grid item xs={12}>
              <TextField fullWidth label="Durée (minutes)" name="dureeMinutes" type="number" value={currentEtape.dureeMinutes} onChange={handleEtapeChange} margin="normal" />
            </Grid>

            <Grid item xs={12}>
              <TextField fullWidth label="Description de l'étape" name="description" value={currentEtape.description} onChange={handleEtapeChange} multiline rows={2} margin="normal" />
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setEtapeDialog(false)}>Annuler</Button>
          <Button variant="contained" onClick={handleEtapeSubmit}>Enregistrer</Button>
        </DialogActions>
      </Dialog>

      {/* Confirm delete */}
      <Dialog open={confirmDeleteOpen} onClose={() => setConfirmDeleteOpen(false)}>
        <DialogTitle>Confirmer la suppression</DialogTitle>
        <DialogContent>
          <Typography>Êtes-vous sûr de vouloir supprimer l'itinéraire "{itemToDelete?.nom}" ?</Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setConfirmDeleteOpen(false)}>Annuler</Button>
          <Button color="error" variant="contained" onClick={handleConfirmDelete}>Supprimer</Button>
        </DialogActions>
      </Dialog>

      {/* Table */}
      <Paper sx={(theme) => ({
        width: '100%',
        overflow: 'hidden',
        borderRadius: 3,
        backgroundColor: theme.palette.mode === 'dark' ? 'rgba(255, 255, 255, 0.05)' : '#fff',
        boxShadow: theme.palette.mode === 'dark'
          ? '0 4px 6px -1px rgba(0,0,0,0.3)'
          : '0 4px 6px -1px rgba(0,0,0,0.1)'
      })}>
        <TableContainer>
          <Table>
            <TableHead>
              <TableRow sx={(theme) => ({
                backgroundColor: theme.palette.mode === 'dark'
                  ? 'rgba(255, 255, 255, 0.05)'
                  : '#F8F9FA',
                '& th': {
                  color: theme.palette.text.secondary,
                  fontWeight: 600
                }
              })}>
                <TableCell>Nom</TableCell>
                <TableCell>Description</TableCell>
                <TableCell>Durée</TableCell>
                <TableCell>Distance</TableCell>
                <TableCell>Difficulté</TableCell>
                <TableCell>Étapes</TableCell>
                <TableCell align="right">Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filtered.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map(it => (
                <TableRow key={it.id} hover>
                  <TableCell>
                    <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                      <RouteIcon sx={{ color: '#1A73E8' }} />
                      {it.nom}
                    </Box>
                  </TableCell>
                  <TableCell>{it.description}</TableCell>
                  <TableCell>
                    <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                      <AccessTimeIcon fontSize="small" />
                      {it.dureeHeures}h ({getTotalDuree(it.etapes)} min)
                    </Box>
                  </TableCell>
                  <TableCell>{it.distanceKm} km</TableCell>
                  <TableCell>
                    <Chip
                      label={it.difficulte}
                      size="small"
                      sx={{
                        bgcolor: it.difficulte === 'Facile' ? '#E8F5E9' :
                                it.difficulte === 'Modéré' ? '#FFF3E0' : '#FFEBEE',
                        color: it.difficulte === 'Facile' ? '#2E7D32' :
                                it.difficulte === 'Modéré' ? '#E65100' : '#C62828',
                      }}
                    />
                  </TableCell>
                  <TableCell>
                    <Box sx={{ display: 'flex', gap: 1, alignItems: 'center' }}>
                      <TimelineIcon fontSize="small" />
                      {it.etapes.length} sites
                    </Box>
                  </TableCell>
                  <TableCell align="right">
                    <IconButton size="small" sx={{ color: '#1A73E8' }} onClick={() => handleEdit(it)}>
                      <EditIcon />
                    </IconButton>
                    <IconButton size="small" sx={{ color: '#F44335' }} onClick={() => handleDeleteClick(it)}>
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
          count={filtered.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={(e, newPage) => setPage(newPage)}
          onRowsPerPageChange={(e) => { setRowsPerPage(parseInt(e.target.value, 10)); setPage(0) }}
          labelRowsPerPage="Lignes par page"
          labelDisplayedRows={({from, to, count}) => `${from}-${to} sur ${count}`}
        />
      </Paper>
    </Box>
  )
}

export default Itineraires
