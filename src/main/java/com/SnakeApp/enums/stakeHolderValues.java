package com.SnakeApp.enums;

public enum stakeHolderValues {
    ADMIN("10"),
    CONSULTANT("20"),
    SEEKER("30");

    private String code;
    stakeHolderValues (String code)
    {
        this.code=code;
    }
    public String code()
    {
        return code;
    }
}