import React, {useEffect, useState} from 'react';
import './AuthPage.css';
import { Routes, Route } from "react-router-dom";
import Login from '../../components/Login/Login';
import Registration from '../../components/Registration/Registration';


function AuthPage() {
    const [error, setError] = useState('')

    useEffect(() => {
        if (error) {
            alert(error)
            setError()
        }
    }, [error])
    
    return (
    <div className='login'>
            <div className='container'>
                <div className='auth-page'>
                <Routes>
                    <Route path="/registration" element={<Registration setError={setError}/>} />
                    <Route path='/login' element={<Login setError={setError}/>} />
                </Routes>
                </div>
            </div>
        
    </div>
    );
}

export default AuthPage;