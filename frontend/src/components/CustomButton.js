import { Button } from '@chakra-ui/react';


function CustomButton({
  children,
  size,
  colour,
  variant,
  ...props
}) {

  const sizeList = {
    large: 'lg',
    medium: 'md',
    small: 'sm',
    xsmall: 'xs',
  }

  const colourList = {
    gray: 'gray',
    red: 'red',
    orange: 'orange',
    yellow: 'yellow',
    green: 'green',
    teal: 'teal',
    blue: 'blue',
    cyan: 'cyan',
    purple: 'purple',
    pink: 'pink',
  }

  return (
    <Button 
      colorScheme={colourList[colour] || 'teal'}
      size={sizeList[size] || 'md'}
      {...props} >{
      children}
    </Button>
  );
}


export default CustomButton;