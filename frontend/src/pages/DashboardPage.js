import useAxios from '../hooks/useAxios';
import Container from '../components/Container';
import Icon from '../components/Icon';
import CustomText from '../components/CustomText';
import InfoBlock from '../components/InfoBlock';
import { Box } from '@chakra-ui/react';
import CustomBox from '../components/CustomBox';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

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
    'amount': '100',
    'currency': '$',
    'flow': '+',
    'created_at': '2022-09-01',
  },
  {
    'id': '2',
    'payee_bank': 'others',
    'amount': '100',
    'currency': '/=',
    'flow': '-',
    'created_at': '2022-09-01',
  },
  {
    'id': '3',
    'payee_bank': 'others',
    'amount': '200',
    'currency': '/=',
    'flow': '-',
    'created_at': '2022-09-02',
  },
  {
    'id': '4',
    'payee_bank': 'mynt',
    'amount': '150',
    'currency': '/=',
    'flow': '-',
    'created_at': '2022-09-03',
  },
  {
    'id': '5',
    'payee_bank': 'mynt',
    'amount': '2605000.3',
    'currency': '£',
    'flow': '+',
    'created_at': '2022-09-01',
  },
];

function DashboardPage() {
  const [ year, setYear ] = useState(new Date().getFullYear());
  const [ month, setMonth ] = useState(new Date().getMonth() + 1);
  const navigate = useNavigate();

  const handleMonthLeftClick = () => {
    if (month === 1) {
      setMonth(12);
      setYear(year - 1);
    } else {
      setMonth(month - 1);
    }
  }

  const handleMonthRightClick = () => {
    if (month === 12) {
      setMonth(1);
      setYear(year + 1);
    } else {
      setMonth(month + 1);
    }
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
        <Icon name={currency} currency/>
        <InfoBlock>
          <CustomText gray small>{currency} {CURRENCIES[currency]}</CustomText>
          <CustomText black big>{amount >= 0 ? '+' : '-'}{currency} {Math.abs(amount).toFixed(2)}</CustomText>
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
                        <CustomText gray small>{data.payee_bank}</CustomText>
                        <CustomText gray small>{data.created_at}</CustomText>
                    </InfoBlock>
                    <Box minWidth='25%'>
                      <CustomText 
                        black 
                        big 
                        fontSize={{ 
                          base: '0.8em', 
                          md: '1.025em',    
                          lg: '1.725em'      
                        }}
                      >
                        {data.flow}{data.currency}{parseFloat(data.amount).toFixed(2)}
                      </CustomText>
                    </Box>
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
      <CustomBox></CustomBox>
      <Container name='Summary' data={summaryData} keyFn={(info) => info.currency}></Container>
      <Container name='Transactions' data={transactionData} keyFn={transactionKeyFn} />
    </Box>
  );
}

export default DashboardPage;