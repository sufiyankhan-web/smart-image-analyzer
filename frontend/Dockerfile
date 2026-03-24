# --- Build Stage ---
FROM node:20-alpine AS build
WORKDIR /app

# Accept Railway backend URL as build arg
ARG VITE_API_URL
ENV VITE_API_URL=$VITE_API_URL

COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build

# --- Serve Stage ---
FROM node:20-alpine
WORKDIR /app

RUN npm install -g serve
COPY --from=build /app/dist ./dist

EXPOSE 3000
CMD ["sh", "-c", "serve -s dist -l ${PORT:-3000}"]
