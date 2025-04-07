package groupx.hw4.chordfinder.model;

public class ChordFinderForm {
    private String notes;
    private String method;
    
    public ChordFinderForm() {
        this.method = "Method1"; // Default method
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    @Override
    public String toString() {
        return "ChordFinderForm [notes=" + notes + ", method=" + method + "]";
    }
}