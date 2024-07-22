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
    width: '15%',
    padding: { base: '0.25em', md: '0.375em', lg: '0.5em' }, 
    height: { base: '1.75em', md: '2.125em', lg: '2.5em' },  
  },

  medium: {
    variant: 'solid',
    color: 'white',
    colorScheme: 'teal',
    width: '40%',
    padding: { base: '0.375em', md: '0.5625em', lg: '0.75em' },
    height: { base: '1.875em', md: '2.3125em', lg: '2.75em' },
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

  medium: {
    fontSize: { base: '0.9375em', md: '1.1875em', lg: '1.4375em' }, 
  },
};

/**
 * CustomButton component
 *
 * This component renders a customizable button element.
 *
 * e.g. 
 * Props:
 * - `standard` (boolean): refers to the standard style of a button where its detail is represented above.
 * - `confirm` (boolean): refers to the confirm style of a button where its detail is represented above. 
 * - `medium` (boolean): refers to the medium style of a button where its detail is represented above. 
 * - `side` (boolean): refers to the side style of a button where its detail is represented above. 
 * - `children` (component): all other components wrapped by the CustomButton component.
 * - `rest` (any): any other props defined in React e.g. onClick, color, ..etc and will be reflected automatically.  
 *
 * Example:
 * <CustomButton standard onClick=() => console.log("CLICKED") >
 *  Submit
 * </CustomButton>
 * 
 * <CustomButton side>
 *  Send
 * </CustomButton>
 * ```
 */
function CustomButton({
  standard,
  confirm,
  medium,
  side,
  children,
  ...rest
}) {
  const mapper = (standard) ? 'standard' : (confirm) ? 'confirm' : (medium) ? 'medium' : 'side';  

  return (
    <Button { ...buttonStyleMapper[mapper] } { ...rest }>
      <Text { ...buttonFontMapper[mapper] }>
        {children}
      </Text>
    </Button>
  );
}


export default CustomButton;