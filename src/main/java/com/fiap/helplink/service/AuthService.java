package com.fiap.helplink.service;

import com.fiap.helplink.config.JwtTokenProvider;
import com.fiap.helplink.dto.AuthRequest;
import com.fiap.helplink.dto.AuthResponse;
import com.fiap.helplink.model.Usuario;
import com.fiap.helplink.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(AuthRequest request) {
        try {
            String email = request.getEmail().trim().toLowerCase();

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, request.getSenha())
            );

            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new BadCredentialsException("Usuário não encontrado"));

            String token = tokenProvider.generateToken(authentication);

            return AuthResponse.builder()
                    .token(token)
                    .tipo("Bearer")
                    .usuarioId(usuario.getIdUsuario())
                    .email(usuario.getEmail())
                    .nome(usuario.getNome())
                    .mensagem("Autenticação bem-sucedida")
                    .build();

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credenciais inválidas");
        } catch (Exception e) {
            throw new RuntimeException("Erro interno no servidor: " + e.getMessage(), e);
        }
    }

    public boolean validarToken(String token) {
        return tokenProvider.validateToken(token);
    }

    public String extrairEmail(String token) {
        return tokenProvider.getEmailFromToken(token);
    }
}
