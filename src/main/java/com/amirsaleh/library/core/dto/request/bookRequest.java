package com.amirsaleh.library.core.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class bookRequest {

    @NotBlank(message = "عنوان کتاب اجباری است")
    @Size(min = 2, max = 100, message = "عنوان باید بین ۲ تا ۱۰۰ کاراکتر باشد")
    @Schema(example = "book")
    private String title;

    @NotBlank(message = "نام نویسنده اجباری است")
    @Size(min = 2, max = 50, message = "نام نویسنده باید بین ۲ تا ۵۰ کاراکتر باشد")
    @Schema(example = "author")
    private String author;

    @NotBlank(message = "شابک اجباری است")
    @Pattern(
            regexp = "^(97(8|9))?\\d{9}(\\d|X)$",
            message = "فرمت شابک نامعتبر است. فرمت صحیح: ۱۳ رقم یا ۱۰ رقم با X"
    )
    @Schema(
            description = "شابک کتاب (فرمت ۱۳ رقمی با پیشوند 978/979 یا فرمت ۱۰ رقمی با X)",
            example = "9789640000000"
    )
    private String isbn;

    @NotNull(message = "تعداد کتاب اجباری است")
    @Min(value = 0, message = "تعداد نمی‌تواند منفی باشد")
    @Max(value = 1000, message = "تعداد نمی‌تواند بیش از ۱۰۰۰ باشد")
    @Schema(example = "3")
    private Integer quantity;
}