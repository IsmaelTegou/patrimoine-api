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
  Rating,
  InputAdornment,
} from '@mui/material'
import AddIcon from '@mui/icons-material/Add'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import PlaceIcon from '@mui/icons-material/Place'
import CloseIcon from '@mui/icons-material/Close'
import SearchIcon from '@mui/icons-material/Search'
import ClearIcon from '@mui/icons-material/Clear'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { LocalizationProvider, TimePicker } from '@mui/x-date-pickers'
import { fr } from 'date-fns/locale'

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

const initialSiteState = {
  nom: '',
  description: '',
  type: '',
  localisation: '',
  province: '',
  heureOuverture: null,
  heureFermeture: null,
  tarifEntree: '',
  capaciteAccueil: '',
  etatConservation: '',
  contactResponsable: '',
  telephoneContact: '',
  moyenneAvis: 0,
  nombreAvis: 0,
  nombreVisites: 0,
  dateCreation: new Date(),
  dateModification: new Date()
}

const Sites = () => {
  const [page, setPage] = useState(0)
  const [rowsPerPage, setRowsPerPage] = useState(5)
  const [openDialog, setOpenDialog] = useState(false)
  const [newSite, setNewSite] = useState(initialSiteState)
  const [sites, setSites] = useState(sitesMock)
  const [isEditing, setIsEditing] = useState(false)
  const [editingId, setEditingId] = useState(null)
  const [confirmDeleteOpen, setConfirmDeleteOpen] = useState(false)
  const [siteToDelete, setSiteToDelete] = useState(null)
  const [selectedSite, setSelectedSite] = useState(null)
  const [selectedRegion, setSelectedRegion] = useState('')
  
  const handleRegionChange = (e) => {
    setSelectedRegion(e.target.value)
    setPage(0)
  }
  const [searchQuery, setSearchQuery] = useState('')

  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value)
    setPage(0)
  }

  const handleClearSearch = () => {
    setSearchQuery('')
    setPage(0)
  }

  const normalize = (str = '') =>
    String(str)
      .normalize('NFD')
      .replace(/\p{Diacritic}/gu, '')
      .toLowerCase()

  const filteredSites = sites.filter((s) => {
    const q = searchQuery.trim().toLowerCase()
    // region filter
    if (selectedRegion) {
      const siteRegion = normalize(s.region || s.province || '')
      const sel = normalize(selectedRegion)
      if (!siteRegion.includes(sel)) return false
    }

    if (!q) return true
    return [
      s.nom,
      s.description,
      s.type,
      s.ville || s.localisation,
      s.region || s.province,
      s.contactResponsable,
      s.telephoneContact,
    ]
      .filter(Boolean)
      .some((field) => String(field).toLowerCase().includes(q))
  })

  const handleInputChange = (event) => {
    const { name, value } = event.target
    setNewSite(prev => ({
      ...prev,
      [name]: value
    }))
  }

  const handleTimeChange = (name) => (value) => {
    setNewSite(prev => ({
      ...prev,
      [name]: value
    }))
  }

  const handleSubmit = () => {
    const siteToAdd = {
      ...newSite,
      id: sites.length + 1,
      visites: newSite.nombreVisites || 0,
      ville: newSite.localisation || '',
      region: newSite.province || '',
      dateModification: new Date(),
      dateCreation: newSite.dateCreation || new Date(),
    }
    if (isEditing && editingId != null) {
      setSites(prev => prev.map(s => (s.id === editingId ? { ...s, ...siteToAdd, id: editingId, dateModification: new Date() } : s)))
    } else {
      setSites(prev => [...prev, siteToAdd])
    }
    // reset
    setIsEditing(false)
    setEditingId(null)
    handleCloseDialog()
  }

  const handleCloseDialog = () => {
    setNewSite(initialSiteState)
    setOpenDialog(false)
    setIsEditing(false)
    setEditingId(null)
  }


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

  // Edit handler - populate dialog with selected site
  const handleEdit = (site) => {
    // map site fields into form fields
    setNewSite({
      nom: site.nom || '',
      description: site.description || '',
      type: site.type || '',
      localisation: site.ville || site.localisation || '',
      province: site.region || site.province || '',
      heureOuverture: site.heureOuverture || null,
      heureFermeture: site.heureFermeture || null,
      tarifEntree: site.tarifEntree || '',
      capaciteAccueil: site.capaciteAccueil || '',
      etatConservation: site.etatConservation || '',
      contactResponsable: site.contactResponsable || '',
      telephoneContact: site.telephoneContact || '',
      moyenneAvis: site.moyenneAvis || 0,
      nombreAvis: site.nombreAvis || 0,
      nombreVisites: site.visites || site.nombreVisites || 0,
      dateCreation: site.dateCreation ? new Date(site.dateCreation) : new Date(),
      dateModification: site.dateModification ? new Date(site.dateModification) : new Date(),
    })
    setIsEditing(true)
    setEditingId(site.id)
    setOpenDialog(true)
  }

  // Delete handlers
  const handleDeleteClick = (site) => {
    setSiteToDelete(site)
    setConfirmDeleteOpen(true)
  }

  const handleConfirmDelete = () => {
    if (siteToDelete) {
      setSites(prev => prev.filter(s => s.id !== siteToDelete.id))
    }
    setSiteToDelete(null)
    setConfirmDeleteOpen(false)
  }

  const handleCancelDelete = () => {
    setSiteToDelete(null)
    setConfirmDeleteOpen(false)
  }

  return (
    <Box>
      {/* Header with title and add button */}
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3, gap: 2 }}>
        <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
          <TextField
            placeholder="Rechercher un site, ville, type, contact..."
            value={searchQuery}
            onChange={handleSearchChange}
            size="small"
            variant="outlined"
            sx={{
              width: { xs: '100%', sm: 420 },
              bgcolor: (theme) => theme.palette.mode === 'dark' 
                ? 'rgba(255, 255, 255, 0.05)' 
                : 'background.paper',
              borderRadius: 2,
              boxShadow: (theme) => theme.palette.mode === 'dark'
                ? '0 1px 6px rgba(0,0,0,0.3)'
                : '0 1px 6px rgba(16,24,40,0.06)',
              '& .MuiOutlinedInput-root': {
                paddingRight: 0,
                '& fieldset': {
                  borderColor: 'transparent',
                },
                '&:hover fieldset': {
                  borderColor: (theme) => theme.palette.mode === 'dark'
                    ? 'rgba(255,255,255,0.1)'
                    : 'rgba(26,115,232,0.08)',
                },
                '&.Mui-focused fieldset': {
                  borderColor: 'primary.main',
                  boxShadow: (theme) => theme.palette.mode === 'dark'
                    ? '0 0 0 4px rgba(26,115,232,0.15)'
                    : '0 0 0 4px rgba(26,115,232,0.06)',
                },
              },
              input: { 
                px: 1.2,
                color: 'text.primary',
              },
            }}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <SearchIcon sx={{ color: '#67748E' }} />
                </InputAdornment>
              ),
              endAdornment: searchQuery ? (
                <InputAdornment position="end">
                  <IconButton size="small" onClick={handleClearSearch}>
                    <ClearIcon fontSize="small" />
                  </IconButton>
                </InputAdornment>
              ) : null,
            }}
          />

          <FormControl
            size="small"
            variant="outlined"
            sx={{
              minWidth: { xs: 140, sm: 220 },
              bgcolor: (theme) => theme.palette.mode === 'dark' 
                ? 'rgba(255, 255, 255, 0.05)' 
                : 'background.paper',
              borderRadius: 2,
              boxShadow: (theme) => theme.palette.mode === 'dark'
                ? '0 1px 6px rgba(0,0,0,0.3)'
                : '0 1px 6px rgba(16,24,40,0.04)',
              '& .MuiOutlinedInput-root': {
                '& fieldset': { borderColor: 'transparent' },
                '&:hover fieldset': { 
                  borderColor: (theme) => theme.palette.mode === 'dark'
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
            }}
          >
            <InputLabel id="region-select-label">Région</InputLabel>
            <Select
              labelId="region-select-label"
              value={selectedRegion}
              label="Région"
              onChange={handleRegionChange}
            >
              <MenuItem value="">Toutes</MenuItem>
              <MenuItem value="Adamaoua">Adamaoua</MenuItem>
              <MenuItem value="Extrême-Nord">Extrême-Nord</MenuItem>
              <MenuItem value="Nord">Nord</MenuItem>
            </Select>
          </FormControl>
        </Box>

        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={() => setOpenDialog(true)}
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

      {/* Add Site Dialog */}
      <Dialog
        open={openDialog}
        onClose={handleCloseDialog}
        maxWidth="md"
        fullWidth
        PaperProps={{
          sx: {
            borderRadius: 2,
            position: 'relative'
          }
        }}
      >
        <DialogTitle sx={{ pb: 1 }}>
          Ajouter un nouveau site
          <IconButton
            onClick={handleCloseDialog}
            sx={{
              position: 'absolute',
              right: 8,
              top: 8,
              color: 'grey.500'
            }}
          >
            <CloseIcon />
          </IconButton>
        </DialogTitle>
        <DialogContent dividers>
          <LocalizationProvider dateAdapter={AdapterDateFns} adapterLocale={fr}>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="Nom du site"
                  name="nom"
                  value={newSite.nom}
                  onChange={handleInputChange}
                  required
                  margin="normal"
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <FormControl fullWidth margin="normal">
                  <InputLabel>Type</InputLabel>
                  <Select
                    name="type"
                    value={newSite.type}
                    onChange={handleInputChange}
                    label="Type"
                    required
                  >
                    <MenuItem value="Naturel">Naturel</MenuItem>
                    <MenuItem value="Culturel">Culturel</MenuItem>
                    <MenuItem value="Historique">Historique</MenuItem>
                  </Select>
                </FormControl>
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label="Description"
                  name="description"
                  value={newSite.description}
                  onChange={handleInputChange}
                  multiline
                  rows={3}
                  margin="normal"
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="Localisation"
                  name="localisation"
                  value={newSite.localisation}
                  onChange={handleInputChange}
                  margin="normal"
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="Province"
                  name="province"
                  value={newSite.province}
                  onChange={handleInputChange}
                  margin="normal"
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TimePicker
                  label="Heure d'ouverture"
                  value={newSite.heureOuverture}
                  onChange={handleTimeChange('heureOuverture')}
                  renderInput={(params) => <TextField {...params} fullWidth margin="normal" />}
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TimePicker
                  label="Heure de fermeture"
                  value={newSite.heureFermeture}
                  onChange={handleTimeChange('heureFermeture')}
                  renderInput={(params) => <TextField {...params} fullWidth margin="normal" />}
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="Tarif d'entrée"
                  name="tarifEntree"
                  value={newSite.tarifEntree}
                  onChange={handleInputChange}
                  type="number"
                  margin="normal"
                  InputProps={{
                    startAdornment: 'FCFA'
                  }}
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="Capacité d'accueil"
                  name="capaciteAccueil"
                  value={newSite.capaciteAccueil}
                  onChange={handleInputChange}
                  type="number"
                  margin="normal"
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="État de conservation"
                  name="etatConservation"
                  value={newSite.etatConservation}
                  onChange={handleInputChange}
                  margin="normal"
                  select
                >
                  <MenuItem value="Excellent">Excellent</MenuItem>
                  <MenuItem value="Bon">Bon</MenuItem>
                  <MenuItem value="Moyen">Moyen</MenuItem>
                  <MenuItem value="Mauvais">Mauvais</MenuItem>
                </TextField>
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="Contact responsable"
                  name="contactResponsable"
                  value={newSite.contactResponsable}
                  onChange={handleInputChange}
                  margin="normal"
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="Téléphone contact"
                  name="telephoneContact"
                  value={newSite.telephoneContact}
                  onChange={handleInputChange}
                  margin="normal"
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <Box sx={{ mt: 2 }}>
                  <Typography component="legend">Moyenne d'avis</Typography>
                  <Rating
                    name="moyenneAvis"
                    value={newSite.moyenneAvis}
                    onChange={(event, newValue) => {
                      handleInputChange({
                        target: { name: 'moyenneAvis', value: newValue }
                      })
                    }}
                    precision={0.5}
                    
                  />
                </Box>
              </Grid>
            </Grid>
          </LocalizationProvider>
        </DialogContent>
        <DialogActions sx={{ px: 3, py: 2 }}>
          <Button onClick={handleCloseDialog} variant="outlined" color="primary">
            Annuler
          </Button>
          <Button onClick={handleSubmit} variant="contained" color="primary">
            Enregistrer
          </Button>
        </DialogActions>
      </Dialog>

      {/* Confirm delete dialog */}
      <Dialog open={confirmDeleteOpen} onClose={handleCancelDelete}>
        <DialogTitle>Confirmer la suppression</DialogTitle>
        <DialogContent>
          <Typography>
            Êtes-vous sûr de vouloir supprimer le site "{siteToDelete?.nom}" ?
          </Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCancelDelete}>Annuler</Button>
          <Button color="error" variant="contained" onClick={handleConfirmDelete}>Supprimer</Button>
        </DialogActions>
      </Dialog>

      {/* Sites table */}
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
                <TableCell>Nom du site</TableCell>
                <TableCell>Ville</TableCell>
                <TableCell>Région</TableCell>
                <TableCell>Type</TableCell>
                <TableCell align="right">Avis</TableCell>
                <TableCell align="right">Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filteredSites
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((site) => (
                  <TableRow
                    key={site.id}
                    hover
                    onClick={() => setSelectedSite(site)}
                    selected={selectedSite?.id === site.id}
                    sx={{ cursor: 'pointer' }}
                  >
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
                    <TableCell align="right">
                      <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'flex-end', gap: 1 }}>
                        <Rating value={site.moyenneAvis || 0} precision={0.5} readOnly size="small" />
                        <Typography variant="body2">{site.moyenneAvis ? Number(site.moyenneAvis).toFixed(1) : '-'}</Typography>
                      </Box>
                    </TableCell>
                    <TableCell align="right">
                      <IconButton
                        size="small"
                        sx={{ color: '#1A73E8' }}
                        onClick={(e) => {
                          e.stopPropagation()
                          handleEdit(site)
                        }}
                      >
                        <EditIcon />
                      </IconButton>
                      <IconButton
                        size="small"
                        sx={{ color: '#F44335' }}
                        onClick={(e) => {
                          e.stopPropagation()
                          handleDeleteClick(site)
                        }}
                      >
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
          count={filteredSites.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
          labelRowsPerPage="Lignes par page"
          labelDisplayedRows={({ from, to, count }) => `${from}-${to} sur ${count}`}
        />
      </Paper>
      {/* Selected site details shown on the same page */}
      {selectedSite && (
        <Box sx={{ mt: 3 }}>
          <Paper sx={{ p: 3, borderRadius: 2 }}>
            <Typography variant="h6" sx={{ fontWeight: 'bold', mb: 1 }}>
              Détails du site — {selectedSite.nom}
            </Typography>
            <Grid container spacing={2}>
              <Grid item xs={12} md={6}>
                <Typography variant="subtitle2">Description</Typography>
                <Typography sx={{ mb: 1 }}>{selectedSite.description || '-'}</Typography>

                <Typography variant="subtitle2">Type</Typography>
                <Typography sx={{ mb: 1 }}>{selectedSite.type || '-'}</Typography>

                <Typography variant="subtitle2">Localisation</Typography>
                <Typography sx={{ mb: 1 }}>{selectedSite.ville || selectedSite.localisation || '-'}</Typography>

                <Typography variant="subtitle2">Province / Région</Typography>
                <Typography sx={{ mb: 1 }}>{selectedSite.region || selectedSite.province || '-'}</Typography>

                <Typography variant="subtitle2">Tarif d'entrée</Typography>
                <Typography sx={{ mb: 1 }}>{selectedSite.tarifEntree ? `${selectedSite.tarifEntree} FCFA` : '-'}</Typography>

                <Typography variant="subtitle2">Capacité d'accueil</Typography>
                <Typography sx={{ mb: 1 }}>{selectedSite.capaciteAccueil || '-'}</Typography>
              </Grid>

              <Grid item xs={12} md={6}>
                <Typography variant="subtitle2">Heure d'ouverture</Typography>
                <Typography sx={{ mb: 1 }}>{selectedSite.heureOuverture ? new Date(selectedSite.heureOuverture).toLocaleTimeString() : '-'}</Typography>

                <Typography variant="subtitle2">Heure de fermeture</Typography>
                <Typography sx={{ mb: 1 }}>{selectedSite.heureFermeture ? new Date(selectedSite.heureFermeture).toLocaleTimeString() : '-'}</Typography>

                <Typography variant="subtitle2">État de conservation</Typography>
                <Typography sx={{ mb: 1 }}>{selectedSite.etatConservation || '-'}</Typography>

                <Typography variant="subtitle2">Contact responsable</Typography>
                <Typography sx={{ mb: 1 }}>{selectedSite.contactResponsable || '-'}</Typography>

                <Typography variant="subtitle2">Téléphone</Typography>
                <Typography sx={{ mb: 1 }}>{selectedSite.telephoneContact || '-'}</Typography>

                <Typography variant="subtitle2">Moyenne d'avis / Nombre d'avis</Typography>
                <Typography sx={{ mb: 1 }}>{(selectedSite.moyenneAvis || 0)} / {selectedSite.nombreAvis || 0}</Typography>
              </Grid>

              <Grid item xs={12}>
                <Typography variant="subtitle2">Nombre de visites</Typography>
                <Typography sx={{ mb: 1 }}>{selectedSite.visites?.toLocaleString() || selectedSite.nombreVisites || 0}</Typography>

                <Typography variant="subtitle2">Date de création</Typography>
                <Typography sx={{ mb: 1 }}>{selectedSite.dateCreation ? new Date(selectedSite.dateCreation).toLocaleString() : '-'}</Typography>

                <Typography variant="subtitle2">Date de modification</Typography>
                <Typography sx={{ mb: 1 }}>{selectedSite.dateModification ? new Date(selectedSite.dateModification).toLocaleString() : '-'}</Typography>
              </Grid>
            </Grid>
          </Paper>
        </Box>
      )}
    </Box>
  )
}

export default Sites
