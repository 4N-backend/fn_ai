package com.fn.ai.user;

public enum UserRoleEnum {
    MASTER(Authority.MASTER),
    HUB_MANAGER(Authority.HUB_MANAGER),
    COMPANY_MANAGER(Authority.COMPANY_MANAGER),
    DELIVERY_MANAGER(Authority.DELIVERY_MANAGER);

    private final String authority;

    UserRoleEnum(String authority) { this.authority = authority; }

    public String getAuthority(){ return this.authority; }

    public static class Authority{
        public static final String MASTER = "ROLE_MASTER";
        public static final String HUB_MANAGER = "ROLE_HUB_MANAGER";
        public static final String COMPANY_MANAGER = "ROLE_COMPANY_MANAGER";
        public static final String DELIVERY_MANAGER= "ROLE_DELIVERY_MANAGER";
    }
}