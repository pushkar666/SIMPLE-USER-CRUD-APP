import axios from 'axios';
import { data } from 'react-router-dom';

const API_URL = 'http://localhost:8080/auth/'; // Replace with your actual API base URL

const UserService = {
    /**
     * Fetches users with pagination.
     * @returns {Promise} Axios response or error.
     */
    getUsers: async (page = 0, size = 10) => {
        try {
            const token = localStorage.getItem('token');
            // console.log(token);
            if (!token) {
                throw new Error('Token is missing. Please log in again.');
            }
            
            const config = {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
                params: {
                    page: page,
                    size: size
                },
            };
            // console.log(config);
            const response = await axios.get(`${API_URL}users`, config);
            // console.log(response.data);
            
            return response.data;
        } catch (error) {
            console.error('Error fetching users:', error);
            throw error;
        }
    },

    /**
     * Fetches users based on query parameters.
     * @returns {Promise} Axios response or error.
     */
    // queryUsers: async (queryParams, page = 0, size = 10) => {
    //     try {
    //         const token = localStorage.getItem('token');
    //         const config = {
    //             headers: {
    //                 Authorization: `Bearer ${token}`,
    //             },
    //             params: {
    //                 page: page,
    //                 size: size
    //             },
    //         };
    //         const response = await axios.post(`${API_URL}queryUsers`, queryParams, config);
    //         return response.data;
    //     } catch (error) {
    //         console.error('Error fetching queried users:', error);
    //         throw error;
    //     }
    // }
    queryUsers: async (queryParams, page = 0, size = 10) => {
        try {
            console.log(queryParams);
            const token = localStorage.getItem('token');
            const config = {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            };
            const body = {
                ...queryParams,
                page: page,
                size: size
            };
            const response = await axios.post(`${API_URL}queryUsers`, body, config); // Using POST and sending data in the body
            console.log(response);
            return response.data;
        } catch (error) {
            console.error('Error fetching queried users:', error);
            throw error;
        }
    },

    /**
     * Authenticates the user and fetches a token.
     * @param {Object} credentials - User credentials { userName, passWord }.
     * @returns {Promise} Axios response or error.
     */
    login: async (credentials) => {
        try {
            const response = await axios.post(`${API_URL}generateToken`, credentials);
            return response.data;
        } catch (error) {
            console.error('Error during login:', error);
            throw error;
        }
    },

    /**
     * Adds a new user to the system.
     * @param {Object} userInfo - User information { username, firstName, lastName, email, password }.
     * @returns {Promise} Axios response or error.
     */
    addNewUser: async (userInfo) => {
        try {
            const response = await axios.post(`${API_URL}addNewUser`, userInfo);
            return response.data;
        } catch (error) {
            console.error('Error adding new user:', error);
            throw error;
        }
    },

};

export default UserService;
