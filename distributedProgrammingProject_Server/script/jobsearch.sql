USE jobsearch;

-- Employees
INSERT INTO employees (name, dateOfBirth, city, gender, phone, email, startDate)
VALUES
    ('Nguyễn Thắng Minh Đạt', STR_TO_DATE('13/01/1990', '%d/%m/%Y'), 'NHA TRANG', 'MALE', '0123456789', 'MinhDat@gmail.com', STR_TO_DATE('01/01/2020', '%d/%m/%Y')),
    ('Trương Công Hải', STR_TO_DATE('15/12/1992', '%d/%m/%Y'), 'TP HỒ CHÍ MINH', 'MALE', '0123456789', 'CongHai@gmail.com', STR_TO_DATE('01/01/2019', '%d/%m/%Y'));

-- Accounts
INSERT INTO accounts (email, password, role, employee_id)
VALUES
    ('MinhDat@gmail.com', '12345678', 'ADMIN', 1),
    ('CongHai@gmail.com', '12345678', 'EMPLOYEE', 2);

-- Applicants
INSERT INTO applicants (name, email, dateOfBirth, gender, phone, number, street, ward, district, city, country)
VALUES
    ('Lê Huỳnh Công Tiếp', 'le.c@gmail.com', STR_TO_DATE('01/01/2000', '%d/%m/%Y'), 'MALE', '0912345678', '123', 'Nguyễn Huệ', 'Phường Trần Phú', 'Thành phố Quy Nhơn', 'Bình Định', 'Vietnam'),
    ('Lê Minh Tuấn', 'minhTuan@gmail.com', STR_TO_DATE('18/01/2001', '%d/%m/%Y'), 'MALE', '0934567890', '456', 'Lê Lợi', 'Phường Hội Thương', 'Thành phố Pleiku', 'Gia Lai', 'Vietnam'),
    ('Nguyễn Thị Mai', 'mai.nguyen@gmail.com', STR_TO_DATE('12/02/2000', '%d/%m/%Y'), 'FEMALE', '0901122334', '789', 'Trần Phú', 'Phường Lê Hồng Phong', 'Thành phố Nha Trang', 'Khánh Hòa', 'Vietnam'),
    ('Trần Văn Huy', 'huy.tran@gmail.com', STR_TO_DATE('25/05/1999', '%d/%m/%Y'), 'MALE', '0976543210', '321', 'Hùng Vương', 'Phường An Cựu', 'Thành phố Huế', 'Thừa Thiên Huế', 'Vietnam'),
    ('Phạm Ngọc Ánh', 'anh.pham@gmail.com', STR_TO_DATE('03/08/2001', '%d/%m/%Y'), 'FEMALE', '0911987654', '654', 'Phan Đình Phùng', 'Phường Bến Nghé', 'Quận 1', 'Thành phố Hồ Chí Minh', 'Vietnam'),
    ('Đỗ Đức Minh', 'minh.doduc@gmail.com', STR_TO_DATE('17/11/2000', '%d/%m/%Y'), 'MALE', '0987654321', '111', 'Lý Thường Kiệt', 'Phường Hàng Bài', 'Quận Hoàn Kiếm', 'Hà Nội', 'Vietnam'),
    ('Võ Thị Hồng Nhung', 'nhung.vo@gmail.com', STR_TO_DATE('09/09/1999', '%d/%m/%Y'), 'FEMALE', '0933221100', '222', 'Cách Mạng Tháng 8', 'Phường Bùi Thị Xuân', 'Quận 1', 'Thành phố Hồ Chí Minh', 'Vietnam'),
    ('Lý Gia Hưng', 'hung.ly@gmail.com', STR_TO_DATE('30/06/2000', '%d/%m/%Y'), 'MALE', '0922334455', '333', 'Điện Biên Phủ', 'Phường Chính Gián', 'Quận Thanh Khê', 'Thành phố Đà Nẵng', 'Vietnam'),
    ('Ngô Thanh Trúc', 'truc.ngo@gmail.com', STR_TO_DATE('21/03/2001', '%d/%m/%Y'), 'FEMALE', '0944556677', '444', 'Pasteur', 'Phường 6', 'Quận 3', 'Thành phố Hồ Chí Minh', 'Vietnam'),
    ('Hoàng Văn Nam', 'nam.hoang@gmail.com', STR_TO_DATE('14/07/1998', '%d/%m/%Y'), 'MALE', '0912345679', '555', 'Nguyễn Trãi', 'Phường 7', 'Quận 5', 'Thành phố Hồ Chí Minh', 'Vietnam'),
    ('Trần Thị Lan Anh', 'lananh.tran@gmail.com', STR_TO_DATE('22/04/2000', '%d/%m/%Y'), 'FEMALE', '0935678901', '666', 'Lê Đại Hành', 'Phường 11', 'Quận 11', 'Thành phố Hồ Chí Minh', 'Vietnam'),
    ('Nguyễn Văn Hùng', 'hung.nguyen@gmail.com', STR_TO_DATE('05/10/1999', '%d/%m/%Y'), 'MALE', '0908765432', '777', 'Trần Hưng Đạo', 'Phường Cầu Kho', 'Quận 1', 'Thành phố Hồ Chí Minh', 'Vietnam'),
    ('Phạm Thị Thu Hà', 'ha.pham@gmail.com', STR_TO_DATE('19/12/2001', '%d/%m/%Y'), 'FEMALE', '0923456789', '888', 'Nguyễn Đình Chiểu', 'Phường 3', 'Quận 3', 'Thành phố Hồ Chí Minh', 'Vietnam'),
    ('Đặng Minh Tuấn', 'tuan.dang@gmail.com', STR_TO_DATE('27/02/2000', '%d/%m/%Y'), 'MALE', '0945678901', '999', 'Lý Tự Trọng', 'Phường Bến Thành', 'Quận 1', 'Thành phố Hồ Chí Minh', 'Vietnam');

