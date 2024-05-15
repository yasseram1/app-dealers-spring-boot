package com.app.appdealers.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.app.appdealers.dto.AuthRequest;
import com.app.appdealers.dto.AuthResponse;
import com.app.appdealers.dto.RegisterRequest;
import com.app.appdealers.dto.RegisterResponse;
import com.app.appdealers.entity.Rol;
import com.app.appdealers.entity.Usuario;
import com.app.appdealers.repository.RolRepository;
import com.app.appdealers.repository.UsuarioRepository;
import com.app.appdealers.services.jwt.JwtService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public ResponseEntity<RegisterResponse> registrarUsuario(RegisterRequest registerRequest, BindingResult br) {
        try {

            Rol rol = rolRepository.findById(1).get(); // Dealer
            
            if(usuarioRepository.existsByEmail(registerRequest.getEmail())) {
                RegisterResponse responseErrors = new RegisterResponse("Error al crear usuario", List.of("El email ya está en uso"));
                return new ResponseEntity<RegisterResponse>(responseErrors, HttpStatus.BAD_REQUEST);
            }

            Usuario nuevoUsuario = Usuario
                    .builder()
                    .nombres(registerRequest.getNombres())
                    .apellidos(registerRequest.getApellidos())
                    .telefono(registerRequest.getTelefono())
                    .dni(registerRequest.getDni())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .rol(rol)
                    .build();

            usuarioRepository.save(nuevoUsuario);

            RegisterResponse responseSuccess = new RegisterResponse("Usuario creado", null);
            return new ResponseEntity<RegisterResponse>(responseSuccess, HttpStatus.CREATED);
        } catch (Exception e) {
            RegisterResponse responseErrors = new RegisterResponse("Error al crear el usuario", List.of("Error del servidor"));
            return new ResponseEntity<RegisterResponse>(responseErrors, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<AuthResponse> login(AuthRequest authRequest) {
        if(!usuarioRepository.existsByEmail(authRequest.getEmail())) {
            AuthResponse authReponse = new AuthResponse(null, "No se encontro usuario con ese email", true);
            return new ResponseEntity<AuthResponse>(authReponse, HttpStatus.NOT_FOUND);
        }

        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
            authenticationManager.authenticate(authToken);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<AuthResponse>(new AuthResponse(null, "Correo y/o contraseña incorrecta.", true),HttpStatus.UNAUTHORIZED);
        }

        Usuario usuario = usuarioRepository.findByEmail(authRequest.getEmail()).get();
        String jwt = jwtService.generateToken(usuario, generateExtraClaims(usuario));
        return new ResponseEntity<AuthResponse>(new AuthResponse(jwt, "Token JWT recibido", false), HttpStatus.OK);
    }

    private Map<String, Object> generateExtraClaims(Usuario usuario) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", usuario.getRol().getDescripcion());
        extraClaims.put("permissions", usuario.getAuthorities());

        return extraClaims;
    }

}
