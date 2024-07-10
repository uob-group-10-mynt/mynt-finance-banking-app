import Theme from "../theme";
import {NavLink, Outlet} from "react-router-dom";
import CustomDrawer from "../components/CustomDrawer";
import { useContext } from "react";
import { LoggedInContext } from "../App";
import { useMediaQuery } from "react-responsive";
import NavigationLinks from "../components/NavigationLinks";



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
                { isTabletOrSmaller && <CustomDrawer text="Navigation" testId="navButton" children={<NavigationLinks/>}/> }
                { isDesktop && <NavigationLinks/> }
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