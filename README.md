# Link Frontend: https://github.com/ducminh1804/blog_frontend
# 📝 Blog API

A **RESTful Blog API** with features like **CRUD for posts and comments, full-text search (Elasticsearch), authentication (Spring Security + JWT), real-time comments (WebSocket STOMP), and caching (Redis).**

---

## 🚀 Technologies Used  

| Technology               | Purpose                                         |
|--------------------------|------------------------------------------------|
| **Spring Boot**         | Core framework                                  |
| **Spring Security + JWT** | Authentication & authorization                 |
| **Redis**               | Caching (to reduce DB load) + Pub/Sub for event-driven updates |
| **Elasticsearch**       | Full-text search for blog posts                 |
| **WebSocket (STOMP)**   | Real-time chat system                     |
| **Spring Data JPA (MySQL)** | ORM for database management               |

---

## 📌 Features  

✅ **User authentication & role-based authorization (JWT, Spring Security)**  
✅ **User registration & login**  
✅ **CRUD operations for blog posts**  
✅ **Real-time comments using WebSocket (STOMP)**  
✅ **Full-text search for blog posts using Elasticsearch**  
✅ **Redis caching to optimize performance**  
✅ **Redis Pub/Sub mechanism for data synchronization**  

---

## ⚡ Installation & Setup  

### 1️⃣ Clone the Repository  
```bash
git clone https://github.com/ducmingg/blog_api.git
cd blog_api
```

### 2️⃣ Configure Environment Variables  
Create an `application.yml` file in `src/main/resources` and provide:  
- **Database configuration (MySQL)**  
- **Redis configuration**  
- **Elasticsearch configuration**  

### 3️⃣ Run the Project  
```bash
mvn spring-boot:run
```

---

## 🔥 API Documentation (Swagger UI)  
Access API docs at:  
🔗 `http://localhost:8080/swagger-ui.html`  
