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

function Signup() {
    const [email, setEmail] = useState('');
    const [firstName, setFirstName] = useState('');
    const [surname, setSurname] = useState('');
    const [dob, setDob] = useState('');
    const [address, setAddress] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const [message, setMessage] = useState('');
    const [apiResponce, setApiResponce] = useState('');
    const [workflowRunID, setWorkflowRunID] = useState('');
    const [sdkToken, setSdkToken] = useState(null);

    const [iframe, setiframe] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        apiCalls();
        clearHooks();
    };

    function clearHooks() {
        if (password === confirmPassword) {
            const credentials = {firstName, surname, email, password, confirmPassword};
            setEmail('');
            setFirstName('');
            setSurname('');
            setPassword('');
            setConfirmPassword('');
        } else {
            setPassword('');
            setConfirmPassword('');
            setMessage('Passwords do not match!');
        }
    }

    async function apiCalls() {
        try {

            let response = await axios.post(onfidoIdetityCheckAPI, {
                "email": email,
                "firstname": firstName,
                "lastname": surname,
                "dob": dob,
                "address": address,
                "phoneNumber": phoneNumber,
                "password": password
            })
            kycChecks(response);
            Cookies.put("email", email);


        } catch (error) {
            console.error('There was an error!', error);
            setMessage('An error occurred. Please try again later.');
        }
    }

    function kycChecks(response) {
        const data = JSON.parse(response.data.data);
        let urlink = data.url;
        // console.log("urlink -> "+urlink);

        document.cookie = `email=${email}`;
        console.log(document.cookie);
        setiframe(urlink);
    }

    const signupInputFields = [
        {
            label: "Email",
            id: "emailInput",
            placeholder: "james@jameslove.com",
            type: "email",
            value: email,
            required: true,
            onChange: (e) => setEmail(e.target.value)
        },
        {
            label: "First Name",
            id: "firstNameInput",
            placeholder: "James",
            type: "firstName",
            value: firstName,
            required: true,
            onChange: (e) => setFirstName(e.target.value)
        },
        {
            label: "Surname",
            id: "surnameInput",
            placeholder: "Love",
            type: "surname",
            value: surname,
            required: true,
            onChange: (e) => setSurname(e.target.value)
        },
        {
            label: "Date of Birth",
            id: "DOBInput",
            placeholder: "16 08 1996",
            type: "date",
            value: dob,
            required: true,
            onChange: (e) => setDob(e.target.value)
        },
        {
            label: "Address",
            id: "AddressInput",
            placeholder: "BS8 1HB",
            type: "address",
            value: address,
            required: true,
            onChange: (e) => setAddress(e.target.value)
        },
        {
            label: "Phone Number",
            id: "PhoneNumberInput",
            placeholder: "+44 7824792473",
            type: "phoneNumber",
            value: phoneNumber,
            required: true,
            onChange: (e) => setPhoneNumber(e.target.value)
        },
        {
            label: "Password",
            id: "passwordInput",
            placeholder: "*******",
            type: "password",
            value: password,
            required: true,
            onChange: (e) => setPassword(e.target.value)
        },
        {
            label: "Confirm Password",
            id: "confirmPasswordInput",
            placeholder: "*******",
            type: "password",
            value: confirmPassword,
            required: true,
            onChange: (e) => setConfirmPassword(e.target.value)
        },
    ];

    return (
        <Page>
            <PageHeader>Sign Up</PageHeader>
            {iframe ? (
                <Center>
                    <iframe src={iframe} style={{height: "700px"}}></iframe>
                </Center>
            ) : (
                <CustomForm onSubmit={handleSubmit} buttonText="Sign Up" buttonId="submitButton">
                    {signupInputFields}
                </CustomForm>
            )}
        </Page>
    );
}

export default Signup;