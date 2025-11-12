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
  Menu,
} from '@mui/material'
import AddIcon from '@mui/icons-material/Add'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import FileUploadIcon from '@mui/icons-material/FileUpload'
import CloseIcon from '@mui/icons-material/Close'
import SearchIcon from '@mui/icons-material/Search'
import ClearIcon from '@mui/icons-material/Clear'

// Mock types and data
const reportTypes = ['Tous', 'rapport avis', 'rapport frequentation', 'rapport engagement']

const reportsMock = [
  {
    id: 1,
    type: 'rapport avis',
    titre: 'Avis - Site A',
    dateDebut: '2025-09-01T00:00',
    dateFin: '2025-09-30T23:59',
    donnees: { total: 120, positif: 95, negatif: 25 },
    dateGeneration: '2025-10-01T10:00',
    generePar: 'Ismael Tegou',
  },
  {
    id: 2,
    type: 'rapport frequentation',
    titre: 'Fréquentation - Juillet',
    dateDebut: '2025-07-01T00:00',
    dateFin: '2025-07-31T23:59',
    donnees: { visites: 4520, moyenneParJour: 145 },
    dateGeneration: '2025-08-01T09:30',
    generePar: 'Aoudou Sehou',
  },
]

const initialReport = {
  type: 'rapport avis',
  titre: '',
  dateDebut: '',
  dateFin: '',
  donnees: '', // store as JSON string in form
  dateGeneration: '',
  generePar: '',
}

