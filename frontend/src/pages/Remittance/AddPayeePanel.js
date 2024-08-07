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
            label: "Name",
            placeholder: "Enter name for your payee",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "bank_account_holder_name",
            label: "Bank Account Holder Name",
            placeholder: "Enter holder name of the bank account",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "bank_name",
            label: "Bank Name",
            placeholder: "Enter bank name of the bank account",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "bank_country",
            label: "Bank Country",
            placeholder: "Enter country of the bank account",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "bic_swift",
            label: "SWIFT/BIC",
            placeholder: "Enter SWIFT/BIC",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "iban",
            label: "IBAN",
            placeholder: "Enter IBAN",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "currency",
            label: "Currency",
            placeholder: "Enter currency of the bank account",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "beneficiary_address",
            label: "Beneficiary Address",
            placeholder: "Enter address of the beneficiary",
            type: "text",
            required: true,
            value: "",
            toArray: true
        },
        {
            id: "beneficiary_country",
            label: "Beneficiary Country",
            placeholder: "Enter country of the beneficiary",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "beneficiary_first_name",
            label: "Beneficiary First Name",
            placeholder: "Enter first name of the beneficiary",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "beneficiary_last_name",
            label: "Beneficiary Last Name",
            placeholder: "Enter last name of the beneficiary",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "beneficiary_city",
            label: "Beneficiary City",
            placeholder: "Enter city of the beneficiary",
            type: "text",
            required: true,
            value: ""
        },
        {
            id: "beneficiary_postcode",
            label: "Beneficiary Postcode",
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
                body: JSON.stringify(formValuesJSON)
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
                    navigate(0);
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
