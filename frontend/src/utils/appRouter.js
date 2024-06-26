import { 
    Suspense, 
    lazy } from 'react';

import { 
    Route, 
    createBrowserRouter, 
    createRoutesFromElements 
} from 'react-router-dom';

//Pages
const Home = lazy(() => import('../pages/Home'))
const Login = lazy(() => import('../pages/Login'))
const RemittancePage = lazy(() => import('../pages/RemittancePage'))

// Layouts
const RootLayout = lazy(() => import('../pages/RootLayout'))

//Loading components
const placeholderLoading = <div>loading...</div>

const appRouter = createBrowserRouter(
    createRoutesFromElements(
        <Route path='/' element={lazyLoad(RootLayout, placeholderLoading)}>
            <Route index element={lazyLoad(Home, placeholderLoading)}/>
            <Route path='login' element={lazyLoad(Login, placeholderLoading)}/>
            <Route path='remittance' element={lazyLoad(RemittancePage, placeholderLoading)}/>
        </Route>
    )
);

function lazyLoad(Component, loadingComponent) {
    return(
        <Suspense fallback={loadingComponent}>
            <Component/>
        </Suspense>
    )
}

export default appRouter;
