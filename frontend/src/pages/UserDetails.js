import { useEffect, useState } from "react";
import CustomForm from "../components/forms/CustomForm";
import { getUserDetailsAPI } from "../utils/APIEndpoints";
import Page from "../components/Page";
import CustomButton from "../components/forms/CustomButton";

const accountFields = [
    {
        label: "First name",
        id: "firstname",
        required: true,
        readonly: true,
        value: "",
    },
    {
        label: "Last name",
        id: "lastname",
        required: true,
        readonly: true,
        value: "",
    },
    {
        label: "Date of birth",
        id: "dob",
        required: true,
        readonly: true,
        value: "",
    },
    {
        label: "Address",
        id: "address",
        required: true,
        readonly: true,
        value: "",
    },
    {
        label: "Phone number",
        id: "phoneNumber",
        required: true,
        readonly: true,
        value: "",
    },
];

const getDetails = async () => {
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
        return accountFields
    } catch (error) {
        console.error(error);
    }
}

//edit form changes state, readonly becomes false
function editForm(detailsFields, setDetails, e, setEditButtonsVisibility) {
    e.preventDefault();
    setDetails(detailsFields.forEach((field) => {
        field.readonly = false;
    }))
    setEditButtonsVisibility("none")
}

export default function UserDetails() {
    const [details, setDetails] = useState("")
    const [editButtonVisibility, setEditButtonsVisibility] = useState()
    useEffect(() => {
        getDetails()
        .then(data => {
            data.forEach(d => {
                console.log(d)
            })
            setDetails(data)
            setEditButtonsVisibility(" ")
            console.log(`details::: ${details}`)
        })
    }, []) // empty array means useEffect() is only called on initial render of component
    return(
        <Page>
            <CustomForm buttonText="Save">
                {accountFields}
            </CustomForm>
            <CustomButton display={editButtonVisibility} medium onClick={(e) => {editForm(details, setDetails, e, setEditButtonsVisibility)}}>
                Edit
            </CustomButton>
        </Page>
    )
}