import { useState } from "react";
import {Button, FormControl, FormErrorMessage, FormHelperText, FormLabel, Input} from "@chakra-ui/react";

function CustomForm({children, onSubmit, buttonText, buttonId, errorOccurred, buttonDisplayed}) {
    const [formData, setFormData] = useState(children)

    return (
        <form onSubmit={onSubmit} style={{display: 'flex', flexDirection: 'column'}}>
            {transformInputs({formData, setFormData, errorOccurred})}
            <Button margin='2' type="submit" data-cy={buttonId} display={buttonDisplayed}>
                {buttonText}
            </Button>
        </form>
    );
}


function transformInputs({formData, setFormData, errorOccurred}) {
    const handleInputChange = (index, event) => {
        const updatedFormData = [...formData];
        updatedFormData[index].value = event.target.value;
        setFormData(updatedFormData);
    };
    
    return (
        formData.map((inputFields, index) => (
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
                        data-cy={inputFields.id}
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