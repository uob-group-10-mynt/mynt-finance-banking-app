import useAxios from '../hooks/useAxios';
import Container from '../components/Container';
import Icon from '../components/Icon';
import CustomText from '../components/CustomText';
import CustomButton from '../components/forms/CustomButton';
import InfoBlock from '../components/InfoBlock';
import { useLocation, useNavigate } from 'react-router-dom';
import { Box } from '@chakra-ui/react';

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
      'created_at': '2022-09-01',
  },
  {
      'id': '2',
      'payee_bank': 'others',
      'amount': '100',
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
                    <Icon bank={data.payee_bank} />
                    <InfoBlock>
                        <CustomText gray small>{data.payee_bank}</CustomText>
                        <CustomText gray small>{data.created_at}</CustomText>
                    </InfoBlock>
                    <CustomText black big>{accountDetail.currencySymbol}{data.amount}</CustomText>
                </>
            );
        },

        onClick: () => {
          navigate('/transactions/' + data.id);
        },
    }
  });

  const accountInfoBlock = (
    <Box
      display='flex' 
      flexDirection='column'
      gap='0.2em'
      borderRadius='lg' 
      width='65%'
      padding='1em'
      backgroundColor='white'
    >
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
        <CustomButton medium style={{ flex: 1, marginRight: '0.5em' }}>Disconnect</CustomButton>
        <CustomButton medium style={{ flex: 1, marginRight: '0.5em' }}>Send</CustomButton>
      </Box>
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
      {accountInfoBlock}
      <Container name='Transactions' data={transactionData} keyFn={transactionKeyFn} />
      <CustomButton medium>More</CustomButton>
    </Box>
  );
}

export default AccountPage;