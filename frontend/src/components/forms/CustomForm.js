import {Button, FormControl, FormErrorMessage, FormHelperText, FormLabel, Input} from "@chakra-ui/react";

function CustomForm({children, onSubmit, buttonText, testId}) {
    return (
        <form onSubmit={onSubmit} style={{display: 'flex', flexDirection: 'column'}}>
            {transformInputs({children})}
            <Button margin='2' type="submit" data-cy={testId}>
                {buttonText}
            </Button>
        </form>
    );
}

function transformInputs({children}) {
    return (
        children.map((inputList) => (
            <div key={inputList.label}>
                <FormControl isRequired={inputList.required} margin='0.5em'>
                    <FormLabel>{inputList.label}</FormLabel>
                    <Input
                        margin='0.5em'
                        placeholder={inputList.placeholder}
                        type={inputList.type}
                        value={inputList.value}
                        onChange={inputList.onChange}
                        required={inputList.required}
                        data-cy={inputList.testId}
                    />
                    <FormErrorMessage data-cy="errorMessage">{inputList.errorMsg}</FormErrorMessage>
                    {inputList.helperText && <FormHelperText>{inputList.helperText}</FormHelperText>}
                </FormControl>
            </div>
        ))
    );
}

export default CustomForm;