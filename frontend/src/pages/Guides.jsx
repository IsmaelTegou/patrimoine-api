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

const guidesMock = [
  { id: 1, nom: 'Aoudou sehou', specialite: 'Histoire', experience: 8, avis: 24 },
  { id: 2, nom: 'Ismael Tegou.', specialite: 'Architecture', experience: 12, avis: 40 },
  { id: 3, nom: 'Alioum Bouba.', specialite: 'Archéologie', experience: 5, avis: 10 },
]

const specialites = ['Tous', 'Histoire', 'Architecture', 'Archéologie', 'Art']

const initialGuide = { nom: '', specialite: '', experience: '', avis: '' }

const Guides = () => {
  const [guides, setGuides] = useState(guidesMock)
  const [page, setPage] = useState(0)
  const [rowsPerPage, setRowsPerPage] = useState(5)
  const [openDialog, setOpenDialog] = useState(false)
  const [isEditing, setIsEditing] = useState(false)
  const [editingId, setEditingId] = useState(null)
  const [current, setCurrent] = useState(initialGuide)
  const [searchQuery, setSearchQuery] = useState('')
  const [specialiteFilter, setSpecialiteFilter] = useState('Tous')
  const [confirmDeleteOpen, setConfirmDeleteOpen] = useState(false)
  const [guideToDelete, setGuideToDelete] = useState(null)

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setCurrent(prev => ({ ...prev, [name]: name === 'experience' || name === 'avis' ? (value === '' ? '' : Number(value)) : value }))
  }

  const openNew = () => {
    setCurrent(initialGuide)
    setIsEditing(false)
    setEditingId(null)
    setOpenDialog(true)
  }

  const handleSubmit = () => {
    const payload = { ...current, id: isEditing && editingId != null ? editingId : guides.length + 1 }
    if (isEditing && editingId != null) setGuides(prev => prev.map(g => g.id === editingId ? { ...g, ...payload } : g))
    else setGuides(prev => [...prev, payload])
    setOpenDialog(false)
  }

  const handleEdit = (g) => { setCurrent({ ...g }); setIsEditing(true); setEditingId(g.id); setOpenDialog(true) }

  const handleDeleteClick = (g) => { setGuideToDelete(g); setConfirmDeleteOpen(true) }
  const handleConfirmDelete = () => { if (guideToDelete) setGuides(prev => prev.filter(g => g.id !== guideToDelete.id)); setGuideToDelete(null); setConfirmDeleteOpen(false) }

  const handleSearchChange = (e) => { setSearchQuery(e.target.value); setPage(0) }
  const handleClearSearch = () => { setSearchQuery(''); setPage(0) }
  const handleSpecialiteFilter = (e) => { setSpecialiteFilter(e.target.value); setPage(0) }

  const filtered = guides.filter(g => {
    const q = searchQuery.trim().toLowerCase()
    const specMatch = specialiteFilter === 'Tous' || g.specialite === specialiteFilter
    const searchMatch = !q || [g.nom, g.specialite].filter(Boolean).some(f => String(f).toLowerCase().includes(q))
    return specMatch && searchMatch
  })

  return (
    <Box>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3, gap: 2 }}>
        <Box sx={{ display: 'flex', gap: 2, alignItems: 'center', flex: 1 }}>
          <TextField
            placeholder="Rechercher un guide, spécialité..."
            value={searchQuery}
            onChange={handleSearchChange}
            size="small"
            sx={(theme) => ({ 
              flex: 1, 
              maxWidth: 480,
              bgcolor: theme.palette.mode === 'dark' 
                ? 'rgba(255, 255, 255, 0.05)' 
                : 'background.paper',
              '& .MuiOutlinedInput-root': { 
                borderRadius: 2,
                '& fieldset': {
                  borderColor: 'transparent',
                },
                '&:hover fieldset': {
                  borderColor: theme.palette.mode === 'dark'
                    ? 'rgba(255,255,255,0.1)'
                    : 'rgba(26,115,232,0.08)',
                },
                '&.Mui-focused fieldset': {
                  borderColor: 'primary.main',
                  boxShadow: theme.palette.mode === 'dark'
                    ? '0 0 0 4px rgba(26,115,232,0.15)'
                    : '0 0 0 4px rgba(26,115,232,0.06)',
                },
              },
              '& .MuiInputBase-input': {
                color: 'text.primary',
              },
            })}
            InputProps={{
              startAdornment: (<InputAdornment position="start"><SearchIcon/></InputAdornment>),
              endAdornment: searchQuery ? (
                <InputAdornment position="end"><IconButton size="small" onClick={handleClearSearch}><ClearIcon fontSize="small"/></IconButton></InputAdornment>
              ) : null,
            }}
          />

          <FormControl size="small" sx={(theme) => ({ 
              minWidth: 180, 
              bgcolor: theme.palette.mode === 'dark' 
                ? 'rgba(255, 255, 255, 0.05)' 
                : 'background.paper',
              borderRadius: 2,
              '& .MuiOutlinedInput-root': {
                '& fieldset': {
                  borderColor: 'transparent',
                },
                '&:hover fieldset': {
                  borderColor: theme.palette.mode === 'dark'
                    ? 'rgba(255,255,255,0.1)'
                    : 'rgba(26,115,232,0.08)',
                },
                '&.Mui-focused fieldset': {
                  borderColor: 'primary.main',
                },
              },
              '& .MuiSelect-select': {
                color: 'text.primary',
              },
              '& .MuiInputLabel-root': {
                color: 'text.secondary',
              },
            })}>
            <InputLabel>Spécialité</InputLabel>
            <Select value={specialiteFilter} label="Spécialité" onChange={handleSpecialiteFilter}>
              {specialites.map(s => <MenuItem key={s} value={s}>{s}</MenuItem>)}
            </Select>
          </FormControl>
        </Box>

        <Button variant="contained" startIcon={<AddIcon/>} onClick={openNew} sx={{ bgcolor: '#1A73E8', '&:hover':{bgcolor:'#1557B0'}, borderRadius:2, textTransform:'none' }}>
          Ajouter un guide
        </Button>
      </Box>

      {/* Dialog add/edit */}
      <Dialog open={openDialog} onClose={() => setOpenDialog(false)} fullWidth maxWidth="sm">
        <DialogTitle>{isEditing ? 'Modifier un guide' : 'Ajouter un guide'}<IconButton onClick={() => setOpenDialog(false)} sx={{ position: 'absolute', right: 8, top: 8 }}><CloseIcon/></IconButton></DialogTitle>
        <DialogContent dividers>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}><TextField fullWidth label="Nom" name="nom" value={current.nom} onChange={handleInputChange} margin="normal" /></Grid>
            <Grid item xs={12} sm={6}>
              <FormControl fullWidth margin="normal">
                <InputLabel>Spécialité</InputLabel>
                <Select name="specialite" value={current.specialite} label="Spécialité" onChange={handleInputChange}>
                  {specialites.filter(s => s !== 'Tous').map(s => <MenuItem key={s} value={s}>{s}</MenuItem>)}
                </Select>
              </FormControl>
            </Grid>

            <Grid item xs={12} sm={6}><TextField fullWidth label="Expérience (ans)" name="experience" type="number" value={current.experience} onChange={handleInputChange} margin="normal" /></Grid>
            <Grid item xs={12} sm={6}><TextField fullWidth label="Nombre d'avis reçus" name="avis" type="number" value={current.avis} onChange={handleInputChange} margin="normal" /></Grid>
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
        <DialogContent><Typography>Supprimer le guide "{guideToDelete ? guideToDelete.nom : ''}" ?</Typography></DialogContent>
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
        bgcolor: theme.palette.mode === 'dark' ? 'rgba(255, 255, 255, 0.05)' : 'background.paper',
        boxShadow: theme.palette.mode === 'dark'
          ? '0 4px 6px -1px rgba(0,0,0,0.4)'
          : '0 4px 6px -1px rgba(0,0,0,0.1)',
      })}>
        <TableContainer>
          <Table>
            <TableHead>
              <TableRow sx={(theme) => ({ 
                bgcolor: theme.palette.mode === 'dark' 
                  ? 'rgba(255, 255, 255, 0.05)'
                  : 'rgba(0, 0, 0, 0.02)',
                '& th': {
                  color: theme.palette.text.secondary,
                  fontWeight: 600,
                }
              })}>
                <TableCell>Nom</TableCell>
                <TableCell>Spécialité</TableCell>
                <TableCell>Expérience (ans)</TableCell>
                <TableCell>Nombre d'avis</TableCell>
                <TableCell align="right">Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filtered.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map(g => (
                <TableRow key={g.id} hover>
                  <TableCell><Box sx={{ display:'flex', alignItems:'center', gap:1 }}><PersonIcon sx={{ color:'#1A73E8' }}/> {g.nom}</Box></TableCell>
                  <TableCell>{g.specialite}</TableCell>
                  <TableCell>{g.experience ?? '-'}</TableCell>
                  <TableCell>{g.avis ?? '-'}</TableCell>
                  <TableCell align="right">
                    <IconButton size="small" sx={{ color: '#1A73E8' }} onClick={() => handleEdit(g)}><EditIcon/></IconButton>
                    <IconButton size="small" sx={{ color: '#F44335' }} onClick={() => handleDeleteClick(g)}><DeleteIcon/></IconButton>
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

export default Guides
