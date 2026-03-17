# Smart Image Quality Analyzer

> A production-ready, full-stack computer vision platform that scores image quality across multiple dimensions and delivers an actionable improvement report — all in real time.

![Java](https://img.shields.io/badge/Java-17-007396?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-6DB33F?logo=spring-boot&logoColor=white)
![React](https://img.shields.io/badge/React-18-61DAFB?logo=react&logoColor=black)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Railway-336791?logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Containerised-2496ED?logo=docker&logoColor=white)

---

## Live Demo

| Service | URL |
|---------|-----|
| Frontend | https://illustrious-empathy-production.up.railway.app |
| Backend API | https://smart-image-analyzer-production.up.railway.app/api |

---

## Features

- **Authentication** — protected dashboard behind a login gate
- **Drag-and-drop upload** — supports JPEG, PNG, WEBP up to 10 MB
- **Multi-module quality analysis** powered by OpenCV:
  - Sharpness / blur detection (Laplacian variance)
  - Exposure & lighting (over/under-exposure percentages, histogram balance)
  - Contrast measurement
  - Composition scoring (rule of thirds, golden ratio, symmetry)
  - Subject detection & centroid mapping
  - Face detection & face-quality scoring
  - Noise estimation
  - EXIF metadata extraction (ISO, aperture, shutter speed, focal length, camera model)
  - Scene-type awareness
- **Weighted final score (0 – 100)** with per-module breakdown
- **AI-style suggestion panel** — prioritised, actionable feedback
- **Visual dashboard** — score ring, metric progress bars, exposure histogram, module score cards, composition overlay
- **Batch analysis** — upload multiple images, get ranked results and quality distribution
- **Image comparison** — side-by-side winner across sharpness, exposure, contrast, and overall score
- **Analysis history** — every result persisted in PostgreSQL and retrievable by ID
- **Production error handling** — structured `ApiErrorDto` responses for all error paths

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Frontend | React 18, Vite 5, Tailwind CSS, Recharts, Axios, React Router |
| Backend | Spring Boot 3.5, Java 17, Spring Data JPA, Bean Validation |
| Image Processing | OpenCV 4.9 (openpnp), metadata-extractor |
| Database | PostgreSQL (production) · MySQL (local dev) |
| Deployment | Railway — separate Docker services for frontend and backend |
| Containerisation | Multi-stage Dockerfiles (Maven build → JRE runtime, Node build → serve) |

---

## Project Structure

```
smart-image-analyzer/
├── backend/                        # Spring Boot REST API
│   ├── Dockerfile                  # Multi-stage Maven → JRE image
│   ├── railway.json                # Railway Docker builder config
│   └── src/main/java/com/project/
│       ├── controller/             # REST endpoints (images, auth)
│       ├── service/                # Business logic
│       ├── util/analysis/          # OpenCV analysis modules
│       ├── model/                  # JPA entity
│       ├── dto/                    # Request/response DTOs
│       ├── repository/             # Spring Data JPA
│       ├── config/                 # CORS configuration
│       └── exception/              # Global exception handling
├── frontend/                       # React client
│   ├── Dockerfile                  # Node build → serve image
│   ├── railway.json                # Railway Docker builder config
│   └── src/
│       ├── pages/                  # LandingPage, DashboardPage, LoginPage
│       ├── components/             # Reusable UI components
│       ├── services/               # Axios API client
│       └── utils/                  # Auth helpers, analysis utilities
└── docs/
    ├── OPENCV_SETUP.md             # OpenCV local setup guide
    └── Smart-Image-Analyzer.postman_collection.json
```

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/images/upload` | Upload & analyse an image |
| `GET` | `/api/images` | List all analyses (newest first) |
| `GET` | `/api/images/{id}` | Fetch one analysis by ID |
| `DELETE` | `/api/images/{id}` | Delete an analysis |
| `POST` | `/api/images/batch` | Analyse multiple images at once |
| `POST` | `/api/images/compare` | Compare two images |
| `POST` | `/api/auth/login` | Log in, receive session token |

Import `docs/Smart-Image-Analyzer.postman_collection.json` to try every endpoint.

---

## Run Locally

### Prerequisites
- Java 17+
- Maven 3.9+
- Node.js 20+
- MySQL running locally (or swap `application.properties` for PostgreSQL)
- OpenCV native libs — see [docs/OPENCV_SETUP.md](docs/OPENCV_SETUP.md)

### Backend

```bash
cd backend
mvn spring-boot:run
```

Defaults to `http://localhost:8080`. Env vars that override defaults:

| Variable | Default |
|----------|---------|
| `DB_URL` | `jdbc:mysql://localhost:3306/smart_image_analyzer` |
| `DB_USERNAME` | `root` |
| `DB_PASSWORD` | _(empty)_ |
| `CORS_ALLOWED_ORIGIN` | `http://localhost:5173` |

### Frontend

```bash
cd frontend
npm install
npm run dev
```

Defaults to `http://localhost:5173`. Set `VITE_API_URL` in `.env.local` if your backend is not on port 8080.

### Default login credentials (demo)

```
Email:    customer@smartphoto.ai
Password: Customer@123
```

---

## Railway Deployment

Two separate Railway services, each pointing to this repository with the root directory set to their respective folder.

| Setting | Backend service | Frontend service |
|---------|----------------|-----------------|
| Root directory | `backend` | `frontend` |
| Builder | Dockerfile | Dockerfile |

**Backend environment variables:**

```
SPRING_PROFILES_ACTIVE=prod
CORS_ALLOWED_ORIGIN=<your-frontend-railway-url>
PGHOST / PGPORT / PGDATABASE / PGUSER / PGPASSWORD  ← injected by Railway PostgreSQL plugin
```

**Frontend environment variables:**

```
VITE_API_URL=<your-backend-railway-url>/api
```
