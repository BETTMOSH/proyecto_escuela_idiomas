package es.roberto.escuelaidiomas.repositorios;

import es.roberto.escuelaidiomas.entidades.TipoIdioma;
import es.roberto.escuelaidiomas.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TipoIdiomaRepository extends JpaRepository<TipoIdioma,Long> {


}
