import './App.css';
import Navbar from './components/Navbar/Navbar';
import {BrowserRouter} from 'react-router-dom'
import UserContext from './UserContext';
import { RoutesHook } from './RoutsHooks';
import { UseAuth } from './AuthHooks'


function App() {
  
  const {user, loginUser, logout} = UseAuth()
  const isLogin = !!user
  const routes = RoutesHook(isLogin)


  return(
    <UserContext.Provider value={{user, isLogin, loginUser, logout}}>
    <div className='app'>
      <BrowserRouter>
        <Navbar />
        { routes }
      </BrowserRouter>
    </div>
    </UserContext.Provider>
  )
}

export default App
