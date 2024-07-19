import ContainerRow from "./ContainerRow";
import CustomBox from "./CustomBox";
import CustomText from "./CustomText";

function Container({
  name='',
  data=[],
  keyFn,
  ...rest
}) {
  const renderedRows = data.map((info) => {
    return <ContainerRow key={keyFn(info)} info={info} />
  });

  return (
    <CustomBox gap='0.9em' { ...rest }>
      <CustomText small black>{name}</CustomText>
      {renderedRows}
    </CustomBox>
  );
}

export default Container;