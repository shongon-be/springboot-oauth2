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

    const getUserName = () => {
        if (user.name && user.name !== null) {
            return user.name;
        }
        return user.login || 'N/A';
    };

    const getUserEmail = () => {
        if (user.email && user.email !== null) {
            return user.email;
        }
        return 'Email not available (GitHub private setting)';
    };

    const getUserPicture = () => {
        return user.picture || user.avatar_url;
    };


    return (
        <div>
        <h2>Dashboard</h2>
        {user ? (
            <div>
                <p>
                    <strong>Name: </strong> {getUserName()}{" "}
                </p>
                <p>
                    <strong>Email: </strong> {getUserEmail()}{" "}
                </p>
                {getUserPicture() && <img src={getUserPicture()}
                    alt="User Profile"
                    referrerPolicy="no-referrer"
                    style={{width: '100px', height: '100px', borderRadius: '50%'}}
                />}
            </div>
        ) : (
            <p>Loading user data.....</p>
        )}
        </div>
    );
};

export default Dashboard;
