import { useContext } from "react";

import ConversionContext from "../contexts/Conversion";

export default function useConversion() {
  return useContext(ConversionContext);
}