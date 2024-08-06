import React, {useState} from 'react';
import {useToast} from '@chakra-ui/react';
import CustomForm from "../../components/forms/CustomForm";
import {addBeneficiaries} from "../../utils/APIEndpoints";
import CustomText from "../../components/CustomText";

export default function AddPayeePanel() {
    const [response, setResponse] = useState('')
    const toast = useToast();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const newPayeeInputFields = [
        {
            id: "account-number",
            label: "Account Number",
            placeholder: "Enter account number",
            type: "number",
            required: true,
            value: ""
        },
        {
            id: "payee-name",
            label: "Payee Name",
            placeholder: "Enter payee name",
            type: "text",
            required: true,
            value: ""
        },
    ];
    const [formData, setFormData] = useState(newPayeeInputFields)

    return (
        <>
            <CustomForm onSubmit={handleAddPayee} buttonText="Add Payee" buttonId="addPayeeButton"
                        parentState={formData} setParentState={setFormData}>
            </CustomForm>
            {loading && <CustomText>Processing...</CustomText>}
            {error && <CustomText>Error {error.message}</CustomText>}
        </>
    );

    async function handleAddPayee() {
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
        newPayeeInputFields.forEach(inputField => {
            inputField.value = ""
        })
        setFormData(newPayeeInputFields)
    }

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
                body: JSON.stringify({formData}),
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
