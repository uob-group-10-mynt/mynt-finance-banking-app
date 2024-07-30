import { Box, useDisclosure, Modal, ModalOverlay, ModalContent, ModalHeader, ModalCloseButton, ModalBody } from "@chakra-ui/react";
import { TriangleUpIcon, TriangleDownIcon } from '@chakra-ui/icons'; 
import { useState } from "react";
import { useNavigate } from "react-router-dom";

import useAllCountryName from "../../hooks/useAllCountryName";
import usePopularCountryName from "../../hooks/usePopularCountryName";
import useDays from "../../hooks/useDays";

import CustomBox from "../../components/util/CustomBox";
import Container from "../../components/container/Container";
import CustomText from "../../components/CustomText";
import Icon from "../../components/util/Icon";
import InfoBlock from "../../components/util/InfoBlock";
import SearchBar from "../../components/SearchBar";
import DateTimeDisplay from "../../components/util/DateTimeDisplay";
import ContainerRowBalanceWrapper from "../../components/container/ContainerRowBalanceWrapper";
import ConversionListPage from "./ConversionListPage";


const popularCurrencyRates = [
  {
    rates: '1.60',
    buy_currency: 'KES',
    sell_currency: 'USD',
    settlement_date: "2024-07-22T14:13:18+00:00", 
    day_before_settlement_date: "2024-07-21T14:13:18+00:00", 
    amount: "-0.11"
  },
  {
    rates: '1.1',
    buy_currency: 'GBP',
    sell_currency: 'USD',
    settlement_date: "2024-07-22T14:13:18+00:00", 
    day_before_settlement_date: "2024-07-21T14:13:18+00:00", 
    amount: "0.32"
  },
  {
    rates: '1.32',
    buy_currency: 'EUR',
    sell_currency: 'USD',
    settlement_date: "2024-07-22T14:13:18+00:00", 
    day_before_settlement_date: "2024-07-21T14:13:18+00:00", 
    amount: "-0.452"
  },
];

export default function ForeignExchangePage() {
  const [ searchTerm, setSearchTerm ] = useState("");
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [ baseCurrency, setBaseCurrency ] = useState(popularCurrencyRates[0].sell_currency);
  const navigate = useNavigate();

  const lowercasedSearchTerm = searchTerm.toLowerCase();

  const openConversionModal = () => {
    onOpen();
  }

  const baseCurrencyOnClick = () => {
    openConversionModal()
  };

  const filterData = (data) => {
    return data.filter(({ info }) => {
      const countryName = useAllCountryName(info.buy_currency);
      return info.buy_currency.toLowerCase().includes(lowercasedSearchTerm) ||
             countryName.toLowerCase().includes(lowercasedSearchTerm);
    });
  };

  const containerSub = (
    <Box>
      <CustomText gray small>
        than last {useDays(popularCurrencyRates[0].day_before_settlement_date)}
      </CustomText>
      <TriangleDownIcon color='blue' fontSize={[ '0.5em', '1em' ]}/>
      <TriangleUpIcon color='red' fontSize={[ '0.5em', '1em' ]}/>
    </Box>
  );

  const popularCurrencyList = popularCurrencyRates.map((info) => {
    const { rates, buy_currency, sell_currency, amount } = info;
    const latestRate = parseFloat(amount);
    const rateChangeIcon = latestRate < 0 ? <TriangleDownIcon color='blue' /> : <TriangleUpIcon color='red' />;
    const formattedAmount = Math.abs(latestRate);
    const countryName = usePopularCountryName(buy_currency);

    return {
      info,
      render: () => (
        <>
          <Icon name={buy_currency} currency />
          <InfoBlock>
            <CustomText black>{countryName}</CustomText> 
            <CustomText medium>{buy_currency}</CustomText> 
          </InfoBlock>
          <ContainerRowBalanceWrapper>
            <Box display="flex" flexDirection="column" justifyContent='center' alignItems="center" gap="0.2em">
              <CustomText big black>{rates} {buy_currency}</CustomText>
              <Box display="flex" flexDirection="row" alignItems="center" gap="0.2em">
                {rateChangeIcon}
                <CustomText black medium>{formattedAmount}</CustomText>
              </Box>
            </Box>
          </ContainerRowBalanceWrapper>
        </>
      ),
      onClick: () => {
        navigate(`/currencies/${baseCurrency}?compare=${buy_currency}`);
      },
    };
  });

  const allCurrencyList = popularCurrencyList; // Assuming allCurrencyList and popularCurrencyList are the same for simplicity

  const filteredAllCurrencyList = filterData(allCurrencyList);
  const showFilteredResults = searchTerm.length > 0;

  return (
    <Box
      display="flex"
      flexDirection='column'
      justifyContent="center"
      alignItems="center"
      gap='1.3em'
      margin='auto'
    >
      <SearchBar
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        placeholder="Search by country or currency code"
      />
      <CustomBox flexDirection='row' justifyContent='space-between' alignItem='center'>
        <CustomBox>
          <CustomText black marginBottom="0.7em">
            Live Exchange Rates At:
          </CustomText>
          <DateTimeDisplay time={popularCurrencyRates[0].settlement_date} />
        </CustomBox>
        <CustomBox 
          alignItems='center' 
          justifyContent='center' 
          gap='0.6em'
          onClick={baseCurrencyOnClick}
        >
          <CustomText black big>Base Currency</CustomText>
          <Icon name={baseCurrency} currency/>
          <CustomText black big>1 {baseCurrency}</CustomText>
        </CustomBox>
      </CustomBox>
      {showFilteredResults ? (
        <Container
          name="Search Results"
          data={filteredAllCurrencyList}
          sub={containerSub}
          keyFn={(item) => item.info.buy_currency}
        />
      ) : (
        <>
          <Container
            name="Popular Countries"
            data={popularCurrencyList}
            sub={containerSub}
            keyFn={(item) => item.info.buy_currency}
          />
          <Container
            name="All"
            data={allCurrencyList}
            sub={containerSub}
            keyFn={(item) => item.info.buy_currency}
          />
        </>
        )}
        <Modal isOpen={isOpen} onClose={onClose} size="2xl">
          <ModalOverlay />
            <ModalContent>
                <ModalCloseButton />
                  <ModalBody>
                    <ConversionListPage 
                      onClose={onClose} 
                      selectedCurrency={baseCurrency}
                      setSelectedCurrency={setBaseCurrency}
                      setBaseCurrency={setBaseCurrency}
                      isBaseCurrency={true}
                    />
                  </ModalBody>
              </ModalContent>
          </Modal>
    </Box>
  );
}
