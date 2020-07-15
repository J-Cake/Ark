package au.com.jschneiderprojects.ark.Compiler;

public enum PrimitiveVar {
    EVALUATED, // The resulting value after an evaluated expression
    RETURNED, // The value of a return expression
    LOCATION, // The current execution point
    REFERENCE // The value of a variable lookup
}
