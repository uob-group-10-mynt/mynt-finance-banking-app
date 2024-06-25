import React from 'react';
import {useColorMode, Button, useColorModeValue} from '@chakra-ui/react';

function Theme() {
    const {toggleColorMode} = useColorMode()

    return (
        <>
            <Button
                position="fixed"
                top="1rem"
                right="1rem"
                size='sm'
                onClick={toggleColorMode}>
                {useColorModeValue("Light", "Dark")} Mode
            </Button>
        </>
    )
}

export default Theme;
