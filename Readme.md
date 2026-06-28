# File Management System

Đây là hệ thống quản lý tài liệu (File Management System) được xây dựng bằng Spring Boot, sử dụng bảo mật JWT và Swagger cho tài liệu hóa API.

## 🚀 Công nghệ sử dụng
- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security (OAuth2/JWT)**
- **Spring Data JPA**

## 📋 Yêu cầu hệ thống
- JDK 17 trở lên.
- Maven 3.x.
- Postgre Sql (hoặc cơ sở dữ liệu bạn đang dùng).

## Tạo cơ sở dữ liệu
1. Tạo cơ sở dữ liệu PostgreSQL:
   ```sql
   host: localhost
   port: 5432
   database: file_management
   username: postgres
   password: 123456
   ```

## ⚙️ Cấu hình dự án
1. **Clone dự án:**
   ```bash
   git clone https://github.com/lehuunghia222000/file-management.git
   cd file-management
   
   2. **Cấu hình cơ sở dữ liệu:**
      Mở file `application.properties` hoặc `application.yml` và cấu hình thông tin cơ sở dữ liệu của bạn:
      ```properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/file_management
      spring.datasource.username=postgres
      spring.datasource.password=123456
      spring.jpa.hibernate.ddl-auto=update
      spring.jpa.show-sql=true
      spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
        ```
   3. **Chạy ứng dụng:**
      Sử dụng Maven để chạy ứng dụng:
      ```bash
      mvn spring-boot:run
      ```
   4. **Truy cập Postman:**
      Sau khi ứng dụng chạy, bạn có thể truy cập Postman để kiểm tra các API.
   Những Curl mẫu để test API:
   - **Đăng ký người dùng:**
     ```bash
     curl -X POST "http://localhost:8080/file-management/api/auth/register" -H "Content-Type: application/json" -d '{"username":"testuser","password":"testpass"}'
     ```
     - **Đăng nhập người dùng:**
       ```bash
       curl -X POST "http://localhost:8080/file-management/api/auth/login" -H "Content-Type: application/json" -d '{"username":"testuser","password":"testpass"}'
       ```
   - **Tạo Document:**
     ```bash
     curl --location 'http://localhost:8080/file-management/api/file/create' \
      --header 'Content-Type: application/json' \
      --header 'Authorization: ••••••' \
      --data '{
      "code": "10099",
      "title": "Báo cáo công việc tháng 6",
      "description": "Báo cáo quá trình làm việc tháng 6",
      "category": "Báo cáo",
      "fileName": "bao cao của Nghĩa tháng 6"

     ```
     5. **Run Unit test:**
          ```bash
          mvn test
          ```
      