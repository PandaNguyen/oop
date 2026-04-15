# Bản thảo phân chia công việc (5 người) — ProTasker (JavaFX, OOP)

## 1) Mục tiêu báo cáo với giảng viên
- Chứng minh nhóm chia việc **rõ ràng – cân bằng – đo được**.
- Mỗi người phụ trách 1 “module” chính + 1 phần tài liệu/kiểm thử để đảm bảo **đóng góp ~20%**.
- Có đầu ra (deliverables) cụ thể: code, giao diện, dữ liệu, tài liệu, demo/presentation.

## 2) Nguyên tắc chia việc (để “đều nhau”)
- Mỗi người sở hữu **1 phạm vi code độc lập** (Controller/Model/View) + **1 đầu ra học thuật** (UML, báo cáo, test, slide).
- Mỗi đầu việc đều có: *phạm vi*, *file liên quan*, *tiêu chí hoàn thành*, *người review*.
- Quy ước chung:
  - Mỗi tính năng merge vào nhánh chính phải có: demo ngắn + checklist test tay.
  - Mỗi PR phải được **1 người khác** review tối thiểu.

## 3) Phân rã công việc theo module (WBS mức cao)
1. Khởi tạo ứng dụng + điều hướng màn hình (JavaFX/FXML).
2. Xác thực & tài khoản: đăng nhập/đăng ký + quản lý thông tin người dùng.
3. Quản lý Project: tạo/sửa/xóa, sắp xếp/lọc/tìm kiếm, xem chi tiết project.
4. Quản lý Task/Subtask: tạo/xóa/cập nhật trạng thái, lọc theo trạng thái, cập nhật progress project.
5. Lưu trữ dữ liệu (DataStore/JSON) + tiện ích + kiểm thử + đóng gói + tài liệu/UML + thuyết trình.

## 4) Bảng phân công (đề xuất) — đảm bảo cân bằng ~20% mỗi người
> Bạn thay “Thành viên A/B/…” bằng tên thật của nhóm.

### Thành viên A — Auth + Luồng đăng nhập/đăng ký + phần “giới thiệu hệ thống”
- **Phạm vi chính**
  - Luồng Login/SignUp và validation cơ bản.
  - Nạp user sau đăng nhập, điều hướng sang Dashboard.
- **File liên quan (tham chiếu theo repo)**
  - `Product/src/main/java/protasker/Controller/LoginController.java`
  - `Product/src/main/java/protasker/Controller/SignUpController.java`
  - `Product/src/main/java/protasker/Model/Authenticator.java`
  - `Product/src/main/java/protasker/Model/User.java`
  - `Product/src/main/resources/View/LogInAndSignUp/login-screen.fxml`
  - `Product/src/main/resources/View/LogInAndSignUp/signup-screen.fxml`
- **Deliverables**
  - Use-case + mô tả luồng đăng nhập/đăng ký (1–2 trang trong báo cáo).
  - Demo live: đăng ký → đăng nhập → vào Dashboard.
- **Tiêu chí hoàn thành**
  - Sai user/pass báo lỗi rõ; đúng thì vào Dashboard.
  - Không crash khi thiếu dữ liệu/field rỗng.
- **Reviewer gợi ý**: Thành viên B.

### Thành viên B — Dashboard/Overview + Search/Sort + phần “kiến trúc & điều hướng”
- **Phạm vi chính**
  - Dashboard hiển thị danh sách project, sắp xếp theo tên/priority/progress/target date.
  - Search project theo tên, cập nhật danh sách.
  - Điều hướng từ Dashboard sang Project/Task/Profile.
- **File liên quan**
  - `Product/src/main/java/protasker/Controller/DashBoardController.java`
  - `Product/src/main/java/protasker/Controller/ProjectItemController.java`
  - `Product/src/main/resources/View/DashBoard/dash-board.fxml`
  - `Product/src/main/resources/View/ProjectScreen/project-dashboarditem.fxml`
- **Deliverables**
  - Sơ đồ điều hướng màn hình (navigation map) đưa vào slide/báo cáo.
  - Demo live: sort + search + chuyển màn.
- **Tiêu chí hoàn thành**
  - Sort/search không lỗi, UI refresh đúng.
  - Điều hướng giữ được `currentUser`.
- **Reviewer gợi ý**: Thành viên C.

### Thành viên C — Project module (List + Detail + CRUD) + phần “UML lớp/thiết kế OOP”
- **Phạm vi chính**
  - Màn Project: list project, tạo project mới, sắp xếp.
  - Màn Project detail: hiển thị thông tin, tiến độ, thành viên (nếu có).
  - Xóa project/refresh lại UI (nếu project có chức năng này).
