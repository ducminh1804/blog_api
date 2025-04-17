# Link Frontend: https://github.com/ducminh1804/blog_frontend
# 📝 Blog API

# 🎥 Overview

The RESTful Blog API is a high-performance, scalable backend solution designed to handle dynamic blog functionalities. It supports robust CRUD operations for posts, comments, and user profiles, while leveraging advanced performance-enhancing features such as Redis caching for fast data retrieval, Redis Pub/Sub for real-time updates, and Elasticsearch for efficient full-text search. The API uses the Closure Table pattern for managing hierarchical comment structures, while batch processing optimizes data operations. Security is reinforced through JWT authentication and Spring Security for secure access control. Built with scalability in mind, the API ensures high availability and can efficiently manage heavy traffic and large datasets.

# ✨ Features

*   ✅ Caching with Redis: Reduces database load when accessing posts, user information, etc.
*   ✅ Redis Pub/Sub: Asynchronous message queue to trigger Elasticsearch indexing upon post creation (non-blocking).
*   ✅ Full-Text Search: Integrated Elasticsearch for fuzzy searching of articles by title.
*   ✅ Real-time Messaging (WebSocket + Redis): Enables real-time chat functionality via WebSocket, temporarily storing messages in Redis before batch writing to the database.
*   ✅ Pagination: Implements pagination for posts and comments using Spring's `Pageable`.
*   ✅ Nested Comments (Closure Table): Organizes nested comments using the **Closure Table** model. This stores parent-child relationships between comments in a separate table, enabling **fast, non-recursive comment tree queries**, optimizing the display of hierarchical comments.
*   ✅ Media Storage with Cloudinary: Uploads and processes images/videos, returning direct URLs.
*   ✅ Authentication & Authorization: Leverages JWT + Spring Security with Role & Permission-based authorization.
*   ✅ Registration & Login: Provides complete basic authentication functionality for users.
*   ✅ CRUD Operations for Posts: Enables creation, reading, updating, and deletion of posts.

# 📂 Folder Structure
```plaintext
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── blog/
│   │               ├── aspect/          # Aspect-Oriented Programming (AOP)
│   │               ├── auth/            # Authentication & Authorization logic
│   │               ├── config/          # Configuration classes (Security, Redis, Elasticsearch, etc.)
│   │               ├── controller/      # REST API controllers
│   │               ├── dto/             # Data Transfer Objects
│   │               ├── entity/          # JPA Entities (database models)
│   │               ├── enums/           # Enums
│   │               ├── exception/       # Custom exceptions and handlers
│   │               ├── repository/      # Spring Data JPA repositories
│   │               ├── search/          # Elasticsearch integration logic
│   │               ├── service/         # Business logic services
│   │               ├── websocket/       # WebSocket config & message handlers
│   │               └── BlogApplication.java  # Main Spring Boot application
│   └── resources/
│       ├── application.yml             # Configuration properties
│       ├── static/                     # Static assets (if any)
│       └── templates/                  # Thymeleaf templates (if used)
├── test/
│   └── java/                           # Unit and integration tests
├── .env                                # Environment variables (dotenv)
├── pom.xml                             # Maven configuration
└── README.md                           # Project README
```

# 🏛️ Base Dependencies

| Technology                 | Purpose                                          |
| -------------------------- | ------------------------------------------------- |
| **Spring Boot**          | Core framework                                   |
| **Spring Security + JWT**  | User authentication & authorization              |
| **Redis**                | Caching (database offloading) + Pub/Sub for event updates |
| **Elasticsearch**        | Full-text search for articles                     |
| **WebSocket (STOMP)**    | Real-time chat system                            |
| **Spring Data JPA (MySQL)** | ORM for database management                      |
| **Swagger (Springdoc OpenAPI)** | Automatic API documentation generation for frontend understanding & testing via Swagger UI |
| **AspectJ + Spring Aspects** | AOP for cross-cutting concerns like logging and auditing |
| **Spring Validation**     | Input data validation using annotations (@NotBlank, etc.) |
| **MapStruct**             | Automatic mapping between Entities ↔ DTOs        |
| **dotenv-java**             | Load environment variables from `.env` files      |

# 🛠️ Prerequisites

Before running this API, ensure you have the following installed:

