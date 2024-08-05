import { useEffect, useState } from "react";
import CustomForm from "../components/forms/CustomForm";
import { getUserDetailsAPI, updateUserDetailsAPI } from "../utils/APIEndpoints";
import Page from "../components/Page";
import CustomButton from "../components/forms/CustomButton";
import { border, Heading } from "@chakra-ui/react";

const accountFields = [
    {
        label: "Email",
        id: "email",
        readonly: true,
        value: "",
        type: "text",
        border: "none"
    },
    {
        label: "Date of birth",
        id: "dob",
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
    const [editButtonDisplayed, setEditButtonDisplayed] = useState(" ")
    const [saveButtonDisplayed, setSaveButtonDisplayed] = useState("none")
    
    
    //edit form changes state, readonly becomes false
    function editForm(e) {
        e.preventDefault();
        
        setDetails(details.map((field) => {
            if (field.id !== 'dob' && field.id !== 'email') {
                field.readonly = false;
            }
            return field
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
            setDetails(accountFields.map(field => {
                field.value = data[field.id]
                return field
            }))
        } catch (error) {
            console.error(error);
        }
    }

     useEffect(() => {
         getAndSetDetails()
     }, []) // empty array means useEffect() is only called on initial render of component
    //getAndSetDetails()
    

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
            //getAndSetDetails()
            setDetails(details.map((field) => {
                field.readonly = true;
                return field
            }))
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
            <CustomForm onSubmit={updateDetails} buttonText="Save" buttonDisplayed={saveButtonDisplayed} buttonId="saveDetailsButton" parentState={details} setParentState={setDetails}>
            </CustomForm>
            <CustomButton data-cy="EditButton" display={editButtonDisplayed} onClick={(e) => {editForm(e)}}>
                Edit
            </CustomButton>
        </Page>
    )
}