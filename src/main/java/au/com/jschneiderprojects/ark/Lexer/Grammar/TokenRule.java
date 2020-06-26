package au.com.jschneiderprojects.ark.Lexer.Grammar;

import au.com.jschneiderprojects.ark.Lexer.Token;

import java.util.ArrayList;

class TokenRule {
    public TokenType type;
    Tuple<Character, Character> delimiter;

    TokenRule(TokenType type) {
        this.type = type;
    }

    TokenRule(TokenType type, char delimiter) {
        this.type = type;
        this.delimiter = new Tuple<>(delimiter, delimiter);
    }

    TokenRule(TokenType type, Tuple<Character, Character> delimiters) {
        this.type = type;
        this.delimiter = delimiters;
    }

    boolean matches(String characters, ArrayList<Token> prev) {
        return false;
    }

    boolean isWrappedByDelimiter(String characters) { // helper functions
        if (characters.length() > 0)
            return characters.charAt(0) == this.delimiter.x && characters.charAt(characters.length() - 1) == this.delimiter.y;
        return false;
    }

    boolean isDelimiterOpen(String characters) {
        if (delimiter != null)
            return characters.indexOf(delimiter.x) >= 0;
        return false;
    }

    boolean isDelimiterClosed(String characters) {
        if (delimiter != null)
            return characters.indexOf(delimiter.x) < characters.lastIndexOf(delimiter.y) && characters.indexOf(delimiter.x) > -1;
        return false;
    }
}
