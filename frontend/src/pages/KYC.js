
import axios from 'axios';
import { Onfido } from 'onfido-sdk-ui';
import { useEffect, useState } from 'react';
import signUp from './CreateUser';
import { useNavigate } from 'react-router-dom';
import { Flex, Box, Heading, FormControl, FormLabel, Input, Button, Text } from "@chakra-ui/react";
import Cookies from 'js-cookie';
import { validateKYCAPI } from '../utils/APIEndpoints';

//TODO: call validateKyc and check KYC critiria to see if the user is approved
function kyc(){

    const navigate = useNavigate();

    const [apiResponse, setApiResponse] = useState();  
    
    useEffect(()=>{
        api();
    },[]);
    
    
    async function api(){
        const h1 = document.getElementById("responce");
        
        const email = Cookies.get("email");
        // const email =  document.cookie;
        console.log("document.cookie: "+Cookies.get("email")); 
        Cookies.set("email","");

        try{
            const response = await axios({
                method:'post',
                url: validateKYCAPI,
                data:{
                    "email": email,
                  }
            });
            setApiResponse(response);
            
            let data = JSON.parse(response.data.data);
            h1.innerText = `Your account is has been ${data.status} by our team.`; // JSON.stringify(, null, 4) ;
        } catch (error){
            setApiResponse(error);
            // h1.innerText = JSON.stringify(response.data, null ,2 );
        }
    }

    const handleButtonClick = () => {
        navigate('/Login');
    };


    return(
        <Flex height="100vh" width="full" align="center" justifyContent="center">
          
          <Box>
            <Box textAlign="center">
                <h1 id="responce"></h1>
            </Box>
            <Button onClick={handleButtonClick} width="full" mt={4} type="submit">
                Proceed 
            </Button>
          </Box>
                    
        </Flex>
    );
};

export default kyc;