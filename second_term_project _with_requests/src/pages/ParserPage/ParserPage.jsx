import React, { useState, useContext, useCallback, useEffect } from 'react';
import axios from 'axios';
import UserContext from '../../UserContext';


function ParserPage() {
    const { user } = useContext(UserContext)
    const [keyword, setKeyword] = useState('')
    const login = user.login
    let [keys, setKeys] = useState('')

    const addKey = async () => {
        try {
            await axios.get('http://localhost:8082/addKeyword',
            {headers: {
                'Content-Type': 'application/json'
            },
            params: {keyword, login}
        }
            )
            .then(res => alert('Ключ отправлен!'))
            .catch((error) => {
                if (error.response) {
                    setError(error.response.data.message)
                }
            })
        } catch (err) {
            console.log(err)
        }
        setKeyword('')
    }

    const getKeys = async () => {
        try {
            await axios.get('http://localhost:8082/getAllKeywords',
            {headers: {
                'Content-Type': 'application/json'
            },
            params: {login}
        }
            )
            .then(res => setKeys(decodeURIComponent(res.data)))
            .catch((error) => {
                if (error.response) {
                    setError(error.response.data.message)
                }
            })
        } catch (err) {
            console.log(err)
        }
        setKeyword('')
    }

    const [error, setError] = useState('')

    useEffect(() => {
        if (error) {
            alert(error)
            setError()
        }
    }, [error])


    return (
        <main className='body'>
            <form className='search-form' onSubmit={(e) => e.preventDefault()}>
                <label htmlFor='tema' className='search-title'>Тема</label>
                <div className="search-input-wrapper">
                    <input className='input-search' type='text' id='tema' value={keyword} onChange={(e) => setKeyword(e.target.value)}></input>
                    <button className='btn btn-h' onClick={addKey} >Добавить</button>
                    <button className='btn btn-h' onClick={getKeys} >Все ключи</button>
                </div>
            </form>
            <div className="list-elements">
                {keys}
            </div>
        </main>
    );
}


export default ParserPage