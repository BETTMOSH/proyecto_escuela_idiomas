package es.roberto.escuelaidiomas.repositorios;

import es.roberto.escuelaidiomas.entidades.Subtipos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubtiposRepository extends JpaRepository<Subtipos,Long> {
    public Optional<Subtipos> findByNombre(String nombre);
}
