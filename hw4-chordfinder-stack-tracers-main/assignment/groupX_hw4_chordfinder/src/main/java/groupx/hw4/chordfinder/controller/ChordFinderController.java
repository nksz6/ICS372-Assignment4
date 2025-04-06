package groupx.hw4.chordfinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import groupx.hw4.chordfinder.model.ChordFinderForm;
import groupx.hw4.chordfinder.model.Note;
import groupx.hw4.chordfinder.service.ChordFinderService;
import groupx.hw4.chordfinder.service.TestingService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ChordFinderController {

    private final ChordFinderService chordFinderService;
    private final TestingService testingService;

    public ChordFinderController(ChordFinderService chordFinderService, TestingService testingService) {
        this.chordFinderService = chordFinderService;
        this.testingService = testingService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("chordFinderForm", new ChordFinderForm());
        return "index";
    }
    
    @PostMapping("/findChords")
    public String findChords(@ModelAttribute ChordFinderForm form, Model model) {
        // Process the input
        String notesInput = form.getNotes();
        
        try {
            // Parse notes from input (comma or space separated)
            List<String> noteList = parseNotes(notesInput);
            
            // Validate the number of notes - updated to allow up to 12 notes
            if (noteList.size() < 3 || noteList.size() > 12) {
                model.addAttribute("error", "Please enter between 3 and 12 valid notes.");
                model.addAttribute("chordFinderForm", form);
                return "index";
            }
            
            // Validate each note
            for (String note : noteList) {
                if (!Note.isValid(note)) {
                    model.addAttribute("error", "Invalid note: " + note);
                    model.addAttribute("chordFinderForm", form);
                    return "index";
                }
            }
            
            // Set the method choice from the form
            String methodChoice = form.getMethod();
            chordFinderService.setMethod(methodChoice);
            
            // Find matching chords
            Set<String> chords = chordFinderService.findChords(noteList);
            
            if (chords.isEmpty()) {
                model.addAttribute("message", "No recognized chords found for these notes.");
            } else {
                model.addAttribute("chords", chords);
                model.addAttribute("notes", String.join(", ", noteList));
            }
            
            // Keep the form data
            model.addAttribute("chordFinderForm", form);
            
            return "result";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error processing notes: " + e.getMessage());
            model.addAttribute("chordFinderForm", form);
            return "index";
        }
    }

    @GetMapping("/test")
    public String test(Model model) {
        String message = testingService.getTestMessage();
        model.addAttribute("message", message);
        return "result";
    }
    
    /**
     * Parses a string of notes into a list
     * Accepts comma or space separated values
     */
    private List<String> parseNotes(String notesInput) {
        if (notesInput == null || notesInput.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        // Split by comma or space
        String[] noteArray = notesInput.trim().split("[,\\s]+");
        
        // Convert to list and trim each note
        return Arrays.stream(noteArray)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}