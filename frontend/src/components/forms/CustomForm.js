import {Button, FormControl, FormErrorMessage, FormHelperText, FormLabel, Input} from "@chakra-ui/react";

function CustomForm({children, onSubmit, buttonText, buttonId}) {
    return (
        <form onSubmit={onSubmit} style={{display: 'flex', flexDirection: 'column'}}>
            {transformInputs({children})}
            <Button margin='2' type="submit" data-cy={buttonId}>
                {buttonText}
            </Button>
        </form>
    );
}

function transformInputs({children}) {
    return (
        children.map((inputFields) => (
            <div key={inputFields.label}>
                <FormControl isRequired={inputFields.required} margin='0.5em'>
                    <FormLabel>{inputFields.label}</FormLabel>
                    <Input
                        margin='0.5em'
                        placeholder={inputFields.placeholder}
                        type={inputFields.type}
                        value={inputFields.value}
                        onChange={inputFields.onChange}
                        required={inputFields.required}
                        data-cy={inputFields.id}
                    />
                    <FormErrorMessage data-cy="errorMessage">{inputFields.errorMsg}</FormErrorMessage>
                    {inputFields.helperText && <FormHelperText>{inputFields.helperText}</FormHelperText>}
                </FormControl>
            </div>
        ))
    );
}

export default CustomForm;