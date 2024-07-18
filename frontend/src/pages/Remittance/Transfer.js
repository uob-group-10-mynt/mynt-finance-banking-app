import {useState} from 'react';
import CustomForm from "../../components/forms/CustomForm";

function Remittance() {
    // State for form fields
    const [senderName, setSenderName] = useState('');
    const [recipientName, setRecipientName] = useState('');
    const [amount, setAmount] = useState('');
    const availableBalance = 1000; // To be replaced with logic to fetch balance dynamically from an API
    const remittanceInputFields = [
        {
            label: "From:",
            id: "fromInput",
            placeholder: "Payer's name",
            type: "text",
            required: true,
            value: senderName,
            onChange: (e) => setSenderName(e.target.value)
        },
        {
            label: "To:",
            id: "toInput",
            placeholder: "Payee's name",
            type: "text",
            required: true,
            value: recipientName,
            onChange: (e) => setRecipientName(e.target.value)
        },
        {
            label: "Amount:",
            id: "amountInput",
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
        <CustomForm onSubmit={handleFormSubmit} buttonText="Send Money" buttonId="submitTransfer">
            {remittanceInputFields}
        </CustomForm>
    );
}

export default Remittance;
