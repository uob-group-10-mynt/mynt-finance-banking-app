import React from 'react';
import reactDom  from 'react-dom';
// import { useState } from "react";
import { useEffect, useState, useRef } from 'react';
import { Flex, Box, Heading, FormControl, FormLabel, Input, Button, Text } from "@chakra-ui/react";
import axios from 'axios';
import {Onfido} from 'onfido-sdk-ui';

function signUp(){
    
    const [email, setEmail] = useState('');
    const [firstName, setFirstName] = useState('');
    const [surname, setSurname] = useState('');
    const [dob, setDob] = useState('');
    const [address, setAddress] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const [message, setMessage] = useState('');
    const [apiResponce,setApiResponce ] = useState('');
    const [workflowRunID,setWorkflowRunID ] = useState('');
    const [sdkToken,setSdkToken ] = useState(null);

    const [iframe, setiframe] = useState('');

    const handleSubmit = (e) => {
      e.preventDefault();
      apiCalls();
      clearHooks();
    };  

    function clearHooks(){
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
    }


    async function apiCalls(){
      try {
        let response = await axios.post('http://localhost:8080/api/v1/auth/onfidoSdk', {
          "email": email,
          "firstname": firstName,
          "lastname": surname,
          "dob": dob,
          "address": address,
          "phoneNumber": phoneNumber,
          "password": password
        }) 
        kycChecks(response);
        
      } catch (error) {
          console.error('There was an error!', error);
          setMessage('An error occurred. Please try again later.');
      };
    }

    function kycChecks(response){
      const data =  JSON.parse(response.data.data);
      let urlink = data.url;
      // console.log("urlink -> "+urlink);
      
      document.cookie = `email=${email}`;     

      setiframe(urlink);
    }

    const fieldsInputList = [
      { 
        label: "Email", 
        testId: "emailInput",
        placeholder: "james@jameslove.com",
        type: "email",
        value: email,
        required: true,
        onChange: (e) => setEmail(e.target.value) 
      },
      { 
        label: "FirstName",
        testId: "firstNameInput",
        placeholder: "James",
        type: "firstName",
        value: firstName,
        required: true,
        onChange: (e) => setFirstName(e.target.value) },
      { 
        label: "Surname", 
        testId: "surnameInput",
        placeholder: "Love",
        type: "surname",
        value: surname,
        required: true,
        onChange: (e) => setSurname(e.target.value) 
      },
      { 
        label: "Date Of Birth", 
        testId: "DOBInput",
        placeholder: "16-08-1996",
        type: "dob",
        value: dob,
        required: true,
        onChange: (e) => setDob(e.target.value) 
      },
      { 
        label: "Address", 
        testId: "AddressInput",
        placeholder: "BS8 1HB",
        type: "address",
        value: address,
        required: true,
        onChange: (e) => setAddress(e.target.value) 
      },
      { 
        label: "PhoneNumber", 
        testId: "PhoneNumberInput",
        placeholder: "+44 7824792473",
        type: "phoneNumber",
        value: phoneNumber,
        required: true,
        onChange: (e) => setPhoneNumber(e.target.value) 
      },
      { 
        label: "Password", 
        testId: "passwordInput",
        placeholder: "*******",
        type: "password",
        value: password,
        required: true,
        onChange: (e) => setPassword(e.target.value) 
      },
      { 
        label: "Confirm Password", 
        testId: "confirmPasswordInput",
        placeholder: "*******",
        type: "password",
        value: confirmPassword,
        required: true,
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
        
        <Box p={2} className="page">
        {iframe ? (<iframe src={iframe} style={{height:"700px"}} ></iframe>):(
          <div>
          <Box textAlign="center">
          <Heading>Sign Up</Heading>
        </Box>
        <Box my={10} textAlign="left">
        <form onSubmit={handleSubmit}>
          {inputFields}
          <Text color={'red.500'}>{message}</Text>
          <Button data-cy="submitButton" width="full" mt={4} type="submit">
            Sign In 
          </Button>
        </form>
      </Box>
      </div>
        )}
        </Box>
      </Flex>
    );
}

export default signUp;