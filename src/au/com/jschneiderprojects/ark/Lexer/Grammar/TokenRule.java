package au.com.jschneiderprojects.ark.Lexer.Grammar;

import au.com.jschneiderprojects.ark.Lexer.Token;

import java.util.ArrayList;

class TokenRule {
    public TokenType type;
    Tuple<Character, Character> delimiter;
    int delimiterCount; // used for bracket matching

    TokenRule(TokenType type) {
        this.type = type;
        this.delimiterCount = 0;
    }

    TokenRule(TokenType type, char delimiter) {
        this.type = type;
        this.delimiter = new Tuple<>(delimiter, delimiter);
        this.delimiterCount = 0;
    }

    TokenRule(TokenType type, Tuple<Character, Character> delimiters) {
        this.type = type;
        this.delimiter = delimiters;
        this.delimiterCount = 0;
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
            return characters.indexOf(delimiter.x) == characters.lastIndexOf(delimiter.y) && characters.indexOf(delimiter.x) > -1;
        return false;
//        if (characters.length() > 0)
//            if (delimiter != null)
//                if (characters.length() - 1 == 0)
//                    return characters.charAt(0) == this.delimiter.x;
//                else
//                    return characters.charAt(0) == this.delimiter.x && characters.charAt(characters.length() - 1) != this.delimiter.y;
//        return false;
    }

    boolean isDelimiterClosed(String characters) {
        if (delimiter != null)
            return characters.indexOf(delimiter.x) < characters.lastIndexOf(delimiter.y) && characters.indexOf(delimiter.x) > -1;
        return false;
//        if (characters.length() > 0)
//            if (delimiter != null)
//                if (characters.length() - 1 != 0)
//                    return characters.charAt(characters.length() - 1) == this.delimiter.y;
//                else
//                    return false;
//            else
//                return true;
//        else
//            return false;
    }
}
