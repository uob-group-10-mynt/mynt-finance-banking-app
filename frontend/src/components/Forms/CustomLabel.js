import React from 'react';
import { FormLabel } from "@chakra-ui/react";


function CustomLabel({
  children
}) {
  
  return (
    <FormLabel>
      {children}
    </FormLabel>
  )
}

export default CustomLabel;