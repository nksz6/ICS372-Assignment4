
package groupx.hw4.chordfinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import groupx.hw4.chordfinder.service.TestingService;

@Controller
public class ChordFinderController {

    private final TestingService testingService;

    public ChordFinderController(TestingService greetingService) {
        this.testingService = greetingService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/test")
    public String greet(Model model) {
        String message = testingService.getTestMessage();
        model.addAttribute("message", message);
        return "result";
    }
}
