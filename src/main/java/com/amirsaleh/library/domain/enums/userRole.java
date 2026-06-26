package com.amirsaleh.library.domain.enums;

public enum userRole {
    admin,
    member;

    public boolean hasAdminAccess() {
        return this == admin ;
    }

    public static userRole getDefault() {
        return member;
    }
}
