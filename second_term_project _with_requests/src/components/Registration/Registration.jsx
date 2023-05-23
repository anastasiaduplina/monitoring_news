import "./Registration.css"
import React, { useState } from "react";
import { Link } from "react-router-dom";
import axios from 'axios'

function Registration({setError}) {

    const [login, setLogin] = useState('')
    const [password, setPassword] = useState('')

    const registration = async () => {
        try {
            await axios.get('http://localhost:8082/addUser',
            {headers: {
                'Content-Type': 'application/json'
            },
            params: {login, password}
        }
            )
            .then(res => alert('Регистрация прошла успешно!'))
            .catch((error) => {
                if (error.response) {
                    setError(error.response.data.message)
                }
            })
        } catch (err) {
            console.log(err)
        }
        setLogin('')
        setPassword('')
    }
    return(
        <div className='registration'>
            <h3>Регистрация</h3>
            <form className='form' onSubmit={(e) => e.preventDefault()}>
                <div className='form-inputs'>
                    <label htmlFor='login'>Логин</label>
                    <input type='login' id='login' value={login} onChange={(e) => setLogin(e.target.value)}></input>
                    <label htmlFor='password'>Пароль</label>
                    <input type='password' id='password' value={password} onChange={(e) => setPassword(e.target.value)}></input>
                </div>
                <div>
                    <button className='btn btn-h' onClick={registration} >Зарегистрироваться</button>
                    <Link to='/login' className='btn-auth btn-h'>Перейти к авторизации</Link>
                </div>
            </form>
        </div>
    )
}

export default Registration