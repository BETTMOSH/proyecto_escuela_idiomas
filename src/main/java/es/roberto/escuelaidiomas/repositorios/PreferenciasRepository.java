package es.roberto.escuelaidiomas.repositorios;


import es.roberto.escuelaidiomas.entidades.Preferencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PostAuthorize;

import java.util.Optional;

public interface PreferenciasRepository extends JpaRepository<Preferencias, Long> {

  @PostAuthorize("returnObject != null and returnObject.usuario.username == authentication.name")
  Optional<Preferencias> findById(Long id);

  @Query("select p from #{#entityName} p where p.usuario.username = ?#{authentication.name}")
  Optional<Preferencias> findUsuarioPreferencias();
}
