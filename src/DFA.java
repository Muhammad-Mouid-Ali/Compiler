import java.util.*;


class DFA {
    Map<Set<Integer>, Map<Character, Set<Integer>>> dfaTransitions;
    Set<Integer> dfaStartState;
    Set<Set<Integer>> dfaAcceptStates;

    DFA(NFA nfa) {
        dfaTransitions = new HashMap<>();
        dfaAcceptStates = new HashSet<>();
        subsetConstruction(nfa);
    }

    private void subsetConstruction(NFA nfa) {
        Queue<Set<Integer>> queue = new LinkedList<>();
        Set<Integer> startSet = epsilonClosure(Set.of(nfa.startState), nfa);
        queue.add(startSet);
        dfaStartState = startSet;
        while (!queue.isEmpty()) {
            Set<Integer> current = queue.poll();
            for (char c = 'a'; c <= 'z'; c++) {
                Set<Integer> next = move(current, c, nfa);
                if (!next.isEmpty()) {
                    dfaTransitions.computeIfAbsent(current, k -> new HashMap<>()).put(c, next);
                    if (!dfaTransitions.containsKey(next)) queue.add(next);
                    if (containsAccept(next, nfa)) dfaAcceptStates.add(next);
                }
            }
        }
    }

    private boolean containsAccept(Set<Integer> states, NFA nfa) {
        for (int state : states) {
            if (nfa.acceptStates.contains(state)) return true;
        }
        return false;
    }

    private Set<Integer> move(Set<Integer> states, char c, NFA nfa) {
        Set<Integer> next = new HashSet<>();
        for (int state : states) {
            Map<Character, Set<Integer>> trans = nfa.transitions.getOrDefault(state, new HashMap<>());
            if (trans.containsKey(c)) next.addAll(trans.get(c));
        }
        return epsilonClosure(next, nfa);
    }

    private Set<Integer> epsilonClosure(Set<Integer> states, NFA nfa) {
        Stack<Integer> stack = new Stack<>();
        Set<Integer> closure = new HashSet<>(states);
        closure.forEach(stack::push);
        while (!stack.isEmpty()) {
            int state = stack.pop();
            Map<Character, Set<Integer>> trans = nfa.transitions.getOrDefault(state, new HashMap<>());
            if (trans.containsKey('ε')) {
                for (int next : trans.get('ε')) {
                    if (!closure.contains(next)) {
                        closure.add(next);
                        stack.push(next);
                    }
                }
            }
        }
        return closure;
    }

    public void displayTransitionTable() {
        System.out.println("DFA Transition State Table:");
        for (Map.Entry<Set<Integer>, Map<Character, Set<Integer>>> entry : dfaTransitions.entrySet()) {
            System.out.println("State: " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}
