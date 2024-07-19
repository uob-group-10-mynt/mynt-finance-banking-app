import React, {useState} from 'react';
import {useToast} from '@chakra-ui/react';
import CustomForm from "../../components/forms/CustomForm";

export default function AddPayeePanel() {
    const [payeeName, setPayeeName] = useState('');
    const [accountNumber, setAccountNumber] = useState('');
    const toast = useToast();
    const newPayeeInputFields = [
        {
            id: "account-number",
            label: "Account Number",
            placeholder: "Enter account number",
            type: "number",
            required: true,
            value: accountNumber,
            onChange: (e) => setAccountNumber(e.target.value)
        },
        {
            id: "payee-name",
            label: "Payee Name",
            placeholder: "Enter payee name",
            type: "text",
            required: true,
            value: payeeName,
            onChange: (e) => setPayeeName(e.target.value)
        },
    ];

    const handleAddPayee = (event) => {
        event.preventDefault();
        // Add validation and submission logic here

        toast({
            title: 'Payee added.',
            description: "You've successfully added a new payee.",
            status: 'success',
            duration: 5000,
            isClosable: true,
        });

        // Clear form and close panel
        setPayeeName('');
        setAccountNumber('');
    };

    return (
        <CustomForm onSubmit={handleAddPayee} buttonText="Add Payee" buttonId="addPayeeButton">
            {newPayeeInputFields}
        </CustomForm>
    );
}
