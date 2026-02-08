package de.baernreuther.dart.x01;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("x01")
@RequiredArgsConstructor
public class X01Controller {
    private final X01Service x01Service;
    @GetMapping
    public String x01(Model model, Principal principal) {
        var stat = x01Service.initializeOrGet(principal.getName());
        model.addAttribute("stat", stat);
        return "x01";
    }

    @PostMapping("/score")
    public String score(@RequestParam Integer score, Model model, Principal principal) {
        this.x01Service.score(principal.getName(), score);
        return "redirect:/x01";
    }

    @PostMapping("/reset")
    public String reset(Principal principal) {
        this.x01Service.reset(principal.getName());
        return "redirect:/x01";
    }
}
