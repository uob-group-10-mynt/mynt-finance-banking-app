import UK from '../images/icons/countries/gb.png';
import US from '../images/icons/countries/us.png';
import KENYA from '../images/icons/countries/ke.png';
import myntIcon from '../images/icons/mynt_icon.png';
import defaultIcon from '../images/icons/default_icon.png';
import { Image } from "@chakra-ui/react";
import { SlArrowLeft, SlArrowRight } from "react-icons/sl";

const iconMapper = {
  '£': UK,
  '$': US,
  '/=': KENYA,
  'mynt': myntIcon,
  others: defaultIcon,
};

function Icon({
  name,
  currency,
  leftArrow,
  rightArrow,
  ...rest
}) {
  if (leftArrow) return <SlArrowLeft {...rest} />;
  if (rightArrow) return <SlArrowRight {...rest} />;

  return (
    <Image 
      src={iconMapper[name] || defaultIcon}
      alt={`${name} icon`}
      objectFit="cover"
      boxSize={{ base: '2em', md: '3em', lg: '4em' }}
      borderRadius={(currency) ? 'full' : ''}
      {...rest}
    />
  );
}

export default Icon;