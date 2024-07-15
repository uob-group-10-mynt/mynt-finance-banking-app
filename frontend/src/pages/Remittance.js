import {useState} from 'react';
import {Box} from '@chakra-ui/react';
import PageHeader from "../components/forms/PageHeader";
import CustomForm from "../components/forms/CustomForm";

function Remittance() {
    // State for form fields
    const [senderName, setSenderName] = useState('');
    const [recipientName, setRecipientName] = useState('');
    const [amount, setAmount] = useState('');
    const availableBalance = 1000; // To be replaced with logic to fetch balance dynamically from an API
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
    // Function to handle form submission
    const handleFormSubmit = (event) => {
        event.preventDefault();
        // Logic to send remittance data to server or perform other actions
        console.log('Form submitted:', senderName, recipientName, amount);
        // Reset form fields after submission
        setSenderName('');
        setRecipientName('');
        setAmount('');
    };

    return (
        <Box className="page">
            <PageHeader>Transfer</PageHeader>
            <CustomForm onSubmit={handleFormSubmit} buttonText="Send Money" testId="submitTransfer">
                {remittanceInputList}
            </CustomForm>
        </Box>
    );
}

export default Remittance;
