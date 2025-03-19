package com.fn.ai.common.filter;

import com.fn.ai.common.context.UserContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class UserContextFilter extends OncePerRequestFilter {

    private static final String USER_ID = "X-User-Id";
    private static final String USER_NAME = "X-User-Name";
    private static final String USER_ROLE = "X-User-Role";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String requestUri = request.getRequestURI();

        if(requestUri.equals("/api/user/signup")){
            filterChain.doFilter(request,response);
        }

//        String userId = request.getHeader(USER_ID);
//        String userName = request.getHeader(USER_NAME);
//        String userRole = request.getHeader(USER_ROLE);
        /**
         * 작업 당시에는 로그인 기능이 되지 않아서 임시로..
         */
        String userId = UUID.randomUUID().toString();
        String userName = "크리스티아누호날두";
        String userRole = "MASTER";

        if (userId != null || userName != null || userRole != null) {
            UserContextHolder.setUserContext(userId, userName, userRole);
        }

        try{
            filterChain.doFilter(request, response);
        }finally {
            UserContextHolder.clear(); // 요청이 끝나면 제거
        }


    }
}
