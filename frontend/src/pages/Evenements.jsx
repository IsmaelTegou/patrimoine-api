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
} from '@mui/material'
import AddIcon from '@mui/icons-material/Add'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import EventIcon from '@mui/icons-material/Event'
import CloseIcon from '@mui/icons-material/Close'
import SearchIcon from '@mui/icons-material/Search'
import ClearIcon from '@mui/icons-material/Clear'

const sitesMock = [
  { id: 1, nom: 'Cathédrale Saint' },
  { id: 2, nom: 'Musée National' },
  { id: 3, nom: 'Site Archéologique' },
]

const eventsMock = [
  {
    id: 1,
    titre: 'Festival culturel',
    description: 'Concerts et expositions',
    site: 1,
    dateDebut: '2025-11-15T10:00',
    dateFin: '2025-11-17T18:00',
    lieu: 'Place centrale',
    type: 'Festival',
    capacite: 500,
    dateCreation: '2025-09-01T09:00',
  },
  {
    id: 2,
    titre: 'Conférence patrimoine',
    description: 'Table ronde sur la conservation',
    site: 2,
    dateDebut: '2025-12-02T09:30',
    dateFin: '2025-12-02T12:30',
    lieu: 'Salle 3',
    type: 'Conférence',
    capacite: 120,
    dateCreation: '2025-10-21T14:00',
  },
]

const initialEventState = {
  titre: '',
  description: '',
  site: '',
  dateDebut: '',
  dateFin: '',
  lieu: '',
  type: '',
  capacite: '',
  dateCreation: '',
}

