import { Box } from "@chakra-ui/react";
import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";

import SplashPage from "../SplashPage";

import CustomBox from "../../components/util/CustomBox";
import Icon from "../../components/util/Icon";
import CustomText from "../../components/CustomText";
import Liner from "../../components/Liner";

const TransactionDetail = ({ label, value }) => {
  // If value is an object, render its properties
  if (typeof value === 'object' && value !== null) {
    return (
      <Box>
        {Object.keys(value).map(subKey => (
          <TransactionDetail
            key={subKey}
            label={subKey.replace(/_/g, ' ')}
            value={value[subKey]}
          />
        ))}
      </Box>
    );
  }

  return (
    <Box display='flex' flexDirection='row' gap='0.7em' alignItems="center" justifyContent='space-around'>
      <Box width='35%'><CustomText medium>{label}:</CustomText></Box>
      <Box width='35%' textAlign='center'><CustomText black medium>{value}</CustomText></Box>
    </Box>
  );
};

export default function TransactionPage() {
  const [transaction, setTransaction] = useState({});
  const [loading, setLoading] = useState(true);
  const location = useLocation();
  const transactionType = location.pathname.split('/')[2];
  const transactionId = location.pathname.split('/')[3];

  useEffect(() => {
    setLoading(true);

    fetch(`http://localhost:8080/api/v1/transaction/${transactionType}/${transactionId}`, {
      headers: { 'Authorization': `Bearer ${sessionStorage.getItem('access')}` }
    })
      .then(response => response.json())
      .then(data => {
        setTransaction(data);
      })
      .catch(e => {
        console.error("ERROR: ", e);
      })
      .finally(() => {
        setLoading(false);
      });
  }, [transactionId, transactionType]);

  if (loading) {
    return <SplashPage />;
  }

  // Extract nested objects directly
  const renderNestedDetails = (obj) => {
    return Object.keys(obj).map(key => {
      if (typeof obj[key] === 'object' && obj[key] !== null) {
        return (
          <Box key={key}>
            {Object.keys(obj[key]).map(subKey => (
              <TransactionDetail
                key={subKey}
                label={subKey.replace(/_/g, ' ')}
                value={obj[key][subKey]}
              />
            ))}
          </Box>
        );
      } else {
        return (
          <TransactionDetail
            key={key}
            label={key.replace(/_/g, ' ')}
            value={obj[key]}
          />
        );
      }
    });
  };

  return (
    <Box display="flex" flexDirection='column' justifyContent="center" alignItems="center" gap='1.3em' margin='auto'>
      <CustomBox>
        <CustomText small black>Transaction Detail</CustomText>
        {transaction.payee_bank && (
          <Box display='flex' flexDirection='row' gap='0.2em' alignItems="center" marginTop='0.3em'>
            <Icon name={transaction.payee_bank} />
            <CustomText black xbig>{transaction.payee_bank}</CustomText>
          </Box>
        )}
        <Box marginLeft="0.4em">
          <CustomText black xbig>
            {transaction.flow}{transaction.amount} {transaction.currency}
          </CustomText>
        </Box>
        <Liner />
        {Object.keys(transaction).map((key) => (
          key !== 'payee_bank' && key !== 'flow' && key !== 'amount' && key !== 'currency' &&
          renderNestedDetails({ [key]: transaction[key] })
        ))}
      </CustomBox>
    </Box>
  );
}
