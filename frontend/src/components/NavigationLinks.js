import { NavLink } from "react-router-dom";
import { useContext } from "react";
import { LoggedInContext } from "../App";

export default function NavigationLinks() {
    const [loggedIn, setLoggedIn, logOut] = useContext(LoggedInContext)
    return (
        <z>
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
        </z>
    )
}