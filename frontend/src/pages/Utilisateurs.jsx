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
  Switch,
  InputAdornment,
} from '@mui/material'
import AddIcon from '@mui/icons-material/Add'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import PersonIcon from '@mui/icons-material/Person'
import CloseIcon from '@mui/icons-material/Close'
import SearchIcon from '@mui/icons-material/Search'
import ClearIcon from '@mui/icons-material/Clear'

const usersMock = [
  { id: 1, nom: 'Tegou', prenom: 'Ismael', email: 'ismael@exemple.com', telephone: '690000001', role: 'Admin', actif: true, password: '******', dateCreation: new Date(), dateModification: new Date(), langue: 'fr' },
  { id: 2, nom: 'AOUDOU', prenom: 'SEHOU', email: 'aoudou@exemple.com', telephone: '690000002', role: 'Admin', actif: true, password: '******', dateCreation: new Date(), dateModification: new Date(), langue: 'en' },
  { id: 3, nom: 'ALIOUM', prenom: 'BOUBA', email: 'alioum@exemple.com', telephone: '690000003', role: 'Admin', actif: true, password: '******', dateCreation: new Date(), dateModification: new Date(), langue: 'fr' },
  { id: 4, nom: 'HAMZA', prenom: 'SEHOU', email: 'hamza@exemple.com', telephone: '690000004', role: 'Gestionnaire', actif: false, password: '******', dateCreation: new Date(), dateModification: new Date(), langue: 'en' },
  { id: 5, nom: 'FADIL', prenom: 'SEHOU', email: 'fadil@exemple.com', telephone: '690000005', role: 'Utilisateur', actif: true, password: '******', dateCreation: new Date(), dateModification: new Date(), langue: 'fr' },
  { id: 6, nom: 'AISSATOU', prenom: 'NAWISSA', email: 'aissatou@exemple.com', telephone: '690000006', role: 'Utilisateur', actif: false, password: '******', dateCreation: new Date(), dateModification: new Date(), langue: 'en' },
]

const initialUserState = {
  nom: '',
  prenom: '',
  email: '',
  telephone: '',
  role: '',
  actif: true,
  password: '',
  dateCreation: new Date(),
  dateModification: new Date(),
  langue: 'fr',
}

