package groupx.hw4.chordfinder.model;

import java.util.Arrays;
import java.util.List;

public class Note {
	// Valid notes in standard notation
    private static final List<String> VALID_NOTES = Arrays.asList(
        "C", "C#", "Db", "D", "D#", "Eb", "E", "F", "F#", 
        "Gb", "G", "G#", "Ab", "A", "A#", "Bb", "B"
    );
    
    private String name;
    
    public Note(String name) {
        if (!isValid(name)) {
            throw new IllegalArgumentException("Invalid note: " + name);
        }
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    /**
     * Checks if a note name is valid
     */
    public static boolean isValid(String noteName) {
        return noteName != null && VALID_NOTES.contains(noteName.trim());
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Note note = (Note) obj;
        return name.equals(note.name);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
