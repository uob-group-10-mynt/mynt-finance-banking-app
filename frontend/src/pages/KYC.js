import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Flex, Box, Heading, FormControl, FormLabel, Input, Button, Text } from "@chakra-ui/react";
import Cookies from 'js-cookie';
import { validateKYCAPI } from '../utils/APIEndpoints';
import SplashPage from './SplashPage';

function kyc(){
    const [loading, setLoading] = useState(true)
    const [apiResponse, setApiResponse] = useState();  
    const [message, setMessage] = useState();  
    const navigate = useNavigate();

    const api = async () => {
        const email = Cookies.get("email");
        try{
            const response = await axios.post(validateKYCAPI, {"email" :email})
            setMessage(`Your account has been ${JSON.parse(response.data.data).status} by our team.`);
            Cookies.set("email","");
        } catch (error){
            setApiResponse(error);
        }
        setLoading(false)
    }
    
    const handleButtonClick = () => {
        navigate('/login')
    };

    useEffect(()=>{
        api()
    },[])

    return(
        loading ? <SplashPage /> :
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