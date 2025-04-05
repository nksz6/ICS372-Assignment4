
package edu.metrostate.ics372.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public String greet(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Hello, World!";
        }
        return "Hello, " + name.trim() + "!";
    }
}
