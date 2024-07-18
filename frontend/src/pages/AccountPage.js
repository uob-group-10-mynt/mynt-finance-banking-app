import { useLocation, useNavigate } from 'react-router-dom';
import { Box } from '@chakra-ui/react';

import useAxios from '../hooks/useAxios';
import Container from '../components/Container';
import Icon from '../components/Icon';
import CustomText from '../components/CustomText';
import CustomButton from '../components/forms/CustomButton';
import ContainerRowBalanceWrapper from '../components/ContainerRowBalanceWrapper';
import InfoBlock from '../components/InfoBlock';
import CustomBox from '../components/CustomBox';


const accountDetail = {
  'id': '1',
  'account_reference': '66f51c98-1ef8-4e48-97de-aac0353ba2b4',
  'bank': 'mynt',
  'label': 'Mynt Dollar Account',
  'balance': '100',
  'currency': 'dollar',
  'currencySymbol': '$'
};

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
      'amount': '10000.0',
      'currency': '₩',
      'flow': '-',
      'created_at': '2022-09-01',
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
                        <CustomText gray small>{data.payee_bank}</CustomText>
                        <CustomText gray small>{data.created_at}</CustomText>
                    </InfoBlock>
                    <ContainerRowBalanceWrapper>
                      <CustomText black big>{data.flow}{accountDetail.currencySymbol}{parseFloat(data.amount).toFixed(2)}</CustomText>
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
      <CustomText black big>{accountDetail.currencySymbol}{accountDetail.balance}</CustomText>
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