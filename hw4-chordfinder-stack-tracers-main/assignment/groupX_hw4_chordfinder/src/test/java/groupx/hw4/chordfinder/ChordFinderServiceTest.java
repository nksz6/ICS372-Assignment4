package groupx.hw4.chordfinder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import groupx.hw4.chordfinder.service.ChordFinderService;

class ChordFinderServiceTest {
    
    private ChordFinderService service;
    
    @BeforeEach
    void setUp() {
        service = new ChordFinderService();
    }

    @Test
    @DisplayName("Should validate notes correctly")
    void testNoteValidation() {
        assertTrue(service.isValidNote("C"));
        assertTrue(service.isValidNote("Bb"));
        assertTrue(service.isValidNote("F#"));
        assertFalse(service.isValidNote("H"));
        assertFalse(service.isValidNote(""));
        assertFalse(service.isValidNote(null));
    }
    
    @Test
    @DisplayName("Should process note input correctly")
    void testProcessNotes() {
        List<String> expected = Arrays.asList("C", "E", "G");
        assertEquals(expected, service.processNotes("C E G"));
        assertEquals(expected, service.processNotes("C, E, G"));
        assertEquals(expected, service.processNotes(" C,E,G "));
        
        assertThrows(IllegalArgumentException.class, () -> service.processNotes(""));
        assertThrows(IllegalArgumentException.class, () -> service.processNotes("C E Z"));
        assertThrows(IllegalArgumentException.class, () -> service.processNotes("C E"));
    }
    
    @Test
    @DisplayName("Should find major triad with Method 1")
    void testFindChordsMethod1Major() {
        service.setMethod("Method1");
        Set<String> chords = service.findChords(Arrays.asList("C", "E", "G"));
        assertTrue(chords.contains("C maj"));
    }
    
    @Test
    @DisplayName("Should find minor triad with Method 1")
    void testFindChordsMethod1Minor() {
        service.setMethod("Method1");
        Set<String> chords = service.findChords(Arrays.asList("A", "C", "E"));
        assertTrue(chords.contains("A min"));
    }
    
    @Test
    @DisplayName("Should find diminished triad with Method 1")
    void testFindChordsMethod1Diminished() {
        service.setMethod("Method1");
        Set<String> chords = service.findChords(Arrays.asList("B", "D", "F"));
        assertTrue(chords.contains("B dim"));
    }
    
    @Test
    @DisplayName("Should find augmented triad with Method 1")
    void testFindChordsMethod1Augmented() {
        service.setMethod("Method1");
        Set<String> chords = service.findChords(Arrays.asList("C", "E", "G#"));
        assertTrue(chords.contains("C aug"));
    }
    
    @Test
    @DisplayName("Should find major triad with Method 2")
    void testFindChordsMethod2Major() {
        service.setMethod("Method2");
        Set<String> chords = service.findChords(Arrays.asList("C", "E", "G"));
        assertTrue(chords.contains("C maj"));
    }
    
    @Test
    @DisplayName("Should handle enharmonic equivalents")
    void testEnharmonicEquivalents() {
        service.setMethod("Method1");
        Set<String> chords = service.findChords(Arrays.asList("B#", "Fb", "G"));
        assertTrue(chords.contains("B# maj"));
    }
    
    @Test
    @DisplayName("Should find extended chords with Method 1")
    void testFindExtendedChordsMethod1() {
        service.setMethod("Method1");
        
        // If chord_rules_method1.json includes seventh chords
        Set<String> maj7Chords = service.findChords(Arrays.asList("C", "E", "G", "B"));
        assertTrue(maj7Chords.contains("C maj7") || maj7Chords.contains("C Maj7"));
        
        Set<String> dom7Chords = service.findChords(Arrays.asList("C", "E", "G", "Bb"));
        assertTrue(dom7Chords.contains("C 7"));
    }
    
    @Test
    @DisplayName("Should find extended chords with Method 2")
    void testFindExtendedChordsMethod2() {
        service.setMethod("Method2");
        
        // If chord_rules_method2.json includes seventh chords
        Set<String> maj7Chords = service.findChords(Arrays.asList("C", "E", "G", "B"));
        assertTrue(maj7Chords.contains("C maj7") || maj7Chords.contains("C Maj7"));
        
        Set<String> dom7Chords = service.findChords(Arrays.asList("C", "E", "G", "Bb"));
        assertTrue(dom7Chords.contains("C 7"));
    }
    
    @Test
    @DisplayName("Should handle large note collections (5+ notes)")
    void testLargeNoteCollections() {
        service.setMethod("Method2");
        
        // C9 chord (C, E, G, Bb, D)
        Set<String> chords = service.findChords(Arrays.asList("C", "E", "G", "Bb", "D"));
        assertFalse(chords.isEmpty());
        
        // Complex jazz chord
        Set<String> jazzChords = service.findChords(
            Arrays.asList("C", "E", "G", "B", "D", "F#"));
        assertFalse(jazzChords.isEmpty());
    }
}