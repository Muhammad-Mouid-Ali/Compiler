import java.util.*;
//import java.util.regex.*;

/**
 * RegularExpression class implements REGEX parsing
 * and NFA construction using Thompson's algorithm for
 * the custom language defined.
 */
public class RegularExpression {
    private String regex;

    public RegularExpression(String regex) {
        this.regex = preprocessRegex(regex);
    }

    private String preprocessRegex(String regex) {
        regex = regex.replace(" ", ""); // Remove spaces
        return regex;
    }

    
    public NFA toNFA() {
        Stack<NFA> stack = new Stack<>();
        for (char c : regex.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                stack.push(NFA.basicNFA(c));
            } else if (c == '+') {
                NFA nfa2 = stack.pop();
                NFA nfa1 = stack.pop();
                stack.push(NFA.union(nfa1, nfa2));
            } else if (c == '*') {
                NFA nfa = stack.pop();
                stack.push(NFA.kleeneStar(nfa));
            } else if (c == '.') {
                NFA nfa2 = stack.pop();
                NFA nfa1 = stack.pop();
                stack.push(NFA.concat(nfa1, nfa2));
            }
        }
        return stack.pop();
    }
}
