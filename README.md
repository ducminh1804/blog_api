📌 Blog API
🔥 Description: This is a RESTful Blog API with features like CRUD for posts and comments, full-text search (Elasticsearch), authentication with JWT (Spring Security), real-time comments (WebSocket STOMP), and caching (Redis).

🚀 Technologies Used
Spring Boot - The core framework.
Spring Security + JWT - Authentication & authorization.
Redis:
Cache - Store blog posts to reduce database load.
Pub/Sub - Synchronize data updates between services.
Elasticsearch - Supports full-text search for blog posts.
WebSocket (STOMP) - Enables real-time commenting on posts.
Spring Data JPA (MySQL) - ORM for efficient database management.
📌 Key Features
✔ User authentication & role-based authorization (JWT, Spring Security)
✔ User registration & login
✔ CRUD operations for blog posts
✔ Real-time commenting (WebSocket STOMP)
✔ Full-text search for posts (Elasticsearch)
✔ Caching blog posts (Redis) for performance optimization
✔ Pub/Sub mechanism for post synchronization (Redis)

📌 Installation & Setup
1️⃣ Clone the repository
bash
Copy
Edit
git clone https://github.com/ducmingg/blog_api.git
cd blog_api
2️⃣ Configure environment variables
Create an application.yml file in src/main/resources and provide database, Redis, and Elasticsearch configurations.

3️⃣ Run the project
bash
Copy
Edit
mvn spring-boot:run
📌 API Documentation (Swagger UI)
Access API docs at:
🔗 http://localhost:8080/swagger-ui.html

