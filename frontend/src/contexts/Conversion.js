import { createContext, useState } from "react";
import useAxios from "../hooks/useAxios";

const ConversionContext = createContext();

function ConversionProvider({ children }) {
  const [ conversionRequest, setConversionRequest ] = useState({});

  const fetchBaseCurrency = useAxios();
  const updateBaseCurrency = useAxios();
  const fetchAllExchangeRates = useAxios();

  const valueToShare = {
    fetchBaseCurrency,
    updateBaseCurrency,
    fetchAllExchangeRates,
    conversionRequest,
    setConversionRequest
  };

  return (
    <ConversionContext.Provider value={valueToShare}>
      {children}
    </ConversionContext.Provider>
  );
}

export default ConversionContext
export { ConversionProvider };
