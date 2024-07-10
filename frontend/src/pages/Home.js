import CustomButton from "../components/forms/CustomButton";
import CustomText from "../components/CustomText";
import Icon from "../components/Icon";
import Container from "../components/Container";
import InfoBlock from "../components/InfoBlock";
import { Box } from "@chakra-ui/react";
import { SuccessBlock } from "../components/successBlock";

const fetchAccountData = [
    {
        'id': '1',
        'bank': 'mynt',
        'label': 'Mynt Dollar Account',
        'amount': '100',
        'currency': 'dollar',
        'currencySymbol': '$'
    },
    {
        'id': '2',
        'bank': 'others',
        'label': 'Mynt Pound Account',
        'amount': '100',
        'currency': 'pound',
        'currencySymbol': '£'
    },
];

export default function Home() {
    const accountKeyFn = (info) => info.id;
    const conversionKeyFn = (info) => info.id;

    const accountData = fetchAccountData.map((data) => {
        return {
            ...data,
            render: () => {
                return (
                    <>
                        <Icon bank={data.bank} />
                        <InfoBlock>
                            <CustomText gray small>{data.label}</CustomText>
                            <CustomText black big>{data.currencySymbol}{data.amount}</CustomText>
                        </InfoBlock>
                        <CustomButton side>Send</CustomButton>
                        
                    </>
                );
            },

            onClick: () => {
                console.log('CLICKED');
            },

            onMouseEnter: () => {
                console.log('ENTERED');
            }
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
            <Container name='Conversion' keyFn={conversionKeyFn} />
            <Container name='Accounts' data={accountData} keyFn={accountKeyFn} />
        </Box>
    );
}
