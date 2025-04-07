package groupx.hw4.chordfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import groupx.hw4.chordfinder.model.ChordFinderForm;
import groupx.hw4.chordfinder.service.ChordFinderService;
import groupx.hw4.chordfinder.service.TestingService;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class ChordFinderController {

    @Autowired
    private ChordFinderService chordFinderService;
    
    @Autowired
    private TestingService testingService;
    
    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("chordFinderForm", new ChordFinderForm());
        return "index";
    }
    
    @PostMapping("/findChords")
    public String findChords(@ModelAttribute ChordFinderForm chordFinderForm, Model model) {
        try {
            // Set the analysis method
            chordFinderService.setMethod(chordFinderForm.getMethod());
            
            // Process notes
            List<String> processedNotes = chordFinderService.processNotes(chordFinderForm.getNotes());
            
            // Find chords
            Set<String> chords = chordFinderService.findChords(processedNotes);
            
            // Add to model
            model.addAttribute("notes", String.join(", ", processedNotes));
            model.addAttribute("chords", chords);
            model.addAttribute("chordFinderForm", chordFinderForm);
            
            return "result";
        } catch (IllegalArgumentException e) {
            // Handle validation errors
            model.addAttribute("error", e.getMessage());
            model.addAttribute("chordFinderForm", chordFinderForm);
            return "index";
        }
    }
    
    @GetMapping("/test")
    public String testService(Model model) {
        String message = testingService.getTestMessage();
        model.addAttribute("message", message);
        model.addAttribute("chordFinderForm", new ChordFinderForm());
        return "result";
    }
    
    // Optional REST API (for Extra Credit)
    @RequestMapping("/api/chords")
    @ResponseBody
    public Map<String, Object> findChordsApi(@RequestBody Map<String, Object> request) {
        try {
            String method = (String) request.getOrDefault("method", "Method1");
            String notes = (String) request.get("notes");
            
            chordFinderService.setMethod(method);
            List<String> processedNotes = chordFinderService.processNotes(notes);
            Set<String> chords = chordFinderService.findChords(processedNotes);
            
            Map<String, Object> response = Map.of(
                "notes", processedNotes,
                "chords", chords,
                "method", method
            );
            
            return response;
        } catch (Exception e) {
            return Map.of(
                "error", e.getMessage()
            );
        }
    }
}