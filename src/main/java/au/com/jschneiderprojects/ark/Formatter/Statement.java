package au.com.jschneiderprojects.ark.Formatter;

import au.com.jschneiderprojects.ark.Executer.Construct;
import au.com.jschneiderprojects.ark.Executer.Expression;
import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;
import au.com.jschneiderprojects.ark.Lexer.Origin;
import au.com.jschneiderprojects.ark.Lexer.Token;

import java.util.ArrayList;

public class Statement {

    public ArrayList<Expression> conditions;
    public ArrayList<Token> preConstruct;
    public Construct construct;
    public Block block;
    Origin origin;

    public Statement(Construct construct, ArrayList<Token> body, Origin origin) {
        this.conditions = new ArrayList<>();
        this.conditions.addAll(postfix(body));

        this.construct = construct;
        this.origin = origin;
    }

    public Statement(Construct construct, ArrayList<Token> preConstruct, ArrayList<Token> body, Origin origin) {
        this.conditions = new ArrayList<>();
        this.conditions.addAll(postfix(body));

        this.preConstruct = preConstruct;

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

        if (this.construct != null) {
            if (this.preConstruct != null && this.preConstruct.size() > 0) {
                String preConstructString = this.preConstruct.toString();
                b.append("{").append(preConstructString, 1, preConstructString.length() - 1).append("} ");
            }

            b.append(this.construct.toString().toUpperCase()).append(" {");
        }

        for (Expression s : this.conditions)
            b.append(s.toString()).append(", ");

        if (b.length() >= 2) {
            b.deleteCharAt(b.length() - 1);
            b.deleteCharAt(b.length() - 1);
        }

        if (this.construct != null)
            b.append("}");

        return b.toString();
    }
}
