package au.com.jschneiderprojects.ark.Compiler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum InstructionType {
    EVALUATE(Arrays.asList(PrimitiveType.LITERAL, PrimitiveType.LITERAL, PrimitiveType.OPERATOR)), // performs `operator` on both operands and stores result in <expr>
    FIND(Collections.singletonList(PrimitiveType.POINTER)), // sets <ref> to resolved value of pointer. Based on settings, `null` if it doesn't exist or result in error.
    RETURN(Collections.singletonList(PrimitiveType.ADDRESS_OR_LITERAL)), // sets <ret> to given parameter
    JUMP(Collections.singletonList(PrimitiveType.ADDRESS)), // moves instruction address to given address
    DECLARE(Arrays.asList(PrimitiveType.POINTER, PrimitiveType.OTHER)), // instructs memory manager to create entry for first parameter, of type, parameter 2
    SET(Arrays.asList(PrimitiveType.POINTER, PrimitiveType.LITERAL)), // instructs changes the value at a given address, optionally moves information if necessary and updates memory manager
    PUSH(Collections.singletonList(PrimitiveType.POINTER)), // adds parameter 1 to stack
    POP(Collections.emptyList()), // removes top item from stack and moves it onto <loc>
    COMPARE(Arrays.asList(PrimitiveType.LITERAL, PrimitiveType.LITERAL)); // compares two numbers and stores comparison result in <comp>

    public List<PrimitiveType> parameters;
    InstructionType(List<PrimitiveType> parameters) {
        this.parameters = parameters;
    }
}
