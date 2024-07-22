import { Box } from "@chakra-ui/react";

const Page = ({children}) => (
    <Box
        maxW={["100%", "100%", "90%", "600px"]}
        p={["1rem", "1.5rem"]}
        borderRadius="0.5rem"
        boxShadow="0 0 10px rgba(0, 0, 0, 0.1)" // Box shadow with 10px blur and 0.1 transparency
        justifyContent="center"
        alignItems="center"
        gap='1.3em'
        margin='auto'
        bg='white'
    >
        {children}
    </Box>
);

export default Page;
