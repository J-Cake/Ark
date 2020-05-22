package au.com.jschneiderprojects.ark.Lexer;

import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;

public class Token {
    TokenType type;
    String source;
    Origin origin;
    int indentationLevel;

    public Token(TokenType type, String source, Origin origin, int indentationLevel) {
        this.type = type;
        this.source = source;
        this.origin = origin;
        this.indentationLevel = indentationLevel;
    }
}
