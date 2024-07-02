import { useState, useContext } from "react";
import { 
  Flex, 
  Box,
  FormControl, 
  FormLabel, 
  Input, 
  Button, 
  FormErrorMessage
} from "@chakra-ui/react";
import { LoggedInContext } from "../App";
import PageHeader from "../components/forms/PageHeader";
import { useNavigate } from "react-router-dom";

const Login = () => {
    const [loggedIn, setLoggedIn] = useContext(LoggedInContext)
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [invalidCredentialsMsg, setInvalidCredentialsMsg] = useState('');
    const navigate = useNavigate();
  
    const handleSubmit = async (e) => {
      e.preventDefault();
      const credentials = { email, password };
  
      try {
        const response = await fetch('http://localhost:8080/api/v1/auth/authenticate', {
          method: 'POST',
          headers: { "Content-type": "application/json" },
          body: JSON.stringify(credentials)
        });
        setEmail('');
        setPassword('');
        if (!response.ok) {
          setInvalidCredentialsMsg('incorrect email or password')
          throw new Error('Authentication failed');
        }
        
        const data = await response.json()
        console.log('access: ', data.access_token)
        console.log('refresh: ', data.refresh_token)
        console.log('REPONSE: ', data);
        sessionStorage.setItem('access', data.access_token)
        sessionStorage.setItem('refresh', data.refresh_token)
        setLoggedIn(true)
        navigate('/')
      } catch (error) {
        console.log(error);
      } 
    };

    const loginFieldsInputList = [
      { label: "Email", testId: "emailInput", placeholder: "hello@email.com", type: "email", value: email, required: true, onChange: (e) => setEmail(e.target.value), errorMsg: "Incorrect email or password" },
      { label: "Password", testId: "passwordInput", placeholder: "*******", type: "password", value: password,required: true, onChange: (e) => setPassword(e.target.value), errorMsg: "Incorrect email or password" },
    ]

    const inputFields = loginFieldsInputList.map((inputList) => {
      return (
        <FormControl isInvalid={invalidCredentialsMsg} isRequired={inputList.required} key={inputList.label}>
          <FormLabel>{inputList.label}</FormLabel>
          <Input 
            isRequired={inputList.required} 
            type={inputList.type} 
            placeholder={inputList.placeholder} 
            value={inputList.value}
            onChange={inputList.onChange}
            data-cy={inputList.testId}
            />
            <FormErrorMessage data-cy="errorMessage">{inputList.errorMsg}</FormErrorMessage>
        </FormControl>  
      );
    });

    return(
      <Flex width="full" align="center" justifyContent="center">
        <Box p={2} className="remittance-page">
          <PageHeader title="Login"/>
          <Box my={4} textAlign="left">
            <form onSubmit={handleSubmit}>
              {inputFields}
              <Button data-cy="submitButton" width="full" mt={4} type="submit">
                Sign In 
              </Button>
            </form>
          </Box>
        </Box>
      </Flex>
    );
}

export default Login;