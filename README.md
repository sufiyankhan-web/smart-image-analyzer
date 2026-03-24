# Smart Image Quality Analyzer

> A production-ready full-stack computer vision project that evaluates image quality and returns practical, explainable feedback.

![Java](https://img.shields.io/badge/Java-17-007396?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-6DB33F?logo=spring-boot&logoColor=white)
![React](https://img.shields.io/badge/React-18-61DAFB?logo=react&logoColor=black)
![Vite](https://img.shields.io/badge/Vite-5-646CFF?logo=vite&logoColor=white)
![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-3-06B6D4?logo=tailwindcss&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Railway-336791?logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED?logo=docker&logoColor=white)
![OpenCV](https://img.shields.io/badge/OpenCV-4.9-5C3EE8?logo=opencv&logoColor=white)

---

## Live Demo

| Service | URL |
|---------|-----|
| Frontend | https://illustrious-empathy-production.up.railway.app |
| Backend API | https://smart-image-analyzer-production.up.railway.app/api |

---

## Highlights

- Upload and analyze JPEG, PNG, and WEBP images (up to 10 MB)
- Multi-module OpenCV analysis pipeline with weighted scoring
- Final quality score (0-100) with component-level breakdown
- Visual dashboard with charts, score rings, and overlays
- Comparison mode for two images
- Batch analysis for multiple files
- Persistent analysis history via REST APIs

---

## Analysis Coverage

- Sharpness and blur detection
- Exposure and clipping analysis
- Contrast scoring
- Composition (rule of thirds, golden ratio, symmetry)
- Subject and face-aware signals
- Noise estimation
- EXIF metadata extraction (ISO, aperture, shutter speed, focal length, camera model)
- Scene awareness

---

## Tech Stack

| Layer | Tools |
|------|-------|
| Frontend | React, Vite, Tailwind CSS, Recharts, Axios |
| Backend | Spring Boot, Java 17, Spring Data JPA, Bean Validation |
| Vision | OpenCV (openpnp), metadata-extractor |
| Database | PostgreSQL (production), MySQL (local) |
| Deployment | Docker, Railway |

---

## API Endpoints

| Method | Endpoint | Purpose |
|--------|----------|---------|
| `POST` | `/api/images/upload` | Upload and analyze one image |
| `GET` | `/api/images` | List all analyses |
| `GET` | `/api/images/{id}` | Get one analysis by ID |
| `DELETE` | `/api/images/{id}` | Delete analysis |
| `POST` | `/api/images/batch` | Analyze multiple images |
| `POST` | `/api/images/compare` | Compare two images |

Postman collection: `docs/Smart-Image-Analyzer.postman_collection.json`

---

## Project Structure

```text
smart-image-analyzer/
├── backend/
│   ├── src/main/java/com/project/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── util/analysis/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── dto/
│   │   ├── config/
│   │   └── exception/
│   ├── src/main/resources/
│   └── Dockerfile
├── frontend/
│   ├── src/
│   │   ├── pages/
│   │   ├── components/
│   │   ├── services/
│   │   └── utils/
│   └── Dockerfile
└── docs/
```

---

## Local Setup

### Prerequisites

- Java 17+
- Maven 3.9+
- Node.js 20+
- MySQL for local development
- OpenCV setup from `docs/OPENCV_SETUP.md`

### Backend

```bash
cd backend
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

---

## Railway Deployment

Configure two services from the same repository:

- Backend root directory: `backend`
- Frontend root directory: `frontend`
- Build method: Dockerfile for both

Backend variables:

- `SPRING_PROFILES_ACTIVE=prod`
- `CORS_ALLOWED_ORIGIN=<your-frontend-url>`
- `PGHOST`, `PGPORT`, `PGDATABASE`, `PGUSER`, `PGPASSWORD` (from Railway PostgreSQL)

Frontend variables:

- `VITE_API_URL=<your-backend-url>/api`

---

## Note

This project is built as a practical engineering solution, not a perfect replacement for professional photographic judgment. The focus is on clear signals, consistent scoring, and useful feedback.
