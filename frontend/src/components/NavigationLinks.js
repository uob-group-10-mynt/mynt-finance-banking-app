import {useContext} from "react";
import {LoggedInContext} from "../App";
import {Link} from "@chakra-ui/react";
import {useNavigate} from "react-router-dom";

function createLinks(dataAboutPages) {
    return (
        dataAboutPages.map((page, index) => (
            <Link key={index} href={"#"} mx="2" data-cy={page.id} onClick={page.onClick}>
            {page.text}
        </Link>  
        ))
    )
}

export default function NavigationLinks() {
    const [loggedIn, setLoggedIn, logOut] = useContext(LoggedInContext)
    const navigate = useNavigate()
    const loggedInData = [
        {text: "Transfer", id: "RemittanceLink", onClick: async ()=>{navigate("/accounts");return false}},
        {text: "Account Details", id: "DetailsLink", onClick: async ()=>{navigate("/userDetails");return false}},
        {text: "Dashboard", id: "DashboardLink", onClick: async ()=>{navigate("/dashboard");return false}},
        {text: "Currencies", id: "CurrenciesLink", onClick: async ()=>{navigate("/currencies");return false}},
        {text: "Accounts", id: "AccountsLink", onClick: async ()=>{navigate("/accounts");return false}},
        {text: "Log Out", id: "LogOutLink", onClick: async ()=>{logOut();navigate("/login");return false}}

    ]
    const loggedOutData = [
        {text: "Home", onClick: async ()=>{navigate("/");return false}},
        {text: "Sign Up", id: "SignUpLink", onClick: async ()=>{navigate("/signup");return false}},
        {text: "Log In", id: "LoginLink", onClick: async ()=>{navigate("/login");return false}}

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