package es.roberto.escuelaidiomas.repositorios;

import es.roberto.escuelaidiomas.entidades.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepositoty extends JpaRepository<Perfil, Long> {
    public Optional<Perfil> findByNombre(String nombre);
}
