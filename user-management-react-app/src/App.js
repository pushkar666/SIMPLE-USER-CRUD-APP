import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './component/Navbar';
import HomePage from './component/HomePage';
import LoginForm from './component/LoginForm';
import AddUser from './component/AddUser';
import UserService from './service/UserService';

/**
 * The main application component.
 * This component handles routing, authentication, and rendering of different components based on the current route.
 *
 * @returns {JSX.Element} - The rendered React component.
 */
const App = () => {
    const [isAuthenticated, setIsAuthenticated] = useState(() => {
        // Initialize authentication state from localStorage
        const token = localStorage.getItem('token');
        return !!token; // Set to true if token exists, false otherwise
    });

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            setIsAuthenticated(true);
        } else {
            setIsAuthenticated(false);
        }
    }, []);

    return (
        <Router>
            <Navbar isAuthenticated={isAuthenticated} setIsAuthenticated={setIsAuthenticated} />
            <Routes>
                {/* Public Routes */}
                <Route path="/" element={<HomePage isAuthenticated={isAuthenticated} />} />
                <Route path="/login" element={<LoginForm setIsAuthenticated={setIsAuthenticated} />} />
                <Route path="/add-user" element={<AddUser />} />

                {/* Protected Route */}
                <Route
                    path="/home"
                    element={
                        isAuthenticated ? (
                            <HomePage isAuthenticated={isAuthenticated} />
                        ) : (
                            <Navigate to="/login" />
                        )
                    }
                />
            </Routes>
        </Router>
    );
};

export default App;
