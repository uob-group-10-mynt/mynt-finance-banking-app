import { 
    Route, 
    createBrowserRouter, 
    createRoutesFromElements 
} from 'react-router-dom';

//Pages
import Home from '../pages/Home';
import Login from '../pages/Login';
import RemittancePage from '../pages/RemittancePage'

//Layouts
import RootLayout from '../pages/RootLayout';


const appRouter = createBrowserRouter(
    createRoutesFromElements(
        <Route path='/' element={<RootLayout/>}>
            <Route index element={<Home/>}/>
            <Route path='login' element={<Login/>}/>
            <Route path='remittance' element={<RemittancePage/>}/>
        </Route>
    )
);

export default appRouter;