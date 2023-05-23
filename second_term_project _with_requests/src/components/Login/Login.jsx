import "./Login.css"
import React, { useState, useContext } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import UserContext from "../../UserContext";

function Login({setError}) {

    const [login, setLogin] = useState('')
    const [password, setPassword] = useState('')
    const { loginUser } = useContext(UserContext)

    const log = async () => {
        try {
            await axios.get('http://localhost:8082/authorization',
            
            {headers: {
                'Content-Type': 'application/json'
            },
            params: {login, password}
            })
            .then(res => loginUser({login: login, password: password}))
            .catch((error) => {
                if (error.response) {
                    setError(error.response.data.message)
                }
            })
            loginUser({login: login, password: password})
        } catch (err) {
            console.log(err)
        }
        setLogin('')
        setPassword('')
    }

    return(
        <>
            <h3>Авторизация</h3>
            <form className='form' onSubmit={(e) => e.preventDefault()}>
                <div className='form-inputs'>
                    <label htmlFor='login'>Логин</label>
                    <input type='login' id='login' value={login} onChange={(e) => setLogin(e.target.value)}></input>
                    <label htmlFor='password'>Пароль</label>
                    <input type='password' id='password' value={password} onChange={(e) => setPassword(e.target.value)}></input>
                </div>
                <div>
                    <button className='btn btn-h' onClick={log}>Войти</button>
                    <Link to='/registration' className='btn-auth btn-h'>К регистрации</Link>
                </div>
            </form>
        </>
    )
}

export default Login