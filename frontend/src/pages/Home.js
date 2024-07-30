import { Box, useDisclosure, Modal, ModalOverlay, ModalContent, ModalHeader, ModalCloseButton, ModalBody } from "@chakra-ui/react";
import { useState } from "react";
import { useNavigate } from 'react-router-dom';

import useFormatAmount from '../hooks/useFormatAmount';
import CustomButton from "../components/forms/CustomButton";
import CustomText from "../components/CustomText";
import Icon from "../components/util/Icon";
import Container from "../components/container/Container";
import ContainerRowBalanceWrapper from "../components/container/ContainerRowBalanceWrapper";
import InfoBlock from "../components/util/InfoBlock";
import ConversionListPage from "./Conversion/ConversionListPage"; 

const fetchAccountData = [
    {
        'id': '1',
        'account_reference': '66f51c98-1ef8-4e48-97de-aac0353ba2b4',
        'bank': 'mynt',
        'label': 'Mynt Dollar Account',
        'balance': '1010234.0',
        'currency': 'USD',
    },
    {
        'id': '2',
        'account_reference': '66f51c98-1ef8-4e48-97de-aac0353ba2b4',
        'bank': 'others',
        'label': 'Mynt Pound Account',
        'balance': '1000.0',
        'currency': 'GBP',
    },
];

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
    const [ baseCurrency, setBaseCurrency ] = useState(fetchConversionData[0].currency);
    const [ compareCurrency, setCompareCurrency ] = useState(fetchConversionData[1].currency);
    const [ isBaseCurrency, setIsBaseCurrency ] = useState(false);

    const accountKeyFn = (info) => info.id;
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

    const accountData = fetchAccountData.map((data) => {
        const { bank, label, balance, currency } = data;
        return {
            ...data,
            render: () => {
                return (
                    <>
                        <Icon name={bank} />
                        <InfoBlock>
                            <CustomText gray small>{label}</CustomText>
                            <CustomText black big>{useFormatAmount(balance, currency)}</CustomText>
                        </InfoBlock>
                        <CustomButton side onClick={(e) => handleSendOnClick(e)}>Send</CustomButton>
                    </>
                );
            },

            onClick: () => {
                navigate('/accounts/' + data.id);
            },
        }
    });

    return (
        <Box
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
