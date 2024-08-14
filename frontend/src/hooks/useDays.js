const DATES = [ "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" ];

export default function useDays(date) {
  return DATES[new Date(date).getDay()];
}