*   Java Development Kit (JDK): Version 17 or higher is recommended.
*   Maven: A build automation tool used for managing project dependencies.
*   MySQL: A relational database used for storing persistent data.
*   Redis: An in-memory data structure store used for caching and Pub/Sub.
*   Elasticsearch: A search engine used for full-text search.
*   Cloudinary Account: (If using media storage features) You'll need an account to upload and manage media files.

# 🚀 Getting Started

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/ducminh1804/blog_frontend
    cd blog_frontend # change directory to the backend folder if frontend and backend are in the same repo
    ```

2.  **Configure Environment Variables:**

    *   Create a `.env` file in the root directory of the project.
    *   Define the necessary environment variables within the `.env` file. Examples:

        ```
        SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/your_database_name?createDatabaseIfNotExist=true&useSSL=false
        SPRING_DATASOURCE_USERNAME=your_mysql_username
        SPRING_DATASOURCE_PASSWORD=your_mysql_password
        REDIS_HOST=localhost
        REDIS_PORT=6379
        ELASTICSEARCH_HOST=localhost
        ELASTICSEARCH_PORT=9200
        CLOUDINARY_CLOUD_NAME=your_cloudinary_cloud_name
        CLOUDINARY_API_KEY=your_cloudinary_api_key
        CLOUDINARY_API_SECRET=your_cloudinary_api_secret
        JWT_SECRET=your_jwt_secret_key  # A strong, randomly generated secret
        ```

3.  **Build and Run the Application:**

    ```bash
    ./mvnw spring-boot:run
    ```

    (or simply `mvn spring-boot:run` if you have Maven installed globally)

4.  **Access Swagger UI:**

    Once the application is running, you can access the API documentation through Swagger UI at:

    🔗 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

# 🔗 References

*   Spring Boot: [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
*   Spring Security: [https://spring.io/projects/spring-security](https://spring.io/projects/spring-security)
*   Redis: [https://redis.io/](https://redis.io/)
*   Elasticsearch: [https://www.elastic.co/](https://www.elastic.co/)
*   Spring Data JPA: [https://spring.io/projects/spring-data-jpa](https://spring.io/projects/spring-data-jpa)
*   Swagger/Springdoc OpenAPI: [https://springdoc.org/](https://springdoc.org/)
*   Cloudinary: [https://cloudinary.com/](https://cloudinary.com/)
*   MapStruct: [https://mapstruct.org/](https://mapstruct.org/)

# 📧 Contact

For any questions or issues, please contact:

[voducminh39@gmail.com]

<p align="center">
    <img src="./src/assets/image11.png" alt="Trang chủ" width="800"/>
    <img src="./src/assets/image10.png" alt="Trang chủ" width="800"/>
    <br>
    <em>Trang đăng kí, đăng nhập</em>
    <br></br>
</p>

<p align="center">
    <img src="./src/assets/image1.png" alt="Trang chủ" width="800"/>
    <br>
    <em>Giao diện chính</em>
    <br></br>
</p>

<p align="center">
    <img src="./src/assets/image3.png" alt="Trang chủ" width="800"/>
    <br>
    <em>Giao diện nhắn tin</em>
    <br></br>
</p>

<p align="center">
    <img src="./src/assets/image4.png" alt="Trang chủ" width="800"/>
    <img src="./src/assets/image6.png" alt="Trang chủ" width="800"/>
    <br>
    <em>Đăng tải bài viết, hình ảnh hoặc video</em>
    <br></br>
</p>

<p align="center">
    <img src="./src/assets/image8.png" alt="Trang chủ" width="800"/>
    <br>
    <em>Chi tiết bài viết</em>
    <br></br>
</p>

<p align="center">
    <img src="./src/assets/image9.png" alt="Trang chủ" width="800"/>
    <br>
    <em>Bình luận lồng nhau</em>
    <br></br>
</p>

<p align="center">
    <img src="./src/assets/image12.png" alt="Trang chủ" width="800"/>
    <img src="./src/assets/image13.png" alt="Trang chủ" width="800"/>
    <br>
    <em>Chuyển đổi ngôn ngữ</em>
    <br></br>
</p>

<p align="center">
    <img src="./src/assets/i19.png" alt="Trang chủ" width="800"/>
    <br>
    <em>Tìm kiếm bài viết với tiêu đề gần đúng</em>
    <br></br>
</p>
