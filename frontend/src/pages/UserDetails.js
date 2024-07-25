import { useEffect, useState } from "react";
import CustomForm from "../components/forms/CustomForm";
import { getUserDetailsAPI } from "../utils/APIEndpoints";

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

export default function UserDetails() {
    const [details, setDetails] = useState("")
    useEffect(() => {
        getDetails()
        .then(data => {
            data.forEach(d => {
                console.log(d)
            })
            setDetails(data)
            console.log(`details::: ${details}`)
        })
        // .then(() => {

        // })
    })
            return(
                <CustomForm buttonText="Edit">
                    {accountFields}
                </CustomForm>
            )
}