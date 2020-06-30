package au.com.jschneiderprojects.ark.Executer;

import au.com.jschneiderprojects.ark.Formatter.Invocation;
import au.com.jschneiderprojects.ark.Formatter.Operators;
import au.com.jschneiderprojects.ark.Formatter.Reference;
import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;
import au.com.jschneiderprojects.ark.Lexer.Origin;
import au.com.jschneiderprojects.ark.Lexer.Token;
import au.com.jschneiderprojects.ark.Log;

import java.util.ArrayList;
import java.util.Stack;

public class Expression {

    ArrayList<Token> tokens;
    Origin origin;

    public Expression(ArrayList<Token> tokens) {
        this.tokens = shuntingYard(tokens);
        this.origin = tokens.get(0).origin;
    }

    private boolean hasHigherPrecedence(Token operand1, Token operand2) {
        if (Operators.exists(operand1.source) && Operators.exists(operand2.source))
            try {
                return Operators.getPrecedence(operand1.source.trim()) > Operators.getPrecedence(operand2.source.trim());
            } catch (Exception e) {
                return false;
            }
        return false;
    }

    private boolean hasEqualPrecedence(Token operand1, Token operand2) {
        if (Operators.exists(operand1.source) && Operators.exists(operand2.source))
            try {
                return Operators.getPrecedence(operand1.source.trim()) == Operators.getPrecedence(operand2.source.trim());
            } catch (Exception e) {
                return false;
            }
        return false;
    }

    private boolean isLeftAssociative(Token operand1) {
        if (Operators.exists(operand1.source))
            try {
                return Operators.getAssociativity(operand1.source.trim()) == Operators.LEFT_ASSOCIATIVE;
            } catch (Exception e) {
                return false;
            }
        return false;
    }

    private ArrayList<Token> shuntingYard(ArrayList<Token> tokens) {
        Stack<Token> opStack = new Stack<>();
        ArrayList<Token> output = new ArrayList<>();

        for (Token t : tokens) {
            if (t.type == TokenType.LeftParenthesis)
                opStack.push(t);
            else if (t.type == TokenType.RightParenthesis) {
                while (opStack.peek().type != TokenType.LeftParenthesis)
                    output.add(opStack.pop());

                if (opStack.peek().type == TokenType.LeftParenthesis)
                    opStack.pop();
            } else if (t.type == TokenType.Operator) {
                if (!opStack.isEmpty())
                    while (!opStack.isEmpty() &&
                            (hasHigherPrecedence(t, opStack.peek()) ||
                            (hasEqualPrecedence(t, opStack.peek()) && isLeftAssociative(t)) &&
                                    opStack.peek().type != TokenType.LeftParenthesis))
                        output.add(opStack.pop());
                opStack.push(t);
            } else if (t.type == TokenType.Int || t.type == TokenType.Float || t instanceof Reference || t instanceof Invocation)
                output.add(t);
        }
        // Handle all edge cases, perhaps with error

        while (opStack.size() > 0)
            output.add(opStack.pop());
        // The `Token is a function call` node doesn't refer to the function invocation, but rather the resolved type of the reference.
        // The parameters of the function are simply comma separated.

        return output;
    }

    public String toString() {
        StringBuilder b = new StringBuilder();

        for (Token t : this.shuntingYard(tokens))
            b.append(t.toString()).append(" ");

        return b.toString().trim();
    }
}
