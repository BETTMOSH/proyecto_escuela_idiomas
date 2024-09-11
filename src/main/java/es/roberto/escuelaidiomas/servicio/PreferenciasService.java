package es.roberto.escuelaidiomas.servicio;

import es.roberto.escuelaidiomas.entidades.Preferencias;
import es.roberto.escuelaidiomas.repositorios.PreferenciasRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PreferenciasService {
    private final PreferenciasRepository repositorio;

    public Optional<Preferencias> findUsuarioPreferencias() {
        return repositorio.findUsuarioPreferencias();
    }

    public Preferencias save(Preferencias preferencias) { return repositorio.save(preferencias); }

    @PreAuthorize("#preferencias.usuario.username == authentication.name")
    public Preferencias saveAuth(Preferencias preferencias) { return repositorio.save(preferencias); }

  @Transactional
  public void deleteAll() {repositorio.deleteAllInBatch();}
}
