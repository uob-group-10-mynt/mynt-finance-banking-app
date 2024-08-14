import { Box, Input } from "@chakra-ui/react";
import { SearchIcon } from '@chakra-ui/icons';

export default function SearchBar({ value, onChange, placeholder }) {
  return (
    <Box 
      width={['80%', '70%', '65%']} 
      height={['50px', '65px', '80px']}
      bg='white'
      textAlign='center'
      padding='1em'
      borderRadius='full'
      display='flex'
      flexDirection='row'
      justifyContent='start'
      alignItems='center'
      gap='0.2em'
      boxShadow='md' 
    >
      <SearchIcon boxSize={[ '4', '5', '6' ]}  color='gray.500' marginLeft='6px' />
      <Input
        placeholder={placeholder}
        value={value}
        onChange={onChange}
        variant='outline'
        border='none' 
        borderRadius='full'
        fontWeight='bold'
        size='md'
        _input={{ 
          fontWeight: 'bold',
          fontSize: [ '0.7em', '0.9em', '1.5em' ], 
        }}
        _placeholder={{ 
          color: 'gray.500',
          fontSize: [ '0.7em', '0.9em', '1.5em' ],
        }}
        _focus={{ 
          borderColor: 'transparent', 
          boxShadow: 'none',
        }}
      />
    </Box>
  );
}
