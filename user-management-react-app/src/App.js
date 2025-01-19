import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './component/Navbar';
import HomePage from './component/HomePage';
import LoginForm from './component/LoginForm';
import AddUser from './component/AddUser';
import UserService from './service/UserService';

const App = () => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        // Check if token exists in localStorage to determine if the user is authenticated
        const token = localStorage.getItem('token');
        if (token) {
            setIsAuthenticated(true);
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
                {isAuthenticated && <Route path="/home" element={<HomePage isAuthenticated={isAuthenticated} />} />}
            </Routes>
        </Router>
    );
};

export default App;
