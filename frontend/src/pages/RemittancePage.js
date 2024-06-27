import {useState} from 'react';
import {Box, Input, FormLabel, FormControl, FormHelperText, Button } from '@chakra-ui/react';

const Remittance = () => {
    // State for form fields
    const [senderName, setSenderName] = useState('');
    const [recipientName, setRecipientName] = useState('');
    const [amount, setAmount] = useState('');

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
        { testId: "fromInput",label: "From:", placeholder: "Payer's name", type:"text", required: true, value: senderName, onChange: (e) => setSenderName(e.target.value) },
        { testId: "toInput",label: "To:", placeholder: "Payee's name", type:"text", required: true, value: recipientName, onChange: (e) => setRecipientName(e.target.value) },
        { testId: "amountInput",label: "Amount:", placeholder: 0, type:"number", required: true, value: amount, onChange: (e) => setAmount(e.target.value), helperText: `Available balance: ${availableBalance.toFixed(2)} KES` },
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
                        data-cy={inputList.testId}
                    />
                    {inputList.helperText ? <FormHelperText>{inputList.helperText}</FormHelperText> : null}
                </FormControl>
            </div>
        );
    });


    return (
        <Box className="remittance-page">
            <h1 className="Remittance-page-header">Transfer</h1>
            <form onSubmit={handleFormSubmit}>
                {renderedRemittancesInput}
                <Button margin='0.5em' type="submit" data-cy="submitButton">Send Money</Button>
            </form>
        </Box>
    );
};

export default Remittance;
