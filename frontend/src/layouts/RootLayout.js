import Theme from "../theme";
import {NavLink, Outlet} from "react-router-dom";
import CustomDrawer from "../components/CustomDrawer";

export default function RootLayout() {
    return (
        <div className="App">
            <Theme>Mode</Theme>
            <header className="App-header">
                <h1 className="App-header">
                    MYNT Technology
                </h1>
                <CustomDrawer text="Navigation" testId="navButton">
                    <NavLink to='/'>Home</NavLink>
                    <NavLink to='login' data-cy="LoginLink">Log In / Sign Up</NavLink>
                    <NavLink to='remittance' data-cy="RemittanceLink">Transfer</NavLink>
                </CustomDrawer>
            </header>
            <main>
                <Outlet/>
            </main>
            <footer>
                <p>&copy; 2024 Mynt. All rights reserved.</p>
            </footer>
        </div>
    );
}