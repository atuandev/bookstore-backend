package com.iuh.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

//Thong nhat so 1 la thong bao thanh cong, 2 so tiep theo la loai bi loi va so cuoi la loi cu the
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
    INVALID_BOOK_TITLE(1016, "Title must not be null", HttpStatus.BAD_REQUEST),
    INVALID_BOOK_DESCRIPTION(1017, "Description must not be null", HttpStatus.BAD_REQUEST),
    INVALID_BOOK_AUTHOR(1018, "Author must not be null", HttpStatus.BAD_REQUEST),
    INVALID_BOOK_SIZE(1019, "Size must be in the format WxH", HttpStatus.BAD_REQUEST),
    INVALID_BOOK_PAGES(1020, "Pages must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_BOOK_WEIGHT(1021, "Weight must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_BOOK_PUBLISH_YEAR(1022, "Publish year must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_BOOK_IMPORT_PRICE(1023, "Import price must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_BOOK_PRICE(1024, "Price must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_BOOK_STOCK(1025, "Stock must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_BOOK_SLUG(1026, "Slug must not be null", HttpStatus.BAD_REQUEST),
    INVALID_BOOK_SOLD(1027, "Sold must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_BOOK_CATEGORY(1028, "Category must not be null", HttpStatus.BAD_REQUEST),
    INVALID_DISCOUNT_NAME(1029, "Name must not be null", HttpStatus.BAD_REQUEST),
    INVALID_DISCOUNT_CODE(1030, "Code must be in the format A-Z,0-9", HttpStatus.BAD_REQUEST),
    INVALID_DISCOUNT_PERCENT(1031, "Percent must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_BOOK_IMAGE_URL(1032, "Url must not be null", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(1033, "Category not found", HttpStatus.NOT_FOUND),
    PUBLISHER_NOT_FOUND(1034, "Publisher not found", HttpStatus.NOT_FOUND),
    BOOK_NOT_FOUND(1035, "Book not found", HttpStatus.NOT_FOUND),
    DISCOUNT_NOT_FOUND(1036, "Discount not found", HttpStatus.NOT_FOUND),
    REVIEW_NOT_FOUND(1400, "Review not found", HttpStatus.NOT_FOUND),
    
    BOOK_EXISTS(1037, "Book already exists", HttpStatus.BAD_REQUEST),
    DISCOUNT_CODE_EXISTED(1038, "Discount code already exists", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_EXISTED(1039, "Category name already exists", HttpStatus.BAD_REQUEST),
    CATEGORY_SLUG_EXISTED(1040, "Category slug already exists", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND(1041, "Order not found", HttpStatus.NOT_FOUND),
    ;

    int code;
    String message;
    HttpStatus statusCode;
}
