import { Box } from "@chakra-ui/react";
import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";

import useFormatAmount from "../../hooks/useFormatAmount";

import CustomBox from "../../components/util/CustomBox";
import Icon from "../../components/util/Icon";
import CustomText from "../../components/CustomText";
import Liner from "../../components/Liner";

const fetchTransactionData = {
  id: '1',
  payee_bank: 'M-PESA',
  payer_bank: 'MYNT',
  amount: '1000.0',
  currency: 'USD',
  flow: '+',
  created_at: "2024-07-01T14:13:18+00:00",
};


export default function TransactionPage() {
  const [ transaction, setTransaction ] = useState({});
  const [ loading, setLoading ] = useState(true);
  const location = useLocation();
  const transactionId = location.pathname.split('/')[2];

  useEffect(() => {
    setLoading(true);

    fetch(`http://localhost:8080/api/v1/transaction/payment/${transactionId}`, {
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
  }, [ transactionId ]);

  console.log("????");
  console.log(transaction);
  
  

  const TransactionDetail = ({ label, value }) => (
    <Box display='flex' flexDirection='row' gap='0.7em' alignItems="center" justifyContent='space-around'>
      <Box width='35%'><CustomText medium>{label}:</CustomText></Box>
      <Box width='35%' textAlign='center'><CustomText black medium>{value}</CustomText></Box>
    </Box>
  );

  return (
    <></>
    // <Box
    //   display="flex"
    //   flexDirection='column'
    //   justifyContent="center"
    //   alignItems="center"
    //   gap='1.3em'
    //   margin='auto'
    // >
    //   <CustomBox>
    //     <CustomText small black>Transaction Detail</CustomText>
    //     <Box display='flex' flexDirection='row' gap='0.2em' alignItems="center" marginTop='0.3em'>
    //       <Icon name={payee_bank} />
    //       <CustomText black xbig>{payee_bank}</CustomText>
    //     </Box>
    //     <Box marginLeft="0.4em">
    //       <CustomText black xbig>
    //         {flow}{useFormatAmount(amount, currency)}
    //       </CustomText>
    //     </Box>
    //     <Liner />
    //     <TransactionDetail label="Description" value={payee_bank} />
    //     <TransactionDetail label="Account" value={payer_bank} />
    //     <TransactionDetail label="Transaction Date" value={created_at.split('T')[0]} />
    //     <TransactionDetail label="Transaction Time" value={created_at.split('T')[1].split('+')[0].substring(0, 5)} />
    //   </CustomBox>
    // </Box>
  );
}
