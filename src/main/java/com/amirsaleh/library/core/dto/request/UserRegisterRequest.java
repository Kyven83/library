package com.amirsaleh.library.core.dto.request;

import com.amirsaleh.library.core.validation.nationalCode.ValidIranianNationalCode;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.examples.Example;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @Schema(
            example = "امیرحسین صالحزاده"
    )
    @NotBlank(message = "نام کامل اجباری است")
    @Size(min = 3, max = 100, message = "نام کامل باید بین ۳ تا ۱۰۰ کاراکتر باشد")
    @Pattern(
            regexp = "^[\\u0600-\\u06FF\\s]+$",
            message = "نام کامل باید فقط شامل حروف فارسی و فاصله باشد"
    )
    private String fullName;

    @Schema(
            example = "0123456789"
    )
    @NotBlank(message = "کد ملی اجباری است")
    @Pattern(
            regexp = "^(?!(\\d)\\1{9})\\d{10}$",
            message = "کد ملی باید ۱۰ رقم باشد و همه ارقام یکسان نباشند"
    )
    @ValidIranianNationalCode
    @Size(min = 10, max = 10, message = "کد ملی باید ۱۰ رقم باشد")
    private String nationalCode;

    @Schema(
            example = "09123456789"
    )
    @NotBlank(message = "شماره تلفن اجباری است")
    @Pattern(
            regexp = "^(0)?9\\d{9}$",
            message = "فرمت شماره تلفن نامعتبر است. فرمت صحیح: 09123456789"
    )
    private String phoneNumber;

    @Schema(
            example = "MyStrongP@ss123"
    )
    @NotBlank(message = "رمز عبور اجباری است")
    @Size(min = 8, max = 50, message = "رمز عبور باید بین ۸ تا ۵۰ کاراکتر باشد")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message = "رمز عبور باید حداقل شامل یک حرف بزرگ، یک حرف کوچک و یک عدد باشد"
    )
    private String password;

    @Schema(
            example = "MyStrongP@ss123"
    )
    @NotBlank(message = "تکرار رمز عبور اجباری است")
    private String confirmPassword;
}