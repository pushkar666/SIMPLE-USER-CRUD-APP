import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, TextField, Button, Snackbar, Alert } from '@mui/material';
import UserService from '../service/UserService';

const LoginForm = ({ setIsAuthenticated }) => {
    const [credentials, setCredentials] = useState({ userName: '', passWord: '' });
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const token = await UserService.login(credentials);
            localStorage.setItem('token', token);
            setIsAuthenticated(true);
            navigate('/home');
        } catch (err) {
            setError('Login failed. Please check your credentials.');
        }
    };

    return (
        <Box sx={{ p: 3, maxWidth: 400, mx: 'auto' }}>
            <form onSubmit={handleSubmit}>
                <TextField
                    label="Username"
                    fullWidth
                    margin="normal"
                    value={credentials.userName}
                    onChange={(e) => setCredentials({ ...credentials, userName: e.target.value })}
                />
                <TextField
                    label="Password"
                    type="password"
                    fullWidth
                    margin="normal"
                    value={credentials.passWord}
                    onChange={(e) => setCredentials({ ...credentials, passWord: e.target.value })}
                />
                <Button type="submit" variant="contained" fullWidth sx={{ mt: 2 }}>
                    Login
                </Button>
            </form>
            {error && (
                <Snackbar open autoHideDuration={6000} onClose={() => setError('')}>
                    <Alert severity="error">{error}</Alert>
                </Snackbar>
            )}
        </Box>
    );
};

export default LoginForm;
