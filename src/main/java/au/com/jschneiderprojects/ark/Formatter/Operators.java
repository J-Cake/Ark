package au.com.jschneiderprojects.ark.Formatter;

public class Operators {
    private static final String[] operators = new String[]{"+", "-", "*", "/", "%", "**", "!", "and", "or", "not", ">", "<", "=", "++", "--", "+=", "-=", "*=", "/=", "%=", "**=", "negate", "fallback", "notNull", "==", "truthy", "falsy", "new"};
    private static final int[] precedence = new int[]{3, 3, 4, 4, 4, 5, 5, 2, 2, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 1, 1, 1, 4};
    private static final boolean[] associativity = new boolean[]{true, true, true, true, true, false, false, true, true, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true}; // true is left associative

    public static boolean LEFT_ASSOCIATIVE = true;

    public static Integer getPrecedence(String op) {
        for (int a = 0; a < operators.length; a++)
            if (op.equals(operators[a]))
                return precedence[a];
            return null;
    }

    public static Boolean getAssociativity(String op) {
        for (int a = 0; a < operators.length; a++)
            if (op.equals(operators[a]))
                return associativity[a];
            return null;
    }

    public static boolean exists(String op) {
        for (String operator : operators)
            if (op.equals(operator))
                return true;
            return false;
    }
}
