package es.roberto.escuelaidiomas.servicio;

import es.roberto.escuelaidiomas.entidades.Subtipos;
import es.roberto.escuelaidiomas.repositorios.SubtiposRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SubtiposService {

    private final SubtiposRepository repositorio;

    public List<Subtipos> findAll() {
        return repositorio.findAll();
    }

    public Subtipos save(Subtipos s) {
        return repositorio.save(s);
    }

    public void saveAll(List<Subtipos> lista) {
        repositorio.saveAll(lista);
    }

    public Optional<Subtipos> findById(Long id){
        return repositorio.findById(id);
    }

    public Optional<Subtipos>findByNombre(String nombre) {
        return repositorio.findByNombre(nombre);
    }

    public void delete(Subtipos s) {
        repositorio.delete(s);
    }
}
