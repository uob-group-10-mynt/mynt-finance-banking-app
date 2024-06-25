import RemittancePage from './RemittancePage';
import {ChakraProvider, ColorModeScript, extendTheme} from '@chakra-ui/react'
import Header from '../components/Layout/Header';
import Footer from '../components/Layout/Footer';


const App = () => {
    return (
        <ChakraProvider theme={theme}>
            <ColorModeScript initialColorMode={theme.config.initialColorMode}/>
            <div className="App">
                    <RemittancePage/>                
            </div>
        </ChakraProvider>
    );
};


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

export default App;