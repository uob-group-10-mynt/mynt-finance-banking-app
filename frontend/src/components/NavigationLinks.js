import {useContext} from "react";
import {LoggedInContext} from "../App";
import {Link} from "@chakra-ui/react";

const pages = [
    {href: "/", text: "Home"},
    {href: "/remittance/payee", text: "Transfer", testId: "RemittanceLink"},
    {href: "/signup", text: "Sign Up", testId: "SignUpLink"},
    // Add more links as needed
];

const links = pages.map((page, index) => (
    <Link key={index} href={page.href} mx="2" data-cy={page.testId}>
        {page.text}
    </Link>
));

export default function NavigationLinks() {
    const [loggedIn, setLoggedIn, logOut] = useContext(LoggedInContext)
    return (
        <>
            {links}
            {
                loggedIn ?
                    <Link href={'login'} mx="2" data-cy="LogOutLink" onClick={() => {
                        logOut();
                    }}>Log Out</Link>
                    :
                    <Link href={'login'} mx="2" data-cy="LoginLink">Log In</Link>
            }
        </>
    )
}