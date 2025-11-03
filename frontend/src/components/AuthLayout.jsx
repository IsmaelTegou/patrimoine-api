import { styled } from '@mui/material/styles';
import { Box } from '@mui/material';

export const AuthLayout = styled(Box)(({ theme, image }) => ({
  minHeight: '100vh',
  display: 'flex',
  flexDirection: 'column',
  position: 'relative',
  '&::before': {
    content: '""',
    position: 'absolute',
    top: 0,
    left: 0,
      right: 0,
    height: '50%',
    backgroundImage: `url(${image})`,
    backgroundSize: '100%',
    backgroundPosition: 'center',
    filter: 'brightness(0.7)',
    zIndex: -1,
  },
  '&::after': {
    content: '""',
    position: 'absolute',
    top: '50%',
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: theme.palette.background.default,
    zIndex: -1,
  },
}));