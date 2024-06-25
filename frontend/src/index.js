import React, {Suspense, lazy} from 'react';
import {createRoot} from 'react-dom';
import {createBrowserRouter,RouterProvider,Route,Link} from "react-router-dom";

const App = lazy(() => import('./App'));
const Error = lazy(() => import('./pages/Error'));
const CreateAccount = lazy(() => import('./pages/CreateAccount'));
const SignIn = lazy(() => import('./pages/SignIn'));
const Remittance = lazy(() => import('./pages/RemittancePage'));
const Loans = lazy(() => import('./pages/Loans'));
const Home = lazy(() => import('./pages/Home'));
const Dashboard = lazy(() => import('./pages/Dashboard'));

const router = createBrowserRouter(
    [
        {path:"*",          element:<Home/>,         errorElement: <Error/>},
        {path:"/",          element:<Home/>,         errorElement: <Error/>},
        {path:"home",       element:<Home/>,         errorElement: <Error/>},
        {path:"dashboard",  element:<Dashboard/>,    errorElement: <Error/>},
        {path:"error",      element:<Error/>,        errorElement: <Error/>},
        {path:"create",     element:<CreateAccount/>,errorElement: <Error/>},
        {path:"signIn",     element:<SignIn/>,       errorElement: <Error/>},
        {path:"remittances",element:<Remittance/>,   errorElement: <Error/>},
        {path:"loan",       element:<Loans/>,        errorElement: <Error/>},
    ]
);

const root = createRoot(document.getElementById('root'));
root.render(
    <Suspense fallback={<div>Loading...</div>}>
        <RouterProvider router={router} />
    </Suspense>
);
