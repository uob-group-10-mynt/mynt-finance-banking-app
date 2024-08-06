import { Image } from "@chakra-ui/react";
import { SlArrowLeft, SlArrowRight } from "react-icons/sl";

import AUSTRALIA from '../../../public/images/icons/countries/au.png';
import BAHRAIN from '../../../public/images/icons/countries/bh.png';
import CANADA from '../../../public/images/icons/countries/ca.png';
import CHINA from '../../../public/images/icons/countries/cn.png';
import CZECH from '../../../public/images/icons/countries/cz.png';
import DENMARK from '../../../public/images/icons/countries/dk.png';
import EUROPEAN_UNION from '../../../public/images/icons/countries/eu.jpg';
import HONG_KONG from '../../../public/images/icons/countries/hk.png';
import HUNGARY from '../../../public/images/icons/countries/hu.png';
import INDIA from '../../../public/images/icons/countries/in.png';
import INDONESIA from '../../../public/images/icons/countries/id.png';
import ISRAEL from '../../../public/images/icons/countries/il.png';
import JAPAN from '../../../public/images/icons/countries/jp.png';
import KENYA from '../../../public/images/icons/countries/ke.png';
import KUWAIT from '../../../public/images/icons/countries/kw.png';
import MALAYSIA from '../../../public/images/icons/countries/my.png';
import MEXICO from '../../../public/images/icons/countries/mx.png';
import NEW_ZEALAND from '../../../public/images/icons/countries/nz.png';
import NORWAY from '../../../public/images/icons/countries/no.png';
import OMAN from '../../../public/images/icons/countries/om.png';
import PHILLIPPINES from '../../../public/images/icons/countries/ph.png';
import POLAND from '../../../public/images/icons/countries/pl.png';
import QATAR from '../../../public/images/icons/countries/qa.png';
import ROMANIA from '../../../public/images/icons/countries/ro.png';
import RUSSIA from '../../../public/images/icons/countries/ru.png';
import SAUDI_ARABIA from '../../../public/images/icons/countries/sa.png';
import SINGAPORE from '../../../public/images/icons/countries/sg.png';
import SOUTH_AFRICA from '../../../public/images/icons/countries/za.png';
import SWEDEN from '../../../public/images/icons/countries/se.png';
import SWITZERLAND from '../../../public/images/icons/countries/ch.png';
import THAILAND from '../../../public/images/icons/countries/th.png';
import TURKEY from '../../../public/images/icons/countries/tr.png';
import UGANDA from '../../../public/images/icons/countries/ug.png';
import UK from '../../../public/images/icons/countries/gb.png';
import UAE from '../../../public/images/icons/countries/ae.png';
import US from '../../../public/images/icons/countries/us.png';

import myntIcon from '../../../public/images/icons/mynt_icon.png';
import defaultIcon from '../../../public/images/icons/default_icon.png';


const iconMapper = {
  'AUD': AUSTRALIA,       // Australian Dollar
  'BHD': BAHRAIN,         // Bahrain Dinar
  'CAD': CANADA,          // Canadian Dollar
  'CNY': CHINA,           // Chinese Yuan (Renminbi)
  'CZK': CZECH,           // Czech Koruna
  'DKK': DENMARK,         // Danish Krone
  'EUR': EUROPEAN_UNION,  // EURO
  'HKD': HONG_KONG,       // Hong Kong Dollar
  'HUF': HUNGARY,         // Hungarian Forint
  'INR': INDIA,           // Indian Rupee
  'IDR': INDONESIA,       // Indonesian Rupee
  'ILS': ISRAEL,          // Israel Shekel
  'JPY': JAPAN,           // Japanese Yen
  'KES': KENYA,           // Kenyan Shilling
  'KWD': KUWAIT,          // Kuwait Dinar
  'MYR': MALAYSIA,        // Malaysian Ringgit
  'MXN': MEXICO,          // Mexican Peso
  'NZD': NEW_ZEALAND,     // New Zealand Dollar
  'NOK': NORWAY,          // Norwegian Krone
  'OMR': OMAN,            // Oman Rial
  'PHP': PHILLIPPINES,    // Philippine Peso
  'PLN': POLAND,          // Polish Zloty
  'QAR': QATAR,           // Qatar Rial
  'RON': ROMANIA,         // Romanian Leu
  'RUB': RUSSIA,          // Russian Ruble
  'SAR': SAUDI_ARABIA,    // Saudi Riyal
  'SGD': SINGAPORE,       // Singapore Dollar
  'ZAR': SOUTH_AFRICA,    // South African Rand
  'SEK': SWEDEN,          // Swedish Krona
  'CHF': SWITZERLAND,     // Swiss Franc
  'THB': THAILAND,        // Thai Baht
  'TRY': TURKEY,          // Turkish Lira
  'UGX': UGANDA,          // Ugandan Shilling
  'GBP': UK,              // UK Sterling
  'AED': UAE,             // UAE Dirham
  'USD': US,              // US Dollar
  'The Currency Cloud Limited': myntIcon,       // Mynt icon
  'others': defaultIcon,  // Default icon for others
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