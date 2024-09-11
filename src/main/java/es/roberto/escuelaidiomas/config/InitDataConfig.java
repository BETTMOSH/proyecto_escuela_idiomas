package es.roberto.escuelaidiomas.config;

import es.roberto.escuelaidiomas.entidades.*;
import es.roberto.escuelaidiomas.servicio.*;
import es.roberto.escuelaidiomas.storage.StorageService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class InitDataConfig {

        private final StorageService storageService;
        private final AlumnoService alumnoService;
        private final TipoIdiomaService tipoIdiomaService;
        private final SubtiposService subtiposService;
        private final UsuarioService usuarioService;
        private final PerfilService perfilService;


    @PersistenceContext
    private EntityManager em;

    @Transactional
    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initStorage();
        initUsuarios();
        initAlumno();
        initPreferencias();

    }

    public void initStorage() {
        storageService.deleteAll();
        storageService.init();
    }


    public void initUsuarios() {

        Perfil perfilUser = Perfil.builder().nombre("ROLE_USER").build();

        Perfil perfilAdmin = Perfil.builder().nombre("ROLE_ADMIN").build();

        Usuario usuario1 = Usuario.builder()
                .username(InitDatasets.USUARIO_USERNAME_USER)
                .email("user@alumno.es")
                .password("user")
                .perfiles(Set.of(perfilUser))
                .build();

        Usuario usuario2 = Usuario.builder()
                .username(InitDatasets.USUARIO_USERNAME_ADMIN)
                .email("admin@alumno.es")
                .password("admin")
                .perfiles(Set.of(perfilUser, perfilAdmin))
                .build();

        usuarioService.saveAll(Arrays.asList(usuario1, usuario2));

    }
    public void initPreferencias() {
        Preferencias prefs1 = Preferencias.builder()
                .darkMode(true)
                .idioma("en_US")
                .usuario(em.find(Usuario.class, InitDatasets.USUARIO_ID_1))
                .build();
        em.persist(prefs1);

        Preferencias prefs2 = Preferencias.builder()
                .darkMode(false)
                .idioma("es_ES")
                .usuario(em.find(Usuario.class, InitDatasets.USUARIO_ID_2))
                .build();
        em.persist(prefs2);

        // no se hace flush y aún así se graba en la base de datos
        // porque con @Transactional al terminar el método la sincronización ocurre automáticamente

    }

    public void initAlumno() {

        TipoIdioma aleman = TipoIdioma.builder().nombre("ALEMAN").build();
        tipoIdiomaService.save(aleman);

        TipoIdioma ingles = TipoIdioma.builder().nombre("INGLES").build();
        tipoIdiomaService.save(ingles);

        TipoIdioma frances = TipoIdioma.builder().nombre("FRANCES").build();
        tipoIdiomaService.save(frances);

        TipoIdioma español = TipoIdioma.builder().nombre("ESPAÑOL").build();
        tipoIdiomaService.save(español);

        TipoIdioma italiano = TipoIdioma.builder().nombre("ITALIANO").build();
        tipoIdiomaService.save(italiano);

        //tipoIdiomaService.saveAll(Arrays.asList(aleman, ingles, frances, español));

        List<Subtipos> alemanTipo = Arrays.asList(
                Subtipos.builder().nombre("Baviera").tipo(aleman).build(),
                Subtipos.builder().nombre("Sajonia").tipo(aleman).build(),
                Subtipos.builder().nombre("Hesse").tipo(aleman).build()
        );
        subtiposService.saveAll(alemanTipo);

        List<Subtipos> inglesTipo = Arrays.asList(
                Subtipos.builder().nombre("Britanico").tipo(ingles).build(),
                Subtipos.builder().nombre("Americano").tipo(ingles).build(),
                Subtipos.builder().nombre("Australiano").tipo(ingles).build()
        );
        subtiposService.saveAll(inglesTipo);

        List<Subtipos> francesTipo = Arrays.asList(
                Subtipos.builder().nombre("Normando").tipo(frances).build(),
                Subtipos.builder().nombre("Borgoñon").tipo(frances).build(),
                Subtipos.builder().nombre("Provenzal").tipo(frances).build()
        );
        subtiposService.saveAll(francesTipo);

        List<Subtipos> españolTipo = Arrays.asList(
                Subtipos.builder().nombre("Castellano").tipo(español).build(),
                Subtipos.builder().nombre("Gallego").tipo(español).build(),
                Subtipos.builder().nombre("Catalan").tipo(español).build()
        );
        subtiposService.saveAll(españolTipo);

        List<Subtipos> italianoTipo = Arrays.asList(
                Subtipos.builder().nombre("Toscano").tipo(italiano).build(),
                Subtipos.builder().nombre("Sardo").tipo(italiano).build(),
                Subtipos.builder().nombre("Siciliano").tipo(italiano).build()
        );
        subtiposService.saveAll(italianoTipo);


        alumnoService.saveAll(
                Arrays.asList(
                        Alumno.builder()
                                .nombre("Roberto")
                                .apellidos("Garcia")
                                .email("abc@dfg.com")
                                .tipoIdioma(aleman)
                                .subtipo(subtiposService.findByNombre("Baviera").orElse(null))
                                .fechaNacimiento(LocalDate.of(1999, 1, 11))
                                .presencial(true)
                                .foto("/image/roberto.jpg") //TODO: Se ha añadido una foto por defecto
                                .build(),

                        Alumno.builder()
                                .nombre("Gabriel")
                                .apellidos("Moran")
                                .email("jjaaasss@hotmail.com")
                                .tipoIdioma(ingles)
                                .subtipo(subtiposService.findByNombre("Britanico").orElse(null))
                                .fechaNacimiento(LocalDate.of(2000, 2, 12))
                                .presencial(true)
                                .foto("/image/gabriel.jpg") //TODO: Se ha añadido una foto por defecto
                                .build(),

                        Alumno.builder()
                                .nombre("Marlene")
                                .apellidos("Pilares")
                                .email("mpilare@gmail.com")
                                .tipoIdioma(frances)
                                .subtipo(subtiposService.findByNombre("Normando").orElse(null))
                                .fechaNacimiento(LocalDate.of(2009, 9, 1))
                                .presencial(true)
                                .foto("/image/marlene.jpg") //TODO: Se ha añadido una foto por defecto
                                .build(),

                        Alumno.builder()
                                .nombre("Pilar")
                                .apellidos("Salinas")
                                .email("salinaspilar@gmail.com")
                                .tipoIdioma(italiano)
                                .subtipo(subtiposService.findByNombre("Toscano").orElse(null))
                                .fechaNacimiento(LocalDate.of(2000, 12, 12))
                                .presencial(false)
                                .foto("/image/pilar.jpg") //TODO: Se ha añadido una foto por defecto
                                .build()
                ));

    }

}

