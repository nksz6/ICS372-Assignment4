//NICK KELLEY ICS372
package groupx.hw4.chordfinder.service;

import org.springframework.stereotype.Service;	//Spring library for service layer
import org.springframework.core.io.ClassPathResource; //Spring library for loading resources from classpath
import com.fasterxml.jackson.databind.ObjectMapper; //Jackson library for JSON parsing of the rules for method 1 and 2.

import java.io.IOException; //for handling IO exceptions
import java.io.InputStream; //for reading the JSON files
import java.util.*; //just import all the collections classes

@Service
public class ChordFinderService {
    //Note values will be an HashMap of String to Integer but valid notes will be a List of Strings
    private static final Map<String, Integer> NOTE_VALUES = new HashMap<>();
    private static final List<String> VALID_NOTES = Arrays.asList(
        "C", "C#", "Db", "D", "D#", "Eb", "E", "F", "F#", 
        "Gb", "G", "G#", "Ab", "A", "A#", "Bb", "B", 
        "Cb", "B#", "Fb", "E#"
    );
    
    //Storing chord rules for both methods in a HashMap
    private Map<String, List<Integer>> chordRulesMethod1 = new HashMap<>();
    private Map<String, List<Integer>> chordRulesMethod2 = new HashMap<>();
    
    //Current method being used(default is Method1)
    private String currentMethod = "Method1";
    
    static {
        NOTE_VALUES.put("C", 0);
        NOTE_VALUES.put("C#", 1);
        NOTE_VALUES.put("Db", 1);
        NOTE_VALUES.put("D", 2);
        NOTE_VALUES.put("D#", 3);
        NOTE_VALUES.put("Eb", 3);
        NOTE_VALUES.put("E", 4);
        NOTE_VALUES.put("F", 5);
        NOTE_VALUES.put("F#", 6);
        NOTE_VALUES.put("Gb", 6);
        NOTE_VALUES.put("G", 7);
        NOTE_VALUES.put("G#", 8);
        NOTE_VALUES.put("Ab", 8);
        NOTE_VALUES.put("A", 9);
        NOTE_VALUES.put("A#", 10);
        NOTE_VALUES.put("Bb", 10);
        NOTE_VALUES.put("B", 11);
        NOTE_VALUES.put("B#", 0);	//same as C - enharmonic
        NOTE_VALUES.put("Cb", 11);	// same as B - enharmonic
        NOTE_VALUES.put("E#", 5);	//same as F - enharmonic
        NOTE_VALUES.put("Fb", 4);	//same as E - enharmonic
    }
    
    public ChordFinderService() {	//Call to load the rules from JSON files
        loadChordRules();
    }
    
    //Setter for method
    public void setMethod(String method) {
        this.currentMethod = method;
    }
    
    //Getter for method
    public String getMethod() {
        return this.currentMethod;
    }
    
    //Load chord rules from JSON files
    @SuppressWarnings("unchecked")
    private void loadChordRules() {
    	//Object Mapper is from Jackson library, parses JSON into Java objects
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            //Loading Method 1 rules
        	//Spring 'ClassPathResource' will locates the method 1 JSON in resources
        	//Input stream is set to it so it reads it
        	//Then the JSON is read into a map
            InputStream is1 = new ClassPathResource("chord_rules_method1.json").getInputStream();
            chordRulesMethod1 = mapper.readValue(is1, Map.class);
            
            // Load Method 2 rules
            //same as above but for method 2
            InputStream is2 = new ClassPathResource("chord_rules_method2.json").getInputStream();
            chordRulesMethod2 = mapper.readValue(is2, Map.class);
            
            //Incase of any issues, initialize with defaults
        } catch (IOException e) {
            e.printStackTrace();
            //Initialize with default values if files can't be loaded
            initializeDefaultChords();
        }
        
