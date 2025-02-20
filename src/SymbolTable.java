import java.util.*;

/**
 * SymbolTable class tracks all variables, functions, and identifiers
 * with details like names, types, and line numbers for error checking
 * and scope management.
 */
public class SymbolTable {
    private static class Entry {
        String name;
        String type;
        int lineNumber;

        Entry(String name, String type, int lineNumber) {
            this.name = name;
            this.type = type;
            this.lineNumber = lineNumber;
        }

        @Override
        public String toString() {
            return String.format("Name: %s, Type: %s, Line: %d", name, type, lineNumber);
        }
    }

    private final List<Entry> entries;

    public SymbolTable() {
        this.entries = new ArrayList<>();
    }

    /**
     * Adds a new entry to the symbol table.
     *
     * @param name       Name of the variable or identifier
     * @param type       Type of the variable or identifier
     * @param lineNumber Line number where it appears
     */
    public void addEntry(String name, String type, int lineNumber) {
        if (!contains(name)) {
            entries.add(new Entry(name, type, lineNumber));
        }
    }

    /**
     * Checks if an entry with the given name exists.
     *
     * @param name Name to check
     * @return true if exists, false otherwise
     */
    public boolean contains(String name) {
        return entries.stream().anyMatch(entry -> entry.name.equals(name));
    }

    /**
     * Displays all entries in the symbol table.
     */
    public void display() {
        System.out.println("Symbol Table:");
        entries.forEach(System.out::println);
    }

    /**
     * Retrieves an entry by name.
     *
     * @param name Name of the entry
     * @return Entry object or null if not found
     */
    public Entry getEntry(String name) {
        return entries.stream().filter(e -> e.name.equals(name)).findFirst().orElse(null);
    }
}