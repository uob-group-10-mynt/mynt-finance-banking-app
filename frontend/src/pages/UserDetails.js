import { useEffect, useState } from "react";
import CustomForm from "../components/forms/CustomForm";
import { getUserDetailsAPI, updateUserDetailsAPI } from "../utils/APIEndpoints";
import Page from "../components/Page";
import CustomButton from "../components/forms/CustomButton";

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



//edit form changes state, readonly becomes false
function editForm(detailsFields, setDetails, e, setEditButtonDisplayed, setSaveButtonDisplayed) {
    e.preventDefault();
    setDetails(detailsFields.forEach((field) => {
        field.readonly = false;
    }))
    setEditButtonDisplayed("none")
    setSaveButtonDisplayed(" ")
}


export default function UserDetails() {
    const [details, setDetails] = useState("")
    const [editButtonDisplayed, setEditButtonDisplayed] = useState()
    const [saveButtonDisplayed, setSaveButtonDisplayed] = useState()

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
    
    const updatedFormData = async (formValuesJSON) => {
        try {
            const response = await fetch(updateUserDetailsAPI, {
                method: 'POST',
                headers: {
                    "Content-type": "application/json",
                    Authorization: `bearer ${sessionStorage.getItem('access')}`
                },
                body: JSON.stringify(formValuesJSON)
            });
            getAndSetDetails()
            if (!response.ok) {
                //TODO clear form? or reset it to previous state
                throw new Error('Authentication failed');
            }
            setEditButtonDisplayed(" ")
            setSaveButtonDisplayed("none")
        } catch (error) {
            //TODO handle errors by redirecting to relevant error page
            console.error(error);
        }
    }

    return(
        <Page>
            <CustomForm onSubmit={updatedFormData} buttonText="Save" buttonDisplayed={saveButtonDisplayed}>
                {accountFields}
            </CustomForm>
            <CustomButton display={editButtonDisplayed} medium onClick={(e) => {editForm(details, setDetails, e, setEditButtonDisplayed, setSaveButtonDisplayed)}}>
                Edit
            </CustomButton>
        </Page>
    )
}