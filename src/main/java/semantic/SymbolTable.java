package semantic;

import java.util.*;

public class SymbolTable {
    private final Deque<Map<String, String>> scopes = new ArrayDeque<>();

    public void enterScope() {
        scopes.push(new HashMap<>());
    }

    public void exitScope() {
        scopes.pop();
    }

    public boolean declare(String name, String type) {
        Map<String, String> current = scopes.peek();
        if (current.containsKey(name)) return false;
        current.put(name, type);
        return true;
    }

    public boolean isDeclared(String name) {
        for (Map<String, String> scope : scopes) {
            if (scope.containsKey(name)) {
                return true;
            }
        }
        return false;
    }

    public String getType(String name) {
        for (Map<String, String> scope : scopes) {
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return null;
    }
}
