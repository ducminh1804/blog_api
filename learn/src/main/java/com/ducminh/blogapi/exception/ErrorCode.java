package com.ducminh.blogapi.exception;

import lombok.Data;

public enum ErrorCode {
    USER_NOT_FOUND(1000, "Không tìm thấy người dùng"),
    INVALID_DATA(1001, "Invalid data"),
    USERNAME_EXISTS(1002, "Username đã tồn tại"),
    EMAIL_EXISTS(1003, "Email đã tồn tại"),
    WRONG_PASSWORD(1004, "Sai mật khẩu"),
    EXPIRED_JWT(1005, "Token hết hạn"),
    INVALID_SIGNATURE(1006, "Token không hợp lệ"),
    INVALID_JWT(1007, "Lỗi xác thực"),
    EMPTY_TOKEN(1011, "Token không được để trống"),

    INVALID_ROLE(1008, "Role không tồn tại"),

    ACCESSDINED(1009, "Không có quyển truy cập"),

    NOT_PERMISSION(1010, "Không được phép"),
    REFRESHTOKEN_EXPIRED(1012, "Refresh Token hết hạn, vui lòng đăng nhập lại"),

    ALL_EXCEPTION(1111, "Ngoại lệ bao quát");


    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
