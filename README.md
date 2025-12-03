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
* **Type-Safe Frontend:** Built with TypeScript to ensure reliability and maintainability.

## How to Run
1.  Clone the repository.
2.  **Backend:** Navigate to `backend/` and run `./mvnw spring-boot:run`.
3.  **Frontend:** Navigate to `frontend/`, run `npm install` then `npm start`.
4.  Access the application at `http://localhost:3000`.

Instruction: Get this code into a GitHub repository named VoyageVista. This covers the "Java," "TypeScript," "Problem Solving," and specifically the "Audit Traceability" and "Voyage Itinerary" requirements of the job post.
