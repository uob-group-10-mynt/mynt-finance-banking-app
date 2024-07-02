import { useState } from "react";
import { 
  Flex, 
  Box,
  FormControl, 
  FormLabel, 
  Input, 
  Button 
} from "@chakra-ui/react";

import PageHeader from "../components/forms/PageHeader";
import useAxios from "../hooks/useAxios";

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
  
    const handleSubmit = async (e) => {
      e.preventDefault();
      setLoading(true);
      setError(null);
  
      const credentials = { email, password };
  
      try {
        const response = await fetch('http://localhost:8080/api/v1/auth/authenticate', {
          method: 'POST',
          headers: { "Content-type": "application/json" },
          body: JSON.stringify(credentials)
        });
  
        if (!response.ok) {
          throw new Error('Authentication failed');
        }
        console.log(response.json());
        console.log('Form submitted', credentials);
        setEmail('');
        setPassword('');
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };


    // const [email, setEmail] = useState('');
    // const [password, setPassword] = useState('');
    // const handleSubmit = (e) => {
    //   e.preventDefault();
    //   const { fetchData, error, loading } = useAxios('/auth/authenticate', 
    //     'POST', {
    //       data: {
    //         email,
    //         password
    //       }
    //     })
    //     console.log(fetchData, error, loading)
    //     setEmail(' ')
    //     setPassword(' ')
    // }
    //TODO hash password before posting it
    // const handleSubmit = (e) => {
    //   e.preventDefault();
    //   const credentials = { email, password };
    //   //TODO send POST request
    //   fetch('http://localhost:8080/api/v1/auth/authenticate', {
    //     method: 'POST',
    //     headers: { "Content-type": "application/json" },
    //     body: JSON.stringify(credentials)
    //   }).then(()=> {
    //     console.log('Form submitted', credentials);
    //     setEmail('');
    //     setPassword('');
    //   })
    // }

    const loginFieldsInputList = [
      { label: "Email", testId: "emailInput", placeholder: "hello@email.com", type: "email", value: email, required: true, onChange: (e) => setEmail(e.target.value) },
      { label: "Password", testId: "passwordInput", placeholder: "*******", type: "password", value: password,required: true, onChange: (e) => setPassword(e.target.value) },
    ]

    const inputFields = loginFieldsInputList.map((inputList) => {
      return (
        <FormControl isRequired={inputList.required} key={inputList.label}>
          <FormLabel>{inputList.label}</FormLabel>
          <Input 
            isRequired={inputList.required} 
            type={inputList.type} 
            placeholder={inputList.placeholder} 
            value={inputList.value}
            onChange={inputList.onChange}
            data-cy={inputList.testId}
            />
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