-- Recruiters
INSERT INTO recruiters (name, email, logo, number, street, ward, district, city, country, phone)
VALUES
    ('Công ty dược phẩm Đại Hưng', 'daihung@gmail.com', 'daihung.png', '319-A12', 'Lý Thường Kiệt', 'Phường 15', 'Quận 11', 'Hồ Chí Minh', 'Vietnam', '0987654321'),
    ('Công ty TNHH Bosch Global Software Technologies', 'bosch@gmail.com', 'bosch.png', '4364', 'Cộng Hòa', 'Phường 12', 'Tân Bình', 'Hồ Chí Minh', 'Vietnam', '02866866244'),
    ('Công ty EM and AI', 'emai@gmail.com', 'em&ai.png', '123', 'ABC Street', NULL, 'Quận 1', 'Hồ Chí Minh', 'Vietnam', '09012345678'),
    ('Công ty Aletech Technology Solutions', 'aletech@gmail.com', 'cles.png', '789', 'Lê Văn Sỹ', 'Phường 13', 'Quận 3', 'Hồ Chí Minh', 'Vietnam', '0987654321'),
    ('Công Ty Tnhh Lg Electronics Việt Nam', 'lg@gmail.com', 'lg.png', '123', 'Điện Biên Phủ', 'Phường 17', 'Bình Thạnh', 'Hồ Chí Minh', 'Vietnam', '02877778888'),
    ('Công ty Viễn thông Viettel', 'viettel@gmail.com', 'vittel.png', '25', 'Đinh Tiên Hoàng', 'Phường Đa Kao', 'Quận 1', 'Hồ Chí Minh', 'Vietnam', '0906789012'),
    ('Công Ty TNHH Glodival', 'glodival@gmail.com', 'g.png', '12', 'Hoàng Hoa Thám', 'Phường 13', 'Bình Thạnh', 'Hồ Chí Minh', 'Vietnam', '0912345678'),
    ('Ngân hàng Thương mại cổ phần Việt Nam Thịnh Vượng (VPBank)', 'vpbank@gmail.com', 'vpbank.png', '202', 'Lê Lợi', NULL, 'Quận 1', 'Hồ Chí Minh', 'Vietnam', '02899998888'),
    ('Công Ty cổ phần cơ điện Tomeco', 'tomeco@gmail.com', 'tomeco.png', '58', 'Nam Kỳ Khởi Nghĩa', 'Phường 8', 'Quận 3', 'Hồ Chí Minh', 'Vietnam', '02833334444'),
    ('Công ty TNHH Phần mềm FPT', 'fpt@gmail.com', 'fpt.png', '21', 'Trường Sơn', NULL, 'Tân Bình', 'Hồ Chí Minh', 'Vietnam', '02866667777'),
    ('Công ty tài chính đến từ Hàn Quốc Shinhan Finance', 'shinhan@gmail.com', 'shinhans.png', '123', 'Nguyễn Văn Trỗi', 'Phường 10', 'Phú Nhuận', 'Hồ Chí Minh', 'Vietnam', '02822221111'),
    ('Công ty TNHH PASONA Tech Việt Nam', 'pasona@gmail.com', 'pasona.png', '10', 'Phan Đăng Lưu', 'Phường 6', 'Bình Thạnh', 'Hồ Chí Minh', 'Vietnam', '02855556666'),
    ('Công ty cổ phần kỹ nghệ BANICO', 'banico@gmail.com', 'banico.png', '8', 'Đinh Bộ Lĩnh', 'Phường 26', 'Bình Thạnh', 'Hồ Chí Minh', 'Vietnam', '02844445555'),
    ('Công ty cổ phần STRINGEE', 'stringee@gmail.com', 'stringee.png', '54', 'Võ Thị Sáu', 'Phường 8', 'Quận 3', 'Hồ Chí Minh', 'Vietnam', '02866664444'),
    ('Công ty TNHH NGen', 'ngen@gmail.com', 'ngen.png', '34', 'Nguyễn Thái Sơn', NULL, 'Gò Vấp', 'Hồ Chí Minh', 'Vietnam', '02833332222'),
    ('Công Ty TNHH Phát Triển Giải Pháp & CNTT Diệp Anh', 'diepanh@gmail.com', 'diepanh.png', '90', 'Trần Quốc Toản', NULL, 'Quận 3', 'Hồ Chí Minh', 'Vietnam', '09012345678'),
    ('Công ty Cổ phần Công nghệ Tin học và Dịch vụ Goline', 'goline@gmail.com', 'goline.png', '26', 'Bạch Đằng', 'Phường 15', 'Bình Thạnh', 'Hồ Chí Minh', 'Vietnam', '012388883'),
    ('Bee Logistics Corporation', 'bee@gmail.com', 'blc.png', '100', 'Trần Hưng Đạo', 'Phường Cầu Ông Lãnh', 'Quận 1', 'Hồ Chí Minh', 'Vietnam', '02812345678'),
    ('CÔNG TY TNHH KỸ THƯƠNG VIỆT TRUNG', 'viettrung@gmail.com', 'vt.png', '200', 'Nguyễn Thị Minh Khai', 'Phường 6', 'Quận 3', 'Hồ Chí Minh', 'Vietnam', '02887654321'),
    ('CÔNG TY CỔ PHẦN FABL VIỆT NAM', 'fabl@gmail.com', 'fabl.png', '300', 'Lê Duẩn', 'Phường Bến Nghé', 'Quận 1', 'Hồ Chí Minh', 'Vietnam', '02823456789'),
    ('Công ty Cổ phần VNT Invest Group', 'vnt@gmail.com', 'vnt.png', '400', 'Võ Văn Tần', 'Phường 5', 'Quận 3', 'Hồ Chí Minh', 'Vietnam', '02834567890'),
    ('CÔNG TY CỔ PHẦN WELLING', 'welling@gmail.com', 'well.png', '500', 'Nguyễn Đình Chiểu', 'Phường 2', 'Quận 3', 'Hồ Chí Minh', 'Vietnam', '02845678901'),
    ('Công ty cổ phần Tập đoàn Videc', 'videc@gmail.com', 'videc.png', '600', 'Pasteur', 'Phường Bến Nghé', 'Quận 1', 'Hồ Chí Minh', 'Vietnam', '02856789012'),
    ('CÔNG TY CỔ PHẦN KOVITECH', 'kovitech@gmail.com', 'kovi.png', '700', 'Nguyễn Trãi', 'Phường 11', 'Quận 5', 'Hồ Chí Minh', 'Vietnam', '02867890123'),
    ('Công ty Bike Life', 'bikelife@gmail.com', 'bike.png', '800', 'Lý Tự Trọng', 'Phường Bến Thành', 'Quận 1', 'Hồ Chí Minh', 'Vietnam', '02878901234');

-- Professions
INSERT INTO professions (name)
VALUES
    ('IT'),
    ('Dịch vụ'),
    ('Marketing'),
    ('Kế toán'),
    ('Thiết kế');

