package com.roomrentalmanagementbackend.filter;

import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.dto.user.response.UserInfoDTO;
import com.roomrentalmanagementbackend.enums.UserRole;
import com.roomrentalmanagementbackend.service.UserService;
import com.roomrentalmanagementbackend.utils.JWTUtils;
import com.roomrentalmanagementbackend.utils.MessageUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    JWTUtils jwtUtils;
    UserService userService;
    List<String> securedPaths;
    MessageUtils messageUtils;
    AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtUtils.resolveToken(request);

        if (token == null || !jwtUtils.verifyToken(token)) {
            errorResponse(request, response, "jwt.token.Invalid");
            return;
        }

        String username = jwtUtils.getUsernameFromextractClaims(token);

        if (username == null) {
            errorResponse(request, response, "jwt.token.Invalid");
        }

        UserInfoDTO user = userService.getUserInfoAfterAuthen(username);

        String roleName = UserRole.getUserRole(user.getRole());
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roleName.toUpperCase());

        var auth = new UsernamePasswordAuthenticationToken(user, null, List.of(authority));
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    private void errorResponse(HttpServletRequest request, HttpServletResponse response, String key) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        ApiResponse<Object> apiResponse = ApiResponse.error(HttpStatus.UNAUTHORIZED, messageUtils.getMessage(key));


        String json = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(apiResponse);
        response.getWriter().write(json);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return securedPaths.stream()
                .noneMatch(pattern -> pathMatcher.match(pattern, request.getServletPath()));
    }
}
