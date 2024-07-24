import {FormControl, FormErrorMessage, FormHelperText, FormLabel, Input} from "@chakra-ui/react";
import CustomButton from "./CustomButton";

function CustomForm({children, onSubmit, buttonText, buttonId, errorOccurred}) {
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
                    <FormLabel>{inputFields.label}</FormLabel>
                    <Input
                        placeholder={inputFields.placeholder}
                        type={inputFields.type}
                        value={inputFields.value}
                        onChange={inputFields.onChange}
                        required={inputFields.required}
                        data-cy={inputFields.id}
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