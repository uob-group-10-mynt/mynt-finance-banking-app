import Theme from "../theme";
import {Outlet} from "react-router-dom";
import CustomDrawer from "../components/CustomDrawer";
import {useContext} from "react";
import {LoggedInContext} from "../App";
import {useMediaQuery} from "react-responsive";
import NavigationLinks from "../components/NavigationLinks";
import CustomHeading from "../components/CustomHeading";
import Header from "./Header";
import {Box} from "@chakra-ui/react";
import Footer from "./Footer";

export default function RootLayout() {
    const [loggedIn, setLoggedIn, logOut] = useContext(LoggedInContext)
    const isTabletOrSmaller = useMediaQuery({query: '(max-width: 768px)'})
    const isDesktop = useMediaQuery({query: '(min-width: 769px)'})

    return (
        <div>
            <Theme>Mode</Theme>
            <header>
                <Box display="flex" alignItems="baseline">
                    <CustomHeading size={"lg"}>
                        Mynt
                    </CustomHeading>
                    {isTabletOrSmaller && <CustomDrawer id="navButton">{<NavigationLinks/>}</CustomDrawer>}
                    {isDesktop && <Header>{<NavigationLinks/>}</Header>}
                </Box>
            </header>
            <main>
                <Outlet/>
            </main>
            <footer>
                <Footer>{<NavigationLinks/>}</Footer>
            </footer>
        </div>
    );
}