import error_404_img from '../../public/images/error_404.png';
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, Image, Heading, Text, Button } from '@chakra-ui/react';

function ErrorPage() {
  const navigate = useNavigate();

  const handleGoHome = () => {
    navigate('/');
  };

  return (
    <Box 
      _dark={{ bg: 'gray.800' }} 
      minH='100vh' 
      display='flex' 
      flexDirection={'column'}
      alignItems='center' 
      justifyContent='center' 
      gap={'2em'}
      p={4}
    >
      <Image 
        src={error_404_img} 
        alt="404 Not Found" 
        borderRadius='md'
      />
      <Heading 
        fontSize={{ base: '2xl', md: '4xl', lg: '5xl' }} 
        color='teal.500'
      >
        404 Not Found Error
      </Heading>
      <Text 
        fontSize={{ base: 'md', md: 'lg', lg: 'xl' }} 
        textAlign='center'
        color='gray.600' 
        _dark={{ color: 'gray.300' }}
      >
        The page you were looking for appears to have been removed, deleted or does not exist.
      </Text>
      <Button 
        colorScheme='teal' 
        size='lg' 
        onClick={handleGoHome}
        boxShadow='md'
      >
        Go Home
      </Button>
    </Box>
  );
}

export default ErrorPage;
