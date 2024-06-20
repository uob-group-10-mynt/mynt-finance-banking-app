import React, {useState} from 'react';
import {Box, Input, Button, FormLabel} from '@chakra-ui/react';

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

    return (
        <Box className="remittance-page">
            <h1 className="Remittance-page-header">Transfer</h1>
            <form onSubmit={handleFormSubmit}>
                <FormLabel>
                    From:
                    <Input
                        placeholder="Payer's name"
                        type="text"
                        value={senderName}
                        onChange={(e) => setSenderName(e.target.value)}
                        required
                    />
                </FormLabel>
                <br/>
                <FormLabel>
                    To:
                    <Input
                        placeholder="Payee's name"
                        type="text"
                        value={recipientName}
                        onChange={(e) => setRecipientName(e.target.value)}
                        required
                    />
                </FormLabel>
                <br/>
                <FormLabel>
                    Amount:
                    <Input
                        type="number"
                        value={amount}
                        onChange={(e) => setAmount(e.target.value)}
                        required
                    />
                    <span>Available balance: {availableBalance.toFixed(2)} KES</span>
                </FormLabel>
                <br/>
                <FormLabel>
                    Payment Date:
                    <br/>
                    <Input
                        type="date"
                        value={paymentDate}
                        onChange={(e) => setPaymentDate(e.target.value)}
                        required
                    />
                </FormLabel>
                <br/>
                <Button type="submit">Send Money</Button>
            </form>
        </Box>
    );
};

export default Remittance;
