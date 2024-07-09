import myntIcon from '../images/icons/mynt_icon.png';
import defaultIcon from '../images/icons/default_icon.png';
import { Image } from "@chakra-ui/react";

const iconMapper = {
  mynt: myntIcon,
  default: defaultIcon
}

function Icon({
  mynt,
  ...rest
}) {
  const mapper = (mynt) ? 'mynt' : 'default';

  return (
    <Image 
      src={iconMapper[mapper]}
      alt='icon'
      objectFit='cover'
      boxSize={{ base: '3em', md: '4em', lg: '5em' }}
      { ...rest }
    />
  )
}

export default Icon;