package org.example.crudpruebafabi.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.crudpruebafabi.agregates.request.SignInRequest;
import org.example.crudpruebafabi.agregates.request.SignUpRequest;
import org.example.crudpruebafabi.agregates.response.AuthenticationResponse;
import org.example.crudpruebafabi.model.Rol;
import org.example.crudpruebafabi.model.Role;
import org.example.crudpruebafabi.model.Usuario;
import org.example.crudpruebafabi.repository.RolRepository;
import org.example.crudpruebafabi.repository.UsuarioRepository;
import org.example.crudpruebafabi.service.AuthenticationService;
import org.example.crudpruebafabi.service.UsuarioService;
import org.example.crudpruebafabi.util.JwtUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioService usuarioService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario signUpAdmin(SignUpRequest signUpRequest) {
        String username = signUpRequest.usuario();
        String email = signUpRequest.email();
        String password = signUpRequest.clave();
        Set<Rol> roles = new HashSet<>();
        Rol userRol = rolRepository.findByNombreRol(Role.ADMIN).orElseGet(() -> rolRepository.save(new Rol(null, Role.ADMIN)));
        roles.add(userRol);

        Usuario user = Usuario.builder()
                .usuario(username)
                .email(email)
                .password(new BCryptPasswordEncoder().encode(password))
                .roles(roles)
                .activo(true)
                .build();

        Usuario userCreated = usuarioRepository.save(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated,password, userCreated.getAuthorities());
        return userCreated;
    }

    @Override
    public Usuario signUpUser(SignUpRequest signUpRequest) {
        String username = signUpRequest.usuario();
        String email = signUpRequest.email();
        String password = signUpRequest.clave();
        Set<Rol> roles = new HashSet<>();
        Rol userRol = rolRepository.findByNombreRol(Role.USER).orElseGet(() -> rolRepository.save(new Rol(null, Role.USER)));
        roles.add(userRol);

        Usuario user = Usuario.builder()
                .usuario(username)
                .email(email)
                .password(new BCryptPasswordEncoder().encode(password))
                .roles(roles)
                .activo(true)
                .build();

        Usuario userCreated = usuarioRepository.save(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated,password, userCreated.getAuthorities());
        return userCreated;
    }

    @Override
    public AuthenticationResponse signin(SignInRequest signInRequest) {
        String email = signInRequest.email();
        String password = signInRequest.clave();

        Authentication authentication = this.authenticate(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(email,"User loged successfully",accessToken,true);
        return authenticationResponse;
    }

    private Authentication authenticate(String email, String password){
        UserDetails userDetails = usuarioService.userDetailsService().loadUserByUsername(email);
        if (userDetails == null){
            throw new BadCredentialsException("Usuario o contraseña incorrectos");
        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Usuario o contraseña incorrectos");
        }
        return new UsernamePasswordAuthenticationToken(email,userDetails.getPassword(),userDetails.getAuthorities());
    }
}
