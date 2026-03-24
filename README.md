# Smart Image Quality Analyzer

Smart Image Quality Analyzer is a full-stack web app that evaluates photo quality using OpenCV and presents results in a clean dashboard.

The goal of this project is simple: make technical image-quality signals understandable and practical for everyday users.

## Live Demo

- Frontend: https://illustrious-empathy-production.up.railway.app
- Backend API: https://smart-image-analyzer-production.up.railway.app/api

## What It Does

- Upload an image (JPEG, PNG, WEBP, up to 10 MB)
- Analyze quality across multiple dimensions
- Return a final score from 0 to 100
- Show visual charts and module-level scores
- Generate practical suggestions to improve image quality
- Support compare mode for two images
- Support batch analysis for multiple images
- Persist and retrieve analysis history

## Core Analysis Modules

- Sharpness and blur detection
- Exposure and clipping analysis
- Contrast analysis
- Composition scoring (rule of thirds, golden ratio, symmetry)
- Subject and face-aware evaluation
- Noise estimation
- EXIF metadata extraction
- Scene awareness

## Tech Stack

- Frontend: React, Vite, Tailwind CSS, Recharts, Axios
- Backend: Spring Boot 3.5, Java 17, Spring Data JPA, Bean Validation
- Vision: OpenCV (openpnp), metadata-extractor
- Database: PostgreSQL (Railway), MySQL for local development
- Deployment: Docker + Railway

## API Endpoints

- POST /api/images/upload
- GET /api/images
- GET /api/images/{id}
- DELETE /api/images/{id}
- POST /api/images/batch
- POST /api/images/compare

Postman collection: docs/Smart-Image-Analyzer.postman_collection.json

## Run Locally

Prerequisites:

- Java 17+
- Maven 3.9+
- Node.js 20+
- MySQL running locally
- OpenCV setup from docs/OPENCV_SETUP.md

Backend:

```bash
cd backend
mvn spring-boot:run
```

Frontend:

```bash
cd frontend
npm install
npm run dev
```

## Deployment (Railway)

Use two separate services from the same repository:

- Backend service root directory: backend
- Frontend service root directory: frontend
- Builder: Dockerfile for both services

Backend variables:

- SPRING_PROFILES_ACTIVE=prod
- CORS_ALLOWED_ORIGIN=<your-frontend-url>
- PGHOST / PGPORT / PGDATABASE / PGUSER / PGPASSWORD (injected by Railway PostgreSQL)

Frontend variables:

- VITE_API_URL=<your-backend-url>/api

## Note

This project is not meant to claim perfect photographic judgment. It is a practical engineering project that combines computer vision signals into a consistent and useful scoring workflow.
