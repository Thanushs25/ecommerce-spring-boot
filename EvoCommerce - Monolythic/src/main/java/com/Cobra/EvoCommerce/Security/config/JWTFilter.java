package com.Cobra.EvoCommerce.Security.config;

import com.Cobra.EvoCommerce.Exception.ErrorDetails;
import com.Cobra.EvoCommerce.Security.service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("customAdminDetailsService")
    private UserDetailsService adminDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String token = authHeader.substring(7);

        try {
            final String username = jwtService.extractUserName(token);
            final String role = jwtService.extractRole(token);

            Authentication authentication
                    = SecurityContextHolder.getContext().getAuthentication();

            if (username != null && authentication == null) {
                UserDetails userDetails = getUserDetails(request, role, username);
                    if (jwtService.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        //maintaining the session
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
            }
            filterChain.doFilter(request, response);
        }
        catch (ExpiredJwtException ex){
            writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token has expired", request);
        }
        catch (JwtException ex){
            writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid JWT Token", request);
        }
        catch (ResponseStatusException ex){
            writeErrorResponse(response, HttpStatus.UNAUTHORIZED, ex.getReason(), request);
        }
    }

    private UserDetails getUserDetails(HttpServletRequest request, String role, String username) {
        if(request.getRequestURI().contains("/admin") && !"ADMIN".equalsIgnoreCase(role)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Access denied: Admins only");
        }
        else if(request.getRequestURI().contains("/user") && !"USER".equalsIgnoreCase(role)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Access denied: Users only");
        }

        UserDetailsService selectedService = "ADMIN".equalsIgnoreCase(role) ?
                adminDetailsService : userDetailsService;

        return selectedService.loadUserByUsername(username);
    }

    private void writeErrorResponse(HttpServletResponse response, HttpStatus httpStatus,
                                    String message, HttpServletRequest request) throws IOException{
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                message,
                request.getRequestURI(),
                httpStatus.getReasonPhrase().replace(" ", "_")
        );

        response.setStatus(httpStatus.value());
        response.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String json = mapper.writeValueAsString(errorDetails);
        response.getWriter().write(json);
    }
}
