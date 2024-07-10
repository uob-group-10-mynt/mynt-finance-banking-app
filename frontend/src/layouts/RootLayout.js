import Theme from "../theme";
import {NavLink, Outlet} from "react-router-dom";
import CustomDrawer from "../components/CustomDrawer";
import { useContext } from "react";
import { LoggedInContext } from "../App";
import { useMediaQuery } from "react-responsive";



export default function RootLayout() {
    const [loggedIn, setLoggedIn, logOut] = useContext(LoggedInContext)
    const isTabletOrSmaller = useMediaQuery({ query: '(max-width: 768px)'})
    const isDesktop = useMediaQuery({ query: '(min-width: 769px)'})


    return (
        <div className="App">
            <Theme>Mode</Theme>
            <header className="App-header">
                <h1 className="App-header">
                    MYNT Technology
                </h1>
                { isTabletOrSmaller && <CustomDrawer text="Navigation" testId="navButton">
                    <NavLink to='/'>Home</NavLink>
                    <NavLink to ='signup' data-cy="SignUpLink">Sign Up</NavLink>
                    <NavLink to='remittance' data-cy="RemittanceLink">Transfer</NavLink>
                    {
                        loggedIn ? 
                        <NavLink to={'login'} data-cy="LogOutLink" onClick={() => {
                            logOut();
                        }}>Log Out</NavLink>
                        :
                        <NavLink to={'login'} data-cy="LoginLink">Log In</NavLink>
                    }
                </CustomDrawer>}
                {
                    isDesktop && 
                    <div>
                        <NavLink to='/'>Home</NavLink>
                        <NavLink to ='signup' data-cy="SignUpLink">Sign Up</NavLink>
                        <NavLink to='remittance' data-cy="RemittanceLink">Transfer</NavLink>
                        {
                            loggedIn ? 
                            <NavLink to={'login'} data-cy="LogOutLink" onClick={() => {
                                logOut();
                            }}>Log Out</NavLink>
                            :
                            <NavLink to={'login'} data-cy="LoginLink">Log In</NavLink>
                        }
                    </div>
                }

            </header>
            <main>
                <Outlet/>
            </main>
            <footer>
                <p>&copy; 2024 Mynt. All rights reserved.</p>
            </footer>
        </div>
    );
}