package es.roberto.escuelaidiomas.controladores;

import es.roberto.escuelaidiomas.entidades.Alumno;
import es.roberto.escuelaidiomas.entidades.TipoIdioma;
import es.roberto.escuelaidiomas.servicio.AlumnoService;
import es.roberto.escuelaidiomas.servicio.I18nServices;
import es.roberto.escuelaidiomas.servicio.TipoIdiomaService;
import es.roberto.escuelaidiomas.storage.StorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class EscuelaController {

    private final AlumnoService alumnoService;
    private final I18nServices servicioInternacionalizacion;
    private final StorageService servicioAlmacenamiento;
    private final TipoIdiomaService tipoIdiomaService;


    @ModelAttribute("listaTipos")
    public List<TipoIdioma> listaTipos() {
        return tipoIdiomaService.findAll();
    }



    @GetMapping("/index")
    public String paginaPrincipal(Model model){
        log.info("Mostrando pagina de inicio");
        model.addAttribute("alumnosList", alumnoService.findAll());
        return "index";
    }
    @GetMapping("/listado")
    public String mostrarAlumno(Model model, Pageable pageable) {
        log.info("Mostrando listado de alumnos");
        model.addAttribute("alumnosList", alumnoService.findAllPaginated(pageable));
        return "alumnado/listado";
    }

    //Cambiar el filtro para que busque por nombre y apellidos
    @GetMapping(value = "/listado/filter")
    public String mostrarAlumnoFiltrado(@RequestParam("nombre") String nombre, Model model) {
        log.info("filtrando por nombre");
        model.addAttribute("alumnosList", alumnoService.findByNombre(nombre));
        return "fragmentos/listaAlumnos::listasAlumnos";
    }

    @GetMapping("/contacto")
    public String mostrarContacto(Model model){
        log.info("Mostrando pagina de contacto");
        model.addAttribute("message", "Escribe tu mensaje aquí");
        return "usuario/contacto";
    }

    @PostMapping("/contacto/submit")
    public String solicitud (@ModelAttribute("message") String message, Model model){
        log.info("Mostrando pagina de solicitud");
        model.addAttribute("confirmationMessage", "¡Gracias por tu mensaje! Nos pondremos en contacto contigo lo antes posible.");
        // Redirige a la vista "gracias"
        return "redirect:/gracias";
    }

    @GetMapping("/gracias")
    public String mostrarGracias(Model model){
        log.info("Mostrando pagina de gracias");
        return "usuario/gracias";
    }

    @GetMapping("/galeria")
    public String mostrarGaleria(Model model){
        log.info("Mostrando pagina de galeria");
        return "usuario/galeria";
    }


    @GetMapping("/subtipos")
    public String listaSubtipos(@RequestParam("tipo") Long tipoId, Model model) {
        log.info("Mostrando lista de subtipos");
        Optional<TipoIdioma> tipo = tipoIdiomaService.findById(tipoId);
        model.addAttribute("listaSubtipos", tipo.orElseThrow().getSubtipos());
        return "fragmentos/listaSubtipos::listaSubtipos";
    }


    @GetMapping("/nuevo")
    public String nuevoAlumno(Model model){
        log.info("Estoy en nuevoAlumno");
        model.addAttribute("alumnoDtos", new Alumno());
        model.addAttribute("modoEdicion", false);

        return "alumnado/formulario";
    }

    @PostMapping("/nuevo/submit")
    public String validarFormulario(@RequestParam(value = "fichero", required = false) MultipartFile fichero,
                                    @Valid @ModelAttribute("alumnoDtos") Alumno alumnoNuevo,
                                    BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            log.info("Hay errores en el formulario");
            model.addAttribute("modoEdicion", false);
            bindingResult.getFieldErrors()
                    .forEach(e -> log.info("field: " + e.getField() + ", rejected value: " + e.getRejectedValue()));
            return "alumnado/formulario";

        } else {
            log.info("Alumno guardado: {}", alumnoNuevo);
            alumnoService.save(alumnoNuevo);
            // Verifica si el ID del alumno es null
            if (alumnoNuevo.getId() == null) {
                log.error("El ID del alumno es null");
                bindingResult.rejectValue("id", "error.alumno", "El ID del alumno no puede ser null");
                return "alumnado/formulario";
            }
            if (!fichero.isEmpty()){
                log.info("hay foto");
                String fotoFilename = servicioAlmacenamiento.store(fichero, alumnoNuevo.getId());
                alumnoNuevo.setFoto(MvcUriComponentsBuilder.fromMethodName(EscuelaController.class,
                        "serveFile", fotoFilename).build().toUriString());
                log.info("uri de la foto del Alumno {}", alumnoNuevo.getFoto());

                alumnoService.save(alumnoNuevo);
            }
            log.info("Alumno guardado: {}", alumnoNuevo);
            alumnoService.save(alumnoNuevo);
            return "redirect:/listado";
        }
    }

    // Metodo para mostrar la foto del alumno
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        log.info("Estoy en serveFile");
        Resource file = servicioAlmacenamiento.loadAsResource(filename);
        return ResponseEntity.ok().body(file);
    }

    @GetMapping("/editar/{id}")
    public String editarAlumnoForm(@PathVariable long id, Model model){
        log.info("Estoy en editarAlumnoForm");
        Optional<Alumno> alumno = alumnoService.findById(id);
        if(alumno.isPresent()){
            model.addAttribute("alumnoDtos", alumno.get());
            model.addAttribute("modoEdicion", true);
            return "alumnado/formulario";
        } else {

            return "redirect:/alumnado/listado";
        }
    }

    @PostMapping("/editar/submit")
    public String editarAlumnoSubmit(@RequestParam(value = "fichero", required = false) MultipartFile fichero,
                                     @Valid @ModelAttribute("alumnoDtos") Alumno alumno, BindingResult bindingResult, Model model){
        log.info("Estoy en editarAlumnoSubmit");
        if (bindingResult.hasErrors()) {
            model.addAttribute("modoEdicion", true);
            //model.addAttribute("alumnoDto", alumno);
            return "/alumnado/formulario";
        }
        if (!fichero.isEmpty()) {
            log.info("hay foto");
            String fotoFilename = servicioAlmacenamiento.store(fichero, alumno.getId());
            alumno.setFoto(MvcUriComponentsBuilder
                    .fromMethodName(EscuelaController.class, "serveFile", fotoFilename).build().toUriString());
        }
        alumnoService.save(alumno);
        return "redirect:/listado";
    }


    @GetMapping("/borrar/{id}")
    public String borrarAlumno(@PathVariable("id") Long id){
        Optional<Alumno> alumno = alumnoService.findById(id);
        alumno.ifPresent(alumnoService::delete);
        return "redirect:/listado";
    }

    @GetMapping("/borrar/show/{id}")
    public String showModalBorrarAlumno(@PathVariable("id") Long id, Model model) {
        Optional<Alumno> alumno = alumnoService.findById(id);
        String deleteMessage;
        if (alumno.isPresent())
        deleteMessage = servicioInternacionalizacion.getMessage("alumno.borrar.mensaje",
                new Object[]{alumno.get().getNombre()});
        else
            return "redirect:/?error=true";

        model.addAttribute("delete_url", "/borrar/" + id);
        model.addAttribute("delete_title", servicioInternacionalizacion.getMessage("alumno.borrar.titulo")
        );

        model.addAttribute("delete_message", deleteMessage);
        return "fragmentos/borradomodal::modal-borrar";
    }
}
