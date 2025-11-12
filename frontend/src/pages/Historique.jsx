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
import PersonIcon from '@mui/icons-material/Person'
import CloseIcon from '@mui/icons-material/Close'
import SearchIcon from '@mui/icons-material/Search'
import ClearIcon from '@mui/icons-material/Clear'

const sitesMock = [
  { id: 1, nom: 'Cathédrale Saint' },
  { id: 2, nom: 'Musée National' },
  { id: 3, nom: 'Site Archéologique' },
]

const historyMock = [
  { id: 1, touriste: 'Ismael Tegou', site: 1, dateVisite: '2025-10-01T10:30', dureeMinutes: 90, source: 'Site web' },
  { id: 2, touriste: 'Aoudou Sehou', site: 2, dateVisite: '2025-09-21T14:00', dureeMinutes: 60, source: 'Application mobile' },
]

const initialEntry = { touriste: '', site: '', dateVisite: '', dureeMinutes: '', source: '' }

const Historique = () => {
  const [entries, setEntries] = useState(historyMock)
  const [page, setPage] = useState(0)
  const [rowsPerPage, setRowsPerPage] = useState(5)
  const [openDialog, setOpenDialog] = useState(false)
  const [isEditing, setIsEditing] = useState(false)
  const [editingId, setEditingId] = useState(null)
  const [current, setCurrent] = useState(initialEntry)
  const [searchQuery, setSearchQuery] = useState('')
  const [siteFilter, setSiteFilter] = useState('all')
  const [confirmDeleteOpen, setConfirmDeleteOpen] = useState(false)
  const [entryToDelete, setEntryToDelete] = useState(null)

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setCurrent(prev => ({ ...prev, [name]: name === 'dureeMinutes' ? (value === '' ? '' : Number(value)) : value }))
  }

  const openNew = () => { setCurrent(initialEntry); setIsEditing(false); setEditingId(null); setOpenDialog(true) }

  const handleSubmit = () => {
    const payload = { ...current, id: isEditing && editingId != null ? editingId : entries.length + 1 }
    if (isEditing && editingId != null) setEntries(prev => prev.map(e => e.id === editingId ? { ...e, ...payload } : e))
    else setEntries(prev => [...prev, payload])
    setOpenDialog(false)
  }

  const handleEdit = (e) => { setCurrent({ ...e }); setIsEditing(true); setEditingId(e.id); setOpenDialog(true) }

  const handleDeleteClick = (e) => { setEntryToDelete(e); setConfirmDeleteOpen(true) }
  const handleConfirmDelete = () => { if (entryToDelete) setEntries(prev => prev.filter(en => en.id !== entryToDelete.id)); setEntryToDelete(null); setConfirmDeleteOpen(false) }

  const handleSearchChange = (e) => { setSearchQuery(e.target.value); setPage(0) }
  const handleClearSearch = () => { setSearchQuery(''); setPage(0) }
  const handleSiteFilter = (e) => { setSiteFilter(e.target.value); setPage(0) }

  const filtered = entries.filter(en => {
    const q = searchQuery.trim().toLowerCase()
    const siteMatch = siteFilter === 'all' || String(en.site) === String(siteFilter)
    const searchMatch = !q || [en.touriste, sitesMock.find(s => s.id === en.site)?.nom, en.source]
      .filter(Boolean)
      .some(f => String(f).toLowerCase().includes(q))
    return siteMatch && searchMatch
  })

  return (
    <Box>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3, gap: 2 }}>
        <Box sx={{ display: 'flex', gap: 2, alignItems: 'center', flex: 1 }}>
          <TextField
            placeholder="Rechercher un touriste, site, source..."
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

        <Button variant="contained" startIcon={<AddIcon/>} onClick={openNew} sx={{ bgcolor: '#1A73E8', '&:hover':{bgcolor:'#1557B0'}, borderRadius:2, textTransform:'none' }}>
          Ajouter une visite
        </Button>
      </Box>

      {/* Dialog */}
      <Dialog open={openDialog} onClose={() => setOpenDialog(false)} fullWidth maxWidth="sm">
        <DialogTitle>{isEditing ? 'Modifier la visite' : 'Ajouter une visite'}<IconButton onClick={() => setOpenDialog(false)} sx={{ position: 'absolute', right: 8, top: 8 }}><CloseIcon/></IconButton></DialogTitle>
        <DialogContent dividers>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}><TextField fullWidth label="Nom du touriste" name="touriste" value={current.touriste} onChange={handleInputChange} margin="normal" /></Grid>
            <Grid item xs={12} sm={6}>
              <FormControl fullWidth margin="normal">
                <InputLabel>Site</InputLabel>
                <Select name="site" value={current.site} label="Site" onChange={handleInputChange}>
                  {sitesMock.map(s => <MenuItem key={s.id} value={s.id}>{s.nom}</MenuItem>)}
                </Select>
              </FormControl>
            </Grid>

            <Grid item xs={12} sm={6}><TextField fullWidth label="Date de visite" name="dateVisite" type="datetime-local" value={current.dateVisite} onChange={handleInputChange} InputLabelProps={{ shrink: true }} margin="normal" /></Grid>
            <Grid item xs={12} sm={6}><TextField fullWidth label="Durée de visite (minutes)" name="dureeMinutes" type="number" value={current.dureeMinutes} onChange={handleInputChange} margin="normal" /></Grid>

            <Grid item xs={12}><TextField fullWidth label="Source d'accès" name="source" value={current.source} onChange={handleInputChange} margin="normal" /></Grid>
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
        <DialogContent><Typography>Supprimer l'entrée de visite "{entryToDelete ? entryToDelete.touriste : ''}" ?</Typography></DialogContent>
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
                backgroundColor: theme.palette.mode === 'dark' ? 'rgba(255, 255, 255, 0.05)' : '#F8F9FA'
              })}>
                <TableCell>Nom du touriste</TableCell>
                <TableCell>Nom du site</TableCell>
                <TableCell>Date de visite</TableCell>
                <TableCell>Durée (min)</TableCell>
                <TableCell>Source d'accès</TableCell>
                <TableCell align="right">Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filtered.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map(en => (
                <TableRow key={en.id} hover>
                  <TableCell><Box sx={{ display:'flex', alignItems:'center', gap:1 }}><PersonIcon sx={{ color:'#1A73E8' }}/> {en.touriste}</Box></TableCell>
                  <TableCell>{sitesMock.find(s => s.id === en.site)?.nom || '-'}</TableCell>
                  <TableCell>{en.dateVisite ? new Date(en.dateVisite).toLocaleString() : '-'}</TableCell>
                  <TableCell>{en.dureeMinutes ?? '-'}</TableCell>
                  <TableCell>{en.source}</TableCell>
                  <TableCell align="right">
                    <IconButton size="small" sx={{ color: '#1A73E8' }} onClick={() => handleEdit(en)}><EditIcon/></IconButton>
                    <IconButton size="small" sx={{ color: '#F44335' }} onClick={() => handleDeleteClick(en)}><DeleteIcon/></IconButton>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination rowsPerPageOptions={[5,10,25]} component="div" count={filtered.length} rowsPerPage={rowsPerPage} page={page} onPageChange={(e,newPage)=>setPage(newPage)} onRowsPerPageChange={(e)=>{setRowsPerPage(parseInt(e.target.value,10)); setPage(0)}} labelRowsPerPage="Lignes par page" labelDisplayedRows={({from,to,count})=>`${from}-${to} sur ${count}`} />
      </Paper>
    </Box>
  )
}

export default Historique
