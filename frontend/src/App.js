import { ChakraProvider, ColorModeScript } from '@chakra-ui/react'
import { RouterProvider } from 'react-router-dom';
import { useEffect, useState } from 'react';
import appRouter from './utils/appRouter';
import ChakraUITheme from './utils/ChakraUITheme';
import SplashPage from './pages/SplashPage';


const FOUR_SECONDS = 4000;

const App = () => {
    const [ isLoading, setIsLoading ] = useState(true);
    useEffect(() => {
        setTimeout(() => {
            setIsLoading(false);
        }, FOUR_SECONDS);
    }, []);

    return (
        isLoading 
        ? <SplashPage /> 
        :<ChakraProvider theme={ChakraUITheme}>
            <ColorModeScript initialColorMode={ChakraUITheme.config.initialColorMode}/>
            <RouterProvider router={appRouter}/>
        </ChakraProvider>
    );
};

export default App;
