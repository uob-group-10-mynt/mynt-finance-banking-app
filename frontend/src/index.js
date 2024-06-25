import React from 'react';
// import ReactDOM from 'react-dom/client';
import {createRoot} from 'react-dom';
import './assets/styles/index.css';
import App from './App'; 


// const root = ReactDOM.createRoot(document.getElementById('root'));
const root = createRoot(document.getElementById('root'));

root.render(<App />);
