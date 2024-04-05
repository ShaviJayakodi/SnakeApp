package com.SnakeApp.enums;

public enum stakeHolderValues {
    ADMIN("10"),
    SNAKE_CATCHER("20"),
    USER("30");

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