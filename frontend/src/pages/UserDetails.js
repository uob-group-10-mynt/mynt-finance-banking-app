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
        response.json()
        .then(data => {
            accountFields.forEach(field => {
                field.value = data[field.id]
            })
        })
        .then(() => {
            return accountFields
        })
    } catch (error) {
        console.error(error);
    }
}

export default function UserDetails() {
        getDetails()
        .then(() => {
            return(
                <CustomForm buttonText="Edit">
                    {accountFields}
                </CustomForm>
            )
        })
}