-- Resumes
ALTER TABLE resumes MODIFY COLUMN description VARCHAR(500);
INSERT INTO resumes (description, level, applicant_id, employee_id)
VALUES
    ('- Mục tiêu nghề nghiệp: Mong muốn trở thành chuyên gia trong lĩnh vực Công nghệ thông tin, đặc biệt trong mảng phát triển phần mềm.\n- Trình độ: Đại học\n- Chuyên ngành: Công nghệ thông tin\n- Kinh nghiệm làm việc: Công ty XYZ – Lập trình viên Java.\n- Kỹ năng: Java, Python, SQL, Git.\n- Ngoại ngữ: TOEIC 850.', 'UNIVERSITY', 1, 1),
    ('- Mong muốn trở thành chuyên gia trong lĩnh vực Marketing.\n- Trình độ: Đại học\n- Chuyên ngành: Marketing\n- Kinh nghiệm: Chạy chiến dịch Facebook Ads, Google Ads.\n- Kỹ năng: SEO, Google Analytics.\n- Ngoại ngữ: IELTS 7.0.', 'UNIVERSITY', 2, 2),
    ('- Định hướng làm việc trong ngành Thiết kế đồ họa.\n- Trình độ: Đại học\n- Chuyên ngành: Thiết kế Đồ họa\n- Kỹ năng: Adobe Photoshop, Illustrator.\n- Kinh nghiệm: 1 năm làm thiết kế banner cho công ty thời trang.', 'UNIVERSITY', 3, 1),
    ('- Mong muốn làm trong lĩnh vực Kế toán kiểm toán.\n- Trình độ: Đại học\n- Chuyên ngành: Kế toán\n- Kỹ năng: Excel, Kiểm toán nội bộ.\n- Kinh nghiệm: Kế toán viên tại công ty tài chính.', 'UNIVERSITY', 4, 2),
    ('- Mục tiêu trở thành chuyên viên IT, phát triển hệ thống.\n- Trình độ: Đại học\n- Kỹ năng: Java, Docker, SQL.\n- Kinh nghiệm: Dự án phần mềm quản lý nhân sự.', 'UNIVERSITY', 5, 1),
    ('- Mong muốn làm trong lĩnh vực phân tích dữ liệu Marketing.\n- Trình độ: Đại học\n- Kỹ năng: Google Analytics, Excel, SQL.\n- Kinh nghiệm: 6 tháng internship Digital Marketing.', 'UNIVERSITY', 6, 2),
    ('- Hướng đến vị trí Thiết kế UI/UX.\n- Trình độ: Cao đẳng\n- Kỹ năng: Figma, Adobe XD.\n- Kinh nghiệm: Freelancer thiết kế ứng dụng.', 'COLLEGE', 7, 1),
    ('- Định hướng trở thành chuyên viên Dịch vụ khách hàng.\n- Trình độ: Cao đẳng\n- Kỹ năng: Giao tiếp, xử lý tình huống.\n- Kinh nghiệm: Nhân viên CSKH 1 năm.', 'COLLEGE', 8, 2),
    ('- Mục tiêu làm việc trong lĩnh vực Công nghệ thông tin.\n- Trình độ: Đại học\n- Kỹ năng: Python, REST API, Git.\n- Kinh nghiệm: Ứng dụng web học tập bằng Flask.', 'UNIVERSITY', 9, 1),
    ('- Mục tiêu nghề nghiệp: Trở thành chuyên gia phát triển phần mềm Full Stack.\n- Trình độ: Đại học\n- Chuyên ngành: Công nghệ thông tin\n- Kinh nghiệm làm việc: 2 năm làm lập trình viên tại công ty ABC.\n- Kỹ năng: JavaScript, Node.js, React, MongoDB.\n- Ngoại ngữ: TOEIC 800.', 'UNIVERSITY', 10, 1),
    ('- Mục tiêu nghề nghiệp: Phát triển sự nghiệp trong lĩnh vực Marketing số.\n- Trình độ: Đại học\n- Chuyên ngành: Marketing\n- Kinh nghiệm làm việc: 1 năm chạy chiến dịch quảng cáo tại công ty XYZ.\n- Kỹ năng: Google Ads, SEO, Canva.\n- Ngoại ngữ: IELTS 6.5.', 'UNIVERSITY', 11, 2),
    ('- Mục tiêu nghề nghiệp: Trở thành chuyên viên dịch vụ khách hàng chuyên nghiệp.\n- Trình độ: Cao đẳng\n- Chuyên ngành: Quản trị kinh doanh\n- Kinh nghiệm làm việc: 1.5 năm làm nhân viên CSKH.\n- Kỹ năng: Giao tiếp, xử lý tình huống, CRM.\n- Ngoại ngữ: TOEIC 600.', 'COLLEGE', 12, 1),
    ('- Mục tiêu nghề nghiệp: Làm việc trong lĩnh vực Kế toán tài chính.\n- Trình độ: Đại học\n- Chuyên ngành: Kế toán\n- Kinh nghiệm làm việc: 1 năm làm kế toán viên tại công ty tài chính.\n- Kỹ năng: Excel, SAP, Kế toán thuế.\n- Ngoại ngữ: TOEIC 700.', 'UNIVERSITY', 13, 2),
    ('- Mục tiêu nghề nghiệp: Trở thành nhà thiết kế đồ họa sáng tạo.\n- Trình độ: Cao đẳng\n- Chuyên ngành: Thiết kế đồ họa\n- Kinh nghiệm làm việc: 1 năm làm freelancer thiết kế.\n- Kỹ năng: Adobe Photoshop, Illustrator, Figma.\n- Ngoại ngữ: TOEIC 650.', 'COLLEGE', 14, 1);

-- Resume Profession
INSERT INTO resume_profession (resume_id, profession_id)
VALUES
    (1, 1), -- IT
    (2, 3), -- Marketing
    (3, 5), -- Thiết kế
    (4, 4), -- Kế toán
    (5, 1), -- IT
    (6, 3), -- Marketing
    (7, 5), -- Thiết kế
    (8, 2), -- Dịch vụ
    (9, 1), -- IT
    (10, 1), -- IT
    (11, 3), -- Marketing
    (12, 2), -- Dịch vụ
    (13, 4), -- Kế toán
    (14, 5); -- Thiết kế

