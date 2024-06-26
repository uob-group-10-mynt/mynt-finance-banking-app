import React from 'react';
import {ChakraProvider, ColorModeScript, extendTheme} from '@chakra-ui/react'
import { Route, RouterProvider, createBrowserRouter, createRoutesFromElements } from 'react-router-dom';

//Pages
import Home from './pages/Home';
import Login from './pages/Login';
import RemittancePage from './pages/RemittancePage'
import CreateUser from './pages/CreateUser';

//Layouts
import RootLayout from './pages/RootLayout';

const App = () => {
    return (
        <ChakraProvider theme={theme}>
            <ColorModeScript initialColorMode={theme.config.initialColorMode}/>
            <RouterProvider router={router}/>
        </ChakraProvider>
    );
};

const router = createBrowserRouter(
    createRoutesFromElements(
        <Route path='/' element={<RootLayout/>}>
            <Route index element={<Home/>}/>
            <Route path='login' element={<Login/>}/>
            <Route path='remittance' element={<RemittancePage/>}/>
            <Route path='signup' element={<CreateUser/>}/>
        </Route>
    )
);

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
