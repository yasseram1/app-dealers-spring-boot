package com.app.appdealers.configuration.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.appdealers.entity.Usuario;
import com.app.appdealers.repository.UsuarioRepository;
import com.app.appdealers.services.jwt.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    private final RequestAttributeSecurityContextRepository repository = new RequestAttributeSecurityContextRepository();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.split(" ")[1];

        String username = jwtService.getUsernameFromToken(jwt);

        Usuario usuario = usuarioRepository.findByEmail(username).get();

        if (usuario != null) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
                    usuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);
            this.repository.saveContext(SecurityContextHolder.getContext(), request, response);
            
        }

        filterChain.doFilter(request, response);

    }

}
