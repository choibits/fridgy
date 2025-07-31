package com.fridgy.app.interceptor;

import com.fridgy.app.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component // needs to be a component because it's not a service, controller, or repository (it's a custom interceptor)
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    // request intercepts the request, response intercepts the response, and Object is because it could be anything
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // get the token from the request header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) { // Bearer is a standardized portion of the header app still works without
            // when making the request, need to add Header of Authorization, authorization as key, and Value as Bearer + token
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Auth header");
            return false;
        }
        // otherwise, if the authHeader is there
        String token = authHeader.substring(7); // ignore the first seven characters (Bearer )
        // eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzUzODQzNjIxLCJleHAiOjE3NTM5MzAwMjF9.v5V-ShgL0pnwyNvUk3v6-VuaKIHAgeKWzOU2TnkHcJs

        // validate the token
        // if the token is invalid, return a 401 Unauthorized response
        if (!jwtService.isTokenValid(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
            return false;
        }
        // if the token is valid, allow the request to proceed

        //get the user id from the token to send back to any requests
        request.setAttribute("userId", jwtService.getUserId(token));

        return true;
    }
}
