import {useState, useContext} from "react";
import {LoggedInContext} from "../App";
import PageHeader from "../components/forms/PageHeader";
import {useNavigate} from "react-router-dom";
import {authenticateAPI} from "../utils/APIEndpoints";
import CustomForm from "../components/forms/CustomForm";
import Page from "../components/Page";
import makeRequest from "../utils/Requester";

const loginInputFields = [
    {
        label: "email",
        id: "email",
        placeholder: "hello@email.com",
        type: "email",
        value: "",
        required: true,
        errorMsg: "Incorrect email or password",
        helperText: "The email address associated with your account"
    },
    {
        label: "password",
        id: "password",
        placeholder: "*******",
        type: "password",
        value: "",
        required: true,
        errorMsg: "Incorrect email or password"
    },
]


function Login() {
    const [loggedIn, setLoggedIn] = useContext(LoggedInContext)
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorOccurred, setErrorOccurred] = useState('');
    const [formDetails, setFormDetails] = useState(loginInputFields)
    const navigate = useNavigate();

    const handleLoginSubmit = async (formValuesJSON) => {
        try {
            const data = await makeRequest(
                'POST',
                authenticateAPI,
                false,
                null,
                formValuesJSON
            )
            console.log("data:: ", data)
            sessionStorage.setItem('access', data.access_token)
            sessionStorage.setItem('refresh', data.refresh_token)
            setEmail('');
            setPassword('');
            setErrorOccurred(false)
            setLoggedIn(true)
            navigate('/accounts')
        } catch (error) {
            console.error("error occurred: ", error)
            setErrorOccurred(true)
        }
    }

    return (
        <Page>
            <PageHeader>Login</PageHeader>
            <CustomForm onSubmit={handleLoginSubmit} buttonText="Sign In" buttonId="submitButton"
                        errorOccurred={errorOccurred} parentState={formDetails} setParentState={setFormDetails}>
            </CustomForm>
        </Page>
    );
}

export default Login;