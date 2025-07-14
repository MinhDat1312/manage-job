# Hệ thống Quản lý Dịch vụ Tìm kiếm Việc làm

## 📋 Giới thiệu

Hệ thống Quản lý Dịch vụ Tìm kiếm Việc làm là một ứng dụng phân tán được phát triển bằng Java, sử dụng kiến trúc Client-Server với RMI (Remote Method Invocation). Ứng dụng hỗ trợ quản lý toàn bộ quy trình tìm kiếm việc làm, từ quản lý nhân viên, ứng viên, nhà tuyển dụng đến quản lý tin tuyển dụng và hợp đồng.

## 🖥️ Thành viên nhóm phát triển

### Nguyễn Thắng Minh Đạt

- **Vai trò**: Nhóm trưởng
- **MSSV**: [22697101]
- **Đóng góp**:
  - Thiết kế kiến trúc hệ thống Client-Server
  - Phát triển RMI services và database integration
  - Quản lý project và code review

### Trương Công Hải

- **Vai trò**: Thành viên
- **MSSV**: 22692311
- **Đóng góp**:
  - Thiết kế và phát triển giao diện người dùng
  - Tích hợp FlatLaf Look and Feel
  - Phát triển Data Access Objects (DAO)

### Lê Minh Tuấn

- **Vai trò**: Thành viên
- **MSSV**: 22697621
- **Đóng góp**:
  - Thiết kế database schema
  - Tối ưu hóa queries và performance
  - Testing và debugging UI components

### Lê Huỳnh Công Tiếp

- **Vai trò**: Thành viên
- **MSSV**: 22692271
- **Đóng góp**:
  - Thiết kế database schema
  - Phát triển business logic services
  - Implement controllers và exception handling

## 🏗️ Kiến trúc hệ thống

### Client-Server Architecture với RMI

- **Server**: Xử lý logic nghiệp vụ, quản lý cơ sở dữ liệu
- **Client**: Giao diện người dùng, kết nối đến server thông qua RMI
- **RMI Port**: 7101
- **RMI Host**: LAPTOP-7ERSHT8P (có thể thay đổi theo môi trường)

## 📁 Cấu trúc dự án

```
ManageJob/
├── distributedProgrammingProject_Server/    # Server-side application
│   ├── src/main/java/                      # Source code
│   │   ├── controller/                     # Controllers
│   │   ├── dao/                           # Data Access Objects
│   │   ├── entity/                        # Entity classes
│   │   ├── service/                       # Business logic services
│   │   ├── server/                        # RMI server setup
│   │   ├── util/                          # Utilities
│   │   └── view/                          # Server UI views
│   ├── script/jobsearch.sql               # Database script
│   ├── resumes/                           # Uploaded resumes
│   └── build.gradle                       # Gradle build file
│
├── distributedProgrammingProject_Client/    # Client-side application
│   ├── src/main/java/                      # Source code
│   ├── src/main/resources/image/           # UI resources
│   ├── form/                              # Document templates
│   ├── resumes/                           # Resume storage
│   └── build.gradle                       # Gradle build file
│
└── distributedProgrammingProject.docx      # Project documentation
```

## 🚀 Công nghệ sử dụng

- **Ngôn ngữ**: Java 21
- **Framework UI**: Swing với FlatLaf Look and Feel
- **ORM**: Hibernate 7.0.0.Beta1
- **Database**: MySQL/SQL Server
- **Communication**: Java RMI
- **Build Tool**: Gradle
- **IDE**: IntelliJ IDEA / Eclipse

## 🔧 Dependencies chính

### Server & Client

- Hibernate Core 7.0.0.Beta1
- JAXB Runtime 4.0.5
- FlatLaf (Modern Look and Feel)
- JUnit 5.11.0 (Testing)
- Spire.Doc.Free (Document processing)

## ⚡ Tính năng chính

### 1. 👥 Quản lý Nhân viên

