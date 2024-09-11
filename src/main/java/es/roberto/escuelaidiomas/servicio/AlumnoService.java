package es.roberto.escuelaidiomas.servicio;

import es.roberto.escuelaidiomas.entidades.Alumno;
import es.roberto.escuelaidiomas.repositorios.AlumnoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.text.Normalizer;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlumnoService {
            //He usado TreeSet para que ordene los alumnos por id ya que Set no tiene orden.
    //private Set<Alumno> repositorioAlumnos = new TreeSet<>(Comparator.comparing(Alumno::getId));

    private final AlumnoRepository repositorio;

    public List<Alumno> findAll() {
        return repositorio.findAll();
    }

    public Alumno save(Alumno m) {
        return repositorio.save(m);
    }

    public void saveAll(List<Alumno> lista) {
        repositorio.saveAll(lista);
    }

    public Optional<Alumno>findById(Long id){
        return repositorio.findById(id);
    }

    /*Cambiar el filtro para que busque por nombre*/
    public List<Alumno> findByNombre(String filtro) {
        return repositorio.findByNombreContainsIgnoreCase(unaccent(filtro));
    }


    public Page<Alumno> findAllPaginated(Pageable pageable) {
        return repositorio.findAll(pageable);
    }


    public Alumno edit(Alumno al) {
        log.info("Editando alumno {}", al);
        for (Alumno alumnoEncontrado : repositorio.findAll()) {
            if (Objects.equals(alumnoEncontrado.getId(), al.getId())) {
                repositorio.delete(alumnoEncontrado);
                repositorio.save(al);
                log.info("Alumno editado {}", al);
                return al;
            }
        }
        repositorio.save(al);
        return al;
    }

    public void delete(Alumno alumno) {
        repositorio.delete(alumno);
    }

    /*Quita los acentos a un string.*/
    public static String unaccent(String src) {
        return Normalizer
                .normalize(src, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}


