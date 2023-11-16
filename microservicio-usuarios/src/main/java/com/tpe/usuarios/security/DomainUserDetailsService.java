package com.tpe.usuarios.security;

import com.tpe.usuarios.models.Usuario;
import com.tpe.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .map(this::createSpringSecurityUser).orElseThrow(() -> new UsernameNotFoundException("El usuario con el email "+ email + " no existe."));
    }

    private User createSpringSecurityUser(Usuario u){
       String authority = u.getRol().getNombre();
       List<GrantedAuthority> grantedAuthority = Collections.singletonList(new SimpleGrantedAuthority(authority));

       return new User(u.getEmail(), u.getPassword(), grantedAuthority);
    }

}
