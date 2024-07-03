
import axios from 'axios';
import { Onfido } from 'onfido-sdk-ui';
import { useEffect, useState } from 'react';


//TODO: call validateKyc and check KYC critiria to see if the user is approved
function kyc(){

    const [data, setData] = useState(null);

    useEffect(()=>{
        // const registra = async () => {
            
        //     try{
        //         const response = await axios({
        //             method:'post',
        //             url: `http://localhost:8080/api/v1/auth/register`,
        //             data:{
        //                 "firstname": "James",
        //                 "lastname": "Love",
        //                 "password": "HelloBristol!",
        //                 "email": "James@jameslove.com",
        //                 "role": "USER"
        //               }
        //         });
        //         alert(response.request); 
        //         setData(response);
        //     } catch (error){
        //         alert("clickOnce: "+error);
        //     }
            
            
            
        // } 

        // document.getElementById("kyc").addEventListener("click",()=>{
        //     registra();
        //     console.log("response: "+data);
        // });

        Onfido.init({
            token: '<YOUR_SDK_TOKEN>',
            containerId: 'onfido-mount',
            //containerEl: <div id="root" />, an ALTERNATIVE to `containerId`
            onComplete: function (data) {
              console.log('everything is complete')
            },
            workflowRunId: '<YOUR_WORKFLOW_RUN_ID>',
          })

    },[]);


    // useEffect(()=>{
    //     Onfido.init({
    //         token: '<YOUR_SDK_TOKEN>',
    //         containerId: 'onfido-mount',
    //         steps: ["welcome", "document", "face", "complete"],
    //     });
    // },[]);

    return(
        <div>
            <h1 id="kyc">KYC</h1>
            {/* <div id="onfido-mount"></div> */}
        </div>
    );
};

export default kyc;