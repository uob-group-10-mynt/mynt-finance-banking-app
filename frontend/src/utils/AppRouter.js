import {
    Suspense,
    lazy,
    useContext
} from 'react';
import {
    Navigate,
    Route, 
    createBrowserRouter, 
    createRoutesFromElements
} from 'react-router-dom';
import Loading from '../components/util/Loading';
import {LoggedInContext} from "../App";


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
const Transaction = lazy(() => import('../pages/Transaction/TransactionPage'));
const CurrencyPage = lazy(() => import('../pages/Conversion/CurrencyPage'));

// Remittance
const Remittance = lazy(() => import('../pages/Remittance/Remittance'));
const Transfer = lazy(() => import('../pages/Remittance/Transfer'));
const Payee = lazy(() => import('../pages/Remittance/Payee'));
const Amount = lazy(() => import('../pages/Remittance/Amount'));

function lazyLoad(Component, loadingComponent) {
    return (
        <Suspense fallback={loadingComponent}>
            <Component/>
        </Suspense>
    )
}

function ProtectedRoute({element, loadingComponent}) {
    const [loggedIn] = useContext(LoggedInContext)
    return loggedIn ? lazyLoad(element, loadingComponent) : <Navigate to="/login"/>;
}

const AppRouter = createBrowserRouter(
    createRoutesFromElements(
        <Route path='/' element={lazyLoad(RootLayout, <Loading/>)}>
            <Route index element={lazyLoad(Home, <Loading/>)}/>
            <Route path='login' element={lazyLoad(Login, <Loading/>)}/>
            <Route path='remittance' element={<ProtectedRoute element={Remittance} loadingComponent={<Loading/>}/>}>
                <Route path='payee' element={lazyLoad(Payee, <Loading/>)}/>
                <Route path='amount' element={lazyLoad(Amount, <Loading/>)}/>
                <Route path='transfer' element={lazyLoad(Transfer, <Loading/>)}/>
            </Route>
            <Route path='signup' element={lazyLoad(Signup, <Loading/>)}/>
            <Route path='KYC' element={lazyLoad(Kyc, <Loading/>)}/>
            <Route path='dashboard' element={<ProtectedRoute element={DashBoard} loadingComponent={<Loading/>}/>}/>
            <Route path='accounts/:id' element={<ProtectedRoute element={Account} loadingComponent={<Loading/>}/>}/>
            <Route path='transactions/:id' element={<ProtectedRoute element={Transaction} loadingComponent={<Loading/>}/>}/>
            <Route path='currencies/:currency' element={<ProtectedRoute element={CurrencyPage} loadingComponent={<Loading/>}/>}/>

            {/* <Route path='dashboard' element={lazyLoad(DashBoard, <Loading/>)}></Route>
            <Route path='accounts/:id' element={lazyLoad(Account, <Loading/>)}></Route>
            <Route path='transactions/:id' element={lazyLoad(Transaction, <Loading/>)}></Route>
            <Route path='currencies/:currency' element={lazyLoad(CurrencyPage, <Loading/>)}></Route> */}
            <Route path={'*'} element={lazyLoad(NotFound404, <Loading/>)}/>
        </Route>
    )
);



export default AppRouter;
