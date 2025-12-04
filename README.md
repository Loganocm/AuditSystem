# VoyageVista - Voyage Management & Audit System

## Project Overview
VoyageVista is a Full Stack enterprise application designed to manage cruise itineraries while maintaining strict data integrity through automated audit logging. 

**This project was designed specifically to address challenges in high-volume hospitality logistics, specifically focusing on:**
1.  **Audit Traceability:** Automatically capturing user, date, time, and specific details whenever a record is created or updated.
2.  **Itinerary Management:** A centralized interface for managing voyage dates and statuses.

## Tech Stack
* **Backend:** Java 17, Spring Boot, Spring Data JPA
* **Frontend:** React, TypeScript, Tailwind CSS
* **Database:** H2 (In-Memory for demo) / MySQL compatible

## Key Features
* **Automated Audit Trails:** The system utilizes a service-layer interception pattern to log every mutation of the Voyage entity. This ensures compliance and historical tracking without requiring manual input from the user.
* **Voyage Lifecycle Management:** Create and track voyage statuses (Scheduled -> Active -> Completed).
* **Bulk Maintenance:** Clear all voyages and/or clear all audit logs via UI buttons (calls DELETE /api/voyages and DELETE /api/audit-logs).
* **Per-Session Data Isolation (Simple Mode):** Data shown is isolated per browser session using a lightweight session key. The frontend generates a stable UUID stored in localStorage and sends it with each request in the `X-Session-Id` header. The backend stores and filters `Voyage` and `AuditLog` records by this session key. This keeps each user's screen independent without authentication.
* **Type-Safe Frontend:** Built with TypeScript to ensure reliability and maintainability.

## How to Run
1.  Clone the repository.
2.  Backend (Gradle): `./gradlew bootRun` (Linux/Mac) or `gradlew.bat bootRun` (Windows). The API runs on http://localhost:8080.
3.  Frontend (Vite): In `frontend/`, run `npm install` then `npm run dev` (served at http://localhost:3000`).
4.  API Base URL for the frontend: The app now reads `VITE_API_URL` and falls back to your deployed backend URL `https://auditsystem-jwb6.onrender.com` if not set.
   - Local dev (optional): create `frontend/.env` with `VITE_API_URL=http://localhost:8080`.
   - Production hosting (Vercel/Netlify): set `VITE_API_URL` to your backend URL in project env settings.
5.  Visit the app at `http://localhost:3000`.

## Docker (build, run, and push)
- Build the image (from repo root):
  - PowerShell: `docker build -t shxde/voyagevista:latest .`
- Run the container locally (maps port 8080):
  - PowerShell: `docker run --rm -p 8080:8080 shxde/voyagevista:latest`
- Test endpoints:
  - `curl http://localhost:8080/api/voyages`
  - `curl http://localhost:8080/api/audit-logs`
- Push to Docker Hub (replace with your Docker ID if different):
  - `docker login`
  - `docker push shxde/voyagevista:latest`

## Build performance on Render/CI
- Gradle configuration cache is enabled via `gradle.properties` and also explicitly used in the Docker build stage (`--configuration-cache`).
- This speeds up subsequent builds on Render and other CI/CD systems. If you hit an incompatibility, you can temporarily disable it by removing the flag or setting `org.gradle.configuration-cache=false` in `gradle.properties`.

Instruction: Get this code into a GitHub repository named VoyageVista. This covers the "Java," "TypeScript," "Problem Solving," and specifically the "Audit Traceability" and "Voyage Itinerary" requirements of the job post.
