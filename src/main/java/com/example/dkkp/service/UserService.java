package com.example.dkkp.service;

import com.example.dkkp.dao.UserDao;
import com.example.dkkp.model.User_Entity;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;

public class UserService {
    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public User_Entity getUsersByID(String id) throws Exception {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty.");
        }
        User_Entity user = userDao.getUsersByID(id);
        if (user == null) {
            throw new IllegalArgumentException("User with ID " + id + " not found.");
        }
        decryptUserSensitiveData(user);
        return user;
    }


    public boolean registerNewUser(User_Entity user) {
        //chạy được
        // add check ( từ user lấy các property để kiểm tra nếu đạt
        // thì gọi return userDao.createUser(userC);
        EntityTransaction transaction = userDao.getEntityManager().getTransaction();
        try {
            transaction.begin();
            //mã hóa mật khẩu
            String salt = SecutiryFunction.generateSalt();
            user.setSALT(salt);
            String pass = user.getPASSWORD_ACC() + salt;
            pass = SecutiryFunction.hashString(pass);
            user.setPASSWORD_ACC(pass);
//            LocalDateTime DATE_JOIN = LocalDateTime.now();
            // mã hóa thông tin cá nhân
            encryptUserSensitiveData(user);
            return userDao.createUser(user);
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public boolean login(String EMAIL_ACC, String PASSWORD_ACC) throws Exception {
        //add check
        // chạy được
        // sửa lại các điều kiện kiểm tra định dạng email và pass
        if (EMAIL_ACC != null || !EMAIL_ACC.trim().isEmpty()) {
            if (PASSWORD_ACC != null || !PASSWORD_ACC.trim().isEmpty()) {
                String mail = SecutiryFunction.encrypt(EMAIL_ACC);
                PASSWORD_ACC += userDao.getUsersByMail(mail).getSALT();
                PASSWORD_ACC = SecutiryFunction.hashString(PASSWORD_ACC);
                return userDao.loginValidate(mail, PASSWORD_ACC);
            }
        }
        return false;
    }

    public boolean updateUserInfo(User_Entity user) {
        // add check nếu cần
        // chạy được
        try {
            encryptUserSensitiveData(user);
            String id = user.getID_USER();
            String email = user.getEMAIL_ACC();
            String phone = user.getPHONE_ACC();
            String role = user.getROLE_ACC();
            String name = user.getNAME_USER();
            String add = user.getADDRESS();
            return userDao.updateUser(id,add, email, phone, role, name);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean changePassword(User_Entity user, String newPassword) {
        // chạy được
        try {
            String email = user.getEMAIL_ACC();
            String oldPassword = user.getPASSWORD_ACC();
            if (login(email, oldPassword)) {
                return userDao.changePasswordByEmail(email, newPassword);
            }
            return false;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void decryptUserSensitiveData(User_Entity user) throws Exception {
        if (user.getADDRESS() != null) {
            user.setADDRESS(SecutiryFunction.decrypt(user.getADDRESS()));
        }
        if (user.getPHONE_ACC() != null) {
            user.setPHONE_ACC(SecutiryFunction.decrypt(user.getPHONE_ACC()));
        }
        if (user.getEMAIL_ACC() != null) {
            user.setEMAIL_ACC(SecutiryFunction.decrypt(user.getEMAIL_ACC()));
        }
        if (user.getNAME_USER() != null) {
            user.setNAME_USER(SecutiryFunction.decrypt(user.getNAME_USER()));
        }
    }

    private void encryptUserSensitiveData(User_Entity user) throws Exception {
        if (user.getADDRESS() != null) {
            user.setADDRESS(SecutiryFunction.encrypt(user.getADDRESS()));
        }
        if (user.getPHONE_ACC() != null) {
            user.setPHONE_ACC(SecutiryFunction.encrypt(user.getPHONE_ACC()));
        }
        if (user.getEMAIL_ACC() != null) {
            user.setEMAIL_ACC(SecutiryFunction.encrypt(user.getEMAIL_ACC()));
        }
        if (user.getNAME_USER() != null) {
            user.setNAME_USER(SecutiryFunction.encrypt(user.getNAME_USER()));
        }
    }

}