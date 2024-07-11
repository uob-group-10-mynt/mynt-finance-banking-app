import myntIcon from '../images/icons/mynt_icon.png';
import defaultIcon from '../images/icons/default_icon.png';
import { Image } from "@chakra-ui/react";

const iconMapper = {
  mynt: myntIcon,
  others: defaultIcon
}

function Icon({
  bank,
  ...rest
}) {
  return (
    <Image 
      src={iconMapper[bank]}
      alt='icon'
      objectFit='cover'
      boxSize={{ base: '2em', md: '3em', lg: '4em' }}
      { ...rest }
    />
  )
}

export default Icon;