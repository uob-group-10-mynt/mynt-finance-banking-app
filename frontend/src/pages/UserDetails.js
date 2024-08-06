import { useEffect, useState } from "react";
import CustomForm from "../components/forms/CustomForm";
import { getUserDetailsAPI, updateUserDetailsAPI } from "../utils/APIEndpoints";
import Page from "../components/Page";
import CustomButton from "../components/forms/CustomButton";
import PageHeader from "../components/forms/PageHeader";

const accountFields = [
    {
        label: "Email",
        id: "email",
        alwaysReadOnly:true,
        readonly: true,
        value: "",
        type: "text",
        border: "none"
    },
    {
        label: "Date of birth",
        id: "dob",
        alwaysReadOnly:true,
        readonly: true,
        value: "",
        type: "date",
        border: "none"
    },
    {
        label: "First name",
        id: "firstname",
        required: true,
        readonly: true,
        value: "",
        border: "none"
    },
    {
        label: "Last name",
        id: "lastname",
        required: true,
        readonly: true,
        value: "",
        border: "none"
    },
    {
        label: "Address",
        id: "address",
        required: true,
        readonly: true,
        value: "",
        border: "none"
    },
    {
        label: "Phone number",
        id: "phoneNumber",
        required: true,
        readonly: true,
        value: "",
        border: "none"
    },
];

export default function UserDetails() {
    const [details, setDetails] = useState(accountFields)
    
    
    //edit form changes state, readonly becomes false
    function editForm(e) {
        e.preventDefault();
        
        setDetails(details.map((field) => {
            if (field.id !== 'dob' && field.id !== 'email') {
                field.readonly = false;
                field.border = null
            }
            return field
        }))
    }

    const getAndSetDetails = async () => {
        try {
            const response = await fetch(getUserDetailsAPI, {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${sessionStorage.getItem('access')}`
                }
            });
            if (!response.ok) {
                throw new Error('response not ok');
            }
            const data = await response.json()
            accountFields.forEach(field => {
                    field.value = data[field.id]
                    field.readonly = true
            });
            setDetails(accountFields)
        } catch (error) {
            console.error(error);
        }
    }

     useEffect(() => {
         getAndSetDetails()
     }, []) // empty array means useEffect() is only called on initial render of component
    

    const updateDetails = async (formValuesJSON) => {
        try {
            const response = await fetch(updateUserDetailsAPI, {
                method: 'POST',
                headers: {
                    "Content-type": "application/json",
                    Authorization: `Bearer ${sessionStorage.getItem('access')}`
                },
                body: JSON.stringify(formValuesJSON)
            });
            setDetails(details.map((field) => {
                field.readonly = true;
                field.border = "none"
                return field
            }))
            if (!response.ok) {
                throw new Error('Authentication failed');
            }
        } catch (error) {
            console.error(error);
        }
    }

    return (
        <Page>
            <Heading as='h1' size='xl' mb={4}>Your personal details</Heading>
            <CustomForm onSubmit={updateDetails} buttonText="Save" buttonId="saveDetailsButton" parentState={details} setParentState={setDetails} editable={true}>
            </CustomForm>
        </Page>
    )
}