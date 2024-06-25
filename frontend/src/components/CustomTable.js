import React from 'react';
import {
  Table,
  Thead,
  Tbody,
  Tfoot,
  Tr,
  Th,
  Td,
  TableCaption,
  TableContainer,
} from '@chakra-ui/react'

function CustomTable({ data, config, keyFn }) {
  const renderedHeaders = config.map((column) => {
    return <Th key={column.label} onClick={column.onClick}>{column.label}</Th>;
  });

  const renderedRows = data.map((rowData) => {  
    const renderedCells = config.map((column) => {
      return <Td key={column.label}>{column.render(rowData)}</Td>
    });

    return (
      <Tr key={keyFn(rowData)}>
        {renderedCells}
      </Tr>
    )
  });

  return (
    <TableContainer>
      <Table variant='simple'>
        {/* <TableCaption></TableCaption> */}
        <Thead>
          <Tr>
            {renderedHeaders}
          </Tr>
        </Thead>
        <Tbody>
          {renderedRows}
        </Tbody>
        {/* <Tfoot></Tfoot> */}
      </Table>
    </TableContainer>
  );
}

export default CustomTable;