- **File liên quan**
  - `Product/src/main/java/protasker/Controller/ProjectScreenController.java`
  - `Product/src/main/java/protasker/Controller/NewProjectController.java`
  - `Product/src/main/java/protasker/Controller/ProjectDetailController.java`
  - `Product/src/main/java/protasker/Controller/ProjectHboxController.java`
  - `Product/src/main/java/protasker/Model/Project.java`
  - `Product/src/main/resources/View/ProjectScreen/project-screen.fxml`
  - `Product/src/main/resources/View/ProjectScreen/new-project.fxml`
  - `Product/src/main/resources/View/ProjectScreen/project-detail.fxml`
- **Deliverables**
  - Class diagram (UML) cập nhật theo code hiện tại + giải thích OOP (class/relationship).
  - Demo live: tạo project → xem detail → sắp xếp.
- **Tiêu chí hoàn thành**
  - Tạo project xong danh sách refresh đúng.
  - Detail hiển thị đúng thông tin từ `DataStore`.
- **Reviewer gợi ý**: Thành viên D.

### Thành viên D — Task module (List + New Task + Filter/Status) + phần “kiểm thử chức năng”
- **Phạm vi chính**
  - Màn Task: hiển thị task theo project, tạo task mới.
  - Lọc task: All / In Progress / Done.
  - Cập nhật trạng thái task (nếu thao tác nằm ở `TaskController`) và đảm bảo refresh UI.
- **File liên quan**
  - `Product/src/main/java/protasker/Controller/TaskScreenController.java`
  - `Product/src/main/java/protasker/Controller/NewTaskController.java`
  - `Product/src/main/java/protasker/Controller/TaskController.java`
  - `Product/src/main/java/protasker/Model/Task.java`
  - `Product/src/main/resources/View/TaskScreen/task-screen.fxml`
  - `Product/src/main/resources/View/TaskScreen/new-task.fxml`
  - `Product/src/main/resources/View/TaskScreen/task.fxml`
- **Deliverables**
  - Test checklist (manual) theo luồng: tạo task → đổi status → lọc → kiểm tra progress.
  - Demo live: lọc task + cập nhật status.
- **Tiêu chí hoàn thành**
  - Lọc đúng theo status, không mất dữ liệu khi refresh.
  - Thao tác trạng thái không làm sai progress của project (nếu có).
- **Reviewer gợi ý**: Thành viên E.

### Thành viên E — Data persistence (DataStore/JSON) + tích hợp + “báo cáo/slide/demo”
- **Phạm vi chính**
  - Lưu/đọc dữ liệu (DataStore) và các thao tác CRUD nền (xóa project/task, mapping user–project).
  - Chuẩn hóa dữ liệu mẫu để demo, kiểm tra các case null/thiếu field.
  - Tổng hợp báo cáo + slide thuyết trình + kịch bản demo.
- **File liên quan**
  - `Product/src/main/java/protasker/Model/DataStore.java`
  - `Product/src/main/java/protasker/Model/FileContact.java`
  - `Product/src/main/java/protasker/Model/UserProject.java`
  - `Product/src/main/java/protasker/Model/UserInfo.java`
  - `Product/src/main/java/protasker/Model/userdata.json`
- **Deliverables**
  - 1 file “kịch bản demo” (ai bấm gì, theo thứ tự nào) + phân vai thuyết trình.
  - Tổng hợp báo cáo: mục tiêu, phạm vi, kết quả, hạn chế, hướng phát triển.
- **Tiêu chí hoàn thành**
  - Dữ liệu load/save ổn định, không crash khi file thiếu/đọc lỗi (có xử lý an toàn).
  - Demo chạy trơn tru với dataset chuẩn.
- **Reviewer gợi ý**: Thành viên A.

## 5) Lịch triển khai gợi ý (3 mốc, dễ báo cáo)
- **Mốc 1 (Thiết kế)**: chốt yêu cầu, chia module, cập nhật UML & navigation map.
- **Mốc 2 (Hoàn thiện tính năng)**: mỗi module xong 80%, bắt đầu tích hợp & review chéo.
- **Mốc 3 (Đóng gói & báo cáo)**: fix bug, chuẩn hóa dữ liệu demo, hoàn tất slide/báo cáo.

## 6) Checklist báo cáo trước giảng viên (ngắn gọn)
- Nhóm trình bày: mục tiêu → kiến trúc → demo tính năng chính → UML → kết luận.
- Mỗi người nói 1–2 phút về module mình + chỉ ra file/code mình phụ trách.
- Chuẩn bị sẵn: (1) slide, (2) UML, (3) demo script, (4) dataset demo, (5) phân công này.