const Evenements = () => {
  const [page, setPage] = useState(0)
  const [rowsPerPage, setRowsPerPage] = useState(5)
  const [events, setEvents] = useState(eventsMock)
  const [openDialog, setOpenDialog] = useState(false)
  const [isEditing, setIsEditing] = useState(false)
  const [editingId, setEditingId] = useState(null)
  const [newEvent, setNewEvent] = useState(initialEventState)
  const [confirmDeleteOpen, setConfirmDeleteOpen] = useState(false)
  const [eventToDelete, setEventToDelete] = useState(null)
  const [searchQuery, setSearchQuery] = useState('')
  const [siteFilter, setSiteFilter] = useState('all')

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setNewEvent(prev => ({ ...prev, [name]: name === 'capacite' ? (value === '' ? '' : Number(value)) : value }))
  }

  const handleOpenNew = () => {
    setNewEvent({ ...initialEventState, dateCreation: new Date().toISOString().slice(0,16) })
    setIsEditing(false)
    setEditingId(null)
    setOpenDialog(true)
  }

  const handleSubmit = () => {
    const payload = {
      ...newEvent,
      id: isEditing && editingId != null ? editingId : events.length + 1,
      dateCreation: isEditing && newEvent.dateCreation ? newEvent.dateCreation : (newEvent.dateCreation || new Date().toISOString().slice(0,16)),
    }
    if (isEditing && editingId != null) {
      setEvents(prev => prev.map(ev => ev.id === editingId ? { ...ev, ...payload } : ev))
    } else {
      setEvents(prev => [...prev, payload])
    }
    setOpenDialog(false)
  }

  const handleEdit = (ev) => {
    setNewEvent({ ...ev })
    setIsEditing(true)
    setEditingId(ev.id)
    setOpenDialog(true)
  }

  const handleDeleteClick = (ev) => {
    setEventToDelete(ev)
    setConfirmDeleteOpen(true)
  }

  const handleConfirmDelete = () => {
    if (eventToDelete) setEvents(prev => prev.filter(e => e.id !== eventToDelete.id))
    setEventToDelete(null)
    setConfirmDeleteOpen(false)
  }

  const handleChangePage = (e, newPage) => setPage(newPage)
  const handleChangeRowsPerPage = (e) => { setRowsPerPage(parseInt(e.target.value,10)); setPage(0) }

  const handleSearchChange = (e) => { setSearchQuery(e.target.value); setPage(0) }
  const handleClearSearch = () => { setSearchQuery(''); setPage(0) }
  const handleSiteFilter = (e) => { setSiteFilter(e.target.value); setPage(0) }

  const filtered = events.filter(ev => {
    const q = searchQuery.trim().toLowerCase()
    const siteMatch = siteFilter === 'all' || String(ev.site) === String(siteFilter)
    const searchMatch = !q || [ev.titre, ev.description, ev.lieu, ev.type]
      .filter(Boolean)
      .some(f => String(f).toLowerCase().includes(q))
    return siteMatch && searchMatch
  })

  return (
    <Box>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3, gap: 2 }}>
        <Box sx={{ display: 'flex', gap: 2, alignItems: 'center', flex: 1 }}>
          <TextField
            placeholder="Rechercher un événement, lieu, type..."
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
              startAdornment: (
                <InputAdornment position="start"><SearchIcon/></InputAdornment>
              ),
              endAdornment: searchQuery ? (
                <InputAdornment position="end">
                  <IconButton size="small" onClick={handleClearSearch}><ClearIcon fontSize="small"/></IconButton>
                </InputAdornment>
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
              }
            })}>
            <InputLabel>Site</InputLabel>
            <Select value={siteFilter} label="Site" onChange={handleSiteFilter}>
              <MenuItem value="all">Tous</MenuItem>
              {sitesMock.map(s => <MenuItem key={s.id} value={s.id}>{s.nom}</MenuItem>)}
            </Select>
          </FormControl>
        </Box>

        <Button variant="contained" startIcon={<AddIcon/>} onClick={handleOpenNew}
          sx={{ bgcolor: '#1A73E8', '&:hover':{bgcolor:'#1557B0'}, borderRadius:2, textTransform:'none' }}>
          Ajouter un événement
        </Button>
      </Box>

      {/* Add/Edit dialog */}
      <Dialog open={openDialog} onClose={() => setOpenDialog(false)} maxWidth="md" fullWidth>
        <DialogTitle>
          {isEditing ? 'Modifier un événement' : 'Ajouter un événement'}
          <IconButton onClick={() => setOpenDialog(false)} sx={{ position: 'absolute', right: 8, top: 8 }}><CloseIcon/></IconButton>
        </DialogTitle>
        <DialogContent dividers>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <TextField fullWidth label="Titre" name="titre" value={newEvent.titre} onChange={handleInputChange} required margin="normal" />
            </Grid>
            <Grid item xs={12} sm={6}>
              <FormControl fullWidth margin="normal">
                <InputLabel>Site</InputLabel>
                <Select name="site" value={newEvent.site} label="Site" onChange={handleInputChange}>
                  {sitesMock.map(s => <MenuItem key={s.id} value={s.id}>{s.nom}</MenuItem>)}
                </Select>
              </FormControl>
            </Grid>

            <Grid item xs={12}>
              <TextField fullWidth label="Description" name="description" value={newEvent.description} onChange={handleInputChange} multiline rows={3} margin="normal" />
            </Grid>

            <Grid item xs={12} sm={6}>
              <TextField fullWidth label="Date début" name="dateDebut" type="datetime-local" value={newEvent.dateDebut} onChange={handleInputChange} InputLabelProps={{ shrink: true }} margin="normal" />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField fullWidth label="Date fin" name="dateFin" type="datetime-local" value={newEvent.dateFin} onChange={handleInputChange} InputLabelProps={{ shrink: true }} margin="normal" />
            </Grid>

            <Grid item xs={12} sm={6}>
              <TextField fullWidth label="Lieu" name="lieu" value={newEvent.lieu} onChange={handleInputChange} margin="normal" />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField fullWidth label="Type d'événement" name="type" value={newEvent.type} onChange={handleInputChange} margin="normal" />
            </Grid>

            <Grid item xs={12} sm={6}>
              <TextField fullWidth label="Capacité" name="capacite" type="number" value={newEvent.capacite} onChange={handleInputChange} margin="normal" />
            </Grid>

            <Grid item xs={12} sm={6}>
              <TextField fullWidth label="Date création" name="dateCreation" type="datetime-local" value={newEvent.dateCreation} onChange={handleInputChange} InputLabelProps={{ shrink: true }} margin="normal" />
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenDialog(false)}>Annuler</Button>
          <Button variant="contained" onClick={handleSubmit}>Enregistrer</Button>
        </DialogActions>
      </Dialog>

      {/* Confirm delete */}
      <Dialog open={confirmDeleteOpen} onClose={() => setConfirmDeleteOpen(false)}>
        <DialogTitle>Confirmer la suppression</DialogTitle>
        <DialogContent>
          <Typography>Supprimer l'événement "{eventToDelete ? eventToDelete.titre : ''}" ?</Typography>
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
                <TableCell>Titre</TableCell>
                <TableCell>Description</TableCell>
                <TableCell>Site</TableCell>
                <TableCell>Date début</TableCell>
                <TableCell>Date fin</TableCell>
                <TableCell>Lieu</TableCell>
                <TableCell>Type</TableCell>
                <TableCell>Capacité</TableCell>
                <TableCell>Date création</TableCell>
                <TableCell align="right">Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filtered.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map(ev => (
                <TableRow key={ev.id} hover>
                  <TableCell>{ev.titre}</TableCell>
                  <TableCell>{ev.description}</TableCell>
                  <TableCell>{sitesMock.find(s => s.id === ev.site)?.nom || '-'}</TableCell>
                  <TableCell>{ev.dateDebut ? new Date(ev.dateDebut).toLocaleString() : '-'}</TableCell>
                  <TableCell>{ev.dateFin ? new Date(ev.dateFin).toLocaleString() : '-'}</TableCell>
                  <TableCell>{ev.lieu}</TableCell>
                  <TableCell>{ev.type}</TableCell>
                  <TableCell>{ev.capacite ?? '-'}</TableCell>
                  <TableCell>{ev.dateCreation ? new Date(ev.dateCreation).toLocaleString() : '-'}</TableCell>
                  <TableCell align="right">
                    <IconButton size="small" sx={{ color: '#1A73E8' }} onClick={() => handleEdit(ev)}><EditIcon/></IconButton>
                    <IconButton size="small" sx={{ color: '#F44335' }} onClick={() => handleDeleteClick(ev)}><DeleteIcon/></IconButton>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={[5,10,25]}
          component="div"
          count={filtered.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
          labelRowsPerPage="Lignes par page"
          labelDisplayedRows={({from,to,count}) => `${from}-${to} sur ${count}`}
        />
      </Paper>
    </Box>
  )
}

export default Evenements
