import { Text } from "@chakra-ui/react";

const textMapper = {
  big: {
    as: 'b', 
    fontSize: { base: '1.275em', md: '1.625em', lg: '1.875em' } 
  },

  small: {
    as: 'b', 
    fontSize: { base: '0.55em', md: '0.95em', lg: '1.175em' } 
  },
}

function CustomText({ 
  gray,
  black,
  big,
  small,
  children,
  ...rest
}) {
  const mapper = (small) ? 'small' : 'big';

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