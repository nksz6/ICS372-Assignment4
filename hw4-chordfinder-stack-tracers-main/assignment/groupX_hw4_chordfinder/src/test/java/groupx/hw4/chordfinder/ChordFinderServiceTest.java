package groupx.hw4.chordfinder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import groupx.hw4.chordfinder.service.ChordFinderService;

public class ChordFinderServiceTest {
    
    private ChordFinderService service;
    
    @BeforeEach
    public void setup() {
        service = new ChordFinderService();
        // Explicitly set to Method2 for all tests
        service.setMethod("Method2");
    }

    @Test
    public void testFindChords_C() {
        Set<String> results = service.findChords(List.of("C", "E", "G"));
        assertTrue(results.contains("C maj"), "Should identify C major chord");
    }

    @Test
    public void testFindChords_Cmaj7() {
        Set<String> results = service.findChords(List.of("C", "E", "G", "B"));
        assertTrue(results.contains("C maj7"), "Should identify C maj7 chord");
    }

    @Test
    public void testFindChords_C7() {
        Set<String> results = service.findChords(List.of("C", "E", "G", "Bb"));
        assertTrue(results.contains("C 7"), "Should identify C7 chord");
    }

    @Test
    public void testFindChords_Caug() {
        Set<String> results = service.findChords(List.of("C", "E", "G#"));
        assertTrue(results.contains("C aug"), "Should identify C augmented chord");
    }

    @Test
    public void testFindChords_Cminb6() {
        Set<String> results = service.findChords(List.of("C", "Eb", "G", "Ab"));
        assertTrue(results.contains("C minb6"), "Should identify C minor flat 6 chord");
    }
    
    // Tests for expanded note range (8-12 notes)
    
    @Test
    public void testFindChords_7Notes() {
        // C13 chord with added 9th - C E G Bb D F A
        Set<String> result = service.findChords(List.of("C", "E", "G", "Bb", "D", "F", "A"));
        assertTrue(result.contains("C 7"), "Should identify C7 as a subset chord");
    }
    
    @Test
    public void testFindChords_10Notes() {
        // C major with many extensions - C E G B D F# A
        Set<String> result = service.findChords(List.of("C", "E", "G", "B", "D", "F#", "A", "Bb", "D#", "G#"));
        assertTrue(result.contains("C maj7"), "Should identify C maj7 as a subset chord");
    }
    
    @Test
    public void testFindChords_12Notes() {
        // All 12 chromatic notes
        Set<String> result = service.findChords(List.of("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));
        // Should find at least some chords (since every possible chord is a subset of this)
        assertFalse(result.isEmpty(), "Should identify at least some chords");
    }
    
    @Test
    public void testFindChords_ComplexJazzChord() {
        // Cmaj9#11 chord - C E G B D F#
        Set<String> result = service.findChords(List.of("C", "E", "G", "B", "D", "F#"));
        assertTrue(result.contains("C maj7"), "Should identify C maj7 as a subset chord");
    }
}