import React from 'react';
import reactDom  from 'react-dom';
// import { useState } from "react";
import { useEffect, useState } from 'react';
import { Flex, Box, Heading, FormControl, FormLabel, Input, Button, Text } from "@chakra-ui/react";
import axios from 'axios';

function signUp(){
    const [firstName, setFirstName] = useState('');
    const [surname, setSurname] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [message, setMessage] = useState('');


    const handleSubmit = async (e) => {
      e.preventDefault();
      
      try {
        const response = await axios.post('http://localhost:8080/api/v1/auth/sdk', {
          firstname: "firsdsfadsfddsftName",
          lastname: "surdfdaadsfdssfname",
          email: "edsafdfddsafdsamail",
        }) 
        console.log("response: "+JSON.stringify(response));
        } catch (error) {
          console.error('There was an error!', error);
          setMessage('An error occurred. Please try again later.');
        };

      
      if (password === confirmPassword) {
          const credentials = { firstName, surname,  email, password , confirmPassword };
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

    };  


      //TODO send POST request
      //  fetch('URL FOR API ENDPOINT', {
      //    method: 'POST',
      //    headers: { "Content-type": "application/json" },
      //    body: JSON.stringify(credentials)
      //  }).then(()=> {
      //    console.log('Form submitted', credentials);
      //    setEmail('');
      //    setPassword('');
      //  })

    const fieldsInputList = [
      { 
        label: "FirstName",
        testId: "firstNameInput",
        placeholder: "James",
        type: "firstName",
        value: firstName,
        // required: true,
        onChange: (e) => setFirstName(e.target.value) },
      { 
        label: "Surname", 
        testId: "surnameInput",
        placeholder: "Love",
        type: "surname",
        value: surname,
        // required: true,
        onChange: (e) => setSurname(e.target.value) 
      },
      { 
        label: "Email", 
        testId: "emailInput",
        placeholder: "james@jameslove.com",
        type: "email",
        value: email,
        // required: true,
        onChange: (e) => setEmail(e.target.value) 
      },
      { 
        label: "Password", 
        testId: "passwordInput",
        placeholder: "*******",
        type: "password",
        value: password,
        // required: true,
        onChange: (e) => setPassword(e.target.value) 
      },
      { 
        label: "Confirm Password", 
        testId: "confirmPasswordInput",
        placeholder: "*******",
        type: "password",
        value: confirmPassword,
        // required: true,
        onChange: (e) => setConfirmPassword(e.target.value) 
      },
    ];

    const inputFields = fieldsInputList.map((inputList) => {
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
          <Box textAlign="center">
            <Heading>Sign Up</Heading>
          </Box>
          <Box my={4} textAlign="left">
            <form onSubmit={handleSubmit}>
              {inputFields}
              <Text color={'red.500'}>{message}</Text>
              <Button data-cy="submitButton" width="full" mt={4} type="submit">
                Sign In 
              </Button>
            </form>

            {/* <iframe src='https://eu.onfido.app/l/014a828f-8a93-4651-b446-081b02d45c2b'></iframe> */}
          </Box>
        </Box>
      </Flex>
    );
}

export default signUp;