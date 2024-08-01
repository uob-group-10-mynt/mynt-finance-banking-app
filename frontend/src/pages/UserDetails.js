import { useEffect, useState } from "react";
import CustomForm from "../components/forms/CustomForm";
import { getUserDetailsAPI, updateUserDetailsAPI } from "../utils/APIEndpoints";
import Page from "../components/Page";
import CustomButton from "../components/forms/CustomButton";
import { Heading } from "@chakra-ui/react";

const accountFields = [
    {
        label: "First name",
        id: "firstname",
        required: true,
        readonly: true,
        value: ""
    },
    {
        label: "Last name",
        id: "lastname",
        required: true,
        readonly: true,
        value: ""
    },
    {
        label: "Date of birth",
        id: "dob",
        required: true,
        readonly: true,
        value: "",
        type: "date"
    },
    {
        label: "Address",
        id: "address",
        required: true,
        readonly: true,
        value: ""
    },
    {
        label: "Phone number",
        id: "phoneNumber",
        required: true,
        readonly: true,
        value: ""
    },
];



export default function UserDetails() {
    const [details, setDetails] = useState(accountFields)
    const [editButtonDisplayed, setEditButtonDisplayed] = useState()
    const [saveButtonDisplayed, setSaveButtonDisplayed] = useState()
    
    
    //edit form changes state, readonly becomes false
    function editForm(e) {
        e.preventDefault();
        
        setDetails(details.forEach((field) => {
            field.readonly = false;
        }))
        setEditButtonDisplayed("none")
        setSaveButtonDisplayed(" ")
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
                throw new Error('respose not ok');
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
        .then(() => {
            setEditButtonDisplayed(" ")
            setSaveButtonDisplayed("none")
        })
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
            getAndSetDetails()
            if (!response.ok) {
                throw new Error('Authentication failed');
            }
            setEditButtonDisplayed(" ")
            setSaveButtonDisplayed("none")
        } catch (error) {
            console.error(error);
        }
    }

    return(
        <Page>
            <Heading as='h1' size='xl' mb={4}>Your personal details</Heading>
            <CustomForm onSubmit={updateDetails} buttonText="Save" buttonDisplayed={saveButtonDisplayed} buttonId="saveDetailsButton" parentState={accountFields} setParentState={setDetails}>
            </CustomForm>
            <CustomButton data-cy="EditButton" display={editButtonDisplayed} onClick={(e) => {editForm(e)}}>
                Edit
            </CustomButton>
        </Page>
    )
}