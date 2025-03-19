package com.fn.ai.common.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserContext {

    private String userId;
    private String userName;
    private String userRole;
}
