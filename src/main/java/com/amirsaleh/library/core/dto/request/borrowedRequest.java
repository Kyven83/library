package com.amirsaleh.library.core.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class borrowedRequest {

    UUID userId;
    @NotBlank(message="کتاب نمیتواند خالی باشد")
    @Min(value = 1, message = "حداقل یک مورد نیاز است")
    @Max(value = 3, message = "حداکثر 3 مورد میتواند انتخاب شود")
    @Schema(example = "uuid")
    List<UUID> bookId;
}
