import { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Box } from '@chakra-ui/react';

import useFormatAmount from '../../hooks/useFormatAmount';

import SplashPage from '../SplashPage';

import Container from '../../components/container/Container';
import Icon from '../../components/util/Icon';
import CustomText from '../../components/CustomText';
import CustomButton from '../../components/forms/CustomButton';
import ContainerRowBalanceWrapper from '../../components/container/ContainerRowBalanceWrapper';
import InfoBlock from '../../components/util/InfoBlock';
import DateTimeDisplay from '../../components/util/DateTimeDisplay';
import CustomBox from '../../components/util/CustomBox';


function AccountPage() {
  const navigate = useNavigate();
  const currency = useLocation().pathname.split('/')[2];
  const [ account, setAccount ] = useState({});
  const [ transactions, setTransactions ] = useState([]);
  const [ pages, setPages ] = useState(1);
  const [ loading, setLoading ] = useState(true); 

  useEffect(() => {
    setLoading(true); 

    fetch(`http://localhost:8080/api/v1/transaction?currency=${currency}&page=${pages}`, {
      headers: { 'Authorization': `Bearer ${sessionStorage.getItem('access')}` }
    })
      .then(response => response.json())
      .then(data => {
        setTransactions([ ...transactions, ...data.transactions ]);
        setLoading(false); 
      })
      .catch(e => {
        console.error("ERROR: ", e);
        setLoading(false); 
      });
  }, [ pages ]);

  useEffect(() => {
    setLoading(true);

    fetch(`http://localhost:8080/api/v1/balance/${currency}`, {
      headers: { 'Authorization': `Bearer ${sessionStorage.getItem('access')}` }
    })
      .then(response => response.json())
      .then(data => {
        setAccount(data);
        setLoading(false); 
      })
      .catch(e => {
        console.error("ERROR: ", e);
        setLoading(false); 
      });
  }, []);

  const transactionKeyFn = (info) => info.id;

  const moreButtonOnClick = () => {
    setPages(pages + 1);
  }

  const transactionData = transactions.map((data) => {
    const { id, createdAt, amount, currency, type } = data;

    return {
      ...data,
      render: () => {
        return (
          <>
            <Icon name={'The Currency Cloud Limited'} />
            <InfoBlock>
              <CustomText black small>{'The Currency Cloud Limited'}</CustomText>
              <DateTimeDisplay time={createdAt} />
            </InfoBlock>
            <ContainerRowBalanceWrapper>
              <CustomText black big>{type === 'credit' ? '+' : '-'}{useFormatAmount(amount, currency || 'USD')}</CustomText>
            </ContainerRowBalanceWrapper>
          </>
        );
      },

      onClick: () => {
        navigate('/transactions/' + id);
      },
    }
  });

  const accountInfoBlock = (
    <CustomBox gap='0.2em'>
      <InfoBlock>
        <CustomText gray small style={{ textDecoration: 'underline' }}>{account.account_label}</CustomText>
        <CustomText gray xsmall>{account.account_number_type}: {account.account_number}</CustomText>
      </InfoBlock>
      <CustomText black big>{useFormatAmount(account.balance || 0, account.currency || 'USD')}</CustomText>
      <Box
        display='flex'
        flexDirection='row'
        alignItems="center"
        justifyContent="space-between"
      >
        <CustomButton medium style={{ flex: 1, marginRight: '0.5em' }} colorScheme='blue'>Withdraw</CustomButton>
        <CustomButton medium style={{flex: 1, marginRight: '0.5em'}} onClick={(e) => handleSendOnClick(e)}>Send</CustomButton>
      </Box>
    </CustomBox>
  );

  return (
    (loading) 
    ? <SplashPage />
    : <Box
      display="flex"
      flexDirection='column'
      justifyContent="center"
      alignItems="center"
      gap='1.3em'
      margin='auto'
    >
      {accountInfoBlock}
      <Container name='Transactions' data={transactionData} keyFn={transactionKeyFn} />
      <CustomButton medium onClick={moreButtonOnClick}>More</CustomButton>
    </Box>
  );

    function handleSendOnClick (e) {
        e.stopPropagation();
        navigate('/remittance/payee', {state: {selectedCurrencyAccount: account}});
    }

}

export default AccountPage;
