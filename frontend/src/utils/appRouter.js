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
const CreateUser = lazy(() => import('../pages/CreateUser'))
const DashBoard = lazy(() => import('../pages/DashboardPage'));
const NotFound404 = lazy(() => import('../pages/ErrorPage'));

// Layouts
const RootLayout = lazy(() => import('../layouts/RootLayout'))

//Loading components
const placeholderLoading = <div>loading...</div>

const appRouter = createBrowserRouter(
    createRoutesFromElements(
        <Route path='/' element={lazyLoad(RootLayout, placeholderLoading)}>
            <Route index element={lazyLoad(Home, placeholderLoading)}/>
            <Route path='login' element={lazyLoad(Login, placeholderLoading)}/>
            <Route path='remittance' element={lazyLoad(RemittancePage, placeholderLoading)}/>
            <Route path='signup' element={lazyLoad(CreateUser, placeholderLoading)}/>
            <Route path='dashboard' element={lazyLoad(DashBoard, placeholderLoading)}></Route>
            <Route path={'*'} element={lazyLoad(NotFound404, placeholderLoading)}></Route>
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
