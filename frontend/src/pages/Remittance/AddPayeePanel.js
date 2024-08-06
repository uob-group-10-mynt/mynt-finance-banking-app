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
            id: "name",
            label: "name",
            placeholder: "Enter name for your payee",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "bank-account-holder-name",
            label: "bank_account_holder_name",
            placeholder: "Enter holder name of the bank account",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "bank-country",
            label: "bank_country",
            placeholder: "Enter country of the bank account",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "currency",
            label: "currency",
            placeholder: "Enter currency of the bank account",
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

    async function handleAddPayee(formValuesJSON) {
        setLoading(true);
        try {
            // POST request to add a payee
            const response = await fetch(addBeneficiaries, {
                method: 'POST',
                headers: {
                    Authorization: `Bearer ${sessionStorage.getItem('access')}`,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({formValuesJSON})
            });

            if (!response.ok) {
                const error = new Error(await response.text());
                setError(error);
                setLoading(false);
                return;
            }

            setResponse(await response.json());
        } catch (error) {
            setError(error);
        } finally {
            setLoading(false);
        }

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

}
