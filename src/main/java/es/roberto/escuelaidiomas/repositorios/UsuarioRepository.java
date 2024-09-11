package es.roberto.escuelaidiomas.repositorios;

import es.roberto.escuelaidiomas.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsernameOrEmail(String username, String email);

    Optional<Usuario> findByUsername(String username);

    @Query("select u from Usuario u " +
            "where lower(u.username) = ?1 or lower(u.email) = ?1")
    Optional<Usuario> buscarPorUsernameOEmail(String s);

    @Query("select u from Usuario u " +
            "where lower(u.username) = :cadena or lower(u.email) = :cadena")
    Optional<Usuario> buscarPorUsernameOEmail_args_por_nombre(String cadena);
}
