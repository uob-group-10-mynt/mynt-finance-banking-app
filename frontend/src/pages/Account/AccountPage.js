import { Box, useDisclosure, Modal, ModalOverlay, ModalContent, ModalHeader, ModalCloseButton, ModalBody } from '@chakra-ui/react';
import { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

import useFormatAmount from '../../hooks/useFormatAmount';

import SplashPage from '../SplashPage';
import ConversionModalPage from '../ConversionModalPage';

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
  const { isOpen, onOpen, onClose } = useDisclosure();
  const currency = useLocation().pathname.split('/')[2];
  const [ account, setAccount ] = useState({});
  const [ transactions, setTransactions ] = useState([]);
  const [ pages, setPages ] = useState(1);
  const [ loading, setLoading ] = useState(true); 

  useEffect(() => {
    setLoading(true); 

    fetch(`http://localhost:8080/api/v1/transaction?currency=${currency}&per_page=${5}&page=${pages}`, {
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

  const withdrawalOnClick = () => {
    onOpen();
  };

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
              <CustomText black big>{type === 'credit' ? '+' : '-'}{useFormatAmount(amount, currency || 'USD')}</CustomText>
            </ContainerRowBalanceWrapper>
          </>
        );
      },

      onClick: () => {
        navigate(`/transactions/${related_entity_type}/${id}`);
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
        <CustomButton medium style={{ flex: 1, marginRight: '0.5em' }} colorScheme='blue' onClick={withdrawalOnClick}>Withdraw</CustomButton>
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
      <Modal isOpen={isOpen} onClose={onClose} size="2xlg">
        <ModalOverlay />
          <ModalContent>
            <ModalHeader>Withdraw Funds</ModalHeader>
              <ModalCloseButton />
              <ModalBody>
                <ConversionModalPage onClose={onClose} currency={currency} />
              </ModalBody>
          </ModalContent>
      </Modal>
    </Box>
  );

    function handleSendOnClick (e) {
        e.stopPropagation();
        navigate('/remittance/payee', {state: {selectedCurrencyAccount: account}});
    }

}

export default AccountPage;
