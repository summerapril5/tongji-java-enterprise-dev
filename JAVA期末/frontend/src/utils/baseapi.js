import axios from 'axios';

export const httpInstance = axios.create({
  baseURL: '/api',
  timeout: 5000
})