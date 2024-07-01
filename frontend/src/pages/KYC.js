
import axios from 'axios';
import { Onfido } from 'onfido-sdk-ui';
import { useEffect } from 'react';

function kyc(){

    // axios.



    useEffect(()=>{
        Onfido.init({
            token: '<YOUR_SDK_TOKEN>',
            containerId: 'onfido-mount',
            steps: ["welcome", "document", "face", "complete"],
        });
    },[]);

    return(
        <div>
            <h1>KYC</h1>
            <div id="onfido-mount"></div>
        </div>
    );
};

export default kyc;