- Quản lý thông tin cá nhân: họ tên, ngày sinh, ngày vào làm, giới tính, liên lạc
- Hệ thống tài khoản với phân quyền
- Thống kê hiệu suất làm việc theo tháng
- Quản trị viên tạo và cấp quyền cho nhân viên

### 2. 🎯 Quản lý Ứng viên

- Thông tin cá nhân: họ tên, ngày sinh, địa chỉ, liên lạc
- Quản lý hồ sơ ứng tuyển với yêu cầu cụ thể
- Hỗ trợ nhiều hồ sơ cho một ứng viên
- Cập nhật trạng thái hồ sơ tự động

### 3. 🏢 Quản lý Nhà tuyển dụng

- Thông tin công ty: tên, logo, địa chỉ, mô tả
- Quản lý tin tuyển dụng với đầy đủ thông tin
- Hệ thống tính phí linh hoạt theo số lượng tuyển dụng
- Quản lý thanh toán và phí xét duyệt (150.000 VNĐ/hồ sơ)

### 4. 📄 Quản lý Hợp đồng

- **Hợp đồng ứng tuyển**: Giữa ứng viên và công ty
- **Hợp đồng đăng tin**: Giữa nhà tuyển dụng và hệ thống
- In hợp đồng và thu phí dịch vụ

## 🛠️ Cài đặt và Chạy ứng dụng

### Yêu cầu hệ thống

- Java JDK 21+
- MySQL Server
- Gradle 7+

### 1. Chuẩn bị Database

```sql
-- Chạy script tạo database
mysql -u root -p < distributedProgrammingProject_Server/script/jobsearch.sql
```

### 2. Chạy Server

```bash
cd distributedProgrammingProject_Server
./gradlew run
```

### 3. Chạy Client

```bash
cd distributedProgrammingProject_Client
./gradlew run
```

## 🔧 Cấu hình

### Kết nối RMI

Trong file `Main.java` của Client, cập nhật địa chỉ server:

```java
allService = (AllService) Naming.lookup("rmi://YOUR_SERVER_IP:7101/allService");
```

### Database Configuration

Cấu hình kết nối database trong file `persistence.xml`:

```xml
<persistence-unit name="jobsearch">
    <!-- Database configuration -->
</persistence-unit>
```

## 📊 Database Schema

Hệ thống sử dụng các bảng chính:

- `employees` - Thông tin nhân viên
- `applicants` - Thông tin ứng viên
- `recruiters` - Thông tin nhà tuyển dụng
- `job_postings` - Tin tuyển dụng
- `applications` - Hồ sơ ứng tuyển
- `contracts` - Hợp đồng
- `accounts` - Tài khoản hệ thống

## 🎯 Quy trình sử dụng

### Cho Nhà tuyển dụng:

1. Đăng ký tài khoản
2. Đăng tin tuyển dụng
3. Xem và duyệt hồ sơ ứng tuyển
4. Thanh toán phí dịch vụ

### Cho Ứng viên:

1. Tạo hồ sơ cá nhân
2. Tìm kiếm và ứng tuyển công việc
3. Theo dõi trạng thái hồ sơ
4. Nhận thông báo qua email

### Cho Nhân viên:

1. Đăng nhập hệ thống
2. Quản lý dữ liệu theo chức năng được phân quyền
3. Xử lý hợp đồng và thanh toán
4. Tạo báo cáo thống kê

## 📞 Liên hệ

- 📧 Email: [nguyenthangdat84@gmail.com](mailto:nguyenthangdat84@gmail.com)
- 🐙 GitHub: [github.com/MinhDat1312](https://github.com/MinhDat1312)
- 🌐 Facebook: [fb.com/MinhDat](https://www.facebook.com/minh.at.784106)
- 📸 Instagram: [instagram.com/MinhDat](https://www.instagram.com/ntmdat1312)
- 💼 LinkedIn: [linkedin.com/in/MinhDat](https://www.linkedin.com/in/minh-%C4%91%E1%BA%A1t-14b018263)

---

_Dự án được phát triển như một phần của môn học Lập trình Phân tán - Distributed Programming_
