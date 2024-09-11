package es.roberto.escuelaidiomas.servicio;

import es.roberto.escuelaidiomas.entidades.TipoIdioma;
import es.roberto.escuelaidiomas.repositorios.TipoIdiomaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TipoIdiomaService {


    public final TipoIdiomaRepository repositorio;

    public List<TipoIdioma> findAll() {
        return repositorio.findAll();
    }

    public TipoIdioma save(TipoIdioma tipoIdioma) {
        repositorio.save(tipoIdioma);
        return tipoIdioma;
    }

    public void saveAll(List<TipoIdioma> lista) {
        repositorio.saveAll(lista);
    }

    public Optional<TipoIdioma>findById(Long id){
        return repositorio.findById(id);
    }



    public void delete(TipoIdioma tipoIdioma) {
        repositorio.delete(tipoIdioma);
    }
}
