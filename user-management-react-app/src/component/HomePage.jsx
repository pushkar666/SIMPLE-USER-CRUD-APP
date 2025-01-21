import React, { useEffect, useState } from 'react';
// import { useSearchParams } from 'react-router-dom';
import { Table, TableBody, TableCell, TableHead, TableRow, Pagination, Typography, Box } from '@mui/material';
import UserService from '../service/UserService';

/**
 * The HomePage component is responsible for displaying a list of users fetched from the API.
 * It uses React hooks to manage state and fetch data when the component mounts or the authentication status changes.
 *
 * @param {Object} props - The component's props
 * @param {boolean} props.isAuthenticated - Indicates whether the user is authenticated or not
 *
 * @returns {JSX.Element} - The rendered HomePage component
 */
const HomePage = ({ isAuthenticated }) => {
    const [users, setUsers] = useState([]);
    const [totalPages, setTotalPages] = useState(0);
    const [page, setPage] = useState(1);
    const [size] = useState(10); // Default page size (you can adjust this if needed)
    // const [searchParams] = useSearchParams();

    useEffect(() => {
        const fetchUsers = async () => {
            if (isAuthenticated) {
                try {
                    const data = await UserService.getUsers(page - 1, size); // API expects 0-based page index
                    // console.log(data.content);
                    setUsers(data.content);
                    setTotalPages(data.totalPages);
                } catch (err) {
                    console.error("Error fetching users:", err);
                }
            }
        };

        fetchUsers();
    }, [isAuthenticated, page, size]);


    return (
        <Box sx={{ p: 3 }}>
            {isAuthenticated ? (
                <>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>ID</TableCell>
                                <TableCell>Username</TableCell>
                                <TableCell>First Name</TableCell>
                                <TableCell>Last Name</TableCell>
                                <TableCell>Email</TableCell>
                                <TableCell>Roles</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {users.map((user) => (
                                <TableRow key={user.id}>
                                    <TableCell>{user.id}</TableCell>
                                    <TableCell>{user.userName}</TableCell>
                                    <TableCell>{user.firstName}</TableCell>
                                    <TableCell>{user.lastName}</TableCell>
                                    <TableCell>{user.email}</TableCell>
                                    <TableCell>{user.roles}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                    <Pagination
                        count={totalPages}
                        page={page}
                        onChange={(e, newPage) => setPage(newPage)}
                        sx={{ mt: 2 }}
                    />
                </>
            ) : (
                <Typography sx={{ opacity: 0.5, textAlign: 'center', mt: 5 }}>
                    Please Login to see Users
                </Typography>
            )}
        </Box>
    );
};

export default HomePage;