package com.example.construction.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Locale;

public class RequestHeaderParser {
    public static <JwtAuthenticationToken> boolean verifyUserName(String usernameInput){
        JwtAuthenticationToken jwtAuthenticationToken= (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String username = jwtAuthenticationToken.getClass().getName().toLowerCase(Locale.ROOT);
        if (Util.isNullOrEmpty(username))
            return false;
        return username.equalsIgnoreCase(usernameInput);
    }
}
