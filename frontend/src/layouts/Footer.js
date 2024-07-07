import { Box, Flex, Link, Text } from '@chakra-ui/react';

const Footer = () => {
    return (
        <Box as="footer" bg="gray.800" color="white" py="6">
            <Flex justify="center">
                <Link mx="2" href="/">Home</Link>
                <Link mx="2" href="signup">Sign Up</Link>
                <Link mx="2" href="remittance">Transfer</Link>
                <Link mx="2" href="#">Contact</Link>
            </Flex>
            <Flex justify="center" mt="2">
                <Text fontSize="sm">&copy; 2024 Mynt. All rights reserved.</Text>
            </Flex>
        </Box>
    );
};

export default Footer;
