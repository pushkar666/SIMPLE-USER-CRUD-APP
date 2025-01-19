import React, { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { Table, TableBody, TableCell, TableHead, TableRow, Pagination, Typography, Box } from '@mui/material';
import UserService from '../service/UserService';

const HomePage = ({ isAuthenticated }) => {
    const [users, setUsers] = useState([]);
    const [totalPages, setTotalPages] = useState(0);
    const [page, setPage] = useState(1);
    const [searchParams] = useSearchParams();

    useEffect(() => {
        if (isAuthenticated) {
            const queryParams = Object.fromEntries([...searchParams]);
            UserService.getUsers({ ...queryParams, page: page - 1 })
                .then((data) => {
                    setUsers(data.content);
                    setTotalPages(data.totalPages);
                })
                .catch((err) => console.error(err));
        }
    }, [isAuthenticated, page, searchParams]);

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
                                    <TableCell>{user.roles.join(', ')}</TableCell>
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
