package es.roberto.escuelaidiomas.servicio;

import es.roberto.escuelaidiomas.entidades.Usuario;
import es.roberto.escuelaidiomas.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository repositorio;

    private final PasswordEncoder passwordEncoder;

    public Usuario save(Usuario u) {
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return repositorio.save(u);
    }

    public List<Usuario> saveAll (List<Usuario> lista) { return repositorio.saveAll(lista); }

    public Usuario findById(long id) {
        return repositorio.findById(id).orElse(null);
    }

    public Usuario buscarPorUsernameOEmail(String s) {
        return repositorio.buscarPorUsernameOEmail(s).orElse(null);
    }

    public Usuario findByUsernameOrEmail(String username, String email) {
        return repositorio.findByUsernameOrEmail(username,email).orElse(null);
    }

    public Usuario findByUsername(String username) {
        return repositorio.findByUsername(username).orElse(null);
    }

}
