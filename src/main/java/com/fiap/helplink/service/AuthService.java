package com.fiap.helplink.service;

import com.fiap.helplink.security.JwtTokenProvider;
import com.fiap.helplink.dto.AuthRequest;
import com.fiap.helplink.dto.AuthResponse;
import com.fiap.helplink.model.Usuario;
import com.fiap.helplink.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.BadCredentialsException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(AuthRequest request) {

        try {
            String email = request.getEmail().trim().toLowerCase();

            // 1) Buscar usuário pelo email
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new BadCredentialsException("Credenciais inválidas"));

            // 2) Validar senha com BCrypt
            boolean senhaValida = passwordEncoder.matches(
                    request.getSenha(),
                    usuario.getSenha()
            );

            if (!senhaValida) {
                throw new BadCredentialsException("Credenciais inválidas");
            }

            // 3) Gerar token usando SOMENTE o email
            String token = tokenProvider.generateTokenFromEmail(usuario.getEmail());

            // 4) Montar resposta
            AuthResponse response = new AuthResponse();
            response.setToken(token);
            response.setTipo("Bearer");
            response.setUsuarioId(usuario.getIdUsuario());
            response.setEmail(usuario.getEmail());
            response.setNome(usuario.getNome());
            response.setMensagem("Autenticação bem-sucedida");

            return response;

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credenciais inválidas");

        } catch (Exception e) {
            throw new RuntimeException("Erro interno no servidor: " + e.getMessage(), e);
        }
    }
}
