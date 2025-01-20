import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AppBar, Toolbar, IconButton, Drawer, Button, InputBase, Typography, Box } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import SearchIcon from '@mui/icons-material/Search';
// import UserService from '../service/UserService';

/**
 * A functional component that renders a navigation bar with a drawer menu, search functionality, and authentication controls.
 *
 * @param {Object} props - The component's props.
 * @param {boolean} props.isAuthenticated - Indicates whether the user is authenticated.
 * @param {Function} props.setIsAuthenticated - A function to set the authentication state.
 *
 * @returns {JSX.Element} - The rendered navigation bar component.
 */
const Navbar = ({ isAuthenticated, setIsAuthenticated }) => {
    const [drawerOpen, setDrawerOpen] = useState(false);
    const [searchQuery, setSearchQuery] = useState('');
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem('token');
        setIsAuthenticated(false);
        navigate('/');
    };

    const handleSearch = (e) => {
        if (e.key === 'Enter' && isAuthenticated) {
            navigate(`/home?search=${searchQuery}`);
        }
    };

    return (
        <AppBar position="static" sx={{ bgcolor: 'silver' }}>
            <Toolbar>
                <IconButton edge="start" color="inherit" onClick={() => setDrawerOpen(true)}>
                    <MenuIcon />
                </IconButton>
                <Drawer anchor="left" open={drawerOpen} onClose={() => setDrawerOpen(false)} sx={{ width: 250 }}>
                    <Box sx={{ width: 250, p: 2 }}>
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={() => navigate('/add-user')}
                            fullWidth
                            sx={{ mb: 2 }}
                        >
                            ADD USERS
                        </Button>
                        {isAuthenticated ? (
                            <Button variant="contained" color="error" onClick={handleLogout} fullWidth>
                                LOGOUT
                            </Button>
                        ) : (
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={() => navigate('/login')}
                                fullWidth
                            >
                                LOGIN
                            </Button>
                        )}
                    </Box>
                </Drawer>
                <Typography variant="h6" sx={{ flexGrow: 1, ml: 2 }}>
                    NAVBAR
                </Typography>
                {isAuthenticated && (
                    <Box sx={{ display: 'flex', alignItems: 'center', bgcolor: 'white', p: 0.5, borderRadius: 1 }}>
                        <SearchIcon />
                        <InputBase
                            placeholder="SEARCH FOR USERS"
                            value={searchQuery}
                            onChange={(e) => setSearchQuery(e.target.value)}
                            onKeyDown={handleSearch}
                        />
                    </Box>
                )}
            </Toolbar>
        </AppBar>
    );
};

export default Navbar;