        //If maps are for some reason empty, initialize with defaults
        if (chordRulesMethod1.isEmpty() || chordRulesMethod2.isEmpty()) {
            initializeDefaultChords();
        }
    }
    
    private void initializeDefaultChords() {
        // Method 1 (interval-based) defaults - like HW3
        chordRulesMethod1.put("maj", Arrays.asList(4, 3));
        chordRulesMethod1.put("min", Arrays.asList(3, 4));
        chordRulesMethod1.put("dim", Arrays.asList(3, 3));
        chordRulesMethod1.put("aug", Arrays.asList(4, 4));
        
        // Method 2 (root-based) defaults - like HW3 again
        chordRulesMethod2.put("maj", Arrays.asList(4, 7)); 
        chordRulesMethod2.put("min", Arrays.asList(3, 7));
        chordRulesMethod2.put("dim", Arrays.asList(3, 6));
        chordRulesMethod2.put("aug", Arrays.asList(4, 8));
    }
    
    //Make sure the notes are not null and trim.
    //check if they are valid notes as defined in note.java
    public boolean isValidNote(String note) {
        return note != null && VALID_NOTES.contains(note.trim());
    }
    
    //Process the notes.
    //Make sure input is not empty.
    public List<String> processNotes(String notesInput) {
        if (notesInput == null || notesInput.trim().isEmpty()) {
            throw new IllegalArgumentException("No notes provided");
        }
        
        //Split the notes incase of commas or spaces and add them to a ArrayList so size isn't fixed.
        String[] noteArray = notesInput.split("[,\\s]+");
        List<String> processedNotes = new ArrayList<>();
        
        //Iterate through each note in the ArrayList, trim it, and check if it's valid
        //If valid, add it to the processed notes list
        for (String note : noteArray) {
            String trimmedNote = note.trim();
            if (!trimmedNote.isEmpty()) {
                if (isValidNote(trimmedNote)) {
                    processedNotes.add(trimmedNote);
                } else {
                    throw new IllegalArgumentException("Invalid note: " + trimmedNote);
                }
            }
        }
        
        //Make sure atleast 3 notes were provided.
        if (processedNotes.size() < 3) {
            throw new IllegalArgumentException("At least 3 notes are required");
        }
        
        //Make sure no more than 12 notes provided.
        if (processedNotes.size() > 12) {
            throw new IllegalArgumentException("Maximum of 12 notes allowed");
        }
        
        //return the processed notes.
        return processedNotes;
    }
    
       //Find the chords.
    public Set<String> findChords(List<String> notes) {
        if (notes == null || notes.size() < 3) {
            return Collections.emptySet();	//return empty set incase erroneous input somehow got thru.
        }
        
        //Using a set for possible chords to avoid duplicates and maintain insertion order
        Set<String> possibleChords = new LinkedHashSet<>();
        
        //Try each note as a root since order don't matter.
        for (String root : notes) {
            if ("Method1".equals(currentMethod)) {
                identifyChordsByMethod1(root, notes, possibleChords);
            } else {
                identifyChordsByMethod2(root, notes, possibleChords);
            }
        }
        
        return possibleChords;
    }
   
     // Identifies chords using Method 1 (calculating intervals between adjacent notes)
     // Tried to implement this similar to HW3, with 'getChordThroughMethods
    private void identifyChordsByMethod1(String root, List<String> notes, Set<String> possibleChords) {
        //sortedNotes calls the method to sort the notes from the root.
        List<String> sortedNotes = sortNotesFromRoot(root, notes);
        
        if (sortedNotes.size() < 3) {
            return; //Make sure sorted notes is atleast 3 notes big.  
        }
        
        // Calculate intervals between adjacent notes
        List<Integer> intervalList = new ArrayList<>(); 											//making an Integer list to keep track of Intervals = 'intervalList'
        for (int i = 0; i < sortedNotes.size() - 1; i++) { 											//iterate through the sorted notes
            int interval = calculateInterval(sortedNotes.get(i), sortedNotes.get(i + 1));			//calculate the interval between each note and the next
            intervalList.add(interval);																//add them to intervalList
        }
        
        //check against the chord patterns in the JSON that was loaded w/ ChordRules.
        // for every pair in the JSON (KV PAIRS)
        for (Map.Entry<String, List<Integer>> entry : chordRulesMethod1.entrySet()) {
            String chordType = entry.getKey(); //chordType is the string key - so like 'maj7'
            List<Integer> pattern = entry.getValue(); //pattern is the list of integers - [4, 3, 4]
            
            //for each pattern, see if it has the same amount number of Integers first
            if (pattern.size() <= intervalList.size()) { //if pattern has less than or equals cause extended chords may exist.
                boolean matches = true; //set boolean to true.
                
                // Compare the pattern to our intervals
                for (int i = 0; i < pattern.size(); i++) {
                    if (!pattern.get(i).equals(intervalList.get(i))) {
                        matches = false; //it it doesn't equal it don't match.
                        break;
                    }
                }
                
                if (matches) {
                    possibleChords.add(root + " " + chordType); //if it do add the root plus chord type to possible chords.
                }
            }
        }
    }
    
    /**
     * Identifies chords using Method 2 (calculating intervals from the root)
     * This approach compares semitone distances from root to all other notes
     */
    private void identifyChordsByMethod2(String root, List<String> notes, Set<String> possibleChords) {
        // Calculate intervals from root to each note
        List<Integer> intervalsFromRoot = new ArrayList<>();
        
        for (String note : notes) {
            if (!note.equals(root)) {
                int interval = calculateInterval(root, note);
                if (!intervalsFromRoot.contains(interval)) {
                    intervalsFromRoot.add(interval);
                }
            }
        }
        
        // Sort the intervals
        Collections.sort(intervalsFromRoot);
        
        // Check each pattern in our rules
        for (Map.Entry<String, List<Integer>> entry : chordRulesMethod2.entrySet()) {
            String chordType = entry.getKey();
            List<Integer> pattern = entry.getValue();
            
            // All intervals in the pattern must be in our calculated intervals
            boolean matches = true;
            for (Integer interval : pattern) {
                if (!intervalsFromRoot.contains(interval)) {
                    matches = false;
                    break;
                }
            }
            
            if (matches) {
                possibleChords.add(root + " " + chordType);
            }
        }
    }
    
    
    
    //Called by method 1 chord identification, takes in a root and list of notes.
    private List<String> sortNotesFromRoot(String root, List<String> notes) {
        
    	//Making an ArrayList for sorted notes, adding the root first.
    	List<String> sorted = new ArrayList<>();
        sorted.add(root);
        
        //Copy the old list and remove the root since we already added it.
        List<String> otherNotes = new ArrayList<>(notes);
        otherNotes.remove(root);
        
        //sort by interval using a comparator to take each note and calculate the amount of semi-tones from the root with the calculateInterval method.
        //Does this in ascending order
        otherNotes.sort(Comparator.comparingInt(note -> calculateInterval(root, note)));
        
        //adding the sorted non-rotes to the list we made first containing the root.
        sorted.addAll(otherNotes);
        return sorted; //return that sorted list.
    }
    
    //method to calculate the interval between two nodes (semi-tones)
    private int calculateInterval(String note1, String note2) {
        Integer value1 = NOTE_VALUES.get(note1);
        Integer value2 = NOTE_VALUES.get(note2);
        
        //make sure they are valid notes
        if (value1 == null || value2 == null) {
            throw new IllegalArgumentException("Invalid note(s): " + note1 + ", " + note2);
        }
        
        //if second note is less than the first, wrap around by adding 12 and subtract the value of the first note.
        //Like C(0) to E(4) is 4, so thats a major 4, and E(4) to G(7) is 3, so thats a minor third.
        //The if statement is for cases where you go from say, G(7) to C(0), because that would be -7. So add 12 to C(0 + 12)
        //then subtract the first note value, (12 - 7 = 5) which is a perfect fourth.
        if (value2 < value1) {
            return value2 + 12 - value1;
        }
        return value2 - value1; //return the second value - the first to get the interval.
    }
    
    /**
     * For testing - gets the loaded chord patterns
     */
    public Map<String, List<Integer>> getChordRules(String method) {
        if ("Method1".equals(method)) {
            return chordRulesMethod1;
        } else {
            return chordRulesMethod2;
        }
    }
}