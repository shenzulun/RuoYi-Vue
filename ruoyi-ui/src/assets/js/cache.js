import storage from 'good-storage'


const CACHE_KEY = '__xj__'


export function loadData() {
  return storage.get(CACHE_KEY, {})
}

export function saveCatch(key, val) {
  let data = storage.get(CACHE_KEY, {})
  data[key] = val
  storage.set(CACHE_KEY, data)
  return data
}