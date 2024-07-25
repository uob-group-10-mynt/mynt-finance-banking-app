import {
    FormControl,
    FormErrorMessage,
    FormHelperText,
    FormLabel,
    Input,
    InputGroup,
    InputLeftElement
} from "@chakra-ui/react";
import CustomButton from "./CustomButton";

function CustomForm({children, onSubmit, buttonText, buttonId, errorOccurred, buttonDisplayed}) {
    return (
        <form onSubmit={onSubmit} style={{display: 'flex', flexDirection: 'column'}}>
            {transformInputs({children, errorOccurred})}
            <CustomButton standard width='100%' margin='2' type='submit' data-cy={buttonId}>
                {buttonText}
            </CustomButton>
        </form>
    );
}

function transformInputs({children, errorOccurred}) {
    return (
        children.map((inputFields) => (
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