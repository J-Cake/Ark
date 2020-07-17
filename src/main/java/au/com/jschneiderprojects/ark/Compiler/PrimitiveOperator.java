package au.com.jschneiderprojects.ark.Compiler;

import au.com.jschneiderprojects.ark.Executer.Operators;

public class PrimitiveOperator extends Primitive {
    Operators operator;

    PrimitiveOperator(Operators operator) {
        super(PrimitiveType.OPERATOR);
        this.operator = operator;
    }
}
