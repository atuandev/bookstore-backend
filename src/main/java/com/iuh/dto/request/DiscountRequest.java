package com.iuh.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class DiscountRequest {

    @Size(min = 1, message = "INVALID_DISCOUNT_NAME")
    String name;

    @Pattern(regexp = "^[A-Z0-9]+$", message = "INVALID_DISCOUNT_CODE")
    String code;

    @Min(value = 0, message = "INVALID_DISCOUNT_PERCENT")
    Integer percent;

    LocalDate startDate;

    LocalDate endDate;
}