-- Jobs
INSERT INTO jobs (title, description, startDate, endDate, level, numberOfPositions, salary, workingType, visible, recruiter_id)
VALUES
    -- Jobs from 2023-2024 (IDs 1-31)
    ('Tuyển lập trình viên backend', 'Phát triển hệ thống backend cho ứng dụng.', STR_TO_DATE('01/03/2023', '%d/%m/%Y'), STR_TO_DATE('15/04/2023', '%d/%m/%Y'), 'UNIVERSITY', 5, 12000000.00, 'FULLTIME', TRUE, 1),
    ('Tuyển lập trình viên mobile', 'Phát triển ứng dụng di động cho iOS và Android.', STR_TO_DATE('10/04/2023', '%d/%m/%Y'), STR_TO_DATE('25/05/2023', '%d/%m/%Y'), 'UNIVERSITY', 4, 13000000.00, 'ONLINE', TRUE, 2),
    ('Tuyển kỹ sư DevOps', 'Quản lý hạ tầng và triển khai ứng dụng.', STR_TO_DATE('15/05/2023', '%d/%m/%Y'), STR_TO_DATE('30/06/2023', '%d/%m/%Y'), 'ENGINEER', 3, 15000000.00, 'FULLTIME', TRUE, 1),
    ('Tuyển chuyên viên an ninh mạng', 'Đảm bảo an toàn thông tin cho hệ thống.', STR_TO_DATE('01/06/2023', '%d/%m/%Y'), STR_TO_DATE('15/07/2023', '%d/%m/%Y'), 'UNIVERSITY', 2, 11000000.00, 'PARTTIME', TRUE, 2),
    ('Tuyển kỹ sư dữ liệu', 'Phân tích và quản lý dữ liệu lớn.', STR_TO_DATE('20/07/2023', '%d/%m/%Y'), STR_TO_DATE('31/08/2023', '%d/%m/%Y'), 'ENGINEER', 3, 14000000.00, 'ONLINE', TRUE, 3),
    ('Tuyển phát triển ứng dụng web', 'Xây dựng và phát triển ứng dụng web.', STR_TO_DATE('01/09/2023', '%d/%m/%Y'), STR_TO_DATE('15/10/2023', '%d/%m/%Y'), 'UNIVERSITY', 5, 12000000.00, 'FULLTIME', TRUE, 4),
    ('Tuyển kiểm thử phần mềm', 'Kiểm tra và đánh giá chất lượng phần mềm.', STR_TO_DATE('20/10/2023', '%d/%m/%Y'), STR_TO_DATE('30/11/2023', '%d/%m/%Y'), 'COLLEGE', 4, 9000000.00, 'PARTTIME', TRUE, 5),
    ('Tuyển kỹ sư phần mềm', 'Phát triển phần mềm theo yêu cầu.', STR_TO_DATE('01/12/2023', '%d/%m/%Y'), STR_TO_DATE('15/01/2024', '%d/%m/%Y'), 'ENGINEER', 2, 13000000.00, 'ONLINE', TRUE, 6),
    ('Tuyển nhân viên hỗ trợ kỹ thuật', 'Hỗ trợ khách hàng về kỹ thuật.', STR_TO_DATE('20/01/2024', '%d/%m/%Y'), STR_TO_DATE('28/02/2024', '%d/%m/%Y'), 'COLLEGE', 3, 8000000.00, 'FULLTIME', TRUE, 7),
    ('Tuyển nhân viên văn phòng', 'Thực hiện các công việc hành chính.', STR_TO_DATE('01/03/2024', '%d/%m/%Y'), STR_TO_DATE('15/04/2024', '%d/%m/%Y'), 'SCHOOL', 4, 4000000.00, 'FULLTIME', TRUE, 8),
    ('Tuyển nhân viên bán hàng', 'Bán hàng và tư vấn khách hàng.', STR_TO_DATE('20/04/2023', '%d/%m/%Y'), STR_TO_DATE('31/05/2023', '%d/%m/%Y'), 'SCHOOL', 3, 3500000.00, 'PARTTIME', TRUE, 9),
    ('Tuyển nhân viên phục vụ', 'Phục vụ khách hàng tại nhà hàng.', STR_TO_DATE('01/06/2023', '%d/%m/%Y'), STR_TO_DATE('15/07/2023', '%d/%m/%Y'), 'SCHOOL', 5, 3000000.00, 'OFFLINE', TRUE, 10),
    ('Tuyển nhân viên marketing', 'Xây dựng chiến lược marketing.', STR_TO_DATE('20/07/2023', '%d/%m/%Y'), STR_TO_DATE('31/08/2023', '%d/%m/%Y'), 'COLLEGE', 2, 7000000.00, 'FULLTIME', TRUE, 11),
    ('Tuyển nhân viên kế toán', 'Quản lý sổ sách và báo cáo tài chính.', STR_TO_DATE('01/09/2023', '%d/%m/%Y'), STR_TO_DATE('15/10/2023', '%d/%m/%Y'), 'UNIVERSITY', 1, 8000000.00, 'PARTTIME', TRUE, 12),
    ('Tuyển nhân viên thiết kế đồ họa', 'Thiết kế đồ họa cho các dự án.', STR_TO_DATE('20/10/2023', '%d/%m/%Y'), STR_TO_DATE('30/11/2023', '%d/%m/%Y'), 'COLLEGE', 2, 6000000.00, 'ONLINE', TRUE, 13),
    ('Tuyển nhân viên giao nhận', 'Thực hiện giao nhận hàng hóa và hỗ trợ khách hàng.', STR_TO_DATE('01/12/2023', '%d/%m/%Y'), STR_TO_DATE('15/01/2024', '%d/%m/%Y'), 'SCHOOL', 5, 3500000.00, 'OFFLINE', TRUE, 18),
    ('Tuyển nhân viên chăm sóc khách hàng', 'Hỗ trợ khách hàng qua điện thoại và email.', STR_TO_DATE('20/01/2024', '%d/%m/%Y'), STR_TO_DATE('28/02/2024', '%d/%m/%Y'), 'SCHOOL', 4, 3200000.00, 'FULLTIME', TRUE, 18),
    ('Tuyển nhân viên kho vận', 'Quản lý kho và vận chuyển hàng hóa.', STR_TO_DATE('01/03/2024', '%d/%m/%Y'), STR_TO_DATE('15/04/2024', '%d/%m/%Y'), 'SCHOOL', 3, 3800000.00, 'OFFLINE', TRUE, 19),
    ('Tuyển nhân viên dịch vụ hậu cần', 'Hỗ trợ logistics và quản lý đơn hàng.', STR_TO_DATE('01/03/2023', '%d/%m/%Y'), STR_TO_DATE('15/04/2023', '%d/%m/%Y'), 'SCHOOL', 4, 3600000.00, 'FULLTIME', TRUE, 19),
    ('Tuyển chuyên viên digital marketing', 'Quản lý chiến dịch quảng cáo trực tuyến.', STR_TO_DATE('10/04/2023', '%d/%m/%Y'), STR_TO_DATE('25/05/2023', '%d/%m/%Y'), 'UNIVERSITY', 2, 9000000.00, 'ONLINE', TRUE, 20),
    ('Tuyển nhân viên content marketing', 'Sáng tạo nội dung cho các kênh truyền thông.', STR_TO_DATE('15/05/2023', '%d/%m/%Y'), STR_TO_DATE('30/06/2023', '%d/%m/%Y'), 'UNIVERSITY', 3, 7500000.00, 'FULLTIME', TRUE, 20),
    ('Tuyển chuyên viên SEO', 'Tối ưu hóa công cụ tìm kiếm cho website.', STR_TO_DATE('01/06/2023', '%d/%m/%Y'), STR_TO_DATE('15/07/2023', '%d/%m/%Y'), 'UNIVERSITY', 2, 8500000.00, 'ONLINE', TRUE, 21),
    ('Tuyển nhân viên nghiên cứu thị trường', 'Phân tích dữ liệu thị trường và khách hàng.', STR_TO_DATE('20/07/2023', '%d/%m/%Y'), STR_TO_DATE('31/08/2023', '%d/%m/%Y'), 'UNIVERSITY', 2, 7000000.00, 'FULLTIME', TRUE, 21),
    ('Tuyển kế toán tổng hợp', 'Quản lý sổ sách kế toán và báo cáo tài chính.', STR_TO_DATE('01/09/2023', '%d/%m/%Y'), STR_TO_DATE('15/10/2023', '%d/%m/%Y'), 'UNIVERSITY', 1, 9000000.00, 'FULLTIME', TRUE, 22),
    ('Tuyển nhân viên kế toán thuế', 'Xử lý các vấn đề liên quan đến thuế.', STR_TO_DATE('20/10/2023', '%d/%m/%Y'), STR_TO_DATE('30/11/2023', '%d/%m/%Y'), 'UNIVERSITY', 2, 8000000.00, 'PARTTIME', TRUE, 22),
    ('Tuyển kế toán viên', 'Thực hiện các nghiệp vụ kế toán cơ bản.', STR_TO_DATE('01/12/2023', '%d/%m/%Y'), STR_TO_DATE('15/01/2024', '%d/%m/%Y'), 'UNIVERSITY', 2, 7500000.00, 'FULLTIME', TRUE, 23),
    ('Tuyển chuyên viên kiểm toán nội bộ', 'Kiểm tra và đánh giá quy trình tài chính.', STR_TO_DATE('20/01/2024', '%d/%m/%Y'), STR_TO_DATE('28/02/2024', '%d/%m/%Y'), 'UNIVERSITY', 1, 9000000.00, 'FULLTIME', TRUE, 23),
    ('Tuyển nhân viên thiết kế giao diện', 'Thiết kế UI/UX cho ứng dụng và website.', STR_TO_DATE('01/03/2024', '%d/%m/%Y'), STR_TO_DATE('15/04/2024', '%d/%m/%Y'), 'COLLEGE', 2, 6500000.00, 'ONLINE', TRUE, 24),
    ('Tuyển nhân viên thiết kế đồ họa', 'Thiết kế hình ảnh và nội dung quảng cáo.', STR_TO_DATE('01/03/2023', '%d/%m/%Y'), STR_TO_DATE('15/04/2023', '%d/%m/%Y'), 'COLLEGE', 3, 6000000.00, 'FULLTIME', TRUE, 24),
    ('Tuyển nhân viên thiết kế đồ họa', 'Thiết kế hình ảnh poster.', STR_TO_DATE('10/04/2023', '%d/%m/%Y'), STR_TO_DATE('25/05/2023', '%d/%m/%Y'), 'COLLEGE', 3, 6000000.00, 'FULLTIME', TRUE, 25),
    ('Tuyển nhân viên thiết kế sản phẩm', 'Thiết kế sản phẩm xe đạp và phụ kiện.', STR_TO_DATE('15/05/2023', '%d/%m/%Y'), STR_TO_DATE('30/06/2023', '%d/%m/%Y'), 'COLLEGE', 2, 6500000.00, 'FULLTIME', TRUE, 25),
    -- Jobs from 2025 (IDs 32-62)
    ('Tuyển lập trình viên backend', 'Phát triển hệ thống backend cho ứng dụng.', STR_TO_DATE('01/04/2025', '%d/%m/%Y'), STR_TO_DATE('20/04/2025', '%d/%m/%Y'), 'UNIVERSITY', 5, 12000000.00, 'FULLTIME', TRUE, 1),
    ('Tuyển lập trình viên mobile', 'Phát triển ứng dụng di động cho iOS và Android.', STR_TO_DATE('02/04/2025', '%d/%m/%Y'), STR_TO_DATE('21/04/2025', '%d/%m/%Y'), 'UNIVERSITY', 4, 13000000.00, 'ONLINE', TRUE, 2),
    ('Tuyển kỹ sư DevOps', 'Quản lý hạ tầng và triển khai ứng dụng.', STR_TO_DATE('03/04/2025', '%d/%m/%Y'), STR_TO_DATE('22/04/2025', '%d/%m/%Y'), 'ENGINEER', 3, 15000000.00, 'FULLTIME', TRUE, 1),
    ('Tuyển chuyên viên an ninh mạng', 'Đảm bảo an toàn thông tin cho hệ thống.', STR_TO_DATE('04/04/2025', '%d/%m/%Y'), STR_TO_DATE('23/04/2025', '%d/%m/%Y'), 'UNIVERSITY', 2, 11000000.00, 'PARTTIME', TRUE, 2),
    ('Tuyển kỹ sư dữ liệu', 'Phân tích và quản lý dữ liệu lớn.', STR_TO_DATE('05/04/2025', '%d/%m/%Y'), STR_TO_DATE('24/04/2025', '%d/%m/%Y'), 'ENGINEER', 3, 14000000.00, 'ONLINE', TRUE, 3),
    ('Tuyển phát triển ứng dụng web', 'Xây dựng và phát triển ứng dụng web.', STR_TO_DATE('06/04/2025', '%d/%m/%Y'), STR_TO_DATE('25/04/2025', '%d/%m/%Y'), 'UNIVERSITY', 5, 12000000.00, 'FULLTIME', TRUE, 4),
    ('Tuyển kiểm thử phần mềm', 'Kiểm tra và đánh giá chất lượng phần mềm.', STR_TO_DATE('07/04/2025', '%d/%m/%Y'), STR_TO_DATE('26/04/2025', '%d/%m/%Y'), 'COLLEGE', 4, 9000000.00, 'PARTTIME', TRUE, 5),
    ('Tuyển kỹ sư phần mềm', 'Phát triển phần mềm theo yêu cầu.', STR_TO_DATE('08/04/2025', '%d/%m/%Y'), STR_TO_DATE('27/04/2025', '%d/%m/%Y'), 'ENGINEER', 2, 13000000.00, 'ONLINE', TRUE, 6),
    ('Tuyển nhân viên hỗ trợ kỹ thuật', 'Hỗ trợ khách hàng về kỹ thuật.', STR_TO_DATE('09/04/2025', '%d/%m/%Y'), STR_TO_DATE('28/04/2025', '%d/%m/%Y'), 'COLLEGE', 3, 8000000.00, 'FULLTIME', TRUE, 7),
    ('Tuyển nhân viên văn phòng', 'Thực hiện các công việc hành chính.', STR_TO_DATE('10/04/2025', '%d/%m/%Y'), STR_TO_DATE('29/04/2025', '%d/%m/%Y'), 'SCHOOL', 4, 4000000.00, 'FULLTIME', TRUE, 8),
    ('Tuyển nhân viên bán hàng', 'Bán hàng và tư vấn khách hàng.', STR_TO_DATE('01/04/2025', '%d/%m/%Y'), STR_TO_DATE('30/04/2025', '%d/%m/%Y'), 'SCHOOL', 3, 3500000.00, 'PARTTIME', TRUE, 9),
    ('Tuyển nhân viên phục vụ', 'Phục vụ khách hàng tại nhà hàng.', STR_TO_DATE('02/04/2025', '%d/%m/%Y'), STR_TO_DATE('01/05/2025', '%d/%m/%Y'), 'SCHOOL', 5, 3000000.00, 'OFFLINE', TRUE, 10),
    ('Tuyển nhân viên marketing', 'Xây dựng chiến lược marketing.', STR_TO_DATE('03/04/2025', '%d/%m/%Y'), STR_TO_DATE('02/05/2025', '%d/%m/%Y'), 'COLLEGE', 2, 7000000.00, 'FULLTIME', TRUE, 11),
    ('Tuyển nhân viên kế toán', 'Quản lý sổ sách và báo cáo tài chính.', STR_TO_DATE('04/04/2025', '%d/%m/%Y'), STR_TO_DATE('03/05/2025', '%d/%m/%Y'), 'UNIVERSITY', 1, 8000000.00, 'PARTTIME', TRUE, 12),
    ('Tuyển nhân viên thiết kế đồ họa', 'Thiết kế đồ họa cho các dự án.', STR_TO_DATE('05/04/2025', '%d/%m/%Y'), STR_TO_DATE('04/05/2025', '%d/%m/%Y'), 'COLLEGE', 2, 6000000.00, 'ONLINE', TRUE, 13),
    ('Tuyển nhân viên giao nhận', 'Thực hiện giao nhận hàng hóa và hỗ trợ khách hàng.', STR_TO_DATE('06/04/2025', '%d/%m/%Y'), STR_TO_DATE('05/05/2025', '%d/%m/%Y'), 'SCHOOL', 5, 3500000.00, 'OFFLINE', TRUE, 18),
    ('Tuyển nhân viên chăm sóc khách hàng', 'Hỗ trợ khách hàng qua điện thoại và email.', STR_TO_DATE('07/04/2025', '%d/%m/%Y'), STR_TO_DATE('06/05/2025', '%d/%m/%Y'), 'SCHOOL', 4, 3200000.00, 'FULLTIME', TRUE, 18),
    ('Tuyển nhân viên kho vận', 'Quản lý kho và vận chuyển hàng hóa.', STR_TO_DATE('08/04/2025', '%d/%m/%Y'), STR_TO_DATE('07/05/2025', '%d/%m/%Y'), 'SCHOOL', 3, 3800000.00, 'OFFLINE', TRUE, 19),
    ('Tuyển nhân viên dịch vụ hậu cần', 'Hỗ trợ logistics và quản lý đơn hàng.', STR_TO_DATE('09/04/2025', '%d/%m/%Y'), STR_TO_DATE('08/05/2025', '%d/%m/%Y'), 'SCHOOL', 4, 3600000.00, 'FULLTIME', TRUE, 19),
    ('Tuyển chuyên viên digital marketing', 'Quản lý chiến dịch quảng cáo trực tuyến.', STR_TO_DATE('10/04/2025', '%d/%m/%Y'), STR_TO_DATE('09/05/2025', '%d/%m/%Y'), 'UNIVERSITY', 2, 9000000.00, 'ONLINE', TRUE, 20),
    ('Tuyển nhân viên content marketing', 'Sáng tạo nội dung cho các kênh truyền thông.', STR_TO_DATE('01/04/2025', '%d/%m/%Y'), STR_TO_DATE('10/05/2025', '%d/%m/%Y'), 'UNIVERSITY', 3, 7500000.00, 'FULLTIME', TRUE, 20),
    ('Tuyển chuyên viên SEO', 'Tối ưu hóa công cụ tìm kiếm cho website.', STR_TO_DATE('02/04/2025', '%d/%m/%Y'), STR_TO_DATE('11/05/2025', '%d/%m/%Y'), 'UNIVERSITY', 2, 8500000.00, 'ONLINE', TRUE, 21),
    ('Tuyển nhân viên nghiên cứu thị trường', 'Phân tích dữ liệu thị trường và khách hàng.', STR_TO_DATE('03/04/2025', '%d/%m/%Y'), STR_TO_DATE('12/05/2025', '%d/%m/%Y'), 'UNIVERSITY', 2, 7000000.00, 'FULLTIME', TRUE, 21),
    ('Tuyển kế toán tổng hợp', 'Quản lý sổ sách kế toán và báo cáo tài chính.', STR_TO_DATE('04/04/2025', '%d/%m/%Y'), STR_TO_DATE('13/05/2025', '%d/%m/%Y'), 'UNIVERSITY', 1, 9000000.00, 'FULLTIME', TRUE, 22),
    ('Tuyển nhân viên kế toán thuế', 'Xử lý các vấn đề liên quan đến thuế.', STR_TO_DATE('05/04/2025', '%d/%m/%Y'), STR_TO_DATE('14/05/2025', '%d/%m/%Y'), 'UNIVERSITY', 2, 8000000.00, 'PARTTIME', TRUE, 22),
    ('Tuyển kế toán viên', 'Thực hiện các nghiệp vụ kế toán cơ bản.', STR_TO_DATE('06/04/2025', '%d/%m/%Y'), STR_TO_DATE('15/05/2025', '%d/%m/%Y'), 'UNIVERSITY', 2, 7500000.00, 'FULLTIME', TRUE, 23),
    ('Tuyển chuyên viên kiểm toán nội bộ', 'Kiểm tra và đánh giá quy trình tài chính.', STR_TO_DATE('07/04/2025', '%d/%m/%Y'), STR_TO_DATE('16/05/2025', '%d/%m/%Y'), 'UNIVERSITY', 1, 9000000.00, 'FULLTIME', TRUE, 23),
    ('Tuyển nhân viên thiết kế giao diện', 'Thiết kế UI/UX cho ứng dụng và website.', STR_TO_DATE('08/04/2025', '%d/%m/%Y'), STR_TO_DATE('17/05/2025', '%d/%m/%Y'), 'COLLEGE', 2, 6500000.00, 'ONLINE', TRUE, 24),
    ('Tuyển nhân viên thiết kế đồ họa', 'Thiết kế hình ảnh và nội dung quảng cáo.', STR_TO_DATE('09/04/2025', '%d/%m/%Y'), STR_TO_DATE('18/05/2025', '%d/%m/%Y'), 'COLLEGE', 3, 6000000.00, 'FULLTIME', TRUE, 24),
    ('Tuyển nhân viên thiết kế đồ họa', 'Thiết kế hình ảnh poster.', STR_TO_DATE('09/04/2025', '%d/%m/%Y'), STR_TO_DATE('18/05/2025', '%d/%m/%Y'), 'COLLEGE', 3, 6000000.00, 'FULLTIME', TRUE, 25),
    ('Tuyển nhân viên thiết kế sản phẩm', 'Thiết kế sản phẩm xe đạp và phụ kiện.', STR_TO_DATE('10/04/2025', '%d/%m/%Y'), STR_TO_DATE('19/05/2025', '%d/%m/%Y'), 'COLLEGE', 2, 6500000.00, 'FULLTIME', TRUE, 25);

