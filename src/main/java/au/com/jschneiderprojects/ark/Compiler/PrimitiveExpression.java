package au.com.jschneiderprojects.ark.Compiler;

import au.com.jschneiderprojects.ark.Executer.Expression;

public class PrimitiveExpression extends Primitive {

    Expression expression;

    public PrimitiveExpression(Expression derivative) {
        this.expression = derivative;
    }
}
