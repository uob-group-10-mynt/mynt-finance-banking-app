import { useLocation, useNavigate } from 'react-router-dom';
import { Box } from '@chakra-ui/react';

import useAxios from '../hooks/useAxios';
import useFormatAmount from '../hooks/useFormatAmount';
import Container from '../components/container/Container';
import Icon from '../components/util/Icon';
import CustomText from '../components/CustomText';
import CustomButton from '../components/forms/CustomButton';
import ContainerRowBalanceWrapper from '../components/container/ContainerRowBalanceWrapper';
import InfoBlock from '../components/util/InfoBlock';
import DateTimeDisplay from '../components/util/DateTimeDisplay';
import CustomBox from '../components/util/CustomBox';

const accountDetail = {
  'id': '1',
  'account_reference': '66f51c98-1ef8-4e48-97de-aac0353ba2b4',
  'bank': 'mynt',
  'label': 'Mynt Dollar Account',
  'balance': '100',
  'currency': 'USD',
  'currencySymbol': '$'
};

const fetchTransactionData = [
  {
      'id': '1',
      'payee_bank': 'mynt',
      'amount': '100',
      'currency': 'USD',
      'currencySymbol': '$',
      'flow': '+',
      'created_at': "2024-06-25T14:13:18+00:00",
  },
  {
      'id': '2',
      'payee_bank': 'others',
      'amount': '10000.0',
      'currency': 'KRW',
      'currencySymbol': '₩',
      'flow': '-',
      'created_at': "2024-06-25T14:13:18+00:00",
  },
];


function AccountPage() {
  const navigate = useNavigate();
  // const location = useLocation();
  // const [] = useAxios();
  const transactionKeyFn = (info) => info.id; 

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
                      <CustomText black big>{data.flow}{useFormatAmount(data.amount, data.currencySymbol)}</CustomText>
                    </ContainerRowBalanceWrapper>
                </>
            );
        },

        onClick: () => {
          navigate('/transactions/' + data.id);
        },
    }
  });

  const accountInfoBlock = (
    <CustomBox gap='0.2em'>
      <InfoBlock>
        <CustomText gray small style={{ textDecoration: 'underline' }}>{accountDetail.label}</CustomText>
        <CustomText gray xsmall>{accountDetail.account_reference}</CustomText>
      </InfoBlock>
      <CustomText black big>{useFormatAmount(accountDetail.balance, accountDetail.currencySymbol)}</CustomText>
      <Box
        display='flex' 
        flexDirection='row'
        alignItems="center"
        justifyContent="space-between"
      >
        <CustomButton medium style={{ flex: 1, marginRight: '0.5em' }} colorScheme='blue'>Withdraw</CustomButton>
        <CustomButton medium style={{ flex: 1, marginRight: '0.5em' }}>Send</CustomButton>
      </Box>
    </CustomBox>
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
      {accountInfoBlock}
      <Container name='Transactions' data={transactionData} keyFn={transactionKeyFn} />
      <CustomButton medium>More</CustomButton>
    </Box>
  );
}

export default AccountPage;
