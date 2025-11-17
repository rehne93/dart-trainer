package de.baernreuther.dart.general;


import de.baernreuther.dart.randomnumberfinish.RandomFinishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class GeneralController {

    private final RandomFinishService randomFinishService;

    @GetMapping("reset")
    public String reset(Principal principal) {
        this.randomFinishService.reset(principal.getName());
        return "redirect:index?reset=true";
    }
}
