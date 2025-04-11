# Link Frontend: https://github.com/ducminh1804/blog_frontend
# 📝 Blog API

**RESTful Blog API được xây dựng với định hướng tối ưu hiệu năng và khả năng mở rộng. Ngoài hỗ trợ các chức năng CRUD cơ bản, còn áp dụng nhiều giải pháp để cải thiện hiệu suất như caching (Redis cache), xử lý bất đồng bộ (Redis PubSub), tìm kiếm toàn văn bản (Elastic Search) tổ chức dữ liệu hiệu quả (Closure Table) và giảm tải cho cơ sở dữ liệu (Batch Processing), đồng thời áp dụng cơ chế xác thực và phân quyền để đảm bảo an toàn truy cập và bảo vệ tài nguyên (Jwt & Spring Security).**

---

## 🚀 Công Nghệ Sử Dụng  

| Công nghệ                | Mục đích                                         |
|--------------------------|--------------------------------------------------|
| **Spring Boot**         | Framework cốt lõi                                |
| **Spring Security + JWT** | Xác thực & phân quyền người dùng                 |
| **Redis**               | Cache (giảm tải DB) + Pub/Sub cho cập nhật sự kiện |
| **Elasticsearch**       | Tìm kiếm toàn văn bài viết                        |
| **WebSocket (STOMP)**   | Hệ thống chat thời gian thực                     |
| **Spring Data JPA (MySQL)** | ORM để quản lý cơ sở dữ liệu                  |
| **Swagger (Springdoc OpenAPI)** | Tự động tạo tài liệu API, giúp frontend dễ hiểu & test API qua Swagger UI       |
| **AspectJ + Spring Aspects**     | Áp dụng AOP để xử lý các tác vụ như logging, audit mà không làm rối business logic |
| **Spring Validation**            | Xác thực dữ liệu đầu vào của người dùng bằng annotation (VD: `@NotBlank`,...)   |
| **MapStruct**                    | Tự động ánh xạ giữa Entity ↔ DTO, giúp code gọn gàng và rõ ràng hơn              |
| **dotenv-java**                  | Load các biến môi trường từ file `.env`, giúp cấu hình dễ dàng và bảo mật hơn    |

---

| Tính năng                                      | Mục đích sử dụng trong dự án                                                                 |
|------------------------------------------------|------------------------------------------------------------------------|
| ✅ Caching bằng Redis                          | Giảm tải DB khi truy xuất bài viết, thông tin người dùng,...         |
| ✅ Redis Pub/Sub                               | Khi đăng bài viết, gửi message để Elasticsearch index bài viết (non-blocking) |
| ✅ Tìm kiếm toàn văn                           | Tích hợp Elasticsearch để tìm kiếm bài viết theo tiêu đề gần đúng     |
| ✅ Tin nhắn thời gian thực (WebSocket + Redis) | Gửi tin nhắn qua WebSocket, lưu tạm Redis → ghi batch vào DB          |
| ✅ Pagination                                   | Phân trang bài viết & bình luận với `Pageable`                        |
| ✅ Nested Comment (Closure Table)              | Mô hình tổ chức bình luận lồng nhau bằng **Closure Table** – lưu quan hệ cha-con giữa các bình luận trong bảng riêng, giúp **truy vấn cây bình luận nhanh, không đệ quy**, tối ưu cho hiển thị dạng cây |
| ✅ Lưu trữ media với Cloudinary                | Upload & xử lý ảnh/video, trả về link trực tiếp                       |
| ✅ Xác thực & phân quyền                       | Sử dụng JWT + Spring Security với phân quyền theo Role & Permission   |
| ✅ Đăng ký & đăng nhập                         | Hỗ trợ đầy đủ chức năng auth cơ bản cho người dùng                    |
| ✅ CRUD bài viết                                | Tạo, xem, sửa, xoá bài viết                                           |



---


## 🔥 API Documentation (Swagger UI)  
Access API docs at:  
🔗 `http://localhost:8080/swagger-ui.html`  
