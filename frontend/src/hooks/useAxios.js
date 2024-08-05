import axios from "axios";
import { useState, useEffect } from 'react';

function useAxios(endpoint, method = "get", options = {}) {
  const [ fetchData, setFetchData ] = useState([]);
  const [ error, setError ] = useState(null);
  const [ loading, setLoading ] = useState(true);
  const token = sessionStorage.getItem('access');  

  const fetchResult = async () => {
    try {      
      const response = await axios({
        url: `http://localhost:8080/api/v1/${endpoint}`,
        method,
        headers: {
          'Content-Type': 'application/json;charset=utf-8',
          "Authorization": `Bearer ${token}`,
        },
        ...options
      });
                  
      setFetchData(JSON.stringify(response.data));
    } catch (e) { 
      setError(e.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (endpoint != null) {
      fetchResult();
    }
    
  }, [ endpoint ]);

  return [ fetchData, error, loading ];
}

export default useAxios;
