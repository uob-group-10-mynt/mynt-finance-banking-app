import { useState } from "react";
import {Button, FormControl, FormErrorMessage, FormHelperText, FormLabel, Input} from "@chakra-ui/react";

function formDataToRequestBody(credentials) {
    // converts form data and returns JSON body  
    // credential.id MUST match the names of the keys in the JSON schema in Swagger/for the backend 
    // e.g. "email", or "firstname"
    const body = {}
    credentials.forEach(credential => {
        body[credential.id] = credential.value
    })
    return body;
}

function CustomForm({parentState, setParentState, onSubmit, buttonText, buttonId, errorOccurred, buttonDisplayed}) {
    return (
        <form onSubmit={(e) => {
            e.preventDefault();
            onSubmit(formDataToRequestBody(parentState));
        }
        } style={{display: 'flex', flexDirection: 'column'}}>
            {transformInputs({parentState, setParentState, errorOccurred})}
            <Button margin='2' type="submit" data-cy={buttonId} display={buttonDisplayed}>
                {buttonText}
            </Button>
        </form>
    );
}

function transformInputs({parentState, setParentState, errorOccurred}) {
    const handleInputChange = (index, event) => {
        const updatedFormData = [...parentState];
        updatedFormData[index].value = event.target.value;
        setParentState(updatedFormData);
    };
    
    return (
        parentState.map((inputFields, index) => (
            <div key={inputFields.label}>
                <FormControl isRequired={inputFields.required} margin='0.5em' isInvalid={errorOccurred}>
                    <FormLabel>{inputFields.label}</FormLabel>
                    <Input
                        margin='0.5em'
                        placeholder={inputFields.placeholder}
                        type={inputFields.type}
                        value={inputFields.value}
                        onChange={(e) => handleInputChange(index, e)}
                        required={inputFields.required}
                        data-cy={inputFields.id+"Input"}
                        readOnly={inputFields.readonly}
                    />
                    {
                        !errorOccurred ? (
                            <FormHelperText>{inputFields.helperText}</FormHelperText>
                        ) : (
                            <FormErrorMessage data-cy="errorMessage">{inputFields.errorMsg}</FormErrorMessage>
                        )
                    }
                </FormControl>
            </div>
        ))
    );
}

export default CustomForm;