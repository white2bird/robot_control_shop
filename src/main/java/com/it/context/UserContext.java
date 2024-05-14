package com.it.context;

/**
 * 
 * @date 2024/4/18 17:38
 */
public class UserContext {

    private static ThreadLocal<Long> userIdLocal = new ThreadLocal<>();



    public static void setUserId(Long userId){
        userIdLocal.set(userId);
    }

    public static Long getUserId(){
        return userIdLocal.get();
    }

    public static void clear(){
        userIdLocal.remove();
    }
}
