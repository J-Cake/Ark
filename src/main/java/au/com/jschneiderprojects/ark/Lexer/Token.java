package au.com.jschneiderprojects.ark.Lexer;

import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;

public class Token {
    public TokenType type;
    public String source;
    public Origin origin;

    public Token(TokenType type, String source, Origin origin) {
        this.type = type;
        this.source = source;
        this.origin = origin;
    }

    public String toString() {
        return (this.type == null ? "<UNKNOWN>" : this.type.toString()) + " (" + (this.source.indexOf("\n") > 0 ? " ... " : this.source) + ")";
    }

    public String getValue() {
        return this.source;
    }
}
