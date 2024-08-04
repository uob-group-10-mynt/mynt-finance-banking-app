import React, {useState} from 'react';
import {useToast} from '@chakra-ui/react';
import CustomForm from "../../components/forms/CustomForm";

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

export default function AddPayeePanel() {
    const [formData, setFormData] = useState(newPayeeInputFields)
    const toast = useToast();

    const handleAddPayee = () => {
        // Add validation and submission logic here

        toast({
            position: 'top',
            title: 'Payee added.',
            description: "You've successfully added a new payee.",
            status: 'success',
            duration: 5000,
            isClosable: true,
        });

        // Clear form and close panel
        newPayeeInputFields.forEach(inputField => {
            inputField.value = ""
        })
        setFormData(newPayeeInputFields)
    };

    return (
        <CustomForm onSubmit={handleAddPayee} buttonText="Add Payee" buttonId="addPayeeButton" parentState={formData} setParentState={setFormData}>
        </CustomForm>
    );
}
