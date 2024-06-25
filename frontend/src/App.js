import RemittancePage from './pages/RemittancePage';
import {ChakraProvider, ColorModeScript, extendTheme} from '@chakra-ui/react'
import Theme from "./theme";

const App = () => {
    return (
        <ChakraProvider theme={theme}>
            <ColorModeScript initialColorMode={theme.config.initialColorMode}/>
            <div className="App">
                <Theme>Mode</Theme>

                <header>
                    <h1 className="App-header">
                        MYNT Technology
                    </h1>
                </header>

                

                <main>
                    <RemittancePage/>
                    {/* Other components/pages can be added here */}
                </main>


                <footer>
                    <p>&copy; 2024 Mynt. All rights reserved.</p>
                </footer>

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
