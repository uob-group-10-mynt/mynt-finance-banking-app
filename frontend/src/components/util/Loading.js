import ReactDOM from 'react-dom';
import { useEffect } from 'react';
import { Spinner, Text, Box } from "@chakra-ui/react";

function Loading() {
  useEffect(() => {
    document.body.classList.add('overflow-hidden');
    
    return () => {
      document.body.classList.remove('overflow-hidden');
    }
  }, []);

  return ReactDOM.createPortal(
    <Box 
      position="fixed" 
      top="0" 
      left="0" 
      width="100vw" 
      height="100vh" 
      display="flex" 
      justifyContent="center" 
      alignItems="center" 
      backgroundColor="rgba(209, 213, 219, 0.8)"
      zIndex="1000" 
    >
      <Box 
        display="flex" 
        flexDirection="column" 
        justifyContent="center" 
        alignItems="center" 
        backgroundColor="white" 
        padding="4em" 
        borderRadius="md"
        boxShadow="lg"
        width='20em'
        height='17em'
      >
        <Spinner
          thickness="4px"
          speed="0.65s"
          color="teal"
          size="xl"
        />
        <Text fontSize="md" mt={4}>Loading .... Please Wait</Text>
      </Box>
    </Box>,
    document.getElementById('loader-container')
  );
}

export default Loading;
