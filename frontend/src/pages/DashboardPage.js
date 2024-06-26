import { useState } from 'react';
import CustomTable from "../components/CustomTable";

const LATEST = 'latest';
const OLDEST = 'oldest';

function DashboardPage() {
  const [ query, setQuery ] = useState({
    date: LATEST,
  });

  const dashboardData = [
    { id: 1, content: "Hello World", date: '25/06/2024' },
    { id: 2, content: "Hello Hi", date: '25/06/2024' },
  ];

  const dashboardConfig = [
    { label: 'content', render: (data) => data.content },
    { label: 'date', render: (data) => data.date, onClick: () => { if (query.date === OLDEST) { setQuery({ ...query, date: LATEST }) } else { setQuery({ ...query, date: OLDEST }) } } },

  ];

  const keyFn = (rowData) => {
    return rowData.id;
  }

  return <CustomTable data={dashboardData} config={dashboardConfig} keyFn={keyFn} />;
}

export default DashboardPage;