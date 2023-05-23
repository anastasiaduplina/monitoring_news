import React from "react"
import { Routes, Route, Navigate } from "react-router-dom"
import ParserPage from "./pages/ParserPage/ParserPage"
import AuthPage from "./pages/AuthPage/AuthPage"

export const RoutesHook = (isLogin) => {

    if (isLogin) { 
        return(
            <Routes>
                <Route path="/" element={<ParserPage />} />
                <Route path="/login" element={<Navigate to="/" replace />} />
            </Routes>
        )
    }

    return(
        <Routes>
            <Route path="/*" element={<AuthPage />} />
            <Route path="/" element={<Navigate to="/login" replace />} />
        </Routes>
    )
}
