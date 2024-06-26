import { Input } from '@chakra-ui/react';

function CustomInput({
  children,

  ...props
}) {
  return (
    <Input 
      
      { ...props }
      >
      {children}
    </Input>
  );
}

export default CustomInput;