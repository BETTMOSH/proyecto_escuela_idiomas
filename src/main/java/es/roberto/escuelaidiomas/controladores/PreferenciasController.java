package es.roberto.escuelaidiomas.controladores;

import es.roberto.escuelaidiomas.entidades.Preferencias;
import es.roberto.escuelaidiomas.entidades.Usuario;
import es.roberto.escuelaidiomas.servicio.PreferenciasService;
import es.roberto.escuelaidiomas.servicio.UsuarioService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Controller
@Data
@Slf4j
@RequestMapping("/prefs")
public class PreferenciasController {

  private final PreferenciasService preferenciasService;
  private final UsuarioService usuarioService;

  @GetMapping("/edit")
  public String editPreferenciasForm(Model model) {

    Optional<Preferencias> preferencias = preferenciasService.findUsuarioPreferencias();
    if (preferencias.isPresent())
      model.addAttribute("preferenciasDto", preferencias);
    else model.addAttribute("preferenciasDto", new Preferencias());

    model.addAttribute("opcionesIdioma", Map.of("es_ES", "Español", "en_US", "Inglés"));
    return "preferencias/form";
  }

  @PostMapping("/edit")
  public String editarPreferenciasSubmit(@ModelAttribute("preferenciasDto") Preferencias preferencias,
                                         Principal principal) {
    log.info("Preferencias {}", preferencias);
    Usuario usuario;
    if (preferencias.getId() == null) {
      // obtener el usuario autenticado
      usuario = usuarioService.findByUsername(principal.getName());
    } else {
      usuario = usuarioService.findById(preferencias.getId());
    }

    preferencias.setUsuario(usuario);
    preferenciasService.saveAuth(preferencias);
    return "redirect:/alumnado/list?lang=" + preferencias.getIdioma();
  }

  @GetMapping(value = "/list", produces = "application/json")
  public ResponseEntity<Object> listaPreferencias() {
    Preferencias prefs = preferenciasService.findUsuarioPreferencias().orElse(null);
    return ResponseEntity.ok().body(Map.of(
      "darkMode", prefs.isDarkMode(),
      "idioma", prefs.getIdioma()));
  }


}
