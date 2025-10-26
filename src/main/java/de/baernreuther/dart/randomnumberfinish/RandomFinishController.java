package de.baernreuther.dart.randomnumberfinish;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RandomFinishController {

    private final RandomFinishService randomFinishService;

    @GetMapping("index")
    public String index(Model model, @RequestParam(value = "reset", defaultValue = "true") boolean reset, Principal principal) {
        if (reset) {
            model.addAttribute("state", randomFinishService.refreshCurrentState(2, 100, principal.getName()));
        } else {
            model.addAttribute("state", randomFinishService.getCurrentState(principal.getName()));
        }
        return "index";
    }

    @PostMapping("/finish")
    public String finish(@ModelAttribute RandomNumberDto randomNumber, Principal principal) {
        randomFinishService.check(randomNumber, principal.getName());
        return "redirect:index" + "?reset="+randomNumber.isCheck();
    }

}