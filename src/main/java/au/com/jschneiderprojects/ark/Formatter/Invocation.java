package au.com.jschneiderprojects.ark.Formatter;

import au.com.jschneiderprojects.ark.Executer.Expression;
import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;
import au.com.jschneiderprojects.ark.Lexer.Token;

import java.util.ArrayList;

public class Invocation extends Token {

    Reference reference;
    public ArrayList<Expression> parameters;

    Invocation(Reference object, ArrayList<Token> parameters) {
        super(TokenType.Invocation, Reference.stringify(parameters), parameters.size() == 0 ? object.origin : parameters.get(0).origin);

        this.reference = object;
        this.parameters = this.parameterise(parameters);
    }

    private ArrayList<Expression> parameterise(ArrayList<Token> tokens) {
        ArrayList<Expression> parameters = new ArrayList<>();

        ArrayList<Token> parameter = new ArrayList<>();
        for (Token t : tokens) {
            if (t.type == TokenType.Comma) {
                parameters.add(new Expression(parameter));
                parameter = new ArrayList<>();
            } else
                parameter.add(t);
        }

        if (parameter.size() > 0)
            parameters.add(new Expression(parameter));

        return parameters;
    }
}
