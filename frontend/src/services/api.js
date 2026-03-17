import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080/api',
  timeout: 30000
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('sia_auth_token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export async function uploadImage(file, onUploadProgress) {
  const formData = new FormData();
  formData.append('file', file);

  const response = await api.post('/images/upload', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
    onUploadProgress
  });

  return response.data;
}

export async function getImageAnalysisById(id) {
  const response = await api.get(`/images/${id}`);
  return response.data;
}

export async function getAllAnalyses() {
  const response = await api.get('/images');
  return response.data;
}

export async function compareImages(firstFile, secondFile) {
  const formData = new FormData();
  formData.append('firstFile', firstFile);
  formData.append('secondFile', secondFile);

  const response = await api.post('/images/compare', formData, {
    headers: { 'Content-Type': 'multipart/form-data' }
  });

  return response.data;
}

export async function analyzeBatch(files) {
  const formData = new FormData();
  files.forEach((file) => formData.append('files', file));

  const response = await api.post('/images/batch', formData, {
    headers: { 'Content-Type': 'multipart/form-data' }
  });

  return response.data;
}

export async function loginCustomer(payload) {
  const response = await api.post('/auth/login', payload);
  return response.data;
}

export default api;
