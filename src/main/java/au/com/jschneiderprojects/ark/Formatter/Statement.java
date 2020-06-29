package au.com.jschneiderprojects.ark.Formatter;

import au.com.jschneiderprojects.ark.Executer.Construct;
import au.com.jschneiderprojects.ark.Executer.Expression;
import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;
import au.com.jschneiderprojects.ark.Lexer.Origin;
import au.com.jschneiderprojects.ark.Lexer.Token;
import au.com.jschneiderprojects.ark.Log;

import java.util.ArrayList;
import java.util.Stack;

public class Statement {

    ArrayList<Expression> conditions;
    Construct construct;
    Block block;
    Origin origin;

    public Statement(Construct construct, ArrayList<Token> body, Origin origin) {
        this.conditions = new ArrayList<>();
        this.conditions.addAll(postfix(body));

        this.construct = construct;
        this.origin = origin;
    }

    ArrayList<Expression> postfix(ArrayList<Token> tokens) {
        ArrayList<Expression> arguments = new ArrayList<>();

        ArrayList<Token> expression = new ArrayList<>();

        for (Token t : tokens)
            if (t.type == TokenType.Comma) {
                if (expression.size() > 0)
                    arguments.add(new Expression(expression));
                expression.clear();
            } else
                expression.add(t);

        if (expression.size() > 0)
            arguments.add(new Expression(expression));

        return arguments;
    }

    public String toString() {
        StringBuilder b = new StringBuilder();

        for (Expression s : this.conditions)
            b.append(s.toString()).append(", ");

        return b.toString();
    }

    public void print() {
        for (Expression condition : this.conditions)
            Log.i(condition.toString() + ", ");
    }

    private ArrayList<ArrayList<Token>> constructArguments(ArrayList<Token> condition) {
        ArrayList<ArrayList<Token>> arguments = new ArrayList<>();

        ArrayList<Token> argument = new ArrayList<>();
        for (Token t : condition) {
            if (t.type == TokenType.Comma) {
                arguments.add(argument);
                argument.clear();
            } else
                argument.add(t);
        }

        return arguments;
    }
}
