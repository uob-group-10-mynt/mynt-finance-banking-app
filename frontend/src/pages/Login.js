import {useState, useContext} from "react";
import {LoggedInContext} from "../App";
import PageHeader from "../components/forms/PageHeader";
import {useNavigate} from "react-router-dom";
import {authenticateAPI} from "../utils/APIEndpoints";
import CustomForm from "../components/forms/CustomForm";
import Page from "../components/Page";

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
            const response = await fetch(authenticateAPI, {
                method: 'POST',
                headers: {"Content-type": "application/json"},
                body: JSON.stringify(formValuesJSON)
            });
            setEmail('');
            setPassword('');
            if (!response.ok) {
                setErrorOccurred(true)
                throw new Error('Authentication failed');
            }
            setErrorOccurred(false)
            const data = await response.json()
            sessionStorage.setItem('access', data.access_token)
            sessionStorage.setItem('refresh', data.refresh_token)
            setLoggedIn(true)
            navigate('/')
        } catch (error) {
            //TODO handle errors by redirecting to relevant error page
            console.error(error);
        }
    }

    return (
        <Page>
            <PageHeader>Login</PageHeader>
            <CustomForm onSubmit={handleLoginSubmit} buttonText="Sign In" buttonId="submitButton"
                        errorOccurred={errorOccurred} parentState={formDetails} setParentState={setFormDetails}>
                {loginInputFields}
            </CustomForm>
        </Page>
    );
}

export default Login;