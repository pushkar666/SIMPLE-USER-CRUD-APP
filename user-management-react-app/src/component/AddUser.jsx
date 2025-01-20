import React, { useState } from 'react';
import { Box, TextField, Button, MenuItem, Select, InputLabel, FormControl, Snackbar, Alert } from '@mui/material';
// import { useNavigate } from 'react-router-dom';
import UserService from '../service/UserService';

/**
 * A functional component for adding a new user to the system.
 * It includes form fields for user details and a role selection dropdown.
 * The form data is handled by the useState hook and submitted to the UserService.
 * Displays a Snackbar with success or error message based on the user creation outcome.
 *
 * @returns {JSX.Element} - The JSX representation of the AddUser component.
 */
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
    // const navigate = useNavigate();

    /**
     * Handles form input changes and updates the userData state.
     *
     * @param {React.ChangeEvent<HTMLInputElement>} e - The event object.
     */
    const handleChange = (e) => {
        setUserData({
            ...userData,
            [e.target.name]: e.target.value,
        });
    };

    /**
     * Handles form submission by calling the UserService to add a new user.
     * Displays success or error message based on the outcome.
     *
     * @param {React.FormEvent<HTMLFormElement>} e - The event object.
     */
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await UserService.addNewUser(userData);
            setError('User created successfully!'); // Set success message for Snackbar
            // navigate('/home'); // Redirect to HomePage after successful user addition
        } catch (err) {
            setError('User creation failed. Please try again.');
        }
    };

    return (
        <Box sx={{ p: 3, maxWidth: 800, mx: 'auto' }}> {/* Increased max width for better looks */}
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
                <Button
                    type="submit"
                    variant="contained"
                    fullWidth
                    sx={{ mt: 2 }}
                    // Set button text dynamically based on selected role
                    children={userData.roles === 'ROLE_USER' ? 'Add User' : 'Add Admin'}
                />
            </form>
            {error && (
                <Snackbar
                    open={!!error} // Ensures Snackbar closes on successful submission
                    autoHideDuration={6000}
                    onClose={() => setError('')}
                >
                    <Alert severity={error ? 'error' : 'success'}>{error}</Alert>
                </Snackbar>
            )}
        </Box>
    );
};

export default AddUser;