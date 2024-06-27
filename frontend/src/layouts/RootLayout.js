import Theme from "../theme";
import { NavLink, Outlet } from "react-router-dom";

export default function RootLayout() {
    return(
    <div className="App">
        <Theme>Mode</Theme>
        <header className="App-header">
            <h1 className="App-header">
                MYNT Technology
            </h1>
            <nav>
                <NavLink to='/'>Home</NavLink>
                <NavLink to='login' data-cy="LoginLink">Log in</NavLink>
                <NavLink to='remittance' data-cy="RemittanceLink">Send money</NavLink>
            </nav>
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