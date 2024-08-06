import React from 'react';
import {useEffect, useState, useRef} from 'react';
import axios from 'axios';
import {Onfido} from 'onfido-sdk-ui';
import {onfidoIdetityCheckAPI} from '../utils/APIEndpoints';
import PageHeader from "../components/forms/PageHeader";
import CustomForm from "../components/forms/CustomForm";
import Page from "../components/Page";
import {Center, Square, Circle} from '@chakra-ui/react';
import Cookies from 'js-cookie';

const signupInputFields = [
    {
        label: "Email",
        id: "email",
        placeholder: "james@jameslove.com",
        type: "email",
        value: "",
        required: true,
        errorMsg: "email address must have correct format"
    },
    {
        label: "First Name",
        id: "firstname",
        placeholder: "James",
        type: "firstname",
        value: "",
        required: true,
        errorMsg: "invalid credentials"
    },
    {
        label: "Surname",
        id: "lastname",
        placeholder: "Love",
        type: "lastname",
        value: "",
        required: true,
        errorMsg: "invalid credentials"
    },
    {
        label: "Date of Birth",
        id: "dob",
        placeholder: "16 08 1996",
        type: "date",
        value: "",
        required: true,
        errorMsg: "please select a date"
    },
    {
        label: "Address",
        id: "address",
        placeholder: "BS8 1HB",
        type: "address",
        value: "",
        required: true,
        errorMsg: "enter your address"
    },
    {
        label: "Phone Number",
        id: "phoneNumber",
        placeholder: "+44 7824792473",
        type: "phoneNumber",
        value: "",
        required: true,
        errorMsg: "invalid credentials"
    },
    {
        label: "Password",
        id: "password",
        placeholder: "*******",
        type: "password",
        value: "",
        required: true,
        errorMsg: "invalid credentials"
    },
    {
        label: "Confirm Password",
        id: "confirmPassword",
        placeholder: "*******",
        type: "password",
        value: "",
        required: true,
        errorMsg: "invalid credentials"
    },
];

function Signup() {
    const [formData, setFormData] = useState(signupInputFields)
    const [errorOccurred, setErrorOccurred] = useState('');
    const [iframe, setiframe] = useState('');


    const handleSubmit = (formValuesJSON) => {
        apiCalls(formValuesJSON);
    };

    async function apiCalls(formValuesJSON) {
        try {

            let response = await axios.post(onfidoIdetityCheckAPI, {
                ...formValuesJSON
            })
            kycChecks(response, formValuesJSON.email);
            Cookies.put("email", formValuesJSON.email);
        } catch (error) {
            setErrorOccurred(true)
        }
    }

    function kycChecks(response, email) {
        if (response.status != 200) {
            setErrorOccurred(true)
            throw new Error('Error signing up');
        }
        const data = JSON.parse(response.data.data);
        let urlink = data.url;
        document.cookie = `email=${email}`;
        console.log("cookie", document.cookie);
        setiframe(urlink);
    }


    return (
        <Page>
            <PageHeader>Sign Up</PageHeader>
            {iframe ? (
                <Center>
                    <iframe src={iframe} style={{height: "700px"}}></iframe>
                </Center>
            ) : (
                <CustomForm onSubmit={handleSubmit} buttonText="Sign Up" buttonId="submitButton" errorOccurred={errorOccurred} parentState={formData} setParentState={setFormData}>
                </CustomForm>
            )}
        </Page>
    );
}

export default Signup;