const AUTH_TOKEN_KEY = 'sia_auth_token';
const AUTH_USER_KEY = 'sia_auth_user';

export function saveAuthSession(loginResponse) {
  localStorage.setItem(AUTH_TOKEN_KEY, loginResponse.token);
  localStorage.setItem(AUTH_USER_KEY, JSON.stringify({
    email: loginResponse.email,
    name: loginResponse.name,
    role: loginResponse.role
  }));
}

export function clearAuthSession() {
  localStorage.removeItem(AUTH_TOKEN_KEY);
  localStorage.removeItem(AUTH_USER_KEY);
}

export function isAuthenticated() {
  return Boolean(localStorage.getItem(AUTH_TOKEN_KEY));
}

export function getCurrentUser() {
  const raw = localStorage.getItem(AUTH_USER_KEY);
  if (!raw) return null;
  try {
    return JSON.parse(raw);
  } catch {
    return null;
  }
}
