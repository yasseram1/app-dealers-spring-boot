package com.app.appdealers.util;

import com.app.appdealers.entity.Usuario;
import com.app.appdealers.repository.UsuarioRepository;
import com.app.appdealers.services.jwt.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    @Autowired
    private static UsuarioRepository usuarioRepository;

    @Autowired
    private static JwtService jwtService;

    public static Usuario getUsuarioFromRequest(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization").split(" ")[1];
        return usuarioRepository.findById(Integer.parseInt(jwtService.getClaim(jwt, Claims::getId))).orElseThrow();
    }

}