-- Job Profession
INSERT INTO job_profession (job_id, profession_id)
VALUES
    (1, 1),  (2, 1),  (3, 1),  (4, 1),  (5, 1),  (6, 1),  (7, 1),  (8, 1),  (9, 1),  (10, 2),
    (11, 3), (12, 2), (13, 3), (14, 4), (15, 5), (16, 2), (17, 2), (18, 2), (19, 2), (20, 3),
    (21, 3), (22, 3), (23, 3), (24, 4), (25, 4), (26, 4), (27, 4), (28, 5), (29, 5), (30, 5),
    (31, 5), (32, 1), (33, 1), (34, 1), (35, 1), (36, 1), (37, 1), (38, 1), (39, 1), (40, 2),
    (41, 3), (42, 2), (43, 3), (44, 4), (45, 5), (46, 2), (47, 2), (48, 2), (49, 2), (50, 3),
    (51, 3), (52, 3), (53, 3), (54, 4), (55, 4), (56, 4), (57, 4), (58, 5), (59, 5), (60, 5),
    (61, 5), (62, 5);

-- Job Resume
INSERT INTO job_resume (job_id, resume_id, status)
VALUES
    -- Jobs 1-31 (2023-2024)
    (1, 1, 'PENDING'), (1, 10, 'ACCEPTED'),
    (2, 1, 'PENDING'), (2, 10, 'REJECTED'),
    (3, 5, 'PENDING'), (3, 10, 'ACCEPTED'),
    (4, 9, 'REJECTED'), (4, 10, 'PENDING'),
    (5, 1, 'PENDING'), (5, 10, 'ACCEPTED'),
    (6, 5, 'PENDING'), (6, 10, 'REJECTED'),
    (7, 9, 'PENDING'), (7, 10, 'ACCEPTED'),
    (8, 1, 'REJECTED'), (8, 10, 'PENDING'),
    (9, 5, 'PENDING'), (9, 10, 'ACCEPTED'),
    (10, 8, 'PENDING'), (10, 12, 'ACCEPTED'),
    (11, 2, 'PENDING'), (11, 11, 'ACCEPTED'),
    (12, 8, 'PENDING'), (12, 12, 'REJECTED'),
    (13, 6, 'PENDING'), (13, 11, 'ACCEPTED'),
    (14, 4, 'PENDING'), (14, 13, 'ACCEPTED'),
    (15, 3, 'PENDING'), (15, 14, 'ACCEPTED'),
    (16, 8, 'PENDING'), (16, 12, 'REJECTED'),
    (17, 8, 'PENDING'), (17, 12, 'ACCEPTED'),
    (18, 8, 'PENDING'), (18, 12, 'REJECTED'),
    (19, 8, 'PENDING'), (19, 12, 'ACCEPTED'),
    (20, 2, 'PENDING'), (20, 11, 'REJECTED'),
    (21, 6, 'PENDING'), (21, 11, 'ACCEPTED'),
    (22, 2, 'PENDING'), (22, 11, 'REJECTED'),
    (23, 6, 'PENDING'), (23, 11, 'ACCEPTED'),
    (24, 4, 'PENDING'), (24, 13, 'REJECTED'),
    (25, 4, 'PENDING'), (25, 13, 'ACCEPTED'),
    (26, 4, 'PENDING'), (26, 13, 'REJECTED'),
    (27, 4, 'PENDING'), (27, 13, 'ACCEPTED'),
    (28, 3, 'PENDING'), (28, 14, 'REJECTED'),
    (29, 7, 'PENDING'), (29, 14, 'ACCEPTED'),
    (30, 3, 'PENDING'), (30, 14, 'REJECTED'),
    (31, 7, 'PENDING'), (31, 14, 'ACCEPTED'),
    -- Jobs 32-62 (2025)
    (32, 1, 'PENDING'), (32, 2, 'ACCEPTED'), (32, 3, 'PENDING'),
    (33, 1, 'PENDING'), (33, 4, 'REJECTED'), (33, 5, 'ACCEPTED'),
    (34, 2, 'PENDING'), (34, 6, 'ACCEPTED'), (34, 7, 'PENDING'),
    (35, 3, 'REJECTED'), (35, 8, 'PENDING'), (35, 9, 'ACCEPTED'),
    (36, 1, 'PENDING'), (36, 4, 'ACCEPTED'), (36, 10, 'PENDING'),
    (37, 2, 'PENDING'), (37, 5, 'REJECTED'), (37, 6, 'PENDING'),
    (38, 3, 'PENDING'), (38, 7, 'ACCEPTED'), (38, 8, 'PENDING'),
    (39, 4, 'REJECTED'), (39, 9, 'PENDING'), (39, 10, 'ACCEPTED'),
    (40, 1, 'PENDING'), (40, 5, 'ACCEPTED'), (40, 6, 'PENDING'),
    (41, 2, 'PENDING'), (41, 7, 'REJECTED'), (41, 11, 'ACCEPTED'),
    (42, 3, 'PENDING'), (42, 8, 'ACCEPTED'), (42, 9, 'PENDING'),
    (43, 1, 'PENDING'), (43, 4, 'REJECTED'), (43, 11, 'ACCEPTED'),
    (44, 2, 'PENDING'), (44, 5, 'ACCEPTED'), (44, 6, 'PENDING'),
    (45, 3, 'PENDING'), (45, 7, 'REJECTED'), (45, 14, 'ACCEPTED'),
    (46, 4, 'PENDING'), (46, 8, 'ACCEPTED'), (46, 9, 'PENDING'),
    (47, 1, 'PENDING'), (47, 2, 'REJECTED'), (47, 12, 'ACCEPTED'),
    (48, 3, 'PENDING'), (48, 5, 'ACCEPTED'), (48, 6, 'PENDING'),
    (49, 4, 'PENDING'), (49, 7, 'REJECTED'), (49, 12, 'ACCEPTED'),
    (50, 1, 'PENDING'), (50, 8, 'ACCEPTED'), (50, 9, 'PENDING'),
    (51, 2, 'PENDING'), (51, 4, 'REJECTED'), (51, 11, 'ACCEPTED'),
    (52, 3, 'PENDING'), (52, 5, 'ACCEPTED'), (52, 6, 'PENDING'),
    (53, 1, 'PENDING'), (53, 7, 'REJECTED'), (53, 11, 'ACCEPTED'),
    (54, 2, 'PENDING'), (54, 8, 'ACCEPTED'), (54, 9, 'PENDING'),
    (55, 3, 'PENDING'), (55, 4, 'REJECTED'), (55, 13, 'ACCEPTED'),
    (56, 1, 'PENDING'), (56, 5, 'ACCEPTED'), (56, 6, 'PENDING'),
    (57, 2, 'PENDING'), (57, 7, 'REJECTED'), (57, 13, 'ACCEPTED'),
    (58, 3, 'PENDING'), (58, 8, 'ACCEPTED'), (58, 9, 'PENDING'),
    (59, 1, 'PENDING'), (59, 4, 'REJECTED'), (59, 14, 'ACCEPTED'),
    (60, 2, 'PENDING'), (60, 5, 'ACCEPTED'), (60, 6, 'PENDING'),
    (61, 3, 'PENDING'), (61, 7, 'REJECTED'), (61, 14, 'ACCEPTED'),
    (62, 1, 'PENDING'), (62, 8, 'ACCEPTED'), (62, 9, 'PENDING');

