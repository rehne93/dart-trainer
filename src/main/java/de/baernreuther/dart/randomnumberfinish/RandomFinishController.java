package de.baernreuther.dart.randomnumberfinish;


import de.baernreuther.dart.randomnumberfinish.model.RandomNumberDto;
import de.baernreuther.dart.randomnumberfinish.model.RandomNumberFinishState;
import de.baernreuther.dart.utility.FinishGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RandomFinishController {

    private final RandomFinishService randomFinishService;
    private final DifficultyCalculator difficultyCalculator;
    private final GameConfiguration gameConfiguration;
    private final FinishGenerator finishGenerator;

    @GetMapping({"index", "/"})
    public String index(Model model, @RequestParam(value = "reset", defaultValue = "true") boolean reset, Principal principal) {
        RandomNumberFinishState state;
        if (reset) {
            state = randomFinishService.refreshCurrentState(principal.getName());
        } else {
            state = randomFinishService.getCurrentState(principal.getName());
        }
        model.addAttribute("state", state);
        model.addAttribute("gameconfig", this.gameConfiguration);
        model.addAttribute("difficulty", this.difficultyCalculator.getDifficulty(state.getCurrentNumber()));
        model.addAttribute("finish", this.finishGenerator.generateFinish(state.getCurrentNumber()));
        model.addAttribute("user", principal.getName());
        return "index";
    }

    @PostMapping("finish")
    public String finish(@ModelAttribute RandomNumberDto randomNumber, Principal principal) {
        randomFinishService.check(randomNumber, principal.getName());
        return "redirect:index" + "?reset=" + randomNumber.isCheck();
    }

    @PostMapping("undo")
    public String undo(Principal principal) {
        randomFinishService.undo(principal.getName());
        return "redirect:index" + "?reset=false";

    }

    @PostMapping("configure")
    @ResponseBody
    public void configure(@RequestBody ConfigDto configDto) {
        if(configDto.min < 2 || configDto.max > 170) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid min or max config");
        }

        this.gameConfiguration.setMax(configDto.max);
        this.gameConfiguration.setMin(configDto.min);
    }

    @GetMapping("reset")
    public String reset(Principal principal) {
        this.randomFinishService.reset(principal.getName());
        return "redirect:index?reset=true";
    }

    public record ConfigDto(int min, int max) {}

}