package au.com.jschneiderprojects.ark.Formatter;

import au.com.jschneiderprojects.ark.Lexer.Grammar.TokenType;
import au.com.jschneiderprojects.ark.Lexer.Token;

public class ParseToken {
    TokenType type;
    Token origin;

    ParseToken(TokenType type, Token origin) {
        this.type = type;
        this.origin = origin;
    }
}
