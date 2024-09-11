package es.roberto.escuelaidiomas.controladores;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModoController {

    @GetMapping("/modo")
    public String mostrarModo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Boolean modoOscuro = (Boolean) session.getAttribute("modoOscuro");
        session.setAttribute("modoOscuro", modoOscuro == null || !modoOscuro);
        return "redirect:/index";
    }
}