const Utilisateurs = () => {
  const [page, setPage] = useState(0)
  const [rowsPerPage, setRowsPerPage] = useState(5)
  const [openDialog, setOpenDialog] = useState(false)
  const [newUser, setNewUser] = useState(initialUserState)
  const [users, setUsers] = useState(usersMock)
  const [isEditing, setIsEditing] = useState(false)
  const [editingId, setEditingId] = useState(null)
  const [confirmDeleteOpen, setConfirmDeleteOpen] = useState(false)
  const [userToDelete, setUserToDelete] = useState(null)
  const [searchQuery, setSearchQuery] = useState('')
  const [roleFilter, setRoleFilter] = useState('all')

  const handleInputChange = (event) => {
    const { name, value, type, checked } = event.target
    setNewUser(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }))
  }

  const handleSubmit = () => {
    const userToAdd = {
      ...newUser,
      id: isEditing && editingId != null ? editingId : users.length + 1,
      dateModification: new Date(),
      dateCreation: isEditing && newUser.dateCreation ? newUser.dateCreation : new Date(),
    }
    if (isEditing && editingId != null) {
      setUsers(prev => prev.map(u => (u.id === editingId ? { ...u, ...userToAdd } : u)))
    } else {
      setUsers(prev => [...prev, userToAdd])
    }
    setIsEditing(false)
    setEditingId(null)
    handleCloseDialog()
  }

  const handleEdit = (user) => {
    setNewUser({ ...user })
    setIsEditing(true)
    setEditingId(user.id)
    setOpenDialog(true)
  }

  const handleDeleteClick = (user) => {
    setUserToDelete(user)
    setConfirmDeleteOpen(true)
  }

  const handleConfirmDelete = () => {
    if (userToDelete) {
      setUsers(prev => prev.filter(u => u.id !== userToDelete.id))
    }
    setUserToDelete(null)
    setConfirmDeleteOpen(false)
  }

  const handleCancelDelete = () => {
    setUserToDelete(null)
    setConfirmDeleteOpen(false)
  }

  const handleCloseDialog = () => {
    setNewUser(initialUserState)
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

  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value)
    setPage(0)
  }

  const handleRoleChange = (e) => {
    setRoleFilter(e.target.value)
    setPage(0)
  }

  const handleClearSearch = () => {
    setSearchQuery('')
    setPage(0)
  }

  const filteredUsers = users.filter((u) => {
    const q = searchQuery.trim().toLowerCase()
    const roleMatch = roleFilter === 'all' || u.role === roleFilter
    const searchMatch = !q || [
      u.nom,
      u.prenom,
      u.email,
      u.telephone,
      u.role,
      u.langue,
    ]
      .filter(Boolean)
      .some((field) => String(field).toLowerCase().includes(q))
    return roleMatch && searchMatch
  })

  return (
    <Box>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3, gap: 2 }}>
        <Box sx={{ display: 'flex', gap: 2, alignItems: 'center', flex: 1 }}>
          <TextField
            placeholder="Rechercher un utilisateur, email, rôle..."
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
                <InputAdornment position="start">
                  <SearchIcon />
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

          <FormControl size="small" sx={(theme) => ({
              minWidth: 160,
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
            <InputLabel>Rôle</InputLabel>
            <Select
              value={roleFilter}
              label="Rôle"
              onChange={handleRoleChange}
            >
              <MenuItem value="all">Tous</MenuItem>
              <MenuItem value="Admin">Admin</MenuItem>
              <MenuItem value="Gestionnaire">Gestionnaire</MenuItem>
              <MenuItem value="Utilisateur">Utilisateur</MenuItem>
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
          Ajouter un utilisateur
        </Button>
      </Box>

      {/* Add/Edit User Dialog */}
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
          {isEditing ? 'Modifier un utilisateur' : 'Ajouter un utilisateur'}
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
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Nom"
                name="nom"
                value={newUser.nom}
                onChange={handleInputChange}
                required
                margin="normal"
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Prénom"
                name="prenom"
                value={newUser.prenom}
                onChange={handleInputChange}
                required
                margin="normal"
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Email"
                name="email"
                value={newUser.email}
                onChange={handleInputChange}
                required
                margin="normal"
                type="email"
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Téléphone"
                name="telephone"
                value={newUser.telephone}
                onChange={handleInputChange}
                required
                margin="normal"
                type="tel"
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <FormControl fullWidth margin="normal">
                <InputLabel>Rôle</InputLabel>
                <Select
                  name="role"
                  value={newUser.role}
                  onChange={handleInputChange}
                  label="Rôle"
                  required
                >
                  <MenuItem value="Admin">Admin</MenuItem>
                  <MenuItem value="Utilisateur">Utilisateur</MenuItem>
                  <MenuItem value="Gestionnaire">Gestionnaire</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid item xs={12} sm={6}>
              <FormControl fullWidth margin="normal">
                <InputLabel>Langue</InputLabel>
                <Select
                  name="langue"
                  value={newUser.langue}
                  onChange={handleInputChange}
                  label="Langue"
                  required
                >
                  <MenuItem value="fr">Français</MenuItem>
                  <MenuItem value="en">Anglais</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Mot de passe"
                name="password"
                value={newUser.password}
                onChange={handleInputChange}
                required
                margin="normal"
                type="password"
              />
            </Grid>
            <Grid item xs={12} sm={6} sx={{ display: 'flex', alignItems: 'center', mt: 2 }}>
              <Typography sx={{ mr: 2 }}>Actif</Typography>
              <Switch
                checked={!!newUser.actif}
                onChange={handleInputChange}
                name="actif"
                color="primary"
              />
            </Grid>
          </Grid>
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
            Êtes-vous sûr de vouloir supprimer l'utilisateur "{userToDelete ? userToDelete.nom + ' ' + userToDelete.prenom : ''}" ?
          </Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCancelDelete}>Annuler</Button>
          <Button color="error" variant="contained" onClick={handleConfirmDelete}>Supprimer</Button>
        </DialogActions>
      </Dialog>

      {/* Users table */}
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
                <TableCell>Nom & Prénom</TableCell>
                <TableCell>Email</TableCell>
                <TableCell>Téléphone</TableCell>
                <TableCell>Rôle</TableCell>
                <TableCell>Actif</TableCell>
                <TableCell>Langue</TableCell>
                <TableCell>Date création</TableCell>
                <TableCell>Date modification</TableCell>
                <TableCell align="right">Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filteredUsers
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((user) => (
                  <TableRow key={user.id} hover>
                    <TableCell>
                      <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                        <PersonIcon sx={{ color: '#1A73E8' }} />
                        {user.nom} {user.prenom}
                      </Box>
                    </TableCell>
                    <TableCell>{user.email}</TableCell>
                    <TableCell>{user.telephone}</TableCell>
                    <TableCell>{user.role}</TableCell>
                    <TableCell>{user.actif ? 'Oui' : 'Non'}</TableCell>
                    <TableCell>{user.langue === 'fr' ? 'Français' : 'Anglais'}</TableCell>
                    <TableCell>{user.dateCreation ? new Date(user.dateCreation).toLocaleDateString() : '-'}</TableCell>
                    <TableCell>{user.dateModification ? new Date(user.dateModification).toLocaleDateString() : '-'}</TableCell>
                    <TableCell align="right">
                      <IconButton size="small" sx={{ color: '#1A73E8' }} onClick={() => handleEdit(user)}>
                        <EditIcon />
                      </IconButton>
                      <IconButton size="small" sx={{ color: '#F44335' }} onClick={() => handleDeleteClick(user)}>
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
          count={filteredUsers.length}
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

export default Utilisateurs
