package com.ducminh.blogapi.exception;

import com.ducminh.blogapi.constant.ErrorCode;

public class AppException extends RuntimeException {
    // thay vì để lớp global bắt runtimeexception mọi lúc, thì ta để nó bắt appexeption vì appexception có thể tùy biến theo trường hợp, để tùy biến dc thì phải cho constructor
    //của nó khởi tạo theo tuwgnf trường hợp=> lí do ta truyển ErrorCode và

    private ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());  // Truyền thông báo lỗi chi tiết vào super constructor
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
