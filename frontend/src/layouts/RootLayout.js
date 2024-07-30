import Theme from "../theme";
import {Outlet} from "react-router-dom";
import CustomDrawer from "../components/CustomDrawer";
import {useMediaQuery} from "react-responsive";
import NavigationLinks from "../components/NavigationLinks";
import CustomHeading from "../components/CustomHeading";
import Header from "./Header";
import {Box, Image} from "@chakra-ui/react";
import Footer from "./Footer";
import iconMynt from '../../public/images/icons/mynt_icon.png'

export default function RootLayout() {
    const isTabletOrSmaller = useMediaQuery({query: '(max-width: 768px)'})
    const isDesktop = useMediaQuery({query: '(min-width: 769px)'})

    return (
        <Box id='viewport' position='relative' minHeight='100vh'>
            <Box id="content-wrap" paddingBottom='6rem'>
                <Theme>Mode</Theme>
                <Box display="flex" alignItems="baseline" ml={4}>
                    <Image
                        src={iconMynt}
                        objectFit="cover"
                        boxSize="2rem"
                    />
                    <CustomHeading size={"lg"} pl={0}>
                        Mynt
                    </CustomHeading>
                    {isTabletOrSmaller && <CustomDrawer id="navButton">{<NavigationLinks/>}</CustomDrawer>}
                    {isDesktop && <Header>{<NavigationLinks/>}</Header>}
                </Box>
                <Outlet/>
            </Box>
            <Footer>{<NavigationLinks/>}</Footer>
        </Box>
    );
}