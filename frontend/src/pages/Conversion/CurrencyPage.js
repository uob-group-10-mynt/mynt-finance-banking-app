import { Box } from "@chakra-ui/react";
import { TriangleUpIcon, TriangleDownIcon } from '@chakra-ui/icons'; 
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

import useCurrencyDescription from "../../hooks/useCurrencyDescription";
import useQuery from "../../hooks/useQuery";
import useDays from "../../hooks/useDays";
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
    >
      <CustomBox width='65%'>
        {/* Currency Header */}
        <CustomText black marginBottom='0.7em'>Live Exchange Rates At: 
          <DateTimeDisplay time={data[data.length - 1].settlement_date} />
        </CustomText>
        <Box
          display='flex'
          flexDirection='row'
          alignItems='center'
          gap='0.3em'
        >
          <Icon name={compare} currency />
          <CustomText black big>{useCurrencyDescription(compare)}</CustomText>
        </Box>

        <Liner />

        {/* Rate Info */}
        <CustomText black big>
          1 {currency} = {data[data.length - 1].rates} {compare}
        </CustomText>

        <Box
          display='flex'
          flexDirection='row'
          alignItems='center'
          justifyContent='start'
          marginTop='0.5em'
        >
          <Box
            display='flex'
            flexDirection='row'
            alignItems='center'
          >
            {rateChangeIcon}
            <CustomText black big>{formattedAmount}</CustomText>
            <CustomText small marginLeft='0.5em'>than last {useDays(fetchLatestRateData.day_before_settlement_date)}</CustomText>
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
