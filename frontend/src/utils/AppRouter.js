import { 
  Suspense, 
  lazy } from 'react';

import { 
  Route, 
  createBrowserRouter, 
  createRoutesFromElements 
} from 'react-router-dom';
import Loading from '../components/Loading';

//Pages
const Home = lazy(() => import('../pages/Home'));
const Login = lazy(() => import('../pages/Login'));
const RemittancePage = lazy(() => import('../pages/Remittance'));
const CreateUser = lazy(() => import('../pages/SignUp'));
const Kyc = lazy(() => import('../pages/KYC'));
const DashBoard = lazy(() => import('../pages/DashboardPage'));
const NotFound404 = lazy(() => import('../pages/ErrorPage'));
const Payee = lazy(() => import('../pages/Payee'));

// Layouts
const RootLayout = lazy(() => import('../layouts/RootLayout'))

const AppRouter = createBrowserRouter(
  createRoutesFromElements(
      <Route path='/' element={lazyLoad(RootLayout, <Loading />)}>
          <Route index element={lazyLoad(Home, <Loading />)}/>
          <Route path='login' element={lazyLoad(Login, <Loading />)}/>
          <Route path='remittance' element={lazyLoad(RemittancePage, <Loading />)}/>
          <Route path='signup' element={lazyLoad(CreateUser, <Loading />)}/>
          <Route path='KYC' element={lazyLoad(Kyc, <Loading />)}/>
          <Route path='dashboard' element={lazyLoad(DashBoard, <Loading />)}></Route>
          <Route path={'*'} element={lazyLoad(NotFound404, <Loading />)}></Route>
          <Route path={'payee'} element={lazyLoad(Payee, <Loading />)}></Route>
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

export default AppRouter;