-- Application Invoices
INSERT INTO invoices (Discriminator, createdDate, fee, applicationCount, applicationFee, applicant_id, employee_id, job_id, recruiter_id)
VALUES
    -- Jobs 1-31 (2023-2024)
    ('Application', STR_TO_DATE('10/03/2023', '%d/%m/%Y'), 70000.00, NULL, NULL, 10, 1, 1, NULL),
    ('Application', STR_TO_DATE('15/04/2023', '%d/%m/%Y'), 65000.00, NULL, NULL, 10, 2, 2, NULL),
    ('Application', STR_TO_DATE('20/05/2023', '%d/%m/%Y'), 80000.00, NULL, NULL, 10, 1, 3, NULL),
    ('Application', STR_TO_DATE('10/06/2023', '%d/%m/%Y'), 75000.00, NULL, NULL, 10, 2, 4, NULL),
    ('Application', STR_TO_DATE('25/07/2023', '%d/%m/%Y'), 60000.00, NULL, NULL, 10, 1, 5, NULL),
    ('Application', STR_TO_DATE('10/09/2023', '%d/%m/%Y'), 70000.00, NULL, NULL, 10, 2, 6, NULL),
    ('Application', STR_TO_DATE('25/10/2023', '%d/%m/%Y'), 65000.00, NULL, NULL, 10, 1, 7, NULL),
    ('Application', STR_TO_DATE('10/12/2023', '%d/%m/%Y'), 80000.00, NULL, NULL, 10, 2, 8, NULL),
    ('Application', STR_TO_DATE('25/01/2024', '%d/%m/%Y'), 75000.00, NULL, NULL, 10, 1, 9, NULL),
    ('Application', STR_TO_DATE('10/03/2024', '%d/%m/%Y'), 60000.00, NULL, NULL, 12, 2, 10, NULL),
    ('Application', STR_TO_DATE('25/04/2023', '%d/%m/%Y'), 70000.00, NULL, NULL, 11, 1, 11, NULL),
    ('Application', STR_TO_DATE('10/06/2023', '%d/%m/%Y'), 65000.00, NULL, NULL, 12, 2, 12, NULL),
    ('Application', STR_TO_DATE('25/07/2023', '%d/%m/%Y'), 80000.00, NULL, NULL, 11, 1, 13, NULL),
    ('Application', STR_TO_DATE('01/09/2023', '%d/%m/%Y'), 75000.00, NULL, NULL, 13, 2, 14, NULL),
    ('Application', STR_TO_DATE('25/10/2023', '%d/%m/%Y'), 60000.00, NULL, NULL, 14, 1, 15, NULL),
    ('Application', STR_TO_DATE('10/12/2023', '%d/%m/%Y'), 70000.00, NULL, NULL, 12, 2, 16, NULL),
    ('Application', STR_TO_DATE('25/01/2024', '%d/%m/%Y'), 65000.00, NULL, NULL, 12, 1, 17, NULL),
    ('Application', STR_TO_DATE('10/03/2024', '%d/%m/%Y'), 80000.00, NULL, NULL, 12, 2, 18, NULL),
    ('Application', STR_TO_DATE('10/03/2023', '%d/%m/%Y'), 75000.00, NULL, NULL, 12, 1, 19, NULL),
    ('Application', STR_TO_DATE('15/04/2023', '%d/%m/%Y'), 60000.00, NULL, NULL, 11, 2, 20, NULL),
    ('Application', STR_TO_DATE('20/05/2023', '%d/%m/%Y'), 70000.00, NULL, NULL, 11, 1, 21, NULL),
    ('Application', STR_TO_DATE('10/06/2023', '%d/%m/%Y'), 65000.00, NULL, NULL, 11, 2, 22, NULL),
    ('Application', STR_TO_DATE('25/07/2023', '%d/%m/%Y'), 80000.00, NULL, NULL, 11, 1, 23, NULL),
    ('Application', STR_TO_DATE('01/09/2023', '%d/%m/%Y'), 75000.00, NULL, NULL, 13, 2, 24, NULL),
    ('Application', STR_TO_DATE('25/10/2023', '%d/%m/%Y'), 60000.00, NULL, NULL, 13, 1, 25, NULL),
    ('Application', STR_TO_DATE('10/12/2023', '%d/%m/%Y'), 70000.00, NULL, NULL, 13, 2, 26, NULL),
    ('Application', STR_TO_DATE('25/01/2024', '%d/%m/%Y'), 65000.00, NULL, NULL, 13, 1, 27, NULL),
    ('Application', STR_TO_DATE('10/03/2024', '%d/%m/%Y'), 80000.00, NULL, NULL, 14, 2, 28, NULL),
    ('Application', STR_TO_DATE('10/03/2023', '%d/%m/%Y'), 75000.00, NULL, NULL, 14, 1, 29, NULL),
    ('Application', STR_TO_DATE('15/04/2023', '%d/%m/%Y'), 60000.00, NULL, NULL, 14, 2, 30, NULL),
    ('Application', STR_TO_DATE('20/05/2023', '%d/%m/%Y'), 70000.00, NULL, NULL, 14, 1, 31, NULL),
    -- Jobs 32-62 (2025)
    ('Application', STR_TO_DATE('10/04/2025', '%d/%m/%Y'), 70000.00, NULL, NULL, 2, 2, 32, NULL),
    ('Application', STR_TO_DATE('12/04/2025', '%d/%m/%Y'), 65000.00, NULL, NULL, 5, 1, 33, NULL),
    ('Application', STR_TO_DATE('15/04/2025', '%d/%m/%Y'), 80000.00, NULL, NULL, 6, 2, 34, NULL),
    ('Application', STR_TO_DATE('10/04/2025', '%d/%m/%Y'), 75000.00, NULL, NULL, 9, 1, 35, NULL),
    ('Application', STR_TO_DATE('12/04/2025', '%d/%m/%Y'), 60000.00, NULL, NULL, 4, 2, 36, NULL),
    ('Application', STR_TO_DATE('15/04/2025', '%d/%m/%Y'), 70000.00, NULL, NULL, 5, 1, 37, NULL),
    ('Application', STR_TO_DATE('10/04/2025', '%d/%m/%Y'), 65000.00, NULL, NULL, 7, 2, 38, NULL),
    ('Application', STR_TO_DATE('12/04/2025', '%d/%m/%Y'), 80000.00, NULL, NULL, 9, 1, 39, NULL),
    ('Application', STR_TO_DATE('15/04/2025', '%d/%m/%Y'), 75000.00, NULL, NULL, 5, 2, 40, NULL),
    ('Application', STR_TO_DATE('10/04/2025', '%d/%m/%Y'), 60000.00, NULL, NULL, 11, 1, 41, NULL),
    ('Application', STR_TO_DATE('12/04/2025', '%d/%m/%Y'), 70000.00, NULL, NULL, 8, 2, 42, NULL),
    ('Application', STR_TO_DATE('15/04/2025', '%d/%m/%Y'), 65000.00, NULL, NULL, 11, 1, 43, NULL),
    ('Application', STR_TO_DATE('10/04/2025', '%d/%m/%Y'), 80000.00, NULL, NULL, 4, 2, 44, NULL),
    ('Application', STR_TO_DATE('12/04/2025', '%d/%m/%Y'), 75000.00, NULL, NULL, 14, 1, 45, NULL),
    ('Application', STR_TO_DATE('15/04/2025', '%d/%m/%Y'), 60000.00, NULL, NULL, 8, 2, 46, NULL),
    ('Application', STR_TO_DATE('10/04/2025', '%d/%m/%Y'), 70000.00, NULL, NULL, 12, 1, 47, NULL),
    ('Application', STR_TO_DATE('12/04/2025', '%d/%m/%Y'), 65000.00, NULL, NULL, 5, 2, 48, NULL),
    ('Application', STR_TO_DATE('15/04/2025', '%d/%m/%Y'), 80000.00, NULL, NULL, 12, 1, 49, NULL),
    ('Application', STR_TO_DATE('10/04/2025', '%d/%m/%Y'), 75000.00, NULL, NULL, 11, 2, 50, NULL),
    ('Application', STR_TO_DATE('12/04/2025', '%d/%m/%Y'), 60000.00, NULL, NULL, 11, 1, 51, NULL),
    ('Application', STR_TO_DATE('15/04/2025', '%d/%m/%Y'), 70000.00, NULL, NULL, 11, 2, 52, NULL),
    ('Application', STR_TO_DATE('10/04/2025', '%d/%m/%Y'), 65000.00, NULL, NULL, 11, 1, 53, NULL),
    ('Application', STR_TO_DATE('12/04/2025', '%d/%m/%Y'), 80000.00, NULL, NULL, 13, 2, 54, NULL),
    ('Application', STR_TO_DATE('15/04/2025', '%d/%m/%Y'), 75000.00, NULL, NULL, 13, 1, 55, NULL),
    ('Application', STR_TO_DATE('10/04/2025', '%d/%m/%Y'), 60000.00, NULL, NULL, 13, 2, 56, NULL),
    ('Application', STR_TO_DATE('12/04/2025', '%d/%m/%Y'), 70000.00, NULL, NULL, 13, 1, 57, NULL),
    ('Application', STR_TO_DATE('15/04/2025', '%d/%m/%Y'), 65000.00, NULL, NULL, 14, 2, 58, NULL),
    ('Application', STR_TO_DATE('10/04/2025', '%d/%m/%Y'), 80000.00, NULL, NULL, 14, 1, 59, NULL),
    ('Application', STR_TO_DATE('12/04/2025', '%d/%m/%Y'), 75000.00, NULL, NULL, 14, 2, 60, NULL),
    ('Application', STR_TO_DATE('15/04/2025', '%d/%m/%Y'), 60000.00, NULL, NULL, 14, 1, 61, NULL),
    ('Application', STR_TO_DATE('10/04/2025', '%d/%m/%Y'), 70000.00, NULL, NULL, 8, 2, 62, NULL);

