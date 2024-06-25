<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 6c05a7e (login form is setup to send POST request to API; bug fix in remittance form - couldn't input values into form fields)
import React, { useState } from "react";
import { 
  Flex, 
  Box, 
  Heading, 
  FormControl, 
  FormLabel, 
  Input, 
  Button 
} from "@chakra-ui/react";
<<<<<<< HEAD

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    //TODO hash password before posting it
    const handleSubmit = (e) => {
      e.preventDefault();
      const credentials = { email, password };
      //TODO send POST request
      fetch('URL FOR API ENDPOINT', {
        method: 'POST',
        headers: { "Content-type": "application/json" },
        body: JSON.stringify(credentials)
      }).then(()=> {
        console.log('Form submitted', credentials);
        setEmail('');
        setPassword('');
      })
    }
    const loginFieldsInputList = [
      { label: "Email", placeholder: "hello@email.com", type: "email", value: email, required: true, onChange: (e) => setEmail(e.target.value) },
      { label: "Password", placeholder: "*******", type: "password", value: password,required: true, onChange: (e) => setPassword(e.target.value) },
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
            />
        </FormControl>  
      );
    });

    return(
      <Flex width="full" align="center" justifyContent="center">
        <Box p={2} className="remittance-page">
          <Box textAlign="center">
            <Heading>Login</Heading>
          </Box>
          <Box my={4} textAlign="left">
            <form onSubmit={handleSubmit}>
              {inputFields}
              <Button width="full" mt={4} type="submit">
                Sign In 
              </Button>
            </form>
          </Box>
        </Box>
      </Flex>
=======
import React from "react";
import { Flex, Box, Heading, FormControl, FormLabel, Input, Button } from "@chakra-ui/react";
=======
>>>>>>> 6c05a7e (login form is setup to send POST request to API; bug fix in remittance form - couldn't input values into form fields)
=======
import React from "react";
import { Flex, Box, Heading, FormControl, FormLabel, Input, Button } from "@chakra-ui/react";
>>>>>>> 09a0074 ([Revert] :  "used wrong branch sorry - login form is setup to send POST request to API; bug fix in remittance form - couldn't input values into form fields")

const Login = () => {
    return(
        <Flex width="full" align="center" justifyContent="center">
      <Box p={2}>
        <Box textAlign="center">
          <Heading>Login</Heading>
        </Box>
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 09a0074 ([Revert] :  "used wrong branch sorry - login form is setup to send POST request to API; bug fix in remittance form - couldn't input values into form fields")
        <Box my={4} textAlign="left">
          <form>
            <FormControl>
              <FormLabel>Email</FormLabel>
              <Input type="email" placeholder="test@test.com" />
            </FormControl>
            <FormControl mt={6}>
              <FormLabel>Password</FormLabel>
              <Input type="password" placeholder="*******" />
            </FormControl>
            <Button width="full" mt={4} type="submit">
              Sign In
            </Button>
          </form>
        </Box>
      </Box>
    </Flex>
<<<<<<< HEAD
>>>>>>> e9698c7 (basic routing and login form)
=======
      </Flex>
>>>>>>> 6c05a7e (login form is setup to send POST request to API; bug fix in remittance form - couldn't input values into form fields)
=======
>>>>>>> 09a0074 ([Revert] :  "used wrong branch sorry - login form is setup to send POST request to API; bug fix in remittance form - couldn't input values into form fields")
    );
}

export default Login;