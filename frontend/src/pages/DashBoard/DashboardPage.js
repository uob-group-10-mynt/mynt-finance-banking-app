import { Box } from '@chakra-ui/react';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import useCurrencyDescription from '../../hooks/useCurrencyDescription';
import useCurrencySymbols from '../../hooks/useCurrencySymbols';
import useFormatAmount from '../../hooks/useFormatAmount';
import Container from '../../components/container/Container';
import Icon from '../../components/util/Icon';
import CustomText from '../../components/CustomText';
import InfoBlock from '../../components/util/InfoBlock';
import DateTimeDisplay from '../../components/util/DateTimeDisplay';
import ContainerRowBalanceWrapper from '../../components/container/ContainerRowBalanceWrapper';
import CustomBox from '../../components/util/CustomBox';
import Calendar from '../../components/calendar/Calendar';
import SplashPage from '../SplashPage';


const MONTHS = {
  1 : 'JAN',
  2 : 'FEB',
  3 : 'MAR',
  4 : 'API',
  5 : 'MAY',
  6 : 'JUN',
  7 : 'JUL',
  8 : 'AUG',
  9 : 'SEP',
  10: 'OCT',
  11: 'NOV',
  12: 'DEC',
}

function countTransactionsByDate(transactions) {
  const transactionCounts = {};

  transactions.forEach(transaction => {
    const date = transaction.created_at.split('T')[0].split('-')[2];

    const day = parseInt(date, 10);

    if (transactionCounts[day]) {
      transactionCounts[day]++;
    } else {
      transactionCounts[day] = 1;
    }
  });

  return transactionCounts;
}

function addZeroIfLessThanTen(number) {
  return (number / 10 < 1) ? `0${number}` : number.toString();
}


function DashboardPage() {
  const today = new Date();

  const [ year, setYear ] = useState(today.getFullYear());
  const [ month, setMonth ] = useState(today.getMonth() + 1);
  const [ startDay, setStartDay ] = useState("01");
  const [ endDay, setEndDay ] = useState(addZeroIfLessThanTen(new Date(today.getFullYear(), today.getMonth() + 1, 0).getDate()));
  const [ transactions, setTransactions ] = useState([]);
  const [ loading, setLoading ] = useState(true); 
  const [ monthHasChanged, setMonthHasChanged ] = useState(true);
  const [ trafficData, setTrafficData ] = useState({});

  const navigate = useNavigate();


  useEffect(() => {
    setLoading(true); 

    fetch(`http://localhost:8080/api/v1/transaction?created_at_from=${year}-${addZeroIfLessThanTen(month)}-${startDay}&created_at_to=${year}-${addZeroIfLessThanTen(month)}-${endDay}`, {
      headers: { 'Authorization': `Bearer ${sessionStorage.getItem('access')}` }
    })
      .then(response => response.json())
      .then(data => {
        setTransactions([ ...data.transactions ]);
        setLoading(false); 
      })
      .catch(e => {
        console.error("ERROR: ", e);
        setLoading(false); 
      });
  }, [ month, startDay, endDay ]);

  useEffect(() => {
    if (monthHasChanged) {
      setTrafficData(countTransactionsByDate(transactions));
      setMonthHasChanged(false);
    }
  }, [ transactions ]);

  const handleCalendarDateClick = (e) => {    
    if (e.target.innerText === "") {
      alert("Please Click Again!!!");
    }

    else {
      setStartDay(addZeroIfLessThanTen(parseInt(e.target.innerText)));
      setEndDay(addZeroIfLessThanTen(parseInt(e.target.innerText)));
      setMonthHasChanged(false);
    }  
  }

  const handleMonthLeftClick = () => {
    if (month === 1) {
      setMonth(12);
      setYear(year - 1);
    } else {
      setMonth(month - 1);
    }

    setStartDay("01");
    setEndDay(addZeroIfLessThanTen(new Date(year, month + 1, 0).getDate()));
    setMonthHasChanged(true);
  }

  const handleMonthRightClick = () => {
    if (month === 12) {
      setMonth(1);
      setYear(year + 1);
    } else {
      setMonth(month + 1);
    }

    setStartDay("01");
    setEndDay(addZeroIfLessThanTen(new Date(year, month + 1, 0).getDate()));
    setMonthHasChanged(true);
  }
  
  const transactionKeyFn = (info) => info.id;

  const totalAmountsByCurrency = transactions.reduce((acc, transaction) => {
    const { currency, amount } = transaction;
    if (!acc[currency]) {
      acc[currency] = 0;
    }

    acc[currency] += parseFloat(amount);
    return acc;
  }, {});

  const summaryData = Object.entries(totalAmountsByCurrency).map(([currency, amount]) => ({
    currency,
    amount,
    render: () => (
      <>
        <Icon name={currency} currency />
        <InfoBlock>
          <CustomText gray small>{useCurrencySymbols(currency)} {useCurrencyDescription(currency)}</CustomText>
          <CustomText black big>{amount >= 0 ? '+' : '-'}{useFormatAmount(Math.abs(amount), currency)}</CustomText>
        </InfoBlock>
      </>
    )
  }));  

  const transactionData = transactions.map((data) => {
    const { id, created_at, amount, currency, type, related_entity_type } = data;

    return {
      ...data,
      render: () => {
        return (
          <>
            <Icon name={'The Currency Cloud Limited'} />
            <InfoBlock>
              <CustomText black small>{'The Currency Cloud Limited'}</CustomText>
              <DateTimeDisplay time={created_at} />
            </InfoBlock>
            <ContainerRowBalanceWrapper>
              <CustomText black big>{type === 'credit' ? '+' : '-'}{useFormatAmount(amount, currency)}</CustomText>
            </ContainerRowBalanceWrapper>
          </>
        );
      },

      onClick: () => {
        navigate(`/transactions/${related_entity_type}/${id}`);
      },
    }
  });

  const monthBlock = (
    <Box
      display="flex"
      flexDirection='row' 
      justifyContent="center" 
      alignItems="center" 
      gap='1.3em'
    >
      <Icon leftArrow onClick={handleMonthLeftClick}></Icon>
      <CustomText black big>{MONTHS[month]}</CustomText>
      <Icon rightArrow onClick={handleMonthRightClick}></Icon>
    </Box>
  );

  return (
    loading 
    ? <SplashPage />
    : <Box
        display="flex"
        flexDirection='column'
        justifyContent="center"
        alignItems="center"
        gap='1.3em'
        margin='auto'
      >
        <CustomBox justifyContent="center" alignItems="center">
          <CustomText small black>{year}</CustomText>
          {monthBlock}
        </CustomBox>
        <CustomBox>
          <Calendar year={year} month={month} data={trafficData} onClick={handleCalendarDateClick} />
        </CustomBox>
        <Container name='Summary' data={summaryData} keyFn={(info) => info.currency}></Container>
        <Container name='Transactions' data={transactionData} keyFn={transactionKeyFn} />
      </Box>
  );
}

export default DashboardPage;