package com.init.user_management.exception;

public enum ErrorCode {
    USER_EXISTED(4000, "User Existed!"),
    INVALID_KEY(4002, "Invalid message key"),
    UNCATEGORIZED_EXCEPTION(5000, "Uncategorized error!"),
    PASSWORD_INVALID(4001, "Password must be at least 8 characters"),
    USER_NOT_EXISTED(4004, "User not existed!"),
    UNAUTHENTICATED(4006, "Unauthenticated")
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
