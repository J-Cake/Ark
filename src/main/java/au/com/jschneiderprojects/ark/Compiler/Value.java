package au.com.jschneiderprojects.ark.Compiler;

import au.com.jschneiderprojects.ark.Compiler.Types.Boolean;
import au.com.jschneiderprojects.ark.Compiler.Types.Float;
import au.com.jschneiderprojects.ark.Compiler.Types.Int;
import au.com.jschneiderprojects.ark.Compiler.Types.String;
import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;
import au.com.jschneiderprojects.ark.Lexer.Token;

public class Value extends Primitive {
    public Value() {
        super(PrimitiveType.LITERAL);
    }

    public static Value fromToken(Token t) {
        if (t.type == TokenType.Int)
            return new Int(); // assign value of `t` here
        else if (t.type == TokenType.Float)
            return new Float();
        else if (t.type == TokenType.String)
            return new String();
        else if (t.type == TokenType.Boolean)
            return new Boolean();
        else return null;
    }
}
