package au.com.jschneiderprojects.ark.Executer;

import au.com.jschneiderprojects.ark.Compiler.Types.Boolean;
import au.com.jschneiderprojects.ark.Compiler.Types.Int;
import au.com.jschneiderprojects.ark.Compiler.Types.Number;
import au.com.jschneiderprojects.ark.Compiler.Types.Object;

public enum Operators {
    PLUS("+", 3, true, 2, Number.class),
    MINUS("-", 3, true, 2, Number.class),
    TIMES("*", 4, true, 2, Number.class),
    DIVIDE("/", 4, true, 2, Number.class),
    MODULO("%", 4, true, 2, Int.class),
    EXPONENT("**", 5, false, 2, Number.class),
    FACTORIAL("!", 5, false, 1, Int.class),
    AND("and", 2, true, 2, Boolean.class),
    OR("or", 2, true, 2, Boolean.class),
    NOT("not", 2, false, 1, Boolean.class),
    GREATER(">", 1, true, 2, Boolean.class),
    LESS("<", 1, true, 2, Value.class),
    FALLBACK("fallback", 4, true, 2, Value.class), // coalescing
    NOTNULL("notNull", 4, false, 1, Boolean.class),
    EQUALS("==", 1, true, 2, Boolean.class),
    TRUTHY("truthy", 1, true, 1, Boolean.class),
    FALSY("falsy", 1, true, 1, Boolean.class),
    NEW("new", 4, true, 1, Object.class);

    public String identifier;
    public int precedence;
    public boolean isLeftAssociative;
    public int operands;
    public Class<?> yield;

    Operators(String identifier, int precedence, boolean isLeftAssociative, int operands, Class<?> output) {
        this.identifier = identifier;
        this.precedence = precedence;
        this.isLeftAssociative = isLeftAssociative;
        this.operands = operands;
        this.yield = output;
    }

    public static Operators find(String identifier) {
        for (Operators o : Operators.values())
            if (o.identifier.equals(identifier))
                return o;
        return null;
    }

    public static boolean exists(String identifier) {
        return find(identifier) != null;
    }

    public static Integer getPrecedence(String identifier) {
        Operators o = find(identifier);

        if (o != null)
            return o.precedence;
        return null;
    }

    public static java.lang.Boolean isLeftAssociative(String identifier) {
        for (Operators o : Operators.values())
            if (o.identifier.equals(identifier))
                return o.isLeftAssociative;
        return null;
    }
}
