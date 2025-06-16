import React, { useEffect, useState } from "react";
import axios from "axios";

const Dashboard = () => {
    const [user, setUser] = useState(null);

    useEffect(() => {
    axios.get('http://localhost:8080/user-info', { withCredentials: true })
        .then((response) => {
            console.log('User data:', response.data); // Debug
            setUser(response.data);
        })
        .catch(error => {
            console.error('Error:', error.response?.status, error.response?.data);
            if (error.response?.status === 401) {
                window.location.href = '/';
            }
        })
}, []);
    return (
        <div>
        <h2>Dashboard</h2>
        {user ? (
            <div>
                <p>
                    <strong>Name: </strong> {user.name}{" "}
                </p>
                <p>
                    <strong>Email: </strong> {user.email}{" "}
                </p>
                {user.picture && <img src={user.picture}
                    alt="User Profile"
                    referrerPolicy="no-referrer"
                />}
            </div>
        ) : (
            <p>Loading user data.....</p>
        )}
        </div>
    );
    };

export default Dashboard;
