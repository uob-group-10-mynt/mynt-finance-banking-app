// returns e.g. date=latest&price=highest
function useQueryBuilder(query) {
  return Object.entries(query)
    .filter(([ key, value ]) => value !== null) 
    .map(([ key, value ]) => `${encodeURIComponent(key)}=${encodeURIComponent(value)}`) 
    .join('&'); 
}

export default useQueryBuilder;