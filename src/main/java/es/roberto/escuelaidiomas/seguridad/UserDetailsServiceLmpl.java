package es.roberto.escuelaidiomas.seguridad;

import es.roberto.escuelaidiomas.entidades.Usuario;
import es.roberto.escuelaidiomas.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceLmpl implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.buscarPorUsernameOEmail(s)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(usuario.getPerfiles().stream()
                        .map((perfil) -> new SimpleGrantedAuthority(perfil.getNombre()))
                        .collect(Collectors.toSet()))
                .disabled(false)
                .build();

    }

}

