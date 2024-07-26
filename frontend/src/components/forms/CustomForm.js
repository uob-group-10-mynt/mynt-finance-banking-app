import {
    FormControl,
    FormErrorMessage,
    FormHelperText,
    FormLabel,
    Input,
    InputGroup,
    InputLeftElement
} from "@chakra-ui/react";

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
import CustomButton from "./CustomButton";

function CustomForm({children, onSubmit, buttonText, buttonId, errorOccurred, buttonDisplayed}) {
    const [formData, setFormData] = useState(children)

    return (
        <form onSubmit={(e) => {
            e.preventDefault();
            onSubmit(formDataToRequestBody(formData));
        }
        } style={{display: 'flex', flexDirection: 'column'}}>
            {transformInputs({formData, setFormData, errorOccurred})}
            <CustomButton standard width='100%' margin='2' type="submit" data-cy={buttonId} display={buttonDisplayed}>
                {buttonText}
            </CustomButton>
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
                    {
                        inputFields.display === "formattedNumber" ? (
                            <FormLabel>{inputFields.label + Intl.NumberFormat("en-GB").format(inputFields.value)}</FormLabel>
                        ) : <FormLabel>{inputFields.label}</FormLabel>
                    }
                    <InputGroup>
                        {
                            inputFields.inputLeftElement ? (
                                <InputLeftElement color='gray.300' fontSize='1.2rem'>{inputFields.inputLeftElement}</InputLeftElement>
                            ) : null
                        }
                        <Input
                            placeholder={inputFields.placeholder}
                            type={inputFields.type}
                            value={inputFields.value}
                            onChange={inputFields.onChange}
                            required={inputFields.required}
                            data-cy={inputFields.id}
                        />
                    </InputGroup>
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