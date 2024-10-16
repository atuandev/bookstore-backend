package com.iuh.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserCreationRequest {

    @Size(min = 3, message = "INVALID_USERNAME")
    String username;

    @Size(min = 6, message = "INVALID_PASSWORD")
    String password;

    @NotNull(message = "INVALID_NAME")
    String name;

    @Pattern(regexp = "(\\w)+(.\\w+)*@(\\w+)+(.\\w+)*$", message = "INVALID_EMAIL")
    String email;
}
