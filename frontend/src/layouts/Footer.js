import {Box, Flex, Text} from '@chakra-ui/react';

export default function Footer({children}) {
    return (
        <Box as="footer" textAlign="center" bg="#2E8B57" color="white" py="6"
             my={{base: "2", sm: "4", md: "6", lg: "8", xl: "10"}}>
            {children}
            <Flex justify="center" mt="2">
                <Text fontSize="sm">&copy; 2024 Mynt. All rights reserved.</Text>
            </Flex>
        </Box>
    );
};
