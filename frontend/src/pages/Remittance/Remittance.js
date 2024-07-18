import PageHeader from "../../components/forms/PageHeader";
import Page from "../../components/Page";
import {Outlet} from "react-router-dom";

export default function Remittance() {
    return (
            <Page>
                <PageHeader>Transfer</PageHeader>
                <Outlet/>
            </Page>
    );
}
