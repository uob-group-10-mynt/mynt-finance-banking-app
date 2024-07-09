import { Button, Text } from '@chakra-ui/react';


const buttonStyleMapper = {
  standard: {
    variant: 'solid',
    color: 'white',
    colorScheme: 'teal',
    width: '80%',
    padding: { base: '0.5em', md: '0.75em', lg: '1em' }, 
    height: { base: '2em', md: '2.5em', lg: '3em' },     
  },

  confirm: {
    variant: 'solid',
    color: 'white',
    colorScheme: 'teal',
    width: '100%',
    padding: { base: '0.625em', md: '0.875em', lg: '1.125em' }, 
    height: { base: '2.25em', md: '2.75em', lg: '3.25em' },    
  },

  side: {
    variant: 'solid',
    color: 'black',
    colorScheme: 'gray',
    width: '10%',
    padding: { base: '0.25em', md: '0.375em', lg: '0.5em' }, 
    height: { base: '1.75em', md: '2.125em', lg: '2.5em' },  
  },
};

const buttonFontMapper = {
  standard: {
    fontSize: { base: '1em', md: '1.25em', lg: '1.5em' }, 
  },

  confirm: {
    fontSize: { base: '1.125em', md: '1.375em', lg: '1.625em' }, 
  },

  side: {
    fontSize: { base: '0.875em', md: '1.125em', lg: '1.375em' }, 
  },
};

function CustomButton({
  standard,
  confirm,
  side,
  children,
  ...rest
}) {
  const mapper = (standard) ? 'standard' : (confirm) ? 'confirm' : 'side';  

  return (
    <Button { ...buttonStyleMapper[mapper] } { ...rest }>
      <Text { ...buttonFontMapper[mapper] }>
        {children}
      </Text>
    </Button>
  );
}


export default CustomButton;