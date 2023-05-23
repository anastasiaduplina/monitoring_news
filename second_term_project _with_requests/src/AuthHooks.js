import React, { useCallback, useEffect, useState } from "react"


export const UseAuth = () => {

    const [user, setUser] = useState()

    const loginUser = useCallback((user) => {
        setUser(user)
        localStorage.setItem('user', JSON.stringify(user))
    }, [])

    const logout = useCallback(() => {
        setUser()
        localStorage.removeItem('user')
    }, [])

    useEffect(() => {
        const data = JSON.parse(localStorage.getItem('user'))
        if (data) {
            loginUser(data)
        }
    }, [loginUser])

    return {user, loginUser, logout}
}