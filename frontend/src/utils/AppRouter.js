import {Suspense, lazy} from 'react';
import {Route, createBrowserRouter, createRoutesFromElements} from 'react-router-dom';
import Loading from '../components/util/Loading';

// Layouts
const RootLayout = lazy(() => import('../layouts/RootLayout'))
// Pages
const Home = lazy(() => import('../pages/Home'));
const Login = lazy(() => import('../pages/Login'));
const Signup = lazy(() => import('../pages/Signup'));
const Kyc = lazy(() => import('../pages/KYC'));
const DashBoard = lazy(() => import('../pages/DashBoard/DashboardPage'))
const NotFound404 = lazy(() => import('../pages/util/ErrorPage'));
const Account = lazy(() => import('../pages/Account/AccountPage'));
// Remittance
const Remittance = lazy(() => import('../pages/Remittance/Remittance'));
const Transfer = lazy(() => import('../pages/Remittance/Transfer'));
const Payee = lazy(() => import('../pages/Remittance/Payee'));
const Amount = lazy(() => import('../pages/Remittance/Amount'));

const AppRouter = createBrowserRouter(
    createRoutesFromElements(
        <Route path='/' element={lazyLoad(RootLayout, <Loading/>)}>
            <Route index element={lazyLoad(Home, <Loading/>)}/>
            <Route path='login' element={lazyLoad(Login, <Loading/>)}/>
            <Route path='remittance' element={lazyLoad(Remittance, <Loading/>)}>
                <Route path='payee' element={lazyLoad(Payee, <Loading/>)}></Route>
                <Route path='amount' element={lazyLoad(Amount, <Loading/>)}></Route>
                <Route path='transfer' element={lazyLoad(Transfer, <Loading/>)}></Route>
            </Route>
            <Route path='signup' element={lazyLoad(Signup, <Loading/>)}/>
            <Route path='KYC' element={lazyLoad(Kyc, <Loading/>)}/>
            <Route path='dashboard' element={lazyLoad(DashBoard, <Loading/>)}></Route>
            <Route path='accounts/:id' element={lazyLoad(Account, <Loading/>)}></Route>
            <Route path={'*'} element={lazyLoad(NotFound404, <Loading/>)}></Route>
        </Route>
    )
);

function lazyLoad(Component, loadingComponent) {
    return (
        <Suspense fallback={loadingComponent}>
            <Component/>
        </Suspense>
    )
}

export default AppRouter;
