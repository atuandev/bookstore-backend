package com.iuh.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    UNCATEGORIZED(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid key message", HttpStatus.BAD_REQUEST),
    USER_EXISTS(1002, "User already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1003, "User not found", HttpStatus.NOT_FOUND),
    INVALID_USERNAME(1004, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1005, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    ROLE_NOT_FOUND(1008, "Role not found", HttpStatus.NOT_FOUND),
    INVALID_PHONE(1009, "Phone number must be 10 characters", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(1010, "Invalid email", HttpStatus.BAD_REQUEST),
    INVALID_NAME(1011, "Name must not be null", HttpStatus.BAD_REQUEST),
    INVALID_ADDRESS(1012, "Address must not be null", HttpStatus.BAD_REQUEST),
    INVALID_USER_ID(1013, "User id must not be null", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_FORMAT(1014, "Phone number must be in the format 0xxxxxxxxx", HttpStatus.BAD_REQUEST),
    USER_ADDRESS_NOT_FOUND(1015, "User address not found", HttpStatus.NOT_FOUND),
    ;

    int code;
    String message;
    HttpStatus statusCode;
}
