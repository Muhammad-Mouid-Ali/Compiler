import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Testing LexicalAnalyzer
            System.out.println("--- Lexical Analysis ---");
            LexicalAnalyzer lexer = new LexicalAnalyzer("src\\source_code.txt");
            lexer.tokenize();
            lexer.getSymbolTable().display();

            // Testing ErrorHandler
            System.out.println("--- Error Handling ---");
            ErrorHandler errorHandler = new ErrorHandler(lexer);
            errorHandler.checkErrors();

            // Testing RegularExpression, NFA, and DFA
            System.out.println("--- Regular Expression to NFA and DFA ---");
            RegularExpression re = new RegularExpression("a+b");
            NFA nfa = re.toNFA();
            DFA dfa = nfa.toDFA();
            dfa.displayTransitionTable();

            System.out.println("--- Testing Complete ---");
        } catch (Exception e) {
            System.err.println("Error during testing: " + e.getMessage());
        }
    }
}

/*
Instructions:
1. Ensure that all provided class files (DFA.java, NFA.java, RegularExpression.java, ErrorHandler.java, LexicalAnalyzer.java, SymbolTable.java) are in the same directory or correctly imported in the package structure.
2. Create a test file named "source_code.txt" with code following your language specifications.
3. Compile all Java files using: javac *.java
4. Run the Main class using: java Main
*/
