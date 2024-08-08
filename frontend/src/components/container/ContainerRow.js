import { useState } from "react";
import { Box } from "@chakra-ui/react";

import useLongPress from '../../hooks/useLongPress';


function ContainerRow({ info }) {
  const [isHovered, setIsHovered] = useState(false);
  const { handlers } = useLongPress(info.onClick);

  return (
    <Box 
      display='flex'
      flexDirection='row'
      alignItems="center"
      justifyContent="space-around"
      backgroundColor={isHovered ? '#f0f0f0' : 'transparent'}
      transition={'background-color 0.3s'}
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
      onDoubleClickCapture={info.onDoubleClickCapture}
      {...handlers}
    >
      {info.render()}
    </Box>
  );
}

export default ContainerRow;