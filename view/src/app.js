import React from 'react';
import Remittance from './remittance';

const App = () => {
    return (
        <div className="App">
            <header className="App-header">
                <h1>MYNT Technology</h1>
            </header>
            <main>
                <Remittance/>
                {/* Other components/pages can be added here */}
            </main>
            <footer>
                <p>&copy; 2024 Mynt. All rights reserved.</p>
            </footer>
        </div>
    );
};

export default App;
