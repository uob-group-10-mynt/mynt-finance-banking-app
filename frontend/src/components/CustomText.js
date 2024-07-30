import { Text } from "@chakra-ui/react";

const textMapper = {
  xbig: {
    as: 'b', 
    fontSize: { base: '1.4em', md: '1.425em', lg: '2.125em'}
  },

  big: {
    as: 'b', 
    fontSize: { base: '0.8em', md: '1.025em', lg: '1.725em'}
  },

  medium: {
    as: 'b', 
    fontSize: { base: '0.65em', md: '0.825em', lg: '1.425em'}
  },

  small: {
    as: 'b', 
    fontSize: { base: '0.55em', md: '0.95em', lg: '1.175em' } 
  },

  xsmall: {
    as: 'b',
    fontSize: { base: '5.5px', md: '0.65em', lg: '0.85em' }
  }
}

function CustomText({ 
  gray,
  black,
  xbig,
  big,
  medium,
  small,
  xsmall,
  children,
  ...rest
}) {
  const mapper = (small) ? 'small' : (xsmall) ? 'xsmall' : (medium) ? 'medium' : (xbig) ? 'xbig' : 'big';

  return (
    <Text 
      color={(black) ? 'black' : 'gray'}
      { ...textMapper[mapper] }
      { ...rest }
    >
      {children}
    </Text>
  );
}

export default CustomText;