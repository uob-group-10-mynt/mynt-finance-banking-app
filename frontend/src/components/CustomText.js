import { Text } from "@chakra-ui/react";

const textMapper = {
  big: {
    as: 'b', 
    fontSize: { base: '0.8em', md: '1.025em', lg: '1.725em'}
  },

  small: {
    as: 'b', 
    fontSize: { base: '0.55em', md: '0.95em', lg: '1.175em' } 
  },

  xsmall: {
    as: 'b',
    fontSize: { base: '7.5px', md: '0.65em', lg: '0.85em' }
  }
}

function CustomText({ 
  gray,
  black,
  big,
  small,
  xsmall,
  children,
  ...rest
}) {
  const mapper = (small) ? 'small' : (xsmall) ? 'xsmall' : 'big';

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