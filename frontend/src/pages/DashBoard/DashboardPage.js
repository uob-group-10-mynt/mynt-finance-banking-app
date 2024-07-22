import { Box } from '@chakra-ui/react';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import useAxios from '../../hooks/useAxios';
import useFormatAmount from '../../hooks/useFormatAmount';
import Container from '../../components/container/Container';
import Icon from '../../components/util/Icon';
import CustomText from '../../components/CustomText';
import InfoBlock from '../../components/util/InfoBlock';
import DateTimeDisplay from '../../components/util/DateTimeDisplay';
import ContainerRowBalanceWrapper from '../../components/container/ContainerRowBalanceWrapper';
import CustomBox from '../../components/util/CustomBox';
import Calendar from '../../components/calendar/Calendar';


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

const CURRENCIES = {
  '$': 'USD DOLLAR',
  '/=': 'KES SHILLING',
  '£': 'GBR POUND',
}

const fetchTransactionData = [
  {
    'id': '1',
    'payee_bank': 'mynt',
    'payer_bank': 'mynt',
    'amount': '100',
    'currency': '$',
    'flow': '+',
    'created_at': "2024-07-01T14:13:18+00:00",
  },
  {
    'id': '2',
    'payee_bank': 'others',
    'payer_bank': 'mynt',
    'amount': '100',
    'currency': '/=',
    'flow': '-',
    'created_at': "2024-07-04T14:13:18+00:00",
  },
  {
    'id': '3',
    'payee_bank': 'others',
    'payer_bank': 'mynt',
    'amount': '200',
    'currency': '/=',
    'flow': '-',
    'created_at': "2024-07-13T14:13:18+00:00",
  },
  {
    'id': '4',
    'payee_bank': 'mynt',
    'payer_bank': 'mynt',
    'amount': '150',
    'currency': '/=',
    'flow': '-',
    'created_at': "2024-07-13T14:13:18+00:00",
  },
  {
    'id': '5',
    'payee_bank': 'mynt',
    'payer_bank': 'mynt',
    'amount': '2605000.3',
    'currency': '£',
    'flow': '+',
    'created_at': "2024-07-13T14:13:18+00:00",
  },
];

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


function DashboardPage() {
  const [ year, setYear ] = useState(new Date().getFullYear());
  const [ month, setMonth ] = useState(new Date().getMonth() + 1);
  const [day, setDay] = useState(null);
  const navigate = useNavigate();


  const handleCalendarDateClick = (e) => {
    setDay(parseInt(e.target.innerText));
  }

  const handleMonthLeftClick = () => {
    if (month === 1) {
      setMonth(12);
      setYear(year - 1);
    } else {
      setMonth(month - 1);
    }

    setDay(null);
  }

  const handleMonthRightClick = () => {
    if (month === 12) {
      setMonth(1);
      setYear(year + 1);
    } else {
      setMonth(month + 1);
    }

    setDay(null);
  }
  
  const transactionKeyFn = (info) => info.id; 

  const totalAmountsByCurrency = fetchTransactionData.reduce((acc, transaction) => {
    const { currency, amount, flow } = transaction;
    if (!acc[currency]) {
      acc[currency] = 0;
    }

    acc[currency] += (flow === '+' ? parseFloat(amount) : -parseFloat(amount));
    return acc;
  }, {});

  const summaryData = Object.entries(totalAmountsByCurrency).map(([currency, amount]) => ({
    currency,
    amount,
    render: () => (
      <>
        <Icon name={currency} currency />
        <InfoBlock>
          <CustomText gray small>{currency} {CURRENCIES[currency]}</CustomText>
          <CustomText black big>{amount >= 0 ? '+' : '-'}{useFormatAmount(Math.abs(amount), currency)}</CustomText>
        </InfoBlock>
      </>
    )
  }));

  const transactionData = fetchTransactionData.map((data) => {
    return {
        ...data,
        render: () => {
            return (
                <>
                    <Icon name={data.payee_bank} />
                    <InfoBlock>
                        <CustomText black small>{data.payee_bank}</CustomText>
                        <DateTimeDisplay time={data.created_at}/>
                    </InfoBlock>
                    <ContainerRowBalanceWrapper>
                      <CustomText black big>
                        {data.flow}{useFormatAmount(data.amount, data.currency)}
                      </CustomText>
                    </ContainerRowBalanceWrapper>
                </>
            );
        },

        onClick: () => {
          navigate('/transactions/' + data.id);
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
    <Box
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
        <Calendar year={year} month={month} data={countTransactionsByDate(transactionData)} onClick={handleCalendarDateClick} />
      </CustomBox>
      <Container name='Summary' data={summaryData} keyFn={(info) => info.currency}></Container>
      <Container name='Transactions' data={transactionData} keyFn={transactionKeyFn} />
    </Box>
  );
}

export default DashboardPage;