import React, { useState } from 'react';
import { Box, TextField, Button, MenuItem, Select, InputLabel, FormControl, Snackbar, Alert } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import UserService from '../service/UserService';

const AddUser = () => {
    const [userData, setUserData] = useState({
        userName: '',
        firstName: '',
        lastName: '',
        email: '',
        passWord: '',
        roles: 'ROLE_USER', // Default to 'ROLE_USER'
    });
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        setUserData({
            ...userData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await UserService.addNewUser(userData);
            navigate('/home'); // Redirect to HomePage after successful user addition
        } catch (err) {
            setError('User creation failed. Please try again.');
        }
    };

    return (
        <Box sx={{ p: 3, maxWidth: 600, mx: 'auto' }}>
            <form onSubmit={handleSubmit}>
                <TextField
                    label="Username"
                    name="userName"
                    fullWidth
                    margin="normal"
                    value={userData.userName}
                    onChange={handleChange}
                />
                <TextField
                    label="First Name"
                    name="firstName"
                    fullWidth
                    margin="normal"
                    value={userData.firstName}
                    onChange={handleChange}
                />
                <TextField
                    label="Last Name"
                    name="lastName"
                    fullWidth
                    margin="normal"
                    value={userData.lastName}
                    onChange={handleChange}
                />
                <TextField
                    label="Email"
                    name="email"
                    fullWidth
                    margin="normal"
                    value={userData.email}
                    onChange={handleChange}
                />
                <TextField
                    label="Password"
                    name="passWord"
                    type="password"
                    fullWidth
                    margin="normal"
                    value={userData.passWord}
                    onChange={handleChange}
                />
                <FormControl fullWidth margin="normal">
                    <InputLabel>Role</InputLabel>
                    <Select
                        name="roles"
                        value={userData.roles}
                        onChange={handleChange}
                    >
                        <MenuItem value="ROLE_USER">User</MenuItem>
                        <MenuItem value="ROLE_ADMIN">Admin</MenuItem>
                    </Select>
                </FormControl>
                <Button type="submit" variant="contained" fullWidth sx={{ mt: 2 }}>
                    Add User
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

export default AddUser;
