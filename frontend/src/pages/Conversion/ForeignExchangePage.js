import { Box, useDisclosure, Modal, ModalOverlay, ModalContent, ModalCloseButton, ModalBody } from "@chakra-ui/react";
import { useState, useEffect, useCallback } from "react";
import { useNavigate } from "react-router-dom";

import useAllCountryName from "../../hooks/useAllCountryName";
import usePopularCountryName from "../../hooks/usePopularCountryName";

import SplashPage from "../SplashPage";

import CustomBox from "../../components/util/CustomBox";
import Container from "../../components/container/Container";
import CustomText from "../../components/CustomText";
import Icon from "../../components/util/Icon";
import InfoBlock from "../../components/util/InfoBlock";
import SearchBar from "../../components/SearchBar";
import DateTimeDisplay from "../../components/util/DateTimeDisplay";
import ContainerRowBalanceWrapper from "../../components/container/ContainerRowBalanceWrapper";
import ConversionListPage from "./ConversionListPage";

export default function ForeignExchangePage() {
  const [ searchTerm, setSearchTerm ] = useState("");
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [ baseCurrency, setBaseCurrency ] = useState(null);
  const [ exchangeRates, setExchangeRates ] = useState([]);
  const [ loading, setLoading ] = useState(true);

  const navigate = useNavigate();

  const lowercasedSearchTerm = searchTerm.toLowerCase();

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
    } catch (error) {
      console.error("Error fetching exchange rates:", error);
    } finally {
      setLoading(false);
    }
  }, [baseCurrency]);

  useEffect(() => {
    fetchExchangeRates();
  }, [fetchExchangeRates]);

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
  }, [baseCurrency, fetchExchangeRates]);

  const openConversionModal = () => {
    onOpen();
  };

  const baseCurrencyOnClick = () => {
    openConversionModal();
  };

  const filterData = (data) => {
    return data.filter(({ info }) => {
      const countryName = useAllCountryName(info.currency);
      return info.currency.toLowerCase().includes(lowercasedSearchTerm) ||
        countryName.toLowerCase().includes(lowercasedSearchTerm);
    });
  };

  const allCurrencyList = exchangeRates
    .filter(info => info.currency !== baseCurrency)
    .map((info) => {
      const { currency, rate } = info;
      const countryName = useAllCountryName(currency);

      return {
        info,
        render: () => (
          <>
            <Icon name={currency} currency />
            <InfoBlock>
              <CustomText black>{countryName}</CustomText>
              <CustomText medium>{currency}</CustomText>
            </InfoBlock>
            <ContainerRowBalanceWrapper>
              <Box display="flex" flexDirection="column" justifyContent='center' alignItems="center" gap="0.2em">
                <CustomText big black>{rate} {currency}</CustomText>
              </Box>
            </ContainerRowBalanceWrapper>
          </>
        ),
        onClick: () => {
          navigate(`/currencies/${baseCurrency}?compare=${currency}`);
        },
      };
    });

    const popularCurrencyList = exchangeRates
      .filter((info) => info.currency !== baseCurrency && usePopularCountryName(info.currency) != 'Unknown Country')
      .map((info) => {
        const { currency, rate } = info;
        const countryName = usePopularCountryName(currency);

        return {
          info,
          render: () => (
            <>
              <Icon name={currency} currency />
              <InfoBlock>
                <CustomText black>{countryName}</CustomText>
                <CustomText medium>{currency}</CustomText>
              </InfoBlock>
              <ContainerRowBalanceWrapper>
                <Box display="flex" flexDirection="column" justifyContent='center' alignItems="center" gap="0.2em">
                  <CustomText big black>{rate} {currency}</CustomText>
                </Box>
              </ContainerRowBalanceWrapper>
            </>
          ),
          onClick: () => {
            navigate(`/currencies/${baseCurrency}?compare=${currency}`);
          },
        };
      });

  const filteredAllCurrencyList = filterData(allCurrencyList);
  const showFilteredResults = searchTerm.length > 0;

  return (
    loading
      ? <SplashPage />
      : <Box
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
        <CustomBox flexDirection='row' justifyContent='space-between' alignItems='center'>
          <CustomBox>
            <CustomText black marginBottom="0.7em">
              Live Exchange Rates At:
            </CustomText>
            <DateTimeDisplay time={"2024-07-22T14:13:18+00:00"} />
          </CustomBox>
          <CustomBox
            alignItems='center'
            justifyContent='center'
            gap='0.6em'
            onClick={baseCurrencyOnClick}
          >
            <CustomText black big>Base Currency</CustomText>
            <Icon name={baseCurrency} currency />
            <CustomText black big>1 {baseCurrency}</CustomText>
          </CustomBox>
        </CustomBox>
        {showFilteredResults ? (
          <Container
            name="Search Results"
            data={filteredAllCurrencyList}
            keyFn={(item) => item.info.currency}
          />
        ) : (
          <>
            <Container
              name="Popular Countries"
              data={popularCurrencyList}
              keyFn={(item) => item.info.currency}
            />
            <Container
              name="All"
              data={allCurrencyList}
              keyFn={(item) => item.info.currency}
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
