import {useContext} from "react";
import {LoggedInContext} from "../App";
import {Link} from "@chakra-ui/react";

function createLinks(dataAboutPages) {
    return (
        dataAboutPages.map((page, index) => (
            <Link key={index} href={page.href} mx="2" data-cy={page.id} onClick={page.onClick}>
            {page.text}
        </Link>  
        ))
    )
}

export default function NavigationLinks() {
    const [loggedIn, logOut] = useContext(LoggedInContext)
    const loggedInData = [
        {href: "/accounts", text: "Transfer", id: "RemittanceLink"},
        {href: "/userDetails", text: "Account Details", id: "DetailsLink"},
        {href: "/login", text: "Log Out", id: "LogOutLink", onClick: logOut}
    ]
    const loggedOutData = [
        {href: "/signup", text: "Sign Up", id: "SignUpLink"},
        {href: "/login", text: "Log In", id: "LoginLink"},
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