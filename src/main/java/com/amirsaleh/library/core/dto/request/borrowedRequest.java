package com.amirsaleh.library.core.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class borrowedRequest {

    UUID userId;
    UUID bookId;
}
