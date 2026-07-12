package com.amirsaleh.library.core.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserLoginRequest {
    private String nationalCode;
    private String password;
}