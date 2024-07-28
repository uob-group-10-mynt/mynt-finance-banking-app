import axios from 'axios';
import { Onfido } from 'onfido-sdk-ui';
import { useEffect, useState } from 'react';
import signUp from './Signup';
import { useNavigate } from 'react-router-dom';
import { Flex, Box, Heading, FormControl, FormLabel, Input, Button, Text } from "@chakra-ui/react";
import Cookies from 'js-cookie';
import { validateKYCAPI } from '../utils/APIEndpoints';

//TODO: call validateKyc and check KYC critiria to see if the user is approved
function kyc(){

    const navigate = useNavigate();

    const [apiResponse, setApiResponse] = useState();  
    const [message, setMessage] = useState();  

    useEffect(()=>{
    
        async function api(){
                
            const email = Cookies.get("email");
    
            try{
                
                console.log("\n\n\n\n\nvalidateKYCAPI: "+validateKYCAPI);
                console.log("email: "+email) ;
                
                const response = await axios({
                    method:'post',
                    url: validateKYCAPI,
                    data:{
                        "email": email,
                      }
                });
                
                console.log(response);
                console.log(apiResponse);
                
                setMessage(`Your account has been ${JSON.parse(response.data.data).status} by our team.`);
                
                Cookies.set("email","");
            } catch (error){
                setApiResponse(error);
            }
            
        }
        
        api();
    },[]);

    const handleButtonClick = () => {
        navigate('/login');
    };

    return(
        <Flex height="100vh" width="full" align="center" justifyContent="center">
          
          <Box>
            <Box textAlign="center">
                {message}
            </Box>
            <Button onClick={handleButtonClick} width="full" id= "buttonKyc" mt={4} type="submit">
                Proceed 
            </Button>
          </Box>
                    
        </Flex>
    );
}

export default kyc;