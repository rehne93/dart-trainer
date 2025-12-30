package de.baernreuther.dart;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("status", statusCode);

            if (statusCode == 404) {
                model.addAttribute("message", "Seite nicht gefunden");
            } else if (statusCode == 403) {
                model.addAttribute("message", "Zugriff verweigert");
            } else {
                model.addAttribute("message", "Ein unerwarteter Fehler ist aufgetreten");
            }
        }

        return "error"; // error.html
    }
}