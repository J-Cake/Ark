package au.com.jschneiderprojects.ark.Compiler;

import au.com.jschneiderprojects.ark.Executer.Expression;
import au.com.jschneiderprojects.ark.Executer.Operators;
import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;
import au.com.jschneiderprojects.ark.Lexer.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class PrimitiveExpression extends Primitive {

    Expression expression;

    public PrimitiveExpression(Expression derivative) {
        super(PrimitiveType.LITERAL);

        this.expression = derivative;
    }

    public ArrayList<Instruction> convert() {
        ArrayList<Instruction> expression = new ArrayList<>();

        Stack<Primitive> stack = new Stack<>();

        for (Token t : this.expression.tokens) {
            if (t.type == TokenType.Int || t.type == TokenType.Float || t.type == TokenType.Boolean || t.type == TokenType.String)
                stack.push(Value.fromToken(t));
            else if (t.type == TokenType.Operator) {
                expression.add(new Instruction(InstructionType.EVALUATE, Arrays.asList(stack.pop(), stack.pop(), new PrimitiveOperator(Operators.find(t.source)))));
                stack.push(new Primitive(PrimitiveVar.EVALUATED) {});
                // TODO: Expression conversion has begun. handle invocations and references for full experience and make primitive types record their value.
            } // else handle invocations and references
        }

        return expression;
    }
}
