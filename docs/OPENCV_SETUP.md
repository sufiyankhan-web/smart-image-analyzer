# OpenCV Java Integration Guide

## Option A (Recommended): `org.openpnp:opencv`
This project already uses `org.openpnp:opencv` in `backend/pom.xml`.

### Why this option
- Downloads native binaries automatically.
- No manual `java.library.path` configuration in most environments.
- Good for local development and CI.

### Verify setup
1. Run backend build:
   - `cd backend`
   - `mvn clean package`
2. Start backend:
   - `mvn spring-boot:run`
3. Upload a test image via `POST /api/images/upload`.

If upload works and metrics are returned, OpenCV is loaded correctly.

## Option B (Manual native installation)
Use this only if your environment disallows automatic native extraction.

1. Install OpenCV native binaries for your OS.
2. Add JVM arg when starting Spring Boot:
   - `-Djava.library.path=<path-to-opencv-native-lib>`
3. Replace `nu.pattern.OpenCV.loadLocally()` with explicit `System.loadLibrary(Core.NATIVE_LIBRARY_NAME)`.

## Troubleshooting
- **UnsatisfiedLinkError**: native library not found. Use Option A or set `java.library.path` correctly.
- **Image decode failed**: verify uploaded file type is JPG/PNG/WEBP and content is valid.
- **Large memory usage**: keep upload limit at 10MB and release Mats after processing.
