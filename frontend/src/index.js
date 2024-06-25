import React from 'react';
import {createRoot} from 'react-dom';
import './styles/index.css';
import {
    createBrowserRouter,
    RouterProvider,
    Route,
    Link,
  } from "react-router-dom";


import App from './App'; 
import Error from './pages/Error';
import CreateAccount from './pages/CreateAccount';
import SignIn from './pages/SignIn';
import Remittance from './pages/RemittancePage';
import Loans from './pages/Loans';

const router = createBrowserRouter([
    { path: "*", element: <Error /> },
    { path: "/", element: <App />, errorElement: <Error /> },
    { path: "error", element: <Error />, errorElement: <Error /> },
    { path: "create", element: <CreateAccount />, errorElement: <Error /> },
    { path: "signIn", element: <SignIn />, errorElement: <Error /> },
    { path: "remittances", element: <Remittance />, errorElement: <Error /> },
    { path: "loan", element: <Loans />, errorElement: <Error /> },
]);

const root = createRoot(document.getElementById('root'));
root.render(
    <RouterProvider router={router} />
);
