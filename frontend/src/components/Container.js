import ContainerRow from "./ContainerRow";
import CustomText from "./CustomText";
import { Box } from "@chakra-ui/react";

function Container({
  name,
  data=[],
  keyFn,
  ...rest
}) {
  const renderedRows = data.map((info) => {
    return <ContainerRow key={keyFn(info)} info={info} />
  });

  return (
    <Box
      display='flex' 
      flexDirection='column'
      borderRadius='lg' 
      width='65%'
      padding='1em'
      backgroundColor='white'
      { ...rest }
    >
      <CustomText small black>{name}</CustomText>
      {renderedRows}
    </Box>
  );
}

export default Container;