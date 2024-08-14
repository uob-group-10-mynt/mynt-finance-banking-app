import { Box } from "@chakra-ui/react";
import CalendarCell from "./CalendarCell";
import CalendarRow from "./CalendarRow";
import TransactionCircle from "./TransactionCircle";
import CustomText from "../CustomText";

const DATES = [ "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" ];
const LASTDAYS = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];

const TRANSACTION_TRAFFIC_COLOUR = {
  NONE: 'white', // WHITE
  LOW: '#B2EBF2',   // Light Blue
  MEDIUM: '#FFCC80', // Light Orange
  HIGH: '#FF8A65'   // Light Red
};

function getColorForTransactionVolume(count) {
  if (count === 0) {
    return TRANSACTION_TRAFFIC_COLOUR.NONE;
  } else if (count <= 5) {
    return TRANSACTION_TRAFFIC_COLOUR.LOW;
  } else if (count <= 10) {
    return TRANSACTION_TRAFFIC_COLOUR.MEDIUM;
  } else {
    return TRANSACTION_TRAFFIC_COLOUR.HIGH;
  }
}

function getLastDateOfMonth(year, month) {
  return new Date(year, month, 0).getDate();
}

function getFirstDayOfMonth(year, month) {
  return new Date(year, month - 1, 1).getDay();
}

function generateCalendar(year, month, data, onClick) {
  const firstDayOfMonth = getFirstDayOfMonth(year, month);
  const daysInMonth = getLastDateOfMonth(year, month);
  const numOfWeeks = Math.ceil((firstDayOfMonth + daysInMonth) / 7);

  let currentDay = 1;
  const calendarCells = [];

  for (let week = 0; week < numOfWeeks; week++) {
    const calendarWeek = [];

    for (let dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++) {
      const currentIndex = week * 7 + dayOfWeek + 1;
      const dayNumber = currentIndex - firstDayOfMonth;

      if (currentIndex <= firstDayOfMonth || dayNumber > daysInMonth) {
        calendarWeek.push(
          <CalendarCell key={`outside-${currentIndex}`}>
            <CustomText small gray> </CustomText>
          </CalendarCell>
        );
      } else {
        const transactionCount = data[dayNumber] || 0;
        const colour = getColorForTransactionVolume(transactionCount)

        calendarWeek.push(
          <CalendarCell key={`current-${dayNumber}`} onClick={onClick}>
            <CustomText small gray>{dayNumber}</CustomText>
            <TransactionCircle colour={colour} />
          </CalendarCell>
        );

        currentDay++;
      }
    }

    calendarCells.push(<CalendarRow key={week}>{calendarWeek}</CalendarRow>);
  }

  return calendarCells;
}

function Calendar({ year, month, data, onClick }) {
  if ((year % 4 === 0 && year % 100 !== 0) || year % 400 === 0) {
    LASTDAYS[1] = 29;
  }

  const calendarCells = generateCalendar(year, month, data, onClick);

  return (
    <Box display="flex" flexDirection="column" gap="1em">
      <CalendarRow>
        {DATES.map((date) => (
          <CalendarCell key={date}>
            <CustomText small gray>{date}</CustomText>
          </CalendarCell>
        ))}
      </CalendarRow>
      {calendarCells}
    </Box>
  );
}

export default Calendar;
