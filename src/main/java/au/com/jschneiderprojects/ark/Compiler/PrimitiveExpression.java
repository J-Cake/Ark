package au.com.jschneiderprojects.ark.Compiler;

import au.com.jschneiderprojects.ark.Executer.Expression;
import au.com.jschneiderprojects.ark.Executer.Operators;
import au.com.jschneiderprojects.ark.Formatter.Invocation;
import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;
import au.com.jschneiderprojects.ark.Lexer.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

            } else if (t.type == TokenType.Reference) { // else handle invocations and references
                expression.add(new Instruction(InstructionType.FIND, Collections.singletonList(new Pointer(t.source))));
                stack.push(new Primitive(PrimitiveVar.REFERENCE) {});

            } else if (t.type == TokenType.Invocation) {
                Invocation invocation = (Invocation)t;

                for (Expression expr : invocation.parameters) {
                    PrimitiveExpression p = new PrimitiveExpression(expr);

                    expression.addAll(p.convert());

                    // TODO: Compare signatures and throw error if they don't match. if they do, declare all parameters of signature as variables

                    // once expression has been evaluated, assign the nth parameter of the function to <expr>
                    // 1. look up function signature
                    // 2. find variable name
                    // 3. declare var, type
                    // 4. set var <expr>
                }

                stack.push(new Primitive(PrimitiveVar.RETURNED){});
            }
        }

        return expression;
    }
}