const Rapports = () => {
  const [reports, setReports] = useState(reportsMock)
  const [page, setPage] = useState(0)
  const [rowsPerPage, setRowsPerPage] = useState(5)
  const [openDialog, setOpenDialog] = useState(false)
  const [isEditing, setIsEditing] = useState(false)
  const [editingId, setEditingId] = useState(null)
  const [current, setCurrent] = useState(initialReport)
  const [searchQuery, setSearchQuery] = useState('')
  const [typeFilter, setTypeFilter] = useState('Tous')

  // For export menu
  const [exportAnchor, setExportAnchor] = useState(null)
  const [exportTarget, setExportTarget] = useState(null)

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setCurrent(prev => ({ ...prev, [name]: value }))
  }

  const openNew = () => { setCurrent(initialReport); setIsEditing(false); setEditingId(null); setOpenDialog(true) }

  const handleSubmit = () => {
    const payload = {
      ...current,
      id: isEditing && editingId != null ? editingId : reports.length + 1,
      // try to parse donnees if it's a JSON string
      donnees: (() => {
        try { return typeof current.donnees === 'string' ? JSON.parse(current.donnees) : current.donnees } catch { return current.donnees }
      })(),
      dateGeneration: current.dateGeneration || new Date().toISOString().slice(0,16),
    }
    if (isEditing && editingId != null) setReports(prev => prev.map(r => r.id === editingId ? { ...r, ...payload } : r))
    else setReports(prev => [...prev, payload])
    setOpenDialog(false)
  }

  const handleEdit = (r) => { setCurrent({ ...r, donnees: typeof r.donnees === 'string' ? r.donnees : JSON.stringify(r.donnees) }); setIsEditing(true); setEditingId(r.id); setOpenDialog(true) }

  const handleDelete = (r) => { setReports(prev => prev.filter(rr => rr.id !== r.id)) }

  const handleSearchChange = (e) => { setSearchQuery(e.target.value); setPage(0) }
  const handleClearSearch = () => { setSearchQuery(''); setPage(0) }
  const handleTypeFilter = (e) => { setTypeFilter(e.target.value); setPage(0) }

  const filtered = reports.filter(r => {
    const q = searchQuery.trim().toLowerCase()
    const typeMatch = typeFilter === 'Tous' || r.type === typeFilter
    const searchMatch = !q || [r.titre, r.generePar, r.type].filter(Boolean).some(f => String(f).toLowerCase().includes(q))
    return typeMatch && searchMatch
  })

  // Export helpers: PDF = download JSON as .pdf (text), Excel = convert JSON to CSV and download as .csv
  const exportReport = (report, format) => {
    if (!report) return
    if (format === 'pdf') {
      const blob = new Blob([JSON.stringify(report.donnees, null, 2)], { type: 'application/json' })
      const url = URL.createObjectURL(blob)
      const a = document.createElement('a')
      a.href = url
      a.download = `${report.titre || 'report'}.pdf`
      a.click()
      URL.revokeObjectURL(url)
    } else if (format === 'excel') {
      // flatten first-level keys to CSV
      const data = Array.isArray(report.donnees) ? report.donnees : [report.donnees]
      const keys = Array.from(new Set(data.flatMap(d => Object.keys(d || {}))))
      const rows = data.map(d => keys.map(k => (d && d[k] != null) ? String(d[k]).replace(/\n/g,' ') : ''))
      const csv = [keys.join(','), ...rows.map(r => r.map(c => `"${c.replace(/"/g,'""')}"`).join(','))].join('\n')
      const blob = new Blob([csv], { type: 'text/csv' })
      const url = URL.createObjectURL(blob)
      const a = document.createElement('a')
      a.href = url
      a.download = `${report.titre || 'report'}.csv`
      a.click()
      URL.revokeObjectURL(url)
    }
    setExportAnchor(null)
    setExportTarget(null)
  }

  const openExportMenu = (event, report) => { setExportAnchor(event.currentTarget); setExportTarget(report) }
  const closeExportMenu = () => { setExportAnchor(null); setExportTarget(null) }

  return (
    <Box>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3, gap: 2 }}>
        <Box sx={{ display: 'flex', gap: 2, alignItems: 'center', flex: 1 }}>
          <TextField
            placeholder="Rechercher un rapport, titre, généré par..."
            value={searchQuery}
            onChange={handleSearchChange}
            size="small"
            sx={(theme) => ({
              flex: 1,
              maxWidth: 520,
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
              minWidth: 220,
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
                },
                '&.Mui-focused fieldset': {
                  borderColor: 'primary.main'
                }
              },
              '& .MuiInputLabel-root': {
                color: theme.palette.text.secondary
              }
            })}>
            <InputLabel>Type de rapport</InputLabel>
            <Select value={typeFilter} label="Type de rapport" onChange={handleTypeFilter}>
              {reportTypes.map(t => <MenuItem key={t} value={t}>{t === 'Tous' ? 'Tous' : t}</MenuItem>)}
            </Select>
          </FormControl>
        </Box>

        <Button variant="contained" startIcon={<AddIcon/>} onClick={openNew} sx={{ bgcolor: '#1A73E8', '&:hover':{bgcolor:'#1557B0'}, borderRadius:2, textTransform:'none' }}>
          Générer un rapport
        </Button>
      </Box>

      {/* Dialog add/edit */}
      <Dialog open={openDialog} onClose={() => setOpenDialog(false)} fullWidth maxWidth="md">
        <DialogTitle>{isEditing ? 'Modifier un rapport' : 'Générer un rapport'}<IconButton onClick={() => setOpenDialog(false)} sx={{ position: 'absolute', right: 8, top: 8 }}><CloseIcon/></IconButton></DialogTitle>
        <DialogContent dividers>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <FormControl fullWidth margin="normal">
                <InputLabel>Type de rapport</InputLabel>
                <Select name="type" value={current.type} label="Type de rapport" onChange={handleInputChange}>
                  {reportTypes.filter(t => t !== 'Tous').map(t => <MenuItem key={t} value={t}>{t}</MenuItem>)}
                </Select>
              </FormControl>
            </Grid>

            <Grid item xs={12} sm={6}><TextField fullWidth label="Titre" name="titre" value={current.titre} onChange={handleInputChange} margin="normal" /></Grid>

            <Grid item xs={12} sm={6}><TextField fullWidth label="Date début" name="dateDebut" type="datetime-local" value={current.dateDebut} onChange={handleInputChange} InputLabelProps={{ shrink: true }} margin="normal" /></Grid>
            <Grid item xs={12} sm={6}><TextField fullWidth label="Date fin" name="dateFin" type="datetime-local" value={current.dateFin} onChange={handleInputChange} InputLabelProps={{ shrink: true }} margin="normal" /></Grid>

            <Grid item xs={12}>
              <TextField fullWidth label="Données (JSON)" name="donnees" value={current.donnees} onChange={handleInputChange} multiline rows={6} margin="normal" helperText="Entrez un JSON valide ou laissez vide pour les données simulées" />
            </Grid>

            <Grid item xs={12} sm={6}><TextField fullWidth label="Date génération" name="dateGeneration" type="datetime-local" value={current.dateGeneration} onChange={handleInputChange} InputLabelProps={{ shrink: true }} margin="normal" /></Grid>
            <Grid item xs={12} sm={6}><TextField fullWidth label="Généré par" name="generePar" value={current.generePar} onChange={handleInputChange} margin="normal" /></Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenDialog(false)}>Annuler</Button>
          <Button variant="contained" onClick={handleSubmit}>Enregistrer</Button>
        </DialogActions>
      </Dialog>

      {/* Export menu */}
      <Menu anchorEl={exportAnchor} open={Boolean(exportAnchor)} onClose={closeExportMenu}>
        <MenuItem onClick={() => exportReport(exportTarget, 'pdf')}>Exporter en PDF</MenuItem>
        <MenuItem onClick={() => exportReport(exportTarget, 'excel')}>Exporter en Excel</MenuItem>
      </Menu>

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
                <TableCell>Type</TableCell>
                <TableCell>Titre</TableCell>
                <TableCell>Date début</TableCell>
                <TableCell>Date fin</TableCell>
                <TableCell>Date génération</TableCell>
                <TableCell>Généré par</TableCell>
                <TableCell>Données (aperçu)</TableCell>
                <TableCell align="right">Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filtered.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map(r => (
                <TableRow key={r.id} hover>
                  <TableCell>{r.type}</TableCell>
                  <TableCell>{r.titre}</TableCell>
                  <TableCell>{r.dateDebut ? new Date(r.dateDebut).toLocaleString() : '-'}</TableCell>
                  <TableCell>{r.dateFin ? new Date(r.dateFin).toLocaleString() : '-'}</TableCell>
                  <TableCell>{r.dateGeneration ? new Date(r.dateGeneration).toLocaleString() : '-'}</TableCell>
                  <TableCell>{r.generePar}</TableCell>
                  <TableCell sx={{ maxWidth: 240, overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{typeof r.donnees === 'string' ? r.donnees : JSON.stringify(r.donnees)}</TableCell>
                  <TableCell align="right">
                    <IconButton size="small" sx={{ color: '#1A73E8' }} onClick={() => handleEdit(r)}><EditIcon/></IconButton>
                    <IconButton size="small" sx={{ color: '#F44335' }} onClick={() => handleDelete(r)}><DeleteIcon/></IconButton>
                    <IconButton size="small" sx={{ color: '#444' }} onClick={(e) => openExportMenu(e, r)}><FileUploadIcon/></IconButton>
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

export default Rapports
