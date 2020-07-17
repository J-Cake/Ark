package au.com.jschneiderprojects.ark.Compiler;

public enum PrimitiveType {
    ADDRESS, // position to jump to
    POINTER, // reference to another ValueType
    LITERAL, // literal value
    OPERATOR, // an operator
    ADDRESS_OR_LITERAL, // either an address (function) or a literal
    OTHER, // a meta-container referring to information that assists the VM rather than being a literal value. Such as a datatype
}
