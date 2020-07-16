package au.com.jschneiderprojects.ark.Compiler;

import java.util.HashMap;

public class NamesRegister {
    private static HashMap<String, Variable> table;

    public void addEntry(Variable object) {
        table.put(object.name, object);
    }

    public static Variable getVar(String name) {
        return table.get(name);
    }
}
