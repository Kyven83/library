package com.amirsaleh.library.core.validation.nationalCode;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IranianNationalCodeValidator implements ConstraintValidator<ValidIranianNationalCode, String> {

    @Override
    public boolean isValid(String nationalCode, ConstraintValidatorContext context) {
        // 1. بررسی ورودی (نال نباشد)
        if (nationalCode == null) {
            return false;
        }

        // 2. حذف فاصله‌های اضافی (Trim)
        nationalCode = nationalCode.trim();

        // 3. بررسی دقیقاً ۱۰ رقم بودن
        if (!nationalCode.matches("\\d{10}")) {
            return false;
        }

        // 4. بررسی اینکه همه ارقام یکسان نباشند
        if (nationalCode.matches("(\\d)\\1{9}")) {
            return false;
        }

        // 5. بررسی کدهای ملی خاص که در الگوریتم مشکل دارند
        // برخی کدها مانند 0123456789 در الگوریتم قبول می‌شوند اما معتبر نیستند
        if (nationalCode.equals("0123456789") ||
                nationalCode.equals("1234567890") ||
                nationalCode.equals("9876543210")) {
            return false;
        }

        // 6. اجرای الگوریتم اصلی کد ملی
        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                int digit = Character.getNumericValue(nationalCode.charAt(i));
                sum += digit * (10 - i); // وزن‌ها: 10, 9, 8, ..., 2
            }

            int remainder = sum % 11;
            int controlDigit = Character.getNumericValue(nationalCode.charAt(9));

            // بررسی رقم کنترل‌کننده
            if (remainder < 2) {
                return controlDigit == remainder;
            } else {
                return controlDigit == (11 - remainder);
            }

        } catch (NumberFormatException e) {
            return false;
        }
    }
}