-- Posted Invoices
INSERT INTO invoices (Discriminator, createdDate, fee, applicationCount, applicationFee, applicant_id, employee_id, job_id, recruiter_id)
VALUES
    -- Jobs 1-31 (2023-2024)
    ('Posted', STR_TO_DATE('15/04/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 1, 1, 1),
    ('Posted', STR_TO_DATE('25/05/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 2, 2, 2),
    ('Posted', STR_TO_DATE('30/06/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 1, 3, 1),
    ('Posted', STR_TO_DATE('15/07/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 2, 4, 2),
    ('Posted', STR_TO_DATE('31/08/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 1, 5, 3),
    ('Posted', STR_TO_DATE('15/10/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 2, 6, 4),
    ('Posted', STR_TO_DATE('30/11/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 1, 7, 5),
    ('Posted', STR_TO_DATE('15/01/2024', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 2, 8, 6),
    ('Posted', STR_TO_DATE('28/02/2024', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 1, 9, 7),
    ('Posted', STR_TO_DATE('15/04/2024', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 2, 10, 8),
    ('Posted', STR_TO_DATE('31/05/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 1, 11, 9),
    ('Posted', STR_TO_DATE('15/07/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 2, 12, 10),
    ('Posted', STR_TO_DATE('31/08/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 1, 13, 11),
    ('Posted', STR_TO_DATE('15/10/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 2, 14, 12),
    ('Posted', STR_TO_DATE('30/11/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 1, 15, 13),
    ('Posted', STR_TO_DATE('15/01/2024', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 2, 16, 18),
    ('Posted', STR_TO_DATE('28/02/2024', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 1, 17, 18),
    ('Posted', STR_TO_DATE('15/04/2024', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 2, 18, 19),
    ('Posted', STR_TO_DATE('15/04/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 1, 19, 19),
    ('Posted', STR_TO_DATE('25/05/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 2, 20, 20),
    ('Posted', STR_TO_DATE('30/06/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 1, 21, 20),
    ('Posted', STR_TO_DATE('15/07/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 2, 22, 21),
    ('Posted', STR_TO_DATE('31/08/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 1, 23, 21),
    ('Posted', STR_TO_DATE('15/10/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 2, 24, 22),
    ('Posted', STR_TO_DATE('30/11/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 1, 25, 22),
    ('Posted', STR_TO_DATE('15/01/2024', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 2, 26, 23),
    ('Posted', STR_TO_DATE('28/02/2024', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 1, 27, 23),
    ('Posted', STR_TO_DATE('15/04/2024', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 2, 28, 24),
    ('Posted', STR_TO_DATE('15/04/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 1, 29, 24),
    ('Posted', STR_TO_DATE('25/05/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 2, 30, 25),
    ('Posted', STR_TO_DATE('30/06/2023', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 1, 31, 25),
    -- Jobs 32-62 (2025)
    ('Posted', STR_TO_DATE('20/04/2025', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 1, 32, 1),
    ('Posted', STR_TO_DATE('21/04/2025', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 2, 33, 2),
    ('Posted', STR_TO_DATE('22/04/2025', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 1, 34, 1),
    ('Posted', STR_TO_DATE('23/04/2025', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 2, 35, 2),
    ('Posted', STR_TO_DATE('25/04/2025', '%d/%m/%Y'), NULL, 1, 200000.00, NULL, 2, 36, 4);