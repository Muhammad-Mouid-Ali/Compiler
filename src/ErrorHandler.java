import java.util.List;

/**
 * ErrorHandler class identifies rule violations and shows
 * the line number where each error occurs.
 */
public class ErrorHandler {
    private final LexicalAnalyzer lexer;

    public ErrorHandler(LexicalAnalyzer lexer) {
        this.lexer = lexer;
    }

    /**
     * Checks for common errors based on language specifications.
     */
    public void checkErrors() {
        List<String> tokens = lexer.getTokens();
        int lineNumber = 1;

        for (String token : tokens) {
            if (isInvalidIdentifier(token)) {
                System.err.printf("Error at line %d: Invalid identifier '%s' (only lowercase letters allowed).%n", lineNumber, token);
            }
            if (isInvalidFloat(token)) {
                System.err.printf("Error at line %d: Invalid float '%s' (should have up to 5 decimal places).%n", lineNumber, token);
            }
            if (isMissingSemicolon(tokens, token)) {
                System.err.printf("Error at line %d: Missing semicolon after '%s'.%n", lineNumber, token);
            }
            lineNumber++;
        }
    }

    /**
     * Validates identifier based on lowercase-only rule.
     */
    private boolean isInvalidIdentifier(String token) {
        return token.matches("[A-Z]+.*");
    }

    /**
     * Checks for float validity with up to 5 decimal places.
     */
    private boolean isInvalidFloat(String token) {
        return token.matches("\\d+\\.\\d{6,}");
    }

    /**
     * Checks if a semicolon is missing after certain tokens.
     */
    private boolean isMissingSemicolon(List<String> tokens, String token) {
        int index = tokens.indexOf(token);
        if ((token.matches("whole|point|flag|letter|[a-z]+|true|false")) && index + 1 < tokens.size()) {
            return !tokens.get(index + 1).equals(";");
        }
        return false;
    }
}
