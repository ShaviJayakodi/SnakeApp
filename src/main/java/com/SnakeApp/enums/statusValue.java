package com.SnakeApp.enums;


public enum statusValue {
    DEACTIVE (0),
    ACTIVE(1),
    PENDING(2),
    COMPLETE (3);

    private int sts;


    statusValue(int sts)
    {
        this.sts=sts;

    }
    public int sts()
    {
        return sts;
    }
}
