package au.com.jschneiderprojects.ark.Formatter;

import au.com.jschneiderprojects.ark.Executer.Expression;
import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;
import au.com.jschneiderprojects.ark.Lexer.Origin;
import au.com.jschneiderprojects.ark.Lexer.Token;

import java.util.ArrayList;
import java.util.Objects;

public class Assignment extends Statement {

    public ArrayList<Token> lhs;
    public Expression rhs;

    public Assignment(ArrayList<Token> body, Origin origin) {
        super(null, body, origin);
        this.assign(body);
    }

    private void assign(ArrayList<Token> body) {
        ArrayList<Token> lhs = new ArrayList<>();
        ArrayList<Token> rhs = null;

        for (Token t : body)
            if (t.type != TokenType.Assignment)
                Objects.requireNonNullElse(rhs, lhs).add(t);
            else
                rhs = new ArrayList<>();

        this.lhs = lhs;
        this.rhs = new Expression(rhs);
    }

    static boolean matches(ArrayList<Token> body) {
        TokenType[] firstTwo = new TokenType[]{TokenType.Reference, TokenType.Assignment};
        TokenType[] firstThree = new TokenType[]{TokenType.Reference, TokenType.Reference, TokenType.Assignment};

        return (body.size() >= 2) && (firstTwo[0] == body.get(0).type && firstTwo[1] == body.get(1).type)
                || (firstThree[0] == body.get(0).type && firstThree[1] == body.get(1).type
                && firstThree[2] == body.get(2).type);
    }

    public boolean shouldDeclare() {
        return this.rhs.tokens.size() == 2;
    }
}
