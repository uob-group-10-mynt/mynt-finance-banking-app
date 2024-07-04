
import axios from 'axios';
import { Onfido } from 'onfido-sdk-ui';
import { useEffect, useState } from 'react';


//TODO: call validateKyc and check KYC critiria to see if the user is approved
function kyc(){

    const [apiResponse, setApiResponse] = useState();  
    
    useEffect(()=>{
        api();
    },[]);
    

    async function api(){
        const h1 = document.getElementById("responce");
        try{
            const response = await axios({
                method:'post',
                url: `http://localhost:8080/api/v1/auth/validateKyc`,
                data:{
                    "workflow_RUN_ID": "14d3ff8f-d512-44c9-b055-e94171781985"
                  }
            });
            setApiResponse(response);
            h1.innerText = JSON.stringify(response.data, null, 2) ;

        } catch (error){
            setApiResponse(error);
            h1.innerText = JSON.stringify(response.data, null ,2 );
        }
    }
        

    return(
        <div>
            <h1 id="kyc">KYC</h1>
            <h1 id="responce"></h1>
        </div>
    );
};

export default kyc;