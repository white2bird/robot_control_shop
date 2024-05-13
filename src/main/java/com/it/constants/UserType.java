package com.it.constants;



/**
 * @author 
 * @date 2024/4/13 22:26
 */
public enum UserType {
    USER(0),
    ADMIN(1);

    private int code;

    private UserType(int code){
        this.code=code;
    }

    public int getCode() {
        return code;
    }
}
