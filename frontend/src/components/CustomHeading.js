import React from "react";
import {Heading} from "@chakra-ui/react";

const CustomHeading = ({children, ...props}) => {
    return (
        <Heading as="h1" size="md" p={2} m={2} {...props}>
            {children}
        </Heading>
    );
};

export default CustomHeading;
