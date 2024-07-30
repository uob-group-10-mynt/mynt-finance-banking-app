// LineChart.js
import React from 'react';
import { Line } from 'react-chartjs-2';
import 'chart.js/auto';

const LineChart = ({ data, label }) => {
  const times = data.map(d => {
    const date = new Date(d.settlement_date);
    const formattedDate = date.toLocaleDateString(); 
    const formattedTime = date.toLocaleTimeString(); 
    return `${formattedDate} ${formattedTime}`; 
  });
  const rates = data.map(d => parseFloat(d.rates));

  const chartData = {
    labels: times,
    datasets: [
      {
        label: label,
        data: rates,
        fill: false,
        borderColor: 'blue',
        tension: 0.1,
        backgroundColor: 'transparent',
      },
    ],
  };

  const chartOptions = {
    responsive: true,
    plugins: {
      legend: {
        display: false,
        position: 'top',
      },
      tooltip: {
        mode: 'index',
        intersect: false,
      },
    },
    scales: {
      x: {
        display: false, 
        title: {
          display: false,
        },
      },
      y: {
        display: false, 
        title: {
          display: false,
        },
        beginAtZero: false,
      },
    },
  };

  return (
    <div>
      <Line data={chartData} options={chartOptions} />
    </div>
  );
};

export default LineChart;
