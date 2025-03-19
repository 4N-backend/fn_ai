package com.fn.ai.common.context;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserContextHolder {

    private static final ThreadLocal<UserContext> userHolder = new ThreadLocal<>();

    public static void setUserContext(String userId,String userName,String userRole){
        userHolder.set(new UserContext(userId,userName,userRole));
    }

    public static void clear(){
        userHolder.remove();
    }

    public static String getUserId(){
        return userHolder.get() != null ? userHolder.get().getUserId() : null;
    }

    public static String getUsername(){
        return userHolder.get() != null ? userHolder.get().getUserName() : null;
    }

    public static String getUserRole(){
        return userHolder.get() != null ? userHolder.get().getUserRole() : null;
    }

    public static UserContext getContext(){
        return userHolder.get();
    }

}
