import Footer from './Footer';
import Header from './Header';
import '../../styles/index.css';
import {ChakraProvider, ColorModeScript, extendTheme} from '@chakra-ui/react'

// TODO: got up too here 

function layout({ children }){
    return(
        <div>
            <ChakraProvider theme={theme}>
                <ColorModeScript initialColorMode={theme.config.initialColorMode}/>
                <Header></Header>
                <main>
                    {children}
                </main> 
                <Footer></Footer>
            </ChakraProvider>
        </div>
    );
}

const theme = extendTheme({
    config: {
        initialColorMode: 'light',
        useSystemColorMode: false,
    },
    colors: {
        // Define your light and dark mode colors here
        brand: {
            500: '#2ecc71', // Example brand color
        },
    },
    styles: {
        global: (props) => ({
            body: {
                bg: props.colorMode === 'dark' ? 'gray.800' : 'white',
                color: props.colorMode === 'dark' ? 'white' : 'gray.800',
            },
        }),
    },
});

export default layout;