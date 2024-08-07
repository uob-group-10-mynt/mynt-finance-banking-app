import React, {useState} from 'react';
import {useToast} from '@chakra-ui/react';
import CustomForm from "../../components/forms/CustomForm";
import {addBeneficiaries} from "../../utils/APIEndpoints";
import CustomText from "../../components/CustomText";
import {useNavigate} from "react-router-dom";

export default function AddPayeePanel() {
    const toast = useToast();
    const navigate = useNavigate();
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
            id: "bank-name",
            label: "bank_name",
            placeholder: "Enter bank name of the bank account",
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
            id: "bic-swift",
            label: "bic_swift",
            placeholder: "Enter SWIFT/BIC",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "iban",
            label: "iban",
            placeholder: "Enter IBAN",
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
        {
            id: "beneficiary-address",
            label: "beneficiary_address",
            placeholder: "Enter address of the beneficiary",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "beneficiary-country",
            label: "beneficiary_country",
            placeholder: "Enter country of the beneficiary",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "beneficiary-first-name",
            label: "beneficiary_first_name",
            placeholder: "Enter first name of the beneficiary",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "beneficiary-last-name",
            label: "beneficiary_last_name",
            placeholder: "Enter last name of the beneficiary",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "beneficiary-city",
            label: "beneficiary_city",
            placeholder: "Enter city of the beneficiary",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "beneficiary-postcode",
            label: "beneficiary_postcode",
            placeholder: "Enter postcode of the beneficiary",
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

            if (response.ok) {
                toast({
                    position: 'top',
                    title: 'Payee added.',
                    description: "You've successfully added a new payee.",
                    status: 'success',
                    duration: 5000,
                    isClosable: true,
                });

                setTimeout(() => {
                    navigate('/remittance/payee');
                }, 2000);
                return;
            }

            if (!response.ok) {
                const error = new Error(await response.text());
                setError(error);
                setLoading(false);
            }
        } catch (error) {
            setError(error);
        } finally {
            setLoading(false);
        }

        // Clear form and close panel
        newPayeeInputFields.forEach(inputField => {
            inputField.value = ""
        })
        setFormData(newPayeeInputFields)
    }

}
