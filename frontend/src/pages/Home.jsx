import React, { useState } from 'react';
import { Box, Grid, Typography, AppBar, Toolbar, InputBase, Paper, IconButton, Dialog } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import ChatBubbleOutlineIcon from '@mui/icons-material/ChatBubbleOutline';
import ShareOutlinedIcon from '@mui/icons-material/ShareOutlined';
import Header from '../components/Header';

// Liste des images à afficher
const images = [
  { src: '/assets/images/fond.jpg', title: 'Site du Lac Bleu' },
  { src: '/assets/images/fond1.jpg', title: 'Parc National' },
  { src: '/assets/images/fond2.jpg', title: 'Montagnes Mystiques' },
  { src: '/assets/images/fondauth.jpg', title: 'Village Authentique' },
  { src: '/assets/images/fondauth1.jpg', title: 'Plage Dorée' },
  { src: '/assets/images/fondauth2.jpg.png', title: 'Forêt Enchantée' },
  { src: '/assets/images/Image_fx79-1024x559.png', title: 'Cascade Secrète' },
  { src: '/assets/images/logo.jpg', title: 'Logo du Patrimoine' },
];

const Home = () => {
  const [liked, setLiked] = useState(Array(images.length).fill(false));
  const [open, setOpen] = useState(false);
  const [selectedImg, setSelectedImg] = useState(null);

  const handleLike = (idx) => {
    setLiked((prev) => {
      const arr = [...prev];
      arr[idx] = !arr[idx];
      return arr;
    });
  };

  const handleOpen = (img) => {
    setSelectedImg(img);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setSelectedImg(null);
  };

  return (
    <Box sx={{ display: 'flex', minHeight: '100vh', background: '#f5f6fa' }}>
      <Box sx={{ flex: 1 }}>
        {/* Barre de recherche */}
        <AppBar position="static" color="default" elevation={0} sx={{ mb: 2 }}>
          <Toolbar>
            <Typography variant="h6" sx={{ flex: 1, fontWeight: 700 }}>
              Découvrir les sites touristiques
            </Typography>
            <Paper component="form" sx={{ p: '2px 4px', display: 'flex', alignItems: 'center', width: 300 }}>
              <InputBase sx={{ ml: 1, flex: 1 }} placeholder="Rechercher un site..." inputProps={{ 'aria-label': 'search sites' }} />
              <SearchIcon sx={{ color: '#1976d2' }} />
            </Paper>
          </Toolbar>
        </AppBar>
        {/* Grille d'images façon Facebook */}
        <Grid container spacing={3} sx={{ p: 3, justifyContent: 'center' }}>
          {images.map((img, idx) => (
            <Grid item xs={12} sx={{ display: 'flex', justifyContent: 'center' }} key={img.src}>
              <Paper elevation={4} sx={{ width: 400, height: 400, borderRadius: 4, overflow: 'hidden', position: 'relative', mb: 2, display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'flex-start' }}>
                {/* Titre de la publication */}
                <Box sx={{ p: 2, bgcolor: '#fff', borderBottom: '1px solid #eee', width: '100%' }}>
                  <Typography variant="h6" sx={{ fontWeight: 600, color: '#1976d2', textAlign: 'center' }}>
                    {img.title}
                  </Typography>
                </Box>
                <Box sx={{ width: '100%', flex: 1, display: 'flex', alignItems: 'center', justifyContent: 'center', cursor: 'pointer' }} onClick={() => handleOpen(img)}>
                  <img
                    src={img.src}
                    alt={img.title}
                    style={{ maxWidth: '90%', maxHeight: '70%', objectFit: 'cover', margin: '0 auto', borderRadius: 8 }}
                  />
                </Box>
                {/* Barre d'actions publication */}
                <Box sx={{ display: 'flex', justifyContent: 'space-around', alignItems: 'center', py: 1.5, bgcolor: '#fafafa', borderTop: '1px solid #eee', width: '100%' }}>
                  <IconButton aria-label="J'aime" size="small" color="primary" onClick={() => handleLike(idx)}>
                    <FavoriteBorderIcon sx={{ mr: 1, color: liked[idx] ? 'red' : undefined, fill: liked[idx] ? 'red' : undefined }} />
                    <Typography variant="body2" sx={{ color: liked[idx] ? 'red' : undefined }}>J'aime</Typography>
                  </IconButton>
                  <IconButton aria-label="Commenter" size="small" color="primary">
                    <ChatBubbleOutlineIcon sx={{ mr: 1 }} />
                    <Typography variant="body2">Commenter</Typography>
                  </IconButton>
                  <IconButton aria-label="Partager" size="small" color="primary">
                    <ShareOutlinedIcon sx={{ mr: 1 }} />
                    <Typography variant="body2">Partager</Typography>
                  </IconButton>
                </Box>
              </Paper>
            </Grid>
          ))}
        </Grid>
        {/* Dialog plein écran pour l'image */}
        <Dialog open={open} onClose={handleClose} maxWidth="md" fullWidth>
          {selectedImg && (
            <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', bgcolor: '#222', p: 2 }}>
              <Typography variant="h5" sx={{ color: '#fff', mb: 2, textAlign: 'center' }}>{selectedImg.title}</Typography>
              <img src={selectedImg.src} alt={selectedImg.title} style={{ maxWidth: '100%', maxHeight: '80vh', borderRadius: 12 }} />
            </Box>
          )}
        </Dialog>
      </Box>
    </Box>
  );
};

export default Home;
