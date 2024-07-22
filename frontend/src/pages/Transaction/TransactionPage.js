import { Box } from "@chakra-ui/react";
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
  currency: '$',
  flow: '+',
  created_at: "2024-07-01T14:13:18+00:00",
};


export default function TransactionPage() {
  const { payee_bank, payer_bank, amount, currency, flow, created_at } = fetchTransactionData;

  const TransactionDetail = ({ label, value }) => (
    <Box display='flex' flexDirection='row' gap='0.7em' alignItems="center" justifyContent='space-around'>
      <Box width='35%'><CustomText medium>{label}:</CustomText></Box>
      <Box width='35%' textAlign='center'><CustomText black medium>{value}</CustomText></Box>
    </Box>
  );

  return (
    <Box
      display="flex"
      flexDirection='column'
      justifyContent="center"
      alignItems="center"
      gap='1.3em'
      margin='auto'
    >
      <CustomBox>
        <CustomText small black>Transaction Detail</CustomText>
        <Box display='flex' flexDirection='row' gap='0.2em' alignItems="center" marginTop='0.3em'>
          <Icon name={payee_bank} />
          <CustomText black xbig>{payee_bank}</CustomText>
        </Box>
        <Box marginLeft="0.4em">
          <CustomText black xbig>
            {flow}{useFormatAmount(amount, currency)}
          </CustomText>
        </Box>
        <Liner />
        <TransactionDetail label="Description" value={payee_bank} />
        <TransactionDetail label="Account" value={payer_bank} />
        <TransactionDetail label="Transaction Date" value={created_at.split('T')[0]} />
        <TransactionDetail label="Transaction Time" value={created_at.split('T')[1].split('+')[0].substring(0, 5)} />
      </CustomBox>
    </Box>
  );
}
