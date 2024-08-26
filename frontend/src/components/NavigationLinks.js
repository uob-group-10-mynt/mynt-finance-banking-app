import {useContext} from "react";
import {LoggedInContext} from "../App";
import {Link} from "@chakra-ui/react";
import {useNavigate} from "react-router-dom";
import { Link as ReactRouterLink } from 'react-router-dom'
import { Link as ChakraLink, LinkProps } from '@chakra-ui/react'



function createLinks(dataAboutPages) {
    return (
        dataAboutPages.map((page, index) => (
            <ChakraLink as={ReactRouterLink} to={page.to} key={index} mx="2" data-cy={page.id} onClick={page.onClick}>
            {page.text}
            </ChakraLink>
        ))
    )
}

export default function NavigationLinks() {
    const [loggedIn, setLoggedIn, logOut] = useContext(LoggedInContext)
    const navigate = useNavigate()
    const loggedInData = [
        {text: "Transfer", id: "RemittanceLink", to:"/accounts", onClick: async ()=>{return false;}},
        {text: "Account Details", id: "DetailsLink", to:"/userDetails", onClick: async ()=>{return false;}},
        {text: "Dashboard", id: "DashboardLink", to:"/dashboard", onClick: async ()=>{return false;}},
        {text: "Currencies", id: "CurrenciesLink", to:"/currencies", onClick: async ()=>{return false;}},
        // {text: "Accounts", id: "AccountsLink", onClick: async ()=>{navigate("/accounts");return false}},
        {text: "Log Out", id: "LogOutLink", to:"/login", onClick: async ()=>{logOut();return false}}

    ]
    const loggedOutData = [
        {text: "Home", to:"/", onClick: async ()=>{return false}},
        {text: "Sign Up", id: "SignUpLink", to:"/signup", onClick: async ()=>{return false}},
        {text: "Log In", id: "LoginLink", to:"/login", onClick: async ()=>{return false}}

    ]
    // const pagesThatAreAlwaysVisible = [
    //     {text: "Home", onClick: async ()=>{navigate("/");return false}}
    // ];

    // let links = createLinks(pagesThatAreAlwaysVisible)
    if (loggedIn) {
        return createLinks(loggedInData)
    } else {
        return createLinks(loggedOutData)
    }

}