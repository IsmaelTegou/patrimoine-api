import ThemeToggle from './ThemeToggle'

// eslint-disable-next-line no-empty-pattern
const Topbar = ({}) => {
  return (
    <>
        <Box sx={{ position: 'absolute', top: 15, right: 21 }}>
        <ThemeToggle />
        </Box>
    </>
    )
}

export default Topbar