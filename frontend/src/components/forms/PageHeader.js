// CustomHeader.js
import React from 'react';
import {Box, Heading} from '@chakra-ui/react';

function PageHeader({title}) {
    return (
        <Box mt="10px" mb="10px" textAlign="center">
            <Heading fontSize="20px">
                {title}
            </Heading>
        </Box>
    );
}

export default PageHeader;
