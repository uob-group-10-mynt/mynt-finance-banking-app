import React, {useState} from 'react';
import {useToast} from '@chakra-ui/react';
import CustomForm from "../../components/forms/CustomForm";
import {addBeneficiaries} from "../../utils/APIEndpoints";
import CustomText from "../../components/CustomText";

export default function AddPayeePanel() {
    const [payeeName, setPayeeName] = useState('');
    const [accountNumber, setAccountNumber] = useState('');
    const [email, setEmail] = useState('');
    const [response, setResponse] = useState('')
    const toast = useToast();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const newPayeeInputFields = [
        {
            id: "email",
            label: "Payee Email",
            placeholder: "Enter payee email",
            type: "text",
            required: true,
            value: email,
            onChange: (e) => setEmail(e.target.value)
        },
        {
            id: "account-number",
            label: "Payee Account Number",
            placeholder: "Enter account number",
            type: "number",
            required: false,
            value: accountNumber,
            onChange: (e) => setAccountNumber(e.target.value)
        },
        {
            id: "payee-name",
            label: "Payee Name",
            placeholder: "Enter payee name",
            type: "text",
            required: false,
            value: payeeName,
            onChange: (e) => setPayeeName(e.target.value)
        },
    ];

    async function handleAddPayee(event) {
        event.preventDefault();

        // Add validation and submission logic here
       await addPayee();
        if (response.ok) {
            toast({
                position: 'top',
                title: 'Payee added.',
                description: "You've successfully added a new payee.",
                status: 'success',
                duration: 5000,
                isClosable: true,
            });
        }

        // Clear form and close panel
        setPayeeName('');
        setAccountNumber('');
    }

    return (
        <>
            <CustomForm onSubmit={handleAddPayee} buttonText="Add Payee" buttonId="addPayeeButton">
                {newPayeeInputFields}
            </CustomForm>
            {loading && <CustomText>Processing...</CustomText>}
            {error && <CustomText>Error {error.message}</CustomText>}
        </>
    );

    async function addPayee() {
        try {
            setLoading(true);
            // POST request to add a payee
            const response = await fetch(addBeneficiaries, {
                method: 'POST',
                headers: {
                    Authorization: `Bearer ${sessionStorage.getItem('access')}`,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    'email': email,
                    'bank_account_holder_name': payeeName,
                    'account_number': accountNumber,
                }),
            });

            if (!response.ok) {
                const error = new Error(await response.text());
                setError(error);
                setLoading(false);
                return;
            }

            // Parse the JSON from the response
            setResponse(await response.json());
        } catch (error) {
            setError(error);
        } finally {
            setLoading(false);
        }
    }

}
