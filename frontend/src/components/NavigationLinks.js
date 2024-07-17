import {useContext} from "react";
import {LoggedInContext} from "../App";
import {Link} from "@chakra-ui/react";


function createLinks(dataAboutPages) {
    return (
        dataAboutPages.map((page, index) => (
            <Link key={index} href={page.href} mx="2" data-cy={page.testId} onClick={page.onClick}>
            {page.text}
        </Link>  
        ))
    )
}

export default function NavigationLinks() {
    const [loggedIn, setLoggedIn, logOut] = useContext(LoggedInContext)
    const loggedInData = [
        {href: "remittance", text: "Transfer", testId: "RemittanceLink"},
        {href: "login", text: "Log Out", testId: "LogOutLink", onClick: logOut}
    ]
    const loggedOutData = [
        {href: "signup", text: "Sign Up", testId: "SignUpLink"},
        {href: "login", text: "Log In", testId: "LoginLink"},
    ]
    const pagesThatAreAlwaysVisible = [
        {href: "/", text: "Home"}
    ];

    let links = createLinks(pagesThatAreAlwaysVisible)
    if (loggedIn) {
        links.push(createLinks(loggedInData))
    } else {
        links.push(createLinks(loggedOutData))
    }

    return links;
}