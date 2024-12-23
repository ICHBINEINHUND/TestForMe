//package com.example.dkkp.controller;
//
//import com.example.dkkp.model.User_Entity;
//import com.example.dkkp.service.UserService;
//import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//
//import java.time.LocalDateTime;
//
//public class UserController {
//
//    private final UserService userService;
//
//    @FXML
//    private TextField tfEmail, tfName, tfPhone, tfAddress, tfRole;
//
//    @FXML
//    private PasswordField pfPassword, pfNewPassword;
//
//    @FXML
//    private Button btnRegister, btnLogin, btnUpdate, btnChangePassword;
//
//    public UserController() {
//        this.userService = new UserService();
//    }
//
//    @FXML
//    public void initialize() {
//        // Hàm này sẽ được gọi khi GUI đã được khởi tạo.
//    }
//
//    @FXML
//    public void handleRegister() {
//        try {
//            String email = tfEmail.getText();
//            String name = tfName.getText();
//            String phone = tfPhone.getText();
//            String address = tfAddress.getText();
//            String role = tfRole.getText();
//            String password = pfPassword.getText();
//
//            // Tạo User_Entity mới, lưu ý là `DATE_JOIN` sẽ được tự động gán tại server (hoặc trong constructor nếu cần)
//            User_Entity newUser = new User_Entity();
//            newUser.setEMAIL_ACC(email);
//            newUser.setNAME_USER(name);
//            newUser.setPHONE_ACC(phone);
//            newUser.setADDRESS(address);
//            newUser.setROLE_ACC(role);
//            newUser.setPASSWORD_ACC(password); // mật khẩu chưa mã hóa
//            newUser.setDATE_JOIN(LocalDateTime.now());  // Thời gian tham gia sẽ là thời điểm đăng ký
//
//            boolean isRegistered = userService.registerNewUser(newUser);
//
//            if (isRegistered) {
//                showAlert(Alert.AlertType.INFORMATION, "Đăng ký thành công!", "Bạn đã đăng ký thành công.");
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Đăng ký thất bại", "Có lỗi xảy ra trong quá trình đăng ký.");
//            }
//        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, "Lỗi đăng ký", e.getMessage());
//        }
//    }
//
//    @FXML
//    public void handleLogin() {
//        try {
//            String email = tfEmail.getText();
//            String password = pfPassword.getText();
//
//            boolean isLoggedIn = userService.login(email, password);
//
//            if (isLoggedIn) {
//                showAlert(Alert.AlertType.INFORMATION, "Đăng nhập thành công!", "Chào mừng bạn trở lại!");
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Đăng nhập thất bại", "Email hoặc mật khẩu không đúng.");
//            }
//        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, "Lỗi đăng nhập", e.getMessage());
//        }
//    }
//
//    @FXML
//    public void handleUpdateUserInfo() {
//        try {
//            String email = tfEmail.getText();
//            String name = tfName.getText();
//            String phone = tfPhone.getText();
//            String address = tfAddress.getText();
//            String role = tfRole.getText();
//
//            User_Entity user = new User_Entity();
//            user.setEMAIL_ACC(email);
//            user.setNAME_USER(name);
//            user.setPHONE_ACC(phone);
//            user.setADDRESS(address);
//            user.setROLE_ACC(role);
//
//            boolean isUpdated = userService.updateUserInfo(user);
//
//            if (isUpdated) {
//                showAlert(Alert.AlertType.INFORMATION, "Cập nhật thành công", "Thông tin người dùng đã được cập nhật.");
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Cập nhật thất bại", "Có lỗi xảy ra trong quá trình cập nhật.");
//            }
//        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, "Lỗi cập nhật", e.getMessage());
//        }
//    }
//
//    @FXML
//    public void handleChangePassword() {
//        try {
//            String email = tfEmail.getText();
//            String oldPassword = pfPassword.getText();
//            String newPassword = pfNewPassword.getText();
//
//            User_Entity user = userService.getUsersByID(email);
//            boolean isPasswordChanged = userService.changePassword(user, newPassword);
//
//            if (isPasswordChanged) {
//                showAlert(Alert.AlertType.INFORMATION, "Đổi mật khẩu thành công", "Mật khẩu đã được thay đổi.");
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Đổi mật khẩu thất bại", "Có lỗi xảy ra khi thay đổi mật khẩu.");
//            }
//        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, "Lỗi đổi mật khẩu", e.getMessage());
//        }
//    }
//
//    private void showAlert(Alert.AlertType alertType, String header, String message) {
//        Alert alert = new Alert(alertType);
//        alert.setHeaderText(header);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//}
