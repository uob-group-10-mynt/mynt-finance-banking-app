import React, {useState} from 'react';
import {Box, Input, FormLabel, FormControl, FormHelperText, Button } from '@chakra-ui/react';
// import Header from './components/SymaticPageComponents/Header';
import Header from '../components/Layout/Header';
import Footer from '../components/Layout/Footer';

const Remittance = () => {
    // State for form fields
    const [senderName, setSenderName] = useState('');
    const [recipientName, setRecipientName] = useState('');
    const [amount, setAmount] = useState('');
    const [paymentDate, setPaymentDate] = useState('');

    const availableBalance = 1000; // To be replaced with logic to fetch balance dynamically from an API

    // Function to handle form submission
    const handleFormSubmit = (event) => {
        event.preventDefault();
        // To be added with logic to send the remittance data to a server or perform some other action based on the form inputs
        console.log('Form submitted:', senderName, recipientName, amount);
        // Reset form fields after submission
        setSenderName('');
        setRecipientName('');
        setAmount('');
    };

    const remitanceInputList = [
        { label: "From:", placeholder: "Payer's name", type:"text", required: true, value: senderName, onChange: () => setSenderName(e.target.value) },
        { label: "To:", placeholder: "Payee's name", type:"text", required: true, value: recipientName, onChange: () => setRecipientName(e.target.value) },
        { label: "Amount:", placeholder: 0, type:"number", required: true, value: amount, onChange: () => setRecipientName(e.target.value), helperText: `Available balance: ${availableBalance.toFixed(2)} KES` },
    ];

    const renderedRemittancesInput = remitanceInputList.map((inputList) => {
        return (
            <div key={inputList.label}>
                <FormControl isRequired={inputList.required} margin='0.5em'>
                    <FormLabel>{inputList.label}</FormLabel>
                    <Input 
                        margin='0.5em'
                        placeholder={inputList.placeholder} 
                        type={inputList.type}
                        value={inputList.value}
                        onChange={inputList.onChange}
                        required={inputList.required}
                    />
                    {inputList.helperText ? <FormHelperText>{inputList.helperText}</FormHelperText> : null}
                </FormControl>
            </div>
        );
    });

    return (
        <div>
            <Header></Header>
            <main>
                <Box className="remittance-page">
                    <h1 className="Remittance-page-header">Transfer</h1>
                    <form onSubmit={handleFormSubmit}>
                        {renderedRemittancesInput}
                        <Button margin='0.5em' type="submit">Send Money</Button>
                    </form>
                </Box>
            </main>
            <Footer></Footer>
        </div>
    );
};

export default Remittance;
