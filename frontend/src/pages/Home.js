import { Box, useDisclosure, Modal, ModalOverlay, ModalContent, ModalHeader, ModalCloseButton, ModalBody } from "@chakra-ui/react";
import { useState, useEffect } from "react";
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

const fetchConversionData = [
    {
        'currency': 'GBP',
        'rates': '1',
    },
    {
        'currency': 'USD',
        'rates': '1.4079',
    }
]

export default function Home() {
    const navigate = useNavigate(); 
    const { isOpen, onOpen, onClose } = useDisclosure();
    const [ selectedCurrency, setSelectedCurrency ] = useState(null);
    const [ baseCurrency, setBaseCurrency ] = useState("KES");
    const [ compareCurrency, setCompareCurrency ] = useState("USD");
    const [ isBaseCurrency, setIsBaseCurrency ] = useState(false);
    const [ accounts, setAccounts ] = useState([]);
    // const [ exchangeRates, setExchangeRates ] = useState([]);
    const [ loading, setLoading ] = useState(true); 

    useEffect(() => {
        setLoading(true);
        fetch('http://localhost:8080/api/v1/balance', { 
            headers: { 'Authorization': `Bearer ${sessionStorage.getItem('access')}` } 
        })
        .then(response => 
            response.json())
        .then(data => 
            setAccounts(data)
        )
        .catch(error => {
            console.error("Error fetching accounts:", error);  
        })
        .finally(() => 
            setLoading(false));
      }, []);

    useEffect(() => {
        setLoading(true);
        fetch('http://localhost:8080/api/v1/rates/getBasicRates', { 
            headers: { 'Authorization': `Bearer ${sessionStorage.getItem('access')}` } 
        })
        .then(response => 
            response.json())
        .then(data => 
            setBaseCurrency(data[0].currency)
        )
        .catch(error => {
            console.error("Error fetching accounts:", error);  
        })
        .finally(() => 
            setLoading(false));
      }, [ baseCurrency ]);

    //   useEffect(() => {
    //     setLoading(true);
    //     fetch('http://localhost:8080/api/v1/rates/updateBaseCurrency', { 
    //         method: 'POST',
    //         headers: { 'Authorization': `Bearer ${sessionStorage.getItem('access')}` } ,
    //         body: JSON.stringify({ new_base_currency: selectedCurrency })
    //     })
    //     .catch(error => {
    //         console.error("Error fetching accounts:", error);  
    //     })
    //     .finally(() => {
    //         setLoading(false);
    //         setBaseCurrency(selectedCurrency);
    //     });
    //   }, [ selectedCurrency ]);
    

    const accountKeyFn = (info) => info.account_number;
    const conversionKeyFn = (info) => info.currency;

    const handleSendOnClick = (e) => {
        e.stopPropagation();
        console.log("SEND BUTTON CLICKED");
    }

    const openConversionModal = (currency, isBase) => {
        setSelectedCurrency(currency);
        setIsBaseCurrency(isBase);
        onOpen();
    }

    const conversionData = fetchConversionData.map((data) => {
        const { currency, rates } = data;
        return {
            ...data,
            render: () => (
                <>
                    <Icon name={currency} currency/>
                    <CustomText>{currency}</CustomText>
                    <ContainerRowBalanceWrapper>
                        <CustomText>{rates}</CustomText>
                    </ContainerRowBalanceWrapper>
                </>
            ),
            onClick: () => {
                openConversionModal(currency, currency === baseCurrency);
            },
        }
    });

    const accountData = accounts.map((data) => {
        const { bank, account_label, balance, currency } = data;
        
        return {
            ...data,
            render: () => {
                return (
                    <>
                        <Icon name={bank} />
                        <InfoBlock>
                            <CustomText gray small>{account_label}</CustomText>
                            <CustomText black big>{useFormatAmount(balance, currency)}</CustomText>
                        </InfoBlock>
                        <CustomButton side onClick={(e) => handleSendOnClick(e)}>Send</CustomButton>
                    </>
                );
            },
            onClick: () => {
                navigate('/accounts/' + data.currency);
            },
        }
    });

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
            <Container name='Accounts' data={accountData} keyFn={accountKeyFn} />

            <Modal isOpen={isOpen} onClose={onClose} size="full">
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>Conversion List</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        <ConversionListPage 
                            onClose={onClose} 
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
        </Box>
    );
}
