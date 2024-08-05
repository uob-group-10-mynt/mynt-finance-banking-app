import React from 'react';
import {Box} from '@chakra-ui/react';
import CustomHeading from "../CustomHeading";

function PageHeader({children}) {
    return (
        <Box textAlign="center">
            <CustomHeading>
                {children}
            </CustomHeading>
        </Box>
    );
}

export default PageHeader;
