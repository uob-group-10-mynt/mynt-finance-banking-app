import { Box } from "@chakra-ui/react";

import ContainerRow from "./ContainerRow";
import CustomBox from "../util/CustomBox";
import CustomText from "../CustomText";

function Container({
  name='',
  data=[],
  sub='',
  keyFn,
  ...rest
}) {
  const renderedRows = data.map((info) => {
    return <ContainerRow key={keyFn(info)} info={info} />
  });

  return (
    <CustomBox gap='0.9em' { ...rest }>
      <Box 
        display='flex' 
        flexDirection='row' 
        justifyContent='space-between' 
        alignItems='center'
      >
        <CustomText big medium black>{name}</CustomText>
        {sub}
      </Box>
      {renderedRows}
    </CustomBox>
  );
}

export default Container;