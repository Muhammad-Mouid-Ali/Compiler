import java.util.*;

class NFA {
    static int stateCounter = 0;
    Set<Integer> states;
    Map<Integer, Map<Character, Set<Integer>>> transitions;
    int startState;
    Set<Integer> acceptStates;

    NFA() {
        states = new HashSet<>();
        transitions = new HashMap<>();
        acceptStates = new HashSet<>();
    }

    static NFA basicNFA(char c) {
        NFA nfa = new NFA();
        int start = stateCounter++;
        int accept = stateCounter++;
        nfa.states.add(start);
        nfa.states.add(accept);
        nfa.startState = start;
        nfa.acceptStates.add(accept);
        nfa.addTransition(start, c, accept);
        return nfa;
    }

    static NFA union(NFA nfa1, NFA nfa2) {
        NFA nfa = new NFA();
        int start = stateCounter++;
        int accept = stateCounter++;
        nfa.startState = start;
        nfa.states.add(start);
        nfa.states.add(accept);
        nfa.acceptStates.add(accept);

        nfa.merge(nfa1);
        nfa.merge(nfa2);

        nfa.addTransition(start, 'ε', nfa1.startState);
        nfa.addTransition(start, 'ε', nfa2.startState);
        for (int state : nfa1.acceptStates) nfa.addTransition(state, 'ε', accept);
        for (int state : nfa2.acceptStates) nfa.addTransition(state, 'ε', accept);

        return nfa;
    }

    static NFA concat(NFA nfa1, NFA nfa2) {
        NFA nfa = new NFA();
        nfa.merge(nfa1);
        nfa.merge(nfa2);

        for (int state : nfa1.acceptStates) {
            nfa.addTransition(state, 'ε', nfa2.startState);
        }
        nfa.acceptStates = nfa2.acceptStates;
        nfa.startState = nfa1.startState;
        return nfa;
    }

    static NFA kleeneStar(NFA nfa1) {
        NFA nfa = new NFA();
        int start = stateCounter++;
        int accept = stateCounter++;
        nfa.states.add(start);
        nfa.states.add(accept);
        nfa.startState = start;
        nfa.acceptStates.add(accept);
        nfa.merge(nfa1);

        nfa.addTransition(start, 'ε', nfa1.startState);
        nfa.addTransition(start, 'ε', accept);
        for (int state : nfa1.acceptStates) {
            nfa.addTransition(state, 'ε', nfa1.startState);
            nfa.addTransition(state, 'ε', accept);
        }
        return nfa;
    }

    private void merge(NFA other) {
        states.addAll(other.states);
        acceptStates.addAll(other.acceptStates);
        transitions.putAll(other.transitions);
    }

    private void addTransition(int from, char c, int to) {
        transitions.computeIfAbsent(from, k -> new HashMap<>())
                   .computeIfAbsent(c, k -> new HashSet<>()).add(to);
    }

    public DFA toDFA() {
        return new DFA(this);
    }
}