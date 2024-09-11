package es.roberto.escuelaidiomas.servicio;

import es.roberto.escuelaidiomas.entidades.Perfil;
import es.roberto.escuelaidiomas.repositorios.PerfilRepositoty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PerfilService {
    private final PerfilRepositoty repositorio;

    public Perfil save(Perfil p) {
        return repositorio.save(p);
    }

    public Optional<Perfil> findByNombre(String nombre) { return repositorio.findByNombre(nombre);}

    public List<Perfil> findAll() {
        return repositorio.findAll();
    }

}
