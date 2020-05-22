package au.com.jschneiderprojects.ark.Lexer;

import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;

public class Token {
    public TokenType type;
    public String source;
    public Origin origin;
    public int indentationLevel;

    public Token(TokenType type, String source, Origin origin, int indentationLevel) {
        this.type = type;
        this.source = source;
        this.origin = origin;
        this.indentationLevel = indentationLevel;
    }
}
