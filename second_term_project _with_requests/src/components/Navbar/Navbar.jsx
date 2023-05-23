import React, { useContext } from 'react';
import './Navbar.css';
import UserContext from '../../UserContext';

function Navbar() {

    const { logout, isLogin } = useContext(UserContext)

    return (
    
    <div className='navbar'>
                { isLogin ?
                    <a href='/login' className='btn btn-h' onClick={logout}>Выйти</a> :
                    ''
                }
    </div>
    );
}

export default Navbar;