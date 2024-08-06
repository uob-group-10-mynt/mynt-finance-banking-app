import React from 'react';
import {Box} from '@chakra-ui/react';
import CustomHeading from "../CustomHeading";

function PageHeader({children, ...props}) {
    return (
        <Box textAlign="center">
            <CustomHeading {...props}>
                {children}
            </CustomHeading>
        </Box>
    );
}

export default PageHeader;
