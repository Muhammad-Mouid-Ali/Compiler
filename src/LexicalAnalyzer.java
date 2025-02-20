import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * LexicalAnalyzer class reads the source code and breaks it
 * into meaningful tokens according to the language specification.
 */
public class LexicalAnalyzer {
    private String sourceCodePath;
    private List<String> tokens;
    private SymbolTable symbolTable;
    private static final String TOKEN_REGEX = "(?<DATATYPE>flag|whole|point|letter)"
            + "|(?<BOOLEAN>true|false)"
            + "|(?<IDENTIFIER>[a-z]+)"
            + "|(?<OPERATOR>[+\\-*/%^])"
            + "|(?<INTEGER>\\\\d+)"
            + "|(?<FLOAT>\\\\d+\\\\.\\\\d{1,5})"
            + "|(?<PRINT>print<<)"
            + "|(?<CHARACTER>'[a-z]')"
            + "|(?<SEMICOLON>;)"
            + "|(?<COMMENT>\\\\$\\\\$.*|##[\\\\s\\\\S]*?##)";

    public LexicalAnalyzer(String sourceCodePath) {
        this.sourceCodePath = sourceCodePath;
        this.tokens = new ArrayList<>();
        this.symbolTable = new SymbolTable();
    }

    /**
     * Reads and tokenizes the source code.
     */
    public void tokenize() {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceCodePath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                processLine(line, lineNumber);
            }
            System.out.println("Tokenization complete. Total tokens: " + tokens.size());
        } catch (IOException e) {
            System.err.println("Error reading source code: " + e.getMessage());
        }
    }

    private void processLine(String line, int lineNumber) {
        Pattern pattern = Pattern.compile(TOKEN_REGEX);
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            if (matcher.group("DATATYPE") != null) {
                symbolTable.addEntry(matcher.group("DATATYPE"), "datatype", lineNumber);
                tokens.add(matcher.group("DATATYPE"));
            } else if (matcher.group("BOOLEAN") != null) {
                tokens.add(matcher.group("BOOLEAN"));
            } else if (matcher.group("IDENTIFIER") != null) {
                symbolTable.addEntry(matcher.group("IDENTIFIER"), "identifier", lineNumber);
                tokens.add(matcher.group("IDENTIFIER"));
            } else if (matcher.group("OPERATOR") != null) {
                tokens.add(matcher.group("OPERATOR"));
            } else if (matcher.group("INTEGER") != null) {
                tokens.add(matcher.group("INTEGER"));
            } else if (matcher.group("FLOAT") != null) {
                tokens.add(matcher.group("FLOAT"));
            } else if (matcher.group("PRINT") != null) {
                tokens.add("print<<");
            } else if (matcher.group("CHARACTER") != null) {
                tokens.add(matcher.group("CHARACTER"));
            } else if (matcher.group("SEMICOLON") != null) {
                tokens.add(";");
            } else if (matcher.group("COMMENT") != null) {
                // Ignore comments
            }
        }
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public List<String> getTokens() {
        return tokens;
    }
}
