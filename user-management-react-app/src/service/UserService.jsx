import axios from 'axios';

const API_URL = 'http://localhost:8080/auth/'; // Replace with your actual API base URL

const UserService = {
    /**
     * Fetches users based on query parameters.
     * @returns {Promise} Axios response or error.
     */
    getUsers: async (queryParams = {}) => {
        try {
            const token = localStorage.getItem('token');
            const config = {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            };
            const response = await axios.post(`${API_URL}queryUsers`, queryParams, config);
            return response.data;
        } catch (error) {
            console.error('Error fetching users:', error);
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
    // addNewUser: async (userInfo) => {
    //     try {
    //         const token = localStorage.getItem('token');
    //         const config = {
    //             headers: {
    //                 Authorization: `Bearer ${token}`,
    //             },
    //         };
    //         const response = await axios.post(`${API_URL}addNewUser`, userInfo, config);
    //         return response.data;
    //     } catch (error) {
    //         console.error('Error adding new user:', error);
    //         throw error;
    //     }
    // },
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
