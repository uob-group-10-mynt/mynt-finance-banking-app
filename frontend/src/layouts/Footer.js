import {Box, Flex, Text} from '@chakra-ui/react';

export default function Footer({children}) {
    return (
        <Box position="absolute" bottom="0" width="100%" textAlign="center" bg="#2E8B57" color="white" py="6">
            {/*{children}*/}
            <Flex justify="center" mt="2">
                <Text fontSize="sm">&copy; 2024 Mynt. All rights reserved.</Text>
            </Flex>
        </Box>
    );
};
