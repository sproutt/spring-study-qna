package codesquad.net.slipp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EntranceController {

    @GetMapping("")
    public String getEntrance(){

        return "redirect:/questions";
    }
}
