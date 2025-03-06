# Learning Management System (LMS)
## Overview

The LMS project is a **microservices-based application** designed to streamline learning and course management. It integrates various tools and technologies to ensure scalability, security, and efficiency.


<img src="https://github.com/user-attachments/assets/976dda89-ba72-4595-86b5-29692c5207af" width="650" height="500" />



## Features

- **Payment Integration:** Supports payments through [PayHere](https://www.payhere.lk/).
- **Microservices Architecture:** Built using Spring Cloud with centralized service discovery via **Eureka**.
- **API Gateway:** Utilizes Spring Gateway for request routing and load balancing.
- **Authentication & Authorization:** Secured with JWT token-based authentication and role-based access control.
- **Content Delivery Network (CDN):** Uses AWS CloudFront with **Signed URLs** to restrict access by IP and provide time-limited links.
- **Storage Solutions:**  
  - **AWS S3:** Securely stores videos.  
  - **Nextcloud:** Manages and stores other documents.
- **Containerization:**  
  - Docker images are created using the **Maven Jib plugin**.  
  - **GitHub Actions** automate Docker image creation and push to DockerHub.
- **Orchestration:** Configured with a Docker Compose file to simplify project setup and deployment.

 
## Architecture

This project employs a microservices architecture with the following components:  
- **Eureka Server:** Centralized service registry for microservices.  
- **Spring Gateway:** Handles routing and authentication.  
- **Multiple Microservices:** Each handles specific functionalities, ensuring modularity and scalability.


## Technologies Used

- Backend: Spring Boot, Spring Cloud, Eureka, Spring Gateway
- Authentication: JWT Tokens
- Storage: AWS S3, Nextcloud
- Payment Gateway: PayHere
- Containerization: Docker, Maven Jib Plugin
- CI/CD: GitHub Actions
