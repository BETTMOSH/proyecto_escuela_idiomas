package es.roberto.escuelaidiomas.repositorios;

import es.roberto.escuelaidiomas.entidades.Alumno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlumnoRepository extends JpaRepository<Alumno,Long> {
    public List<Alumno> findByNombreContainsIgnoreCase(String filtro);
}
