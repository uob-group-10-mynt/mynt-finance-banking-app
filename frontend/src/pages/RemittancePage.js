import {useState} from 'react';
import {Box, Input, FormLabel, FormControl, FormHelperText, Button} from '@chakra-ui/react';
import PageHeader from "../components/forms/PageHeader";

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

    const remittanceInputList = [
        {
            label: "From:",
            testId: "fromInput",
            placeholder: "Payer's name",
            type: "text",
            required: true,
            value: senderName,
            onChange: (e) => setSenderName(e.target.value)
        },
        {
            label: "To:",
            testId: "toInput",
            placeholder: "Payee's name",
            type: "text",
            required: true,
            value: recipientName,
            onChange: (e) => setRecipientName(e.target.value)
        },
        {
            label: "Amount:",
            testId: "amountInput",
            placeholder: 0,
            type: "number",
            required: true,
            value: amount,
            onChange: (e) => setAmount(e.target.value),
            helperText: `Available balance: ${availableBalance.toFixed(2)} KES`
        },
    ];

    const renderedRemittancesInput = remittanceInputList.map((inputList) => {
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
        <Box className="page">
            <PageHeader title="Transfer"/>
            <form onSubmit={handleFormSubmit}>
                {renderedRemittancesInput}
                <Button margin='0.5em' type="submit" data-cy="submitButton">Send Money</Button>
            </form>
        </Box>
    );
};

export default Remittance;
