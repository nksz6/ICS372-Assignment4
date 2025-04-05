
package edu.metrostate.ics372.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.metrostate.ics372.service.HelloService;

@Controller
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/greet")
    public String greet(@RequestParam String name, Model model) {
        model.addAttribute("message", helloService.greet(name));
        return "result";
    }
}
