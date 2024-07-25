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
        type: "firstName",
        value: "",
        required: true,
        errorMsg: "invalid credentials"
    },
    {
        label: "Surname",
        id: "lastname",
        placeholder: "Love",
        type: "surname",
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
    const [email, setEmail] = useState('');
    const [firstName, setFirstName] = useState('');
    const [surname, setSurname] = useState('');
    const [dob, setDob] = useState('');
    const [address, setAddress] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [errorOccurred, setErrorOccurred] = useState('');

    const [message, setMessage] = useState('');
    const [apiResponce, setApiResponce] = useState('');
    const [workflowRunID, setWorkflowRunID] = useState('');
    const [sdkToken, setSdkToken] = useState(null);

    const [iframe, setiframe] = useState('');

    const handleSubmit = (formValuesJSON) => {
        postFormAndKYC(formValuesJSON);
    };

    async function postFormAndKYC(formValuesJSON) {
        try {

            let response = await axios.post(onfidoIdetityCheckAPI, {
                ...formValuesJSON
            })
            kycChecks(response);
            Cookies.put("email", formValuesJSON.email);
        } catch (error) {
            console.error('There was an error!', error);
            setMessage('An error occurred. Please try again later.');
        }
    }

    function kycChecks(response) {

        if (!response.status == 200) {
            setErrorOccurred(true)
            throw new Error('Error signing up');
        }
        setErrorOccurred(false)
        const data = JSON.parse(response.data.data);
        let urlink = data.url;

        document.cookie = `email=${email}`;
        console.log(document.cookie);
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
                <CustomForm onSubmit={handleSubmit} buttonText="Sign Up" buttonId="submitButton" errorOccurred={errorOccurred}>
                    {signupInputFields}
                </CustomForm>
            )}
        </Page>
    );
}

export default Signup;