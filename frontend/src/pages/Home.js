import { Box, useDisclosure, Modal, ModalOverlay, ModalContent, ModalHeader, ModalCloseButton, ModalBody } from "@chakra-ui/react";
import { useState, useEffect, useCallback } from "react";
import { useNavigate } from 'react-router-dom';

import useFormatAmount from '../hooks/useFormatAmount';

import SplashPage from "./SplashPage";

import CustomButton from "../components/forms/CustomButton";
import CustomText from "../components/CustomText";
import Icon from "../components/util/Icon";
import Container from "../components/container/Container";
import ContainerRowBalanceWrapper from "../components/container/ContainerRowBalanceWrapper";
import InfoBlock from "../components/util/InfoBlock";
import ConversionListPage from "./Conversion/ConversionListPage"; 
import DepositPage from './DepositPage'; 

export default function Home() {
    const navigate = useNavigate(); 
    const { isOpen: isConversionOpen, onOpen: onConversionOpen, onClose: onConversionClose } = useDisclosure();
    const { isOpen: isDepositOpen, onOpen: onDepositOpen, onClose: onDepositClose } = useDisclosure();
    const [selectedCurrency, setSelectedCurrency] = useState(null);
    const [baseCurrency, setBaseCurrency] = useState("KES");
    const [compareCurrency, setCompareCurrency] = useState("USD");
    const [isBaseCurrency, setIsBaseCurrency] = useState(false);
    const [accounts, setAccounts] = useState([]);
    const [exchangeRates, setExchangeRates] = useState([]);
    const [loading, setLoading] = useState(true); 

    const fetchExchangeRates = useCallback(async () => {
        setLoading(true);
        try {
          const response = await fetch('http://localhost:8080/api/v1/rates/getBasicRates', {
            headers: { 'Authorization': `Bearer ${sessionStorage.getItem('access')}` }
          });
          const data = await response.json();
          setExchangeRates(data);
    
          if (!baseCurrency && data.length > 0) {
            setBaseCurrency(data[0].currency);
          }

          if (!compareCurrency && data.length > 0) {
            setCompareCurrency(data[1].currency);
          }
        } catch (error) {
          console.error("Error fetching exchange rates:", error);
          
        } finally {
            setLoading(false);
        }
      }, [ baseCurrency, compareCurrency ]);
    
    useEffect(() => {
        fetchExchangeRates();
    }, [ fetchExchangeRates ]);

    useEffect(() => {
        setLoading(true);
        fetch('http://localhost:8080/api/v1/balance', { 
            headers: { 'Authorization': `Bearer ${sessionStorage.getItem('access')}` } 
        })
        .then(response => response.json())
        .then(data => setAccounts(data))
        .catch(error => {
            console.error("THIS IS ", error);
        })
        .finally(() => setLoading(false));
    }, []);

    useEffect(() => {
        if (baseCurrency) {
          setLoading(true);
          fetch('http://localhost:8080/api/v1/rates/updateBaseCurrency', {
            method: 'POST',
            headers: {
              'Authorization': `Bearer ${sessionStorage.getItem('access')}`,
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({ new_base_currency: baseCurrency })
          })
            .then(() => fetchExchangeRates())
            .catch(error => {
              console.error("Error updating base currency:", error);
              setLoading(false);
            });
        }
    }, [ baseCurrency, fetchExchangeRates ]);

    const openConversionModal = (currency, isBase) => {
        setSelectedCurrency(currency);
        setIsBaseCurrency(isBase);
        onConversionOpen();
    };

    const openDepositModal = () => {
        onDepositOpen();
    };

    const accountKeyFn = (info) => `${info.account_number}-${info.currency}`;
    const conversionKeyFn = (info) => info.currency;

    const conversionData = (exchangeRates.length > 1 ) ? exchangeRates.slice(0, 2).map((data) => {
        const { currency, rate } = data;
        return {
            ...data,
            render: () => (
                <>
                    <Icon name={currency} currency/>
                    <CustomText>{currency}</CustomText>
                    <ContainerRowBalanceWrapper>
                        <CustomText>{rate}</CustomText>
                    </ContainerRowBalanceWrapper>
                </>
            ),
            onClick: () => {
                openConversionModal(currency, currency === baseCurrency);
            },
        }
    }) : [];

    console.log("DDDD");
    console.log(accounts);
    
    

    const accountData = (accounts.length > 0) ? accounts.map((data) => {
        const { bank, account_label, balance, currency } = data;
        function handleSendOnClick (e) {
            e.stopPropagation();
            navigate('/remittance/payee', {state: {selectedCurrencyAccount: data}});
            console.log("SEND BUTTON CLICKED");
        }

        return {
            ...data,
            render: () => (
                <>
                    <Icon name={bank} />
                    <InfoBlock>
                        <CustomText gray small>{account_label}</CustomText>
                        <CustomText black big>{useFormatAmount(balance, currency)}</CustomText>
                    </InfoBlock>
                    <CustomButton side onClick={(e) => handleSendOnClick(e)}>send</CustomButton>
                </>
            ),
            onClick: () => {
                navigate('/accounts/' + data.currency);
            },
        };
    }) : [];

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
            <Container name='Conversion' data={conversionData} keyFn={conversionKeyFn} />
            <Container name='Accounts' data={accountData} keyFn={accountKeyFn} sub={<CustomButton xside onClick={openDepositModal}>deposit</CustomButton>}/>

            <Modal isOpen={isConversionOpen} onClose={onConversionClose} size="2xlg">
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>Conversion List</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        <ConversionListPage 
                            onClose={onConversionClose} 
                            selectedCurrency={selectedCurrency} 
                            setSelectedCurrency={setSelectedCurrency} 
                            baseCurrency={baseCurrency}
                            setBaseCurrency={setBaseCurrency}
                            compareCurrency={compareCurrency}
                            setCompareCurrency={setCompareCurrency}
                            isBaseCurrency={isBaseCurrency}
                        />
                    </ModalBody>
                </ModalContent>
            </Modal>

            <Modal isOpen={isDepositOpen} onClose={onDepositClose} size="2xlg">
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>Deposit Funds</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        <DepositPage onClose={onDepositClose} />
                    </ModalBody>
                </ModalContent>
            </Modal>
        </Box>
    );
}
