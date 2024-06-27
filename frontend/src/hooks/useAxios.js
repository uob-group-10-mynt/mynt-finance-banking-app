import axios from "axios";
import { useState, useEffect } from 'react';

function useAxios(endpoint, method = "get", options = {}) {
  const [ fetchData, setFetchData ] = useState(null);
  const [ error, setError ] = useState(null);
  const [ loading, setLoading ] = useState(true);
  // const token = localStorage.getItem("accessToken");

  const fetchResult = async () => {
    try {
      const response = await axios({
        url: endpoint,
        method,
        baseURL: process.env.REACT_APP_BASE_URL,
        headers: {
          'Content-Type': 'application/json;charset=utf-8',
          // Authorization: 
        },
        ...options
      });
      setFetchData(response.data);
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchResult();
  }, []);

  return { fetchData, error, loading };
}

export default useAxios;
