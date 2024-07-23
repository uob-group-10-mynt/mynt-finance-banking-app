// CurrencyPage.js
import { Box } from "@chakra-ui/react";
import { TriangleUpIcon, TriangleDownIcon } from '@chakra-ui/icons'; 
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

import useCurrencyDescription from "../../hooks/useCurrencyDescription";
import useQuery from "../../hooks/useQuery";
import useQueryBuilder from '../../hooks/useQueryBuilder';
import TabBar from "../../components/TabBar";
import CustomText from "../../components/CustomText";
import Icon from "../../components/util/Icon";
import CustomBox from "../../components/util/CustomBox";
import CustomButton from "../../components/forms/CustomButton";
import LineChart from "../../components/LineChart";
import Liner from "../../components/Liner";
import DateTimeDisplay from "../../components/util/DateTimeDisplay";

const TIMES = [ '1D', '1W', '3M', '1Y' ];

const fetchConversionData = [
  {
    rates: '1.54',
    buy_currency: 'USD',
    sell_currency: 'KES',
    settlement_date: "2024-07-13T14:13:18+00:00",
  },
  {
    rates: '1.53',
    buy_currency: 'USD',
    sell_currency: 'KES',
    settlement_date: "2024-07-14T14:13:18+00:00",
  },
  {
    rates: '1.52',
    buy_currency: 'USD',
    sell_currency: 'KES',
    settlement_date: "2024-07-15T14:13:18+00:00",
  },
  {
    rates: '1.55',
    buy_currency: 'USD',
    sell_currency: 'KES',
    settlement_date: "2024-07-16T14:13:18+00:00",
  },
  {
    rates: '1.51',
    buy_currency: 'USD',
    sell_currency: 'KES',
    settlement_date: "2024-07-17T14:13:18+00:00",
  },
  {
    rates: '1.56',
    buy_currency: 'USD',
    sell_currency: 'KES',
    settlement_date: "2024-07-18T14:13:18+00:00",
  },
  {
    rates: '1.57',
    buy_currency: 'USD',
    sell_currency: 'KES',
    settlement_date: "2024-07-19T14:13:18+00:00",
  },
  {
    rates: '1.58',
    buy_currency: 'USD',
    sell_currency: 'KES',
    settlement_date: "2024-07-20T14:13:18+00:00",
  },
  {
    rates: '1.59',
    buy_currency: 'USD',
    sell_currency: 'KES',
    settlement_date: "2024-07-21T14:13:18+00:00",
  },
  {
    rates: '1.60',
    buy_currency: 'USD',
    sell_currency: 'KES',
    settlement_date: "2024-07-22T14:13:18+00:00",
  },
];

const fetchLatestRateData = {
  rates: '1.60',
  buy_currency: 'USD',
  sell_currency: 'KES',
  settlement_date: "2024-07-22T14:13:18+00:00", 
  day_before_settlement_date: "2024-07-21T14:13:18+00:00", 
  amount: "-0.11"
}

export default function CurrencyPage() {
  const location = useLocation();
  const queries = useQuery();
  const currency = location.pathname.split('/')[2].toUpperCase();
  const compare = queries.get('compare').toUpperCase();
  const [ duration, setDuration ] = useState('1D');
  const [ query, setQuery ] = useState({ base: currency, compare: compare, duration: duration });
  const [ data, setData ] = useState(fetchConversionData); 
  const builtQuery = useQueryBuilder(query);

  useEffect(() => {
    setQuery(prev => ({ ...prev, duration }));
  }, [duration]);

  const handleTabOnClick = (e) => {
    setDuration(e.target.innerText);
  };

  const latestRate = parseFloat(fetchLatestRateData.amount);
  const rateChangeIcon = latestRate < 0 ? <TriangleDownIcon color='blue' /> : <TriangleUpIcon color='red' />;
  const formattedAmount = Math.abs(latestRate);

  return (
    <Box
      display="flex"
      flexDirection='column'
      alignItems="center"
      gap='1.3em'
      margin='auto'
      maxWidth='1200px'
      padding='1em'
    >
      <CustomBox padding='1em'>
        {/* Currency Header */}
        <Box
          display='flex'
          flexDirection='row'
          alignItems='center'
          gap='0.6em'
          mb='1em'
        >
          <Icon name={currency} currency />
          <CustomText black big>{useCurrencyDescription(currency)}</CustomText>
        </Box>

        <Liner />

        {/* Rate Info */}
        <CustomText black medium>
          1 {currency} = {data[data.length - 1].rates} {compare}
        </CustomText>

        <Box
          display='flex'
          flexDirection='row'
          alignItems='center'
          justifyContent='space-between'
          mb='1em'
          width='100%'
        >
          <Box
            display='flex'
            flexDirection='row'
            alignItems='center'
            gap='0.2em'
          >
            {rateChangeIcon}
            <CustomText>{formattedAmount}</CustomText>
          </Box>
          <Box
            display='flex'
            flexDirection='row'
            alignItems='center'
            gap='0.5em'
          >
            <CustomText gray medium>Market Rate at: </CustomText>
            <DateTimeDisplay time={data[data.length - 1].settlement_date} />
          </Box>
        </Box>

        {/* Chart and Tabs */}
        <Box
          display='flex'
          flexDirection='column'
          alignItems='center'
          mb='1em'
        >
          <CustomBox>
            <LineChart data={data} label={`${currency} to ${compare}`} />
          </CustomBox>
          <TabBar tabNames={TIMES} tabPanels={[]} onClick={handleTabOnClick} size={['lg', 'xlg']} />
        </Box>

        {/* Action Buttons */}
        <Box
          display='flex' 
          flexDirection='row'
          alignItems="center"
          justifyContent="space-between"
          width='100%'
        >
          <CustomButton medium style={{ flex: 1, marginRight: '0.5em' }} colorScheme='blue'>
            To {currency}
          </CustomButton>
          <CustomButton medium style={{ flex: 1, marginRight: '0.5em' }}>
            To {compare}
          </CustomButton>
        </Box>
      </CustomBox>
    </Box>